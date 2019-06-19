import java.lang.StringBuilder;

//Class representing the board where piece intance positions will be stored
//as well as board dimensions
public class Board {
    private int rows;
    private int cols;
    private Piece[][] spaces; //A 2d array representing the rows and columns of the board
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

    Piece getSpace(int row, int col){ return spaces[row][col]; }

    public int emptySpaces(){
        return (rows*cols) - spaces_filled;
    }

    Boolean setPiece(Piece piece, int row, int col){
        if((row < rows && col < cols) && spaces[row][col] == null){
            spaces[row][col] = piece;
            spaces_filled++;
            piece.setSpace(row, col);
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

