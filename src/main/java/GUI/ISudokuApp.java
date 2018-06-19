package GUI;

import Logic.Cell;

public interface ISudokuApp {
    void fillInSquares(Cell[][] grid);

    void filledWrongNumber();
    void fillCell(Cell cell);
}
