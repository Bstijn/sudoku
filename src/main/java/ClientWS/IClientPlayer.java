package ClientWS;

public interface IClientPlayer {
    void requestLobbies();

    void createLobby();

    void createLobby(String name);

    void createLobby(String name, String password);
}
