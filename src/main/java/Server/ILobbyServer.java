package Server;

import Logic.Cell;
import Shared.Player;

import java.util.ArrayList;

public interface ILobbyServer {
    String getName();
    String getPassword();
    int getId();
    int getCurrentPlayers();
    ISudokuServer getSudoku();

    void addPlayer(IPlayerServer player);

    ArrayList<IPlayerServer> getplayers();

    void remove(Player removingplayer);

    void addSudoku(ISudokuServer sudoku);
}
