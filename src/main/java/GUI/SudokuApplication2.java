package GUI;
import Logic.*;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ResourceBundle;

public class SudokuApplication2 extends Controller implements Initializable, Guiable, ISudokuApp
{
    public Pane sudokuPane;
    public Pane paneNrs;
    public AnchorPane background;
    public Label labelpressed;
    private Group root = new Group();
    private Rectangle[][] rectangles = new Rectangle[9][9];
    private ISudoku sudoku;
    private  Cell selectedCell = null;
    private Rectangle lastHighlighted = null;


    public void initialize(URL location, ResourceBundle resources)
    {
        background.setOnMouseClicked(new EventHandler<MouseEvent>()
        {
            public void handle(MouseEvent event)
            {
                backgroundpressed(event);
            }
        });
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
                        cellMousePressed(x,y);
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

        player.setGuiGame(this);
    }

    private void cellMousePressed(int xpos, int ypos) {
        if(lastHighlighted != null){
            unHighLightLast();
        }
        setSelectedCell(sudoku.getCells()[ypos][xpos]);
        highlight(xpos,ypos,selectedCell);
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
        player.fillCell(nr,selectedCell);
    }
    @Override
    public void fillCell(Cell cell){
        Platform.runLater(() -> {
            sudoku.filCell(cell.getNumber(),sudoku.getCells()[cell.getPosY()][cell.getPosX()]);
            rectangles[cell.getPosX()][cell.getPosY()].setFill(new ImagePattern(new Image(cell.getHighlightImageString())));
        });
    }
    @Override
    public void filledWrongNumber(){
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("The number you want to fill in is wrong.");
            alert.setContentText("Be careful with the next step!");
            alert.showAndWait();
        });


    }


@Override
    public void fillInSquares(Cell[][] grid) {
        sudoku = new Sudoku(grid);
    Platform.runLater(() -> {
        for (Cell[] cs : grid) {
            for (Cell c : cs) {
                ImagePattern pattern = new ImagePattern(new Image(c.getImageString()));
                rectangles[c.getPosX()][c.getPosY()].setFill(pattern);
            }
        }
    });
    }

    public void backgroundpressed(MouseEvent mouseEvent) {
        updateGuiElements();
        if(lastHighlighted != null){
            unHighLightLast();
        }
        setSelectedCell(null);
    }

    private void updateGuiElements() {
        throw new UnsupportedOperationException();
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
