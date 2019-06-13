public class Piece {
    private int id;
    private int[] sides;

    Piece(int id, int up, int right, int down, int left){
        this.id = id;
        this.sides = new int[]{up, right, down, left};
    }

    int[] getSides(){
        return sides;
    }

    public String toString(){
        return Integer.toString(id) + " ";
    }
}
