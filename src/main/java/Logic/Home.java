package Logic;

import Shared.Lobby;
import GUI.StartScherm;

import java.util.ArrayList;

public class Home implements HomeAble {
    private StartScherm gui;

    public Home(StartScherm gui) {
        this.gui = gui;
    }


    @Override
    public void initLobbies(ArrayList<Lobby> lobbies) {

    }

    @Override
    public void addLobbies(ArrayList<Lobby> newLobbies) {

    }

    @Override
    public void addLobbies(Lobby newLobby) {

    }
}
