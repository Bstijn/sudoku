package Server;

import Logic.Cell;
import Shared.Player;

import java.util.ArrayList;

public interface ILobbyServer {
    /**
     * simple getter of the lobby
     * @return
     */
    String getName();
    /**
     * simple getter of the lobby
     * @return
     */
    String getPassword();
    /**
     * simple getter of the lobby
     * @return
     */
    int getId();
    /**
     * simple getter of the lobby
     * @return
     */
    int getCurrentPlayers();

    /**
     * will return the sudoku that is in the lobby.
     * @return
     */
    ISudokuServer getSudoku();

    /**
     * used for adding players in the lobby. and updating the current player count.
     * @param player you wish to add
     */
    void addPlayer(IPlayerServer player);

    /**
     * returns
     * @return players that are in the lobby
     */
    ArrayList<IPlayerServer> getplayers();

    /**
     * will remove a player from the lobby
     * @param removingplayer the player u wish to remove from the lobby
     */
    void remove(Player removingplayer);

    /**
     * will set the sudoku of the lobby.
     * @param sudoku
     */
    void addSudoku(ISudokuServer sudoku);
}
