package ClientWS;

import Logic.Cell;
import Shared.Lobby;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

@ClientEndpoint
public class ClientWebSocket implements IClientSocket,IClientPlayer {
    private Session server;
    private Logger logger = Logger.getLogger(ClientWebSocket.class.getName());
    private ArrayList<ILobbyClient> lobbies = new ArrayList<>();
    private IplayerSocket player;
    public ClientWebSocket(IplayerSocket player) {
        this.player = player;
        URI uri = URI.create("ws://localhost:8090/sudoku");
        try {
            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to server
            server = container.connectToServer(this, uri);
        } catch (Exception e) {
            logger.log(Level.WARNING, e.getMessage());
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        server = session;
        System.out.println("connect to server");
    }
    //TODO IMPLEMENT ALL THE KINDS OF RECIEVING
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println(message);
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if(keyInJson(json,"InitLobbies")){
            fillInitLobbies(json);
        }
        else if(keyInJson(json,"Join")){
            fillSudoku(json);
        }
        else if(keyInJson(json,"Wrong")){
            player.filledWrong();
        }
        else if(keyInJson(json,"Fill")){
            Cell cell = new Cell(json);
            player.fillCell(cell);
        }
    }

    private void fillSudoku(JsonObject json) {
        Cell[][] grid = new Cell[9][9];
        for (int i =0; i<81;i++) {
            Cell c = new Cell(json.get("GRID").getAsJsonArray().get(i).getAsJsonObject());
            grid[c.getPosY()][c.getPosX()] = c;
        }
    player.updateSudoku(grid);
    }

    private void fillInitLobbies(JsonObject json) {
        boolean nextlobbie = true;
        int i = 1;
        lobbies.clear();
        while(nextlobbie){
            if(json.get("Name"+i) == null){
                nextlobbie = false;
                break;
            }
            else{
                String password;
                password = json.get("Password"+i).getAsString();
                String name = json.get("Name"+i).getAsString();
                int id = json.get("Id"+i).getAsInt();
                int curAmountPlayers = json.get("CurPlayers"+i).getAsInt();
                ILobbyClient lobby = new Lobby(name,password,id,curAmountPlayers);
                synchronized (lobbies) {
                    lobbies.add(lobby);
                }
                i++;
            }
        }
        player.updateLobbies(lobbies);


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
        System.out.println("Server Close");

    }

    @Override
    public void requestLobbies() {
        JsonObject json = new JsonObject();
        json.addProperty("RequestLobbies",true);
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createLobby() {
        JsonObject json = new JsonObject();
        json.addProperty("CreateLobby",true);
        json.addProperty("Mode","Solo");
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createLobby(String name) {
        JsonObject json = new JsonObject();
        json.addProperty("CreateLobby",true);
        json.addProperty("Mode","P");
        json.addProperty("Name",name);

        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createLobby(String name, String password) {
        JsonObject json = new JsonObject();
        json.addProperty("CreateLobby",true);
        json.addProperty("Mode","PW");
        json.addProperty("Name",name);
        json.addProperty("Password",password);
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendFill(int nr, Cell selectedCell) {
        JsonObject json = new JsonObject();
        json.addProperty("Fill",nr);
        json.add("Cell",new JsonParser().parse(new Gson().toJson(selectedCell)).getAsJsonObject());
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void joinLobby(int id) {
        JsonObject json = new JsonObject();
        json.addProperty("Join",true);
        json.addProperty("Id",id);
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
