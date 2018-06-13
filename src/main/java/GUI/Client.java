package GUI;

import ClientWS.ClientWebSocket;
import Shared.Player;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        Application.launch();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("home.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(new Player(controller));
        Scene scene = new Scene(root);
        stage = primaryStage;
        stage.setTitle("lobbies");
        stage.setScene(scene);
        stage.show();
    }
}