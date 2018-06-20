package Server;

import javax.websocket.Session;

public interface IPlayerServer {
    /***
     * Will name the client in the  server if the player chooses to name it self before joining lobby.
     * @param name
     */
    void namePlayer(String name);

    /**
     * will return the session that belongs to the player.
     * @return
     */
    Session getSession();
}
