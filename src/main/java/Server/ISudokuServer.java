package Server;

import Logic.Cell;
import Logic.GenerationFaultException;
import com.google.gson.JsonArray;

public interface ISudokuServer {
    /**
     * method used for generating the sudoku
     * @param difficulty how many fillableByPlayer cells u want to have in your sudoku
     * @return grid
     * @throws GenerationFaultException when the generating goes wrong
     */
    Cell[][] start(int difficulty ) throws GenerationFaultException;

    /**
     * getter for the grid of the sudoku
     * @return grid of the sudoku
     */
    Cell[][] getCells();

    /**
     * will check if a cell can be filled by nr
     * @param nr u want to fill the cell with
     * @param selectedcell the cell u want to fill
     * @return if it can ban filled by the nr.
     */
    boolean filCell(int nr, Cell selectedcell);
    /**
     * will generate the Cell[][] in Json Array is used for sending messages over REST and Websockets
     * @return Cell[][] of sudoku in Json Array.
     */
    JsonArray toJsonArray();
}
