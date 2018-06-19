package ClientWS;

import Logic.Cell;

import java.util.ArrayList;

public interface IplayerSocket {
    boolean isInLobby();
    void updateLobbies(ArrayList<ILobbyClient> lobbies);

    void updateSudoku(Cell[][] grid);

    void filledWrong();
    void fillCell(Cell cell);
}
