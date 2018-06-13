package GUI;
import Client.IPlayerClient;
import Logic.*;
import com.sun.org.apache.bcel.internal.generic.INSTANCEOF;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import javax.swing.text.Highlighter;
import java.net.URL;
import java.util.Observable;
import java.util.ResourceBundle;

public class SudokuApplication2 extends Application implements Initializable, Guiable
{
    public Pane sudokuPane;
    public Pane paneNrs;
    public AnchorPane background;
    public Label labelpressed;
    Group root = new Group();
    Rectangle[][] rectangles = new Rectangle[9][9];
    private ISudoku sudoku;
    private int lastX;
    private int lastY;
    private  Cell selectedCell = null;
    private Rectangle lastHighlighted = null;
    private IPlayerClient player;

    private int selectednr;

    public SudokuApplication2(IPlayerClient player,final Stage stage) {
        this.player = player;
        try {
            this.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void start(final Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sudoku.fxml"));
        primaryStage.setTitle("Logic.Sudoku");
        primaryStage.setScene(new Scene(root, 350,600));
        primaryStage.show();

    }

    public static void main(String[] args)
    {
        launch(args);
    }

    public void initialize(URL location, ResourceBundle resources)
    {
        background.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                backgroundpressed(event);
            }
        });
        sudoku = new Sudoku(this);
        for (int i = 0; i < 9; i++)
        {
            for (int j = 0; j < 9; j++)
            {
                Rectangle r = new Rectangle();
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);
                r.setHeight(36);
                r.setWidth(36);
                r.setX(0 + i*36);
                r.setY(0 + j*36);
                if((i+1)%3 == 0){
                    r.setWidth(34);
                }
                if((j+1)%3==0){
                    r.setHeight(34);
                }
                rectangles[i][j] = r;
                final int x = i;
                final int y = j;
                r.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent event)
                    {
                        event.consume();
                        CellMousePressed(event,x,y);
                    }
                });

                root.getChildren().add(r);
            }
        }

        for (int i = 0; i <9 ; i++) {
                Rectangle r = new Rectangle();
                r.setStroke(Color.BLACK);
                r.setFill(Color.WHITE);
                r.setHeight(36);
                r.setWidth(36);
                r.setX(0 + i*36);
                String number = "nr"+String.valueOf((i+1))+".png";
                System.out.println(number);
                Image image = new Image(number);

                ImagePattern pattern = new ImagePattern(image);
                r.setFill(pattern);
                final int x = i+1;
                r.setOnMouseClicked(new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent event)
                    {
                        event.consume();
                        nrSelect(x);
                    }
                });

                paneNrs.getChildren().add(r);

        }
        sudokuPane.getChildren().add(root);
        try {
            fillInSquares(sudoku.start(50));
        } catch (GenerationFaultException e) {
            e.printStackTrace();
        }
    }

    private void CellMousePressed(MouseEvent event, int xpos, int ypos) {
        final int x = xpos;
        final int y = ypos;
        System.out.println("cell pressed");
        System.out.println("Pressed cell: x = " + x + " y:" + y);
        lastX = x;
        lastY = y;
        if(lastHighlighted != null){
            unHighLightLast();
        }
        setSelectedCell(sudoku.getCells()[y][x]);
        highlight(x,y,selectedCell);
    }

    private void highlight(int x, int y,Cell cell) {

        Rectangle r = rectangles[x][y];
        ImagePattern pattern;
        pattern = new ImagePattern(new Image(cell.getHighlightImageString()));
        r.setFill(pattern);
        lastHighlighted = r;
    }

    private void unHighLightLast() {
        Rectangle r = rectangles[selectedCell.getPosX()][selectedCell.getPosY()];
        r.setFill(new ImagePattern(new Image(selectedCell.getImageString())));
        lastHighlighted = null;
    }


    private void nrSelect(int nr){
        if(selectedCell != null) {
            if(sudoku.filCell(nr, selectedCell)){
                rectangles[selectedCell.getPosX()][selectedCell.getPosY()].setFill(new ImagePattern(new Image(selectedCell.getHighlightImageString())));
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning Dialog");
                alert.setHeaderText("The number you want to fill in is wrong.");
                alert.setContentText("Be careful with the next step!");

                alert.showAndWait();
            }
        }
    }


    private void fillInSquares(Cell[][] grid) {
       for(Cell[] cs : grid){
           for(Cell c : cs){
               ImagePattern pattern = new ImagePattern(new Image(c.getImageString()));
               rectangles[c.getPosX()][c.getPosY()].setFill(pattern);
           }
       }
    }

    public void backgroundpressed(MouseEvent mouseEvent) {
        System.out.println("background pressed");
        updateGuiElements();
        if(lastHighlighted != null){
            unHighLightLast();
        }
        setSelectedCell(null);
    }

    private void updateGuiElements() {
    }


    private synchronized void setSelectedCell(Cell selectedCell) {
        this.selectedCell = selectedCell;
    }

    @Override
    public void gameEnd() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("You Won!! :)");
        alert.setContentText("U have succesfully filled in all the numbers");
        alert.showAndWait();
    }
}
