package Shared;

import ClientWS.ILobbyClient;
import Logic.GenerationFaultException;
import Logic.Sudoku;
import Server.ILobbyServer;
import Server.IPlayerServer;
import Server.ISudokuServer;

import javax.websocket.Session;
import java.util.ArrayList;

public class Lobby implements ILobbyServer, ILobbyClient {
    private String name;
    private String password;
    private int Id;
    private int currentPlayers;
    private ISudokuServer sudoku;
    private ArrayList<IPlayerServer> players = new ArrayList<IPlayerServer>();



    public Lobby(String name,String password, int id, int currentPlayers){
        this.name = name;
        this.password = password;
        Id = id;
        this.currentPlayers = currentPlayers;
    }

    public void generateSudoku() throws GenerationFaultException {
        sudoku = new Sudoku();
        sudoku.start(50);
    }

    @Override
    public String toString(){
        return name + " [" + currentPlayers + "]";
    }

    @Override
    public void addPlayer(IPlayerServer player) {
        players.add(player);
        currentPlayers = players.size();
    }


    @Override
    public ArrayList<IPlayerServer> getplayers() {
        return players;
    }

    @Override
    public void remove(Player removingplayer) {
        players.remove(removingplayer);
        currentPlayers -=1;
    }

    @Override
    public void addSudoku(ISudokuServer sudoku) {
        this.sudoku = sudoku;
    }


    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return Id;
    }

    public int getCurrentPlayers() {
        return players.size();
    }

    public ISudokuServer getSudoku() {
        return sudoku;
    }



}
