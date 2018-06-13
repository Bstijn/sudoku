package Shared;

import ClientWS.*;
import GUI.Controller;
import GUI.IStartScherm;
import Server.IPlayerServer;

import javax.websocket.Session;
import java.util.ArrayList;

public class Player implements IPlayerClient, IPlayerServer, IplayerSocket {

    private Session session;
    IClientPlayer client;
    private String name;
    private IStartScherm startScherm;
    public Player(Session session){
        this.session = session;
    }
    private Controller controller;

    public Player(Controller controller) {
        this.controller = controller;
        if (this.client == null) {
            this.client = new ClientWebSocket((IplayerSocket) this);
        }
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
        client.createLobby();
    }

    @Override
    public void createLobby(String name) {
        client.createLobby(name);
    }

    @Override
    public void createLobby(String name, String password) {
        client.createLobby(name,password);
    }

    @Override
    public void updateLobbies(ArrayList<ILobbyClient> lobbies) {
        startScherm.updateLobbies(lobbies);
    }
}
