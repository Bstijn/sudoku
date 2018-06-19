package Logic;

import com.google.gson.JsonArray;

public interface ISudoku {
    /**
     * method that will fill every cell
     * @param difficulty will indicate how many cells will be removed from the sudoku has a maximum
     * @return will return a grid of cells .
     */
    Cell[][] start(int difficulty)throws GenerationFaultException;

    Cell[][] getCells();

    boolean filCell(int nr, Cell selectedcell);

    JsonArray toJsonArray();
}
