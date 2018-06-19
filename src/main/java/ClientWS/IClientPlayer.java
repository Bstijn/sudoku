package ClientWS;

import Logic.Cell;

public interface IClientPlayer {
    void requestLobbies();

    void createLobby();

    void createLobby(String name);

    void createLobby(String name, String password);

    void sendFill(int nr, Cell selectedCell);

    void joinLobby(int id);
}
