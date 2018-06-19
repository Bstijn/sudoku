package Shared;

import ClientWS.*;
import GUI.Controller;
import GUI.IStartScherm;
import GUI.ISudokuApp;
import Logic.Cell;
import Server.IPlayerServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player implements IPlayerClient, IPlayerServer, IplayerSocket {

    private Session session;
    IClientPlayer client;
    private String name;
    private static IStartScherm startScherm;
    private static ISudokuApp guiGame;
    public Player(Session session){
        this.session = session;
    }
    private Controller controller;
    private boolean inLobby = false;

    public Player(Controller controller) {
        this.controller = controller;
        if (this.client == null) {
            this.client = new ClientWebSocket((IplayerSocket) this);
        }
    }
    @Override
    public void setGuiGame(ISudokuApp guiGame) {
        this.guiGame = guiGame;
    }

    @Override
    public void fillCell(int nr, Cell selectedCell) {
        this.client.sendFill(nr,selectedCell);
    }

    @Override
    public void joinLobby(int id) {
        this.client.joinLobby(id);
    }

    public Player(String name){
        this.name = name;
    }

    public Session getSession() {
        return session;
    }

    public String getName() {
        return name;
    }

    public void namePlayer(String name){
        this.name = name;
    }

    @Override
    public void getLobbiesFromServer() {
        client.requestLobbies();
    }

    @Override
    public void createLobby() {
        inLobby = true;
        client.createLobby();
    }

    @Override
    public void createLobby(String name) {
        inLobby = true;
        client.createLobby(name);
    }

    @Override
    public void createLobby(String name, String password) {
        inLobby = true;
        client.createLobby(name,password);
    }

    @Override
    public void setIstartscherm(IStartScherm me) {
        this.startScherm = me;
    }

    @Override
    public boolean isInLobby() {
        return inLobby;
    }

    @Override
    public void updateLobbies(ArrayList<ILobbyClient> lobbies) {
        if (!inLobby) {
            startScherm.updateLobbies(lobbies);
        }
    }

    @Override
    public void updateSudoku(Cell[][] grid) {
        guiGame.fillInSquares(grid);
    }

    @Override
    public void filledWrong() {
        guiGame.filledWrongNumber();
    }
    @Override
    public void fillCell(Cell cell){
        guiGame.fillCell(cell);
    }
}
