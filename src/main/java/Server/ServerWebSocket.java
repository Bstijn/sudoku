package Server;

import Logic.Cell;
import RestServer.GeneratorRequester;
import Shared.Lobby;
import Shared.Player;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;

@ServerEndpoint(value = "/sudoku")
public class ServerWebSocket {
    private static final ArrayList<Player> players = new ArrayList<>();
    private static final ArrayList<ILobbyServer> lobbies = new ArrayList<>();
    private static int nextLobbyid = 0;
    private static final GeneratorRequester requester = new GeneratorRequester();

    @OnOpen
    public void onConnect(Session session) {
        players.add(new Player(session));
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "RequestLobbies")) {
            synchronized (lobbies) {
                sendinitLobbies(session);
            }
        } else if (keyInJson(json, "CreateLobby")) {
            createLobby(json, session, getNextLobbyId());
        } else if (keyInJson(json, "Join")) {
            joinLobby(session, json);
        }
        else if(keyInJson(json,"Fill")){
            sendFillToOthers(session,json.get("Fill").getAsInt(),json.get("Cell").getAsJsonObject());
        }
    }

    private void joinLobby(Session session, JsonObject json) {
        IPlayerServer curplayer = getPlayer(session);
        int id = json.get("Id").getAsInt();
        ILobbyServer lobby = getLobbyFromId(id);
        lobby.addPlayer(curplayer);
        sendJoinConfirm(lobby,curplayer);
    }

    private ILobbyServer getLobbyFromId(int id) {
        for (ILobbyServer l : lobbies) {
            if (l.getId() == id) {
                return l;
            }
        }
        return null;
    }

    //broadcast to all initlobbies
    private void createLobby(JsonObject json, Session session,int id) {
        ILobbyServer lobby = null;
        ISudokuServer sudoku = requester.requestSudoku();
        if(json.get("Mode").getAsString().equals("Solo")){
            lobby = new Lobby("null","null",id,1);
        }
        else if(json.get("Mode").getAsString().equals("P")){
            lobby = new Lobby(json.get("Name").getAsString(),"null",id,1);
        }
        else if (json.get("Mode").getAsString().equals("PW")){
            lobby = new Lobby(json.get("Name").getAsString(),json.get("Password").getAsString(),id,1);
        }
        lobby.addPlayer(getPlayer(session));
        synchronized (lobbies) {
            lobby.addSudoku(sudoku);
            lobbies.add(lobby);
        }
        updateLobbies();
        sendJoinConfirm(lobby,getPlayer(session));
    }

    private void sendJoinConfirm(ILobbyServer lobby,IPlayerServer player) {
        JsonObject json = new JsonObject();
        json.addProperty("Join",true);
        json.addProperty("Id",lobby.getId());
        json.add("GRID",lobby.getSudoku().toJsonArray());
        lobby.getId();
        try {
            player.getSession().getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void updateLobbies() {
        JsonObject json = createLobbyString();
        broadcast(json.toString());

    }



    private static int getNextLobbyId(){
        nextLobbyid++;
        return nextLobbyid;
    }

    private IPlayerServer getPlayer(Session session){
        synchronized (players) {
            for (IPlayerServer p : players) {
                if (p.getSession() == session) {
                    return p;
                }
            }
        }
        return null;
    }

    private void sendinitLobbies(Session session) {
        JsonObject json = createLobbyString();
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private JsonObject createLobbyString(){
        JsonObject json = new JsonObject();
        int i = 1;
        synchronized (lobbies) {
            if (lobbies.size() == 0) {
                json.addProperty("InitLobbies", false);
            } else {
                json.addProperty("InitLobbies", false);
            }
            for (ILobbyServer l : lobbies) {
                json.addProperty("Name" + i, l.getName());
                if (l.getPassword() == null) {
                    json.addProperty("Password" + i, "null");
                } else {
                    json.addProperty("Password" + i, l.getPassword());
                }
                json.addProperty("Id" + i, l.getId());
                json.addProperty("CurPlayers" + i, l.getCurrentPlayers());
                System.out.println(i);
                i++;
            }
        }
        return json;
    }

    private boolean keyInJson(JsonObject json, String key){
        try {
            return json.has(key);
        } catch (NullPointerException ex){
            return false;
        }
    }

    @OnClose
    public void onClose(Session session) {
        Player removingplayer = null;
        synchronized (players) {
            for (Player p : players) {
                if (p.getSession() == session) {
                    removingplayer = p;
                    break;
                }
            }

            players.remove(removingplayer);
        }
        synchronized (lobbies){
            for (ILobbyServer l : lobbies){
                if(l.getplayers().contains(removingplayer)){
                    l.remove(removingplayer);
                }
            }
        }
        broadcast(createLobbyString().toString());
        System.out.println("Server Close");

    }

    public void sendFillToOthers(Session session,int Number, JsonObject cellJson) {
        IPlayerServer player = getPlayer(session);
        ILobbyServer lobby = null;
        for(ILobbyServer l : lobbies){
            if(l.getplayers().contains(player)){
                lobby = l;
                break;
            }
        }
        Cell cell = getCellFromJson(lobby,cellJson);
        if(lobby.getSudoku().filCell(Number,cell)) {
            JsonObject json = new JsonParser().parse(new Gson().toJson(cell)).getAsJsonObject();
            json.addProperty("Fill",true);
            for (IPlayerServer p : lobby.getplayers()) {
                try {
                    p.getSession().getBasicRemote().sendText(json.toString());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else{
            sendInvalidToFiller(session);
        }
    }

    private Cell getCellFromJson(ILobbyServer lobby, JsonObject cellJson) {
        Cell[][] grid=lobby.getSudoku().getCells();
        Cell cell = null;
        for (Cell[] cs : grid){
            for(Cell c : cs){
                if(c.getPosX() == cellJson.get("posX").getAsInt() && c.getPosY() == cellJson.get("posY").getAsInt()){
                    cell = c;
                    return cell;
                }
            }
        }
        return null;
    }


    public void sendInvalidToFiller(Session session) {
        JsonObject json = new JsonObject();
        json.addProperty("Wrong",true);
        try {
            session.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void broadcast(String s) {
        synchronized (players) {
            for (Player p : players) {
                try {
                    p.getSession().getBasicRemote().sendText(s);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
}
