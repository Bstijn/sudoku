package RestServer;

import Logic.Cell;
import Logic.GenerationFaultException;
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
        Sudoku sudoku = new Sudoku();
        try {
            JsonArray grid = new JsonArray();
            Cell[][] cells =sudoku.start(50);
            JsonObject json = new JsonObject();
            for (Cell[] cs : cells){
                for(Cell c : cs){
                    System.out.println(new Gson().toJson(c));
                    int posx = c.getPosX();
                    int posy = c.getPosY();
                    JsonObject cellJson = new JsonParser().parse(new Gson().toJson(c)).getAsJsonObject();
                    grid.add(cellJson);
                }
            }
            return grid.toString();

        } catch (GenerationFaultException e) {
            e.printStackTrace();
        }
        return "Test";
    }



}
