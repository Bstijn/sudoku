package Server;

import Logic.Cell;
import Logic.GenerationFaultException;
import com.google.gson.JsonArray;

public interface ISudokuServer {
    Cell[][] start(int difficulty ) throws GenerationFaultException;
    Cell[][] getCells();
    boolean filCell(int nr, Cell selectedcell);

    JsonArray toJsonArray();
}
