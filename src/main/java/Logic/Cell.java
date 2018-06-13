package Logic;

import com.google.gson.JsonObject;

public class Cell {


    private int posX;
    private int posY;
    private int number;
    private boolean empty;
    private boolean fillableForPlayer = false;
    private boolean filledByPlayer = false;
    private String imageString = "empty.png";
    private String highlightImageString = "emptyHighlight.png";

    public Cell(int posX, int posY, int number, boolean empty){
        this.posX=posX;
        this.posY=posY;
        this.number = number;
        this.empty = empty;
    }


    //CONSTRUCTOR THAT WILL CREATE A CELL OUT OF A JSONOBJECT.
    public Cell(JsonObject celljson){
        posX = celljson.get("posX").getAsInt();
        posY = celljson.get("posY").getAsInt();
        number = celljson.get("number").getAsInt();
        empty = celljson.get("empty").getAsBoolean();
        fillableForPlayer = celljson.get("fillableForPlayer").getAsBoolean();
        filledByPlayer = celljson.get("filledByPlayer").getAsBoolean();
        imageString = celljson.get("imageString").getAsString();
        highlightImageString = celljson.get("highlightImageString").getAsString();
    }




    public int getNumber() {
        return number;
    }

    public boolean isEmpty() {
        return empty;
    }

    public void fill(int number){
        this.number = number;
        empty = false;
        highlightImageString = "nr"+number+"Highlight.png";
        imageString = "nr"+number+".png";
    }

    public int getPosX() {
        return posX;
    }

    public int getPosY() {
        return posY;
    }

    public void emptyCell() {
        empty = true;
        number = 0;
        highlightImageString = "emptyHighlight.png";
        imageString = "empty.png";
    }

    public void setFillableForPlayer(){
        fillableForPlayer = true;
        highlightImageString = "emptyHighlight.png";
        imageString = "empty.png";

    }

    public boolean isFillableForPlayer(){
        return fillableForPlayer;
    }

    public boolean fillIn(int number){
        if(number != this.number){
            return false;
        }
        else{
            filledByPlayer = true;
            highlightImageString = "nr"+number+"filled"+"Highlight.png";
            imageString = "nr"+number+"filled"+".png";
            return true;
        }
    }

    public boolean isFilledByPlayer(){
        return filledByPlayer;
    }

    public String getImageString() {
        return imageString;
    }

    public String getHighlightImageString() {
        return highlightImageString;
    }
}
