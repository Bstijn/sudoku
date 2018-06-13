package GUI;

import Client.ILobbyClient;
import Client.IPlayerClient;
import Client.IClientSocket;
import Shared.Player;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;


import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class StartScherm extends Application implements Initializable, IStartScherm {
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
    private IClientSocket socket;

    public Pane paneLobbys;
    public ListView lvPublicGames;
    private IPlayerClient player;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("home.fxml"));
        primaryStage.setTitle("LobbyBrowser");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }

    public void refreshClick(MouseEvent mouseEvent) {
        for (ILobbyClient l : lobbies){
            lvPublicGames.getItems().clear();
        }
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
        player = new Player(me);
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


}
