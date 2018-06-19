package Shared;

import Logic.GenerationFaultException;
import Logic.Sudoku;
import Server.ISudokuServer;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LobbyTest {
    private Lobby l;
    @Before
    public void setUp() throws Exception {
        l = new Lobby("lobby","password",1,0);
        assertNotNull(l);
        assertEquals("lobby",l.getName());
        assertEquals("password",l.getPassword());
        assertEquals(1,l.getId());
        assertEquals(0,l.getCurrentPlayers());

    }

    @Test
    public void testGenerateSudoku() {
        try {
            l.generateSudoku();
        } catch (GenerationFaultException e) {
            fail();
        }
        assertNotNull(l.getSudoku());
    }

    @Test
    public void testAddPlayer() {
        Player p = new Player("hello");
        l.addPlayer(p);
        assertEquals(1,l.getCurrentPlayers());
        assertEquals(p,l.getplayers().get(0));
    }


    @Test
    public void testRemove() {
        Player p = new Player("hello");
        l.addPlayer(p);
        assertEquals(1,l.getCurrentPlayers());
        assertEquals(p,l.getplayers().get(0));
        l.remove(p);
        assertEquals(0,l.getCurrentPlayers());
    }

    @Test
    public void testAddSudoku() {
        ISudokuServer s = new Sudoku();
        l.addSudoku(s);
        assertEquals(l.getSudoku(),s);
    }
}