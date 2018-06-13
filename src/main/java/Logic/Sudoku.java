package Logic;

import GUI.Guiable;
import Server.ISudokuServer;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Random;

public class Sudoku implements ISudoku, ISudokuServer {
    private Cell[][] cells = new Cell[9][9];
    private Row[] rows = new Row[9];
    private Column[] columns = new Column[9];
    private Box[] boxs = new Box[9];
    private boolean generated = false;
    private ArrayList<Cell> filledIncells = new ArrayList();
    private Guiable gui;

    public Sudoku(Guiable gui){
        this.gui = gui;
        prepareSudoku();
    }

    public Sudoku(){
      prepareSudoku();
    }

    public Sudoku(Cell[][] grid) {
        this.cells = grid;
        fillCheckAbleNines();
    }

    private void prepareSudoku(){
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                cells[i][j] = new Cell(j,i,0,true);
            }
        }
        fillCheckAbleNines();
    }

    private void fillCheckAbleNines() {
        fillRows();
        fillColumns();
        fillBoxs();

    }

    private void fillBoxs() {
        //FML
        Cell[] c0 = {cells[0][0],cells[0][1],cells[0][2],cells[1][0],cells[1][1],cells[1][2],cells[2][0],cells[2][1],cells[2][2]}; boxs[0] = new Box(c0);
        Cell[] c1 = {cells[3][0],cells[3][1],cells[3][2],cells[4][0],cells[4][1],cells[4][2],cells[5][0],cells[5][1],cells[5][2]}; boxs[1] = new Box(c1);
        Cell[] c2 = {cells[6][0],cells[6][1],cells[6][2],cells[7][0],cells[7][1],cells[7][2],cells[8][0],cells[8][1],cells[8][2]}; boxs[2] = new Box(c2);
        Cell[] c3 = {cells[0][3],cells[0][4],cells[0][5],cells[1][3],cells[1][4],cells[1][5],cells[2][3],cells[2][4],cells[2][5]}; boxs[3] = new Box(c3);
        Cell[] c4 = {cells[3][3],cells[3][4],cells[3][5],cells[4][3],cells[4][4],cells[4][5],cells[5][3],cells[5][4],cells[5][5]}; boxs[4] = new Box(c4);
        Cell[] c5 = {cells[6][3],cells[6][4],cells[6][5],cells[7][3],cells[7][4],cells[7][5],cells[8][3],cells[8][4],cells[8][5]}; boxs[5] = new Box(c5);
        Cell[] c6 = {cells[0][6],cells[0][7],cells[0][8],cells[1][6],cells[1][7],cells[1][8],cells[2][6],cells[2][7],cells[2][8]}; boxs[6] = new Box(c6);
        Cell[] c7 = {cells[3][6],cells[3][7],cells[3][8],cells[4][6],cells[4][7],cells[4][8],cells[5][6],cells[5][7],cells[5][8]}; boxs[7] = new Box(c7);
        Cell[] c8 = {cells[6][6],cells[6][7],cells[6][8],cells[7][6],cells[7][7],cells[7][8],cells[8][6],cells[8][7],cells[8][8]}; boxs[8] = new Box(c8);
    }

    private void fillRows() {
        int i = 0;
        while(i < 9) {
            Cell[] tempCells = new Cell[9];
            for (int j = 0; j < 9; j++) {
                tempCells[j] = cells[j][i];
            }
            columns[i] = new Column(tempCells);
            i++;
        }
    }

    private void fillColumns() {
        for (int i = 0;i<9;i++){
            Cell[] tempCells = new Cell[9];
            for (int j = 0; j < 9; j++) {
                tempCells[j] = cells[i][j];
            }
            rows[i] = new Row(tempCells);
        }
    }

    public Cell[][] start(int difficulty) throws GenerationFaultException {
        if(!generated){
        generateTopRow();
        generateRest();
        System.out.println("Done Generating");
        }
        if(generated) {
            removeCells(difficulty);
        }
        return cells;

    }

    private void removeCells(int difficulty) {
        int i =0;
        Random random = new Random();
        while (i<difficulty){
            Cell selectedCell = cells[random.nextInt(9)][random.nextInt(9)];
            if(!selectedCell.isEmpty()){
                //if(checkRemovability(selectedCell)){
                    selectedCell.setFillableForPlayer();
                    i++;
                //}
            }
        }
        //@TODO Step 1: While loop with difficulty
        //@TODO Step 2: Select a random cell[0-8][0-8]
        //@TODO Step 3: Check if this cell can be removed and if the sudoku is still solveable
    }

    private boolean checkRemovability(Cell selectedCell) {
    throw new NotImplementedException();
    }


    //method that will generate the top row with the numbers 1-9
    private void generateTopRow(){
        int i = 0;
        Row topRow = rows[0];
        Random random = new Random();
        while(i<9){
            int number = random.nextInt(9)+1;
            Cell currentCell = topRow.cells[i];
            if(topRow.fillInCell(currentCell,number)){
            currentCell.fill(number);
            filledIncells.add(currentCell);
                //System.out.println("Filled In:" + i);
                i++;
            }
        }
        return;
    }

    private void generateRest() throws GenerationFaultException {
        Random random = new Random();
        while(!checkAllFilled()){
            int x = random.nextInt(9);
            int y = random.nextInt(9);
            Cell fillable = cells[x][y];
            while(fillable.isEmpty()){
                CheckableNine[] cns = giveCheckableNinesContainingCell(fillable);
                boolean[] numbersInCns = giveNumberInCns(cns);
                if(checkIfNumbersAreAvailable(numbersInCns)){
                    int numberToFillIn = giveNumberToFillIn(numbersInCns,random);
                    fillable.fill(numberToFillIn);
                    filledIncells.add(fillable);
                    //System.out.println("FILLING CELL:X:" + fillable.getPosX() + ", Y:" + fillable.getPosY() + ", Number: " + fillable.getNumber());
                }
                else {
                    for (int i = 0; i < 9; i++) {
                        filledIncells.get(filledIncells.size() - 1).emptyCell();
                        filledIncells.remove(filledIncells.size() - 1);
                        //System.out.println("RemovingCells");
                    }
                }

            }
            generated = true;
        }
    }

    private int giveNumberToFillIn(boolean[] numbers, Random random) {
        ArrayList<Integer> numbersAvailable = new ArrayList<Integer>();
        for (int i = 0; i <=8; i++) {
            if(!numbers[i]){
                numbersAvailable.add(i+1);
            }
        }
        return numbersAvailable.get(random.nextInt(numbersAvailable.size()));
    }

    //FALSE MEANS THE NUMBER IS NOT IN THE BOX NOR COLLUMN OR ROW.
    private boolean checkIfNumbersAreAvailable(boolean[] numbers) {
        for(boolean number : numbers){
            if(number == false){
                return true;
            }
        }
        return false;

    }

    /**
     * @return will return true when all Cells in sudoku are not empty.
     */
    private boolean checkAllFilled() {
    for (Cell[] cs : cells){
        for(Cell c : cs){
            if(c.isEmpty()){
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Will return an array of CheckablesNines that contain the cell will dictates these on the hand of ROWS, COLUMNS AND BOXS.
     * @param cell cell u want to check
     * @return array of CheckablesNines that contain the cell
     * @throws GenerationFaultException when cell u chose is not full generated in your checkable nines
     */
    private CheckableNine[] giveCheckableNinesContainingCell(Cell cell) throws GenerationFaultException{
        CheckableNine[] cns = new CheckableNine[3];
        cns[0] = giveCheckableNineContainingCell(rows,cell);
        cns[1] = giveCheckableNineContainingCell(columns,cell);
        cns[2] = giveCheckableNineContainingCell(boxs,cell);
        return cns;
    }

    /**
     * will give Logic.CheckableNine That Contains the cell out of multiple CheckableNines.
     * @param cns Array of CheckAbleNines u get to chose from.
     * @param cell
     * @return First Logic.CheckableNine that contains the cell.
     * @throws GenerationFaultException when the Logic.Cell is not contained in any of the cns.
     */
    private CheckableNine giveCheckableNineContainingCell(CheckableNine[] cns, Cell cell) throws GenerationFaultException{
        for (CheckableNine cn : cns){
            for (Cell c : cn.cells){
                if(c.equals(cell)){
                    return cn;
                }
            }
        }
        throw new GenerationFaultException();
    }

    /**
     * @param cns CheckableNines that have a array of Cells.
     * @return array of booleans that indicate with true that the position of array+1 is filled in the checkableNines that u gave.
     */
    private boolean[] giveNumberInCns(CheckableNine[] cns){
        boolean[] numbers = {false,false,false,false,false,false,false,false,false};
        for(CheckableNine cn : cns){
            for(Cell c : cn.cells){
                if(!c.isEmpty()){
                    numbers[c.getNumber()-1] = true;
                }
            }
        }
        return numbers;
    }

    public void printInConsole() {
        for (Cell[] i : cells) {
            System.out.print("|");
            for (Cell j : i) {
                System.out.print(j.getNumber());
                System.out.print("|");

            }
            System.out.println();
        }
    }

    public Cell[][] getCells(){
        return cells;
    }

    @Override
    public boolean filCell(int nr, Cell selectedcell) {
        boolean succes  = selectedcell.fillIn(nr);
        checkGameFinish();
        return succes;
    }

    private void checkGameFinish() {
        for (Cell[] cs :cells) {
            for(Cell c : cs){
                if(!c.isFilledByPlayer() && c.isFillableForPlayer()){
                    return;
                }
            }
        }
        gui.gameEnd();
    }
}


