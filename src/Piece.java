//Class representation of a piece
public class Piece {
    private int id;
    private int[] sides; //by index: up, right, down, left side

    Piece(int id, int up, int right, int down, int left){
        this.id = id;
        this.sides = new int[]{up, right, down, left};
    }

    int[] getSides(){
        return sides;
    }

    public String toString(){
        return id + " ";
    }
}
