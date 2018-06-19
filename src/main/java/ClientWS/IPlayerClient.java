package ClientWS;

import GUI.IStartScherm;
import GUI.ISudokuApp;
import Logic.Cell;

public interface IPlayerClient {
   void getLobbiesFromServer();

    void createLobby();
    void createLobby(String text);
    void createLobby(String name, String password);

    void setIstartscherm(IStartScherm me);
    void setGuiGame(ISudokuApp guiGame);

    void fillCell(int nr, Cell selectedCell);

    void joinLobby(int id);
}
