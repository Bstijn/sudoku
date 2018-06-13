package Client;

import javafx.scene.control.TextField;

public interface IPlayerClient {
   void getLobbiesFromServer();

    void createLobby();
    void createLobby(String text);
    void createLobby(String name, String password);
}
