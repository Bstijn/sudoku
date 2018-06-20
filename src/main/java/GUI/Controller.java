package GUI;

import ClientWS.IPlayerClient;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;

import java.io.IOException;


public abstract class Controller{

    protected Scene scene;
    protected static IPlayerClient player;

    protected void changeScene(String fxml, IPlayerClient player) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource( fxml + ".fxml"));
        Parent root = loader.load();
        scene = new Scene(root);
        Client.stage.setTitle(fxml);
        Client.stage.setScene(scene);
        Client.stage.centerOnScreen();
        Client.stage.show();
        this.player = player;

    }

    public void setClient(IPlayerClient player) {
        this.player = player;
    }

    protected void showAlert(String message, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.show();
    }
}