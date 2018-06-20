package ClientWS;

import Logic.Cell;

import java.util.ArrayList;

public interface IplayerSocket {
    /**
     * will return value of the boolean inLobby
     * @return inLobby
     */
    boolean isInLobby();

    /**
     * used for giving the player lobbies
     * @param lobbies recieved from client
     */
    void updateLobbies(ArrayList<ILobbyClient> lobbies);

    /**
     * method used for giving the player a grid of the sudoku
     * @param grid sudoku grid
     */
    void updateSudoku(Cell[][] grid);

    /**
     * method used for telling the player the cell he choices to fill was wrong
     */
    void filledWrong();

    /**
     * method used for filling a cell in the sudoku of the player
     * @param cell current cell that is being filled WILL NOT BE THE SAME OBJECT AS THE PLAYER'S SUDOKU CELLS
     */
    void fillCell(Cell cell);
}
