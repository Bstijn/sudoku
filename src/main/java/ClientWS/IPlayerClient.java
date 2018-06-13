package ClientWS;

public interface IPlayerClient {
   void getLobbiesFromServer();

    void createLobby();
    void createLobby(String text);
    void createLobby(String name, String password);
}
