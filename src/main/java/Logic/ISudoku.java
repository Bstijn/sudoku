package Logic;

import com.google.gson.JsonArray;

public interface ISudoku {
    /**
     * method that will fill every cell
     * @param difficulty will indicate how many cells will be removed from the sudoku has a maximum
     * @return will return a grid of cells .
     */
    Cell[][] start(int difficulty)throws GenerationFaultException;

    /**
     * will return the grid of the sudoku
     * @return grid
     */
    Cell[][] getCells();

    /**
     * will give feedback on the selected cell that u want to fill with a number
     * @param nr limit 1-9
     * @param selectedcell the cell u want to fill with nr
     * @return
     */
    boolean filCell(int nr, Cell selectedcell);

    /**
     * will generate the Cell[][] in Json Array is used for sending messages over REST and Websockets
     * @return Cell[][] of sudoku in Json Array.
     */
    JsonArray toJsonArray();
}
