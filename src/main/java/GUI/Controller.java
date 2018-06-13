package GUI;

import ClientWS.IClientSocket;
import ClientWS.IPlayerClient;
import Shared.Player;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;


public abstract class Controller{

    protected IClientSocket client;
    protected Scene scene;
    protected IPlayerClient player;

    public void changeScene(String fxml, Player player) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource( fxml + ".fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        scene = new Scene(root);
        Client.stage.setTitle(fxml);
        Client.stage.setScene(scene);
        Client.stage.centerOnScreen();
        Client.stage.show();
        controller.setClient(player);
    }

    public void setClient(Player player) {
        this.player = player;
    }

    protected void showAlert(String message, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.show();
    }
}