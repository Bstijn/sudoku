package RestServer;

import Logic.Cell;
import Logic.GenerationFaultException;
import Logic.ISudoku;
import Logic.Sudoku;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.Path;
import javax.ws.rs.GET;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/generator")
public class Generator {
    @GET
    @Path("give")
    public String test() {
        ISudoku sudoku = new Sudoku();
        try {
            Cell[][] cells =sudoku.start(50);
            return sudoku.toJsonArray().toString();

        } catch (GenerationFaultException e) {
            e.printStackTrace();
        }
        return "Test";
    }



}
