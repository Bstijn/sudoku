package RestServer;

import Logic.Cell;
import Logic.Sudoku;
import Server.ISudokuServer;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class GeneratorRequester {
    private static final String QUERY = "http://localhost:8093/generator/give";
    public GeneratorRequester(){
    //empty constructor for now maybe filled in future
    }

    public ISudokuServer requestSudoku(){
        HttpGet httpGet = new HttpGet(QUERY);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
             CloseableHttpResponse response = httpClient.execute(httpGet);) {
            HttpEntity entity = response.getEntity();
            final String entityString = EntityUtils.toString(entity);
            return buildSudoku(entityString);
        } catch (IOException e) {
            // Evil, pure evil this solution: ....
            System.out.println("IOException : " + e.toString());
        }
        return null;
    }

    private ISudokuServer buildSudoku(String enityString){
        JsonArray json = new JsonParser().parse(enityString).getAsJsonArray();
        Cell[][] grid = new Cell[9][9];
        for (int i = 0;i< json.size();i++){
            Cell cell = new Cell(json.get(i).getAsJsonObject());
            grid[cell.getPosY()][cell.getPosX()] = cell;
        }
        return new Sudoku(grid);
    }
}
