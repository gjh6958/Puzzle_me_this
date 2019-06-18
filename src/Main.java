import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Main {

    //Parses the input file and returns an arraylist of
    //strings of the lines in the file
    //return: An arraylist of the lines in the file
    private static ArrayList<String> parseFile(){
        String fileName = "ex.in";
        String line;
        ArrayList<String> lines = new ArrayList<>();
        try{
            FileReader reader = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(reader);
            while((line = buffer.readLine()) != null){
                lines.add(line);
            }
            buffer.close();
        }
        catch(FileNotFoundException ex){
            System.out.println("File " + fileName + " does not exist in default directory.");
        }
        catch(IOException ex){
            System.out.println("Error reading file " + fileName);
        }
        return lines;
    }

    //Takes in a string representation of the board dimensions
    //and returns an integer array of the rows and columns size.
    //param raw_dims: string representation of dimensions
    //return: integer array of dimensions
    private static int[] getDims(@NotNull String raw_dims){
        int[] dims = new int[2];
        String[] arrdims = raw_dims.split(", ");
        dims[0] = Integer.parseInt(arrdims[0]);
        dims[1] = Integer.parseInt(arrdims[1]);
        return dims;
    }

    //Function responsible for making piece instances based on the input file
    //param lines: string representation of lines in the input file
    //param numPieces: The number of pieces to make
    //return: An array list of all piece instances
    private static ArrayList<Piece> makePieces(ArrayList<String> lines, int numPieces){
        ArrayList<Piece> pieces = new ArrayList<>();
        for(int i = 0; i < numPieces; i++){
            String raw = lines.get(i);
            String[] id_sides = raw.split(": ");
            String[] strSides = id_sides[1].split(", ");
            int[] sides = new int[4];
            for(int j = 0; j < 4; j++){
                sides[j] = Integer.parseInt(strSides[j]);
            }
            pieces.add(new Piece(Integer.parseInt(id_sides[0]), sides[0], sides[1], sides[2], sides[3]));
        }
        return pieces;
    }

    //Function that takes in a piece and returns the number of zero sides of that instance
    //param: piece
    //return: an integer representation of the number of sides with a zero integer
    private static int countZeros(@NotNull Piece piece){
        int count = 0;
        int[] sides = piece.getSides();
        for(int i = 0; i < 4; i++){
            if(sides[i] == 0){
                count++;
            }
        }
        return count;
    }

    //Function that finds the corner pieces and places them in
    //the correct array indexes of the board.
    //param board: The board instance where piece placement is stored
    //param pieces: An array list of all non-placed piece instances
    private static void placeCorners(@NotNull Board board, ArrayList<Piece> pieces){
        ArrayList<Piece> corners = new ArrayList<>();
        for(int i = 0; i < (board.getRows()*board.getCols()-1); i++){
            if(countZeros(pieces.get(i)) == 2){
                corners.add(pieces.get(i));
            }
        }
        for(int i = 0; i < 4; i++) {
            int[] sides = corners.get(i).getSides();
            if (sides[0] == 0 && sides[1] == 0) {
                board.setPiece(corners.get(i), 0, board.getCols() - 1);
            } else if (sides[0] == 0 && sides[3] == 0) {
                board.setPiece(corners.get(i), 0, 0);
            } else if (sides[1] == 0 && sides[2] == 0) {
                board.setPiece(corners.get(i), board.getRows() - 1, board.getCols() - 1);
            } else {
                board.setPiece(corners.get(i), board.getRows() - 1, 0);
            }
            pieces.remove(corners.get(i));
        }
    }

    // Function tells if the pieces provided in the parameters fit together
    // on the given side.
    // Param: piece1 and piece2 are the pieces in question
    // Param: side - an integer representation of the side to be fit on
    // with respect to the first piece.  0 - top, 1 - right, 2 - bottom, 3 - left
    // Return: A boolean representing if the pieces fit together.
    private static Boolean fit(@NotNull Piece piece1, Piece piece2, int side){
        int[] piece1_sides = piece1.getSides();
        int[] piece2_sides = piece2.getSides();
        if((side == 0) && (piece1_sides[0] == -(piece2_sides[2]))){
            return true;
        }else if((side == 1) && (piece1_sides[1] == -(piece2_sides[3]))){
            return true;
        }else if((side == 2) && (piece1_sides[2] == -(piece2_sides[0]))){
            return true;
        }else return (side == 3) && (piece1_sides[3] == -(piece2_sides[1]));
    }

    //
    private static void placeSides(@NotNull Board board, ArrayList<Piece> side_pieces, int side){
        ArrayList<Piece> fits = new ArrayList<>();
        Piece corner;
        if((side == 0) || (side == 3)){
            corner = board.getSpace(0, 0);
            for(int i = 0; i < side_pieces.size()-1; i++){
                if(fit(corner, side_pieces.get(i), 3)){
                    fits.add(side_pieces.get(i));
                }
            }
            for(int i = 0; i < fits.size()-1; i++){
                
            }
        }else{
            corner = board.getSpace(0, board.getCols());
        }


    }

    // Finds the pieces that make up a side of the puzzle, puts them into correctly named
    // lists and then runs those lists through the placeSides function.
    // Param: board - the instance of the board class to be filled
    // Param: pieces - the list of unplaced pieces
    private static void findSides(@NotNull Board board, ArrayList<Piece> pieces){
        ArrayList<Piece> top = new ArrayList<>();
        ArrayList<Piece> bottom = new ArrayList<>();
        ArrayList<Piece> left = new ArrayList<>();
        ArrayList<Piece> right = new ArrayList<>();
        ArrayList<ArrayList<Piece>> group = new ArrayList<ArrayList<Piece>>(4);
        group.add(top); group.add(right); group.add(bottom); group.add(left);
        for(int i = 0; i < (board.getRows()*board.getCols())-4; i++){
            if(countZeros(pieces.get(i)) == 1){
                int[] sides = pieces.get(i).getSides();
                if(sides[0] == 0){
                    top.add(pieces.get(i));
                }else if(sides[1] == 0){
                    right.add(pieces.get(i));
                }else if(sides[2] == 0){
                    bottom.add(pieces.get(i));
                }else{
                    left.add(pieces.get(i));
                }
            }
        }
        for(int i = 0; i < 4; i++){
            placeSides(board, group.get(i), i);
        }

    }

    //Main function that controls the functionality of the program execution
    public static void main(String[] args){
        ArrayList<String> lines = parseFile();
        int[] dims = getDims(lines.get(0));
        Board board = new Board(dims[0], dims[1]);
        lines.remove(0);
        ArrayList<Piece> pieces = makePieces(lines, (board.getRows() * board.getCols()));
        placeCorners(board, pieces);
        findSides(board, pieces);
        System.out.println(board);
    }
}
