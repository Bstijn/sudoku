package RestServer;

import Logic.GenerationFaultException;
import Logic.ISudoku;
import Logic.Sudoku;


import javax.ws.rs.Path;
import javax.ws.rs.GET;


@Path("/generator")
public class Generator {
    @GET
    @Path("give")
    public String test() {
        ISudoku sudoku = new Sudoku();
        try {
            sudoku.start(50);
            return sudoku.toJsonArray().toString();

        } catch (GenerationFaultException e) {
            return "faulty";
        }
    }



}
