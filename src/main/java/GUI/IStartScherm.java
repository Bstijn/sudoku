package GUI;

import ClientWS.ILobbyClient;

import java.util.ArrayList;

public interface IStartScherm {
    /**
     * method that can be called for updating the lobbies in the view
     * @param lobbies
     */
    void updateLobbies(ArrayList<ILobbyClient> lobbies);
}
