package ClientWS;

import Logic.Cell;

public interface IClientPlayer {
    /**
     * method used for requesting lobbie update.
     */
    void requestLobbies();

    /**
     * creates a lobby for solo
     */
    void createLobby();

    /**
     * creates a lobby for public
     * @param name name the lobby will have
     */
    void createLobby(String name);

    /**
     * creates a lobby for public with password
     * @param name
     * @param password
     */
    void createLobby(String name, String password);

    /**
     * used for sending a message to server that tells how to fill cell
     * @param nr
     * @param selectedCell
     */
    void sendFill(int nr, Cell selectedCell);

    /**
     * used for sending message to server to join a lobby
     * @param id the id of the lobby you want to join.
     */
    void joinLobby(int id);
}
