package GUI;

import Logic.Cell;

public interface ISudokuApp {
    /**
     * method used for updating the grid in the view
     * @param grid
     */
    void fillInSquares(Cell[][] grid);

    /**
     * method used for giving feedback that a number has been filled wrong
     */
    void filledWrongNumber();

    /**
     * method used for updating a cell in the grid of the view
     * @param cell current cell that is being updated.
     */
    void fillCell(Cell cell);
}
