package Server;

import Logic.Cell;
import Logic.GenerationFaultException;

public interface ISudokuServer {
    Cell[][] start(int difficulty ) throws GenerationFaultException;
    Cell[][] getCells();
    boolean filCell(int nr, Cell selectedcell);
}
