package Logic;

import Shared.Lobby;

import java.util.ArrayList;

public interface HomeAble {
    /**
     * @exception will throw exception when connection to server fails.
     * //TODO @Stijn Give option to play solo.
     */
    void initLobbies(ArrayList<Lobby> lobbies) throws ConnectionException;
    void addLobbies(ArrayList<Lobby> newLobbies);
    void addLobbies(Lobby newLobby);
}
