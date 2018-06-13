package GUI;

import ClientWS.ILobbyClient;

import java.util.ArrayList;

public interface IStartScherm {
    void updateLobbies(ArrayList<ILobbyClient> lobbies);
}
