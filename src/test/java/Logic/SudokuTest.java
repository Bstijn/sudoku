package Logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SudokuTest {
    private Sudoku s;
    @Test
    @Before
    public void setUp() throws Exception {
        s = new Sudoku();
        assertNotNull(s.getCells());
    }

    @Test
    public void TestStart() {
        int i = 0;
        try {
            s.start(50);
        } catch (GenerationFaultException e) {
            e.printStackTrace();
        }
        for(Cell[]cs : s.getCells()){
            for(Cell c : cs){
                if(!c.isFillableForPlayer()){
                    i++;
                }
            }
        }
        if(i < 40 && i > 44){
           fail();
        }

    }


    @Test
    public void TestFilCell() {
    Cell c = s.getCells()[0][0];
    int nr = c.getNumber();
    assertTrue(s.filCell(nr,c));
    }
}