package GUI;

import ClientWS.ILobbyClient;
import ClientWS.IPlayerClient;
import ClientWS.IClientSocket;
import Shared.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartScherm extends Controller implements Initializable, IStartScherm {
    public Button join;
    private ArrayList<ILobbyClient> lobbies = new ArrayList<ILobbyClient>();

    public Button connectbutton;
    public TextField tfLobbyName;

    private enum createMode {publicgame,publicpasswordgame,solo};
    private createMode creationGame = createMode.publicgame;
    public Pane panelCreateGame;
    public Button btnStart;
    public ComboBox cbGameCreationSelector;
    public TextField tfPassword;
    public Label lblPassword;
    public Pane paneLobbys;
    public ListView lvPublicGames;

    public void refreshClick(MouseEvent mouseEvent) {
        player.getLobbiesFromServer();
    }



    @Override
    public void initialize(URL location, ResourceBundle resources) {
        lblPassword.setVisible(false);
        tfPassword.setVisible(false);
        cbGameCreationSelector.getItems().add(createMode.publicgame);
        cbGameCreationSelector.getItems().add(createMode.publicpasswordgame);
        cbGameCreationSelector.getItems().add(createMode.solo);
        cbGameCreationSelector.setOnAction((event) ->{
            cbChanged();
        });
        final IStartScherm me = (IStartScherm)this;
        player = new Player(this);
        player.setIstartscherm(me);
    }

    public void createLobby(MouseEvent mouseEvent) {
        mouseEvent.consume();
        if(creationGame == createMode.solo){
            player.createLobby();
        }
        else if(creationGame == createMode.publicgame){
            player.createLobby(tfLobbyName.getText());
        }
        else if(creationGame == createMode.publicpasswordgame){
            player.createLobby(tfLobbyName.getText(),tfPassword.getText());
        }
        try {
            changeScene("sudoku",player);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Will hide the right TextBoxes and Labels.
    private void cbChanged() {
        switch(createMode.valueOf(cbGameCreationSelector.getSelectionModel().getSelectedItem().toString())){
            case publicpasswordgame:
                lblPassword.setVisible(true);
                tfPassword.setVisible(true);
                creationGame = createMode.publicpasswordgame;
                break;
            case publicgame:
                lblPassword.setVisible(false);
                tfPassword.setVisible(false);
                creationGame = createMode.publicgame;
                break;
            case solo:
                lblPassword.setVisible(false);
                tfPassword.setVisible(false);
                creationGame = createMode.solo;
                break;
                default: System.out.println("enum not defined")
                ;break;

        }
    }


    @Override
    public void updateLobbies(ArrayList<ILobbyClient> lobbies) {
        this.lobbies = new ArrayList<>();
        this.lobbies = lobbies;
        Platform.runLater(new Runnable() {
            public void run() {
                lvPublicGames.getItems().setAll(lobbies);
                int lobbySize = lobbies.size();
                int lvSize = lvPublicGames.getItems().size();
                System.out.println(lobbySize);
                System.out.println(lvSize);
                for(int i = 0;i<lvSize-lobbySize;i++){
                    lvPublicGames.getItems().remove(i);
                }
            }
        });
    }

    public void joinLobby(MouseEvent mouseEvent) {
        ILobbyClient lobby = (ILobbyClient) lvPublicGames.getSelectionModel().getSelectedItem();
        if(lobby == null){
            return;
        }
        else{
            try {
                changeScene("sudoku",player);
            } catch (IOException e) {
                e.printStackTrace();
            }
            player.joinLobby(lobby.getId());
        }
    }


}
