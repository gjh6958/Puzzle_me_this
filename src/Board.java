import java.lang.StringBuilder;
public class Board {
    private int rows;
    private int cols;
    private Piece[][] spaces;
    private int spaces_filled;


    Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        spaces = new Piece[rows][cols];
        spaces_filled = 0;
    }

    int getRows(){
        return rows;
    }

    int getCols(){
        return cols;
    }

    public int emptySpaces(){
        return (rows*cols) - spaces_filled;
    }

    Boolean setPiece(Piece piece, int row, int col){
        if((row < rows && col < cols) && spaces[row][col] == null){
            spaces[row][col] = piece;
            spaces_filled++;
            return true;
        }
        return false;
    }

    public String toString(){
        StringBuilder b = new StringBuilder(rows * cols + rows);
        for(int i = 0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                b.append(spaces[i][j]);
            }
            b.append("\n");
        }
        return b.toString();
    }
}

