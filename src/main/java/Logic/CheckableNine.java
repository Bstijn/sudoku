package Logic;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

public abstract class CheckableNine {
    Cell[] cells = new Cell[9];

    public CheckableNine(Cell[] cells){
        for (int i = 0; i < cells.length ; i++) {
            this.cells[i] = cells[i];
        }
    }

    public boolean checkNumberAvailable(int number){
        throw new NotImplementedException();
    }

    public boolean fillInCell(Cell cell,int number){
        for (Cell c : cells){
            if(cell != c && c.getNumber() == number){
                return false;
            }
        }
        return true;
    }
}
