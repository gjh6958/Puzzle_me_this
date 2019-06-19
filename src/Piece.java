//Class representation of a piece
public class Piece {
    private int id;
    private int[] sides; //by index: up, right, down, left side
    private int[] space;

    Piece(int id, int up, int right, int down, int left){
        this.id = id;
        this.sides = new int[]{up, right, down, left};
        this.space = new int[2];
    }

    int[] getSides(){
        return sides;
    }

    void setSpace(int row, int col){
        space[0] = row;
        space[1] = col;
    }

    int[] getSpace(){ return space; }

    int getID() { return id; }

    public String toString(){
        return id + " ";
    }
}
