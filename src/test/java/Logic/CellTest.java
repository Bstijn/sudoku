package Logic;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CellTest {
    private Cell c;
    @Test
    @Before
    public void setUp() throws Exception {
        c= new Cell(4,5,8,false);
        assertEquals(4,c.getPosX());
        assertEquals(5,c.getPosY());
        assertEquals(8,c.getNumber());
        assertEquals(false,c.isEmpty());
    }

    @Test
    public void testFill() {
        c.fill(9);
        assertEquals("nr9.png",c.getImageString());
        assertEquals("nr9Highlight.png",c.getHighlightImageString());
    }

    @Test
    public void testEmptyCell() {
        c.emptyCell();
        assertEquals(true,c.isEmpty());
        assertEquals(0,c.getNumber());
        assertEquals("empty.png",c.getImageString());
        assertEquals("emptyHighlight.png",c.getHighlightImageString());

    }

    @Test
    public void setFillableForPlayer() {
        c.setFillableForPlayer();
        assertEquals(true,c.isFillableForPlayer());
        assertEquals("empty.png",c.getImageString());
        assertEquals("emptyHighlight.png",c.getHighlightImageString());
    }



    @Test
    public void fillIn() {
        c.fillIn(8);
        assertEquals(true,c.isFilledByPlayer());
        assertEquals("nr8filled.png",c.getImageString());
        assertEquals("nr8filledHighlight.png",c.getHighlightImageString());
    }




}