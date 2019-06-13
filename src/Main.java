import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.util.ArrayList;

public class Main {

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

    private static int[] getDims(@NotNull String raw_dims){
        int[] dims = new int[2];
        String[] arrdims = raw_dims.split(", ");
        dims[0] = Integer.parseInt(arrdims[0]);
        dims[1] = Integer.parseInt(arrdims[1]);
        return dims;
    }

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

    private static void placeSides(Board board, ArrayList<Piece> pieces){
        ArrayList<Piece> top = new ArrayList<>();
        ArrayList<Piece> bottom = new ArrayList<>();
        ArrayList<Piece> left = new ArrayList<>();
        ArrayList<Piece> right = new ArrayList<>();
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


    }

    public static void main(String[] args){
        ArrayList<String> lines = parseFile();
        int[] dims = getDims(lines.get(0));
        Board board = new Board(dims[0], dims[1]);
        lines.remove(0);
        ArrayList<Piece> pieces = makePieces(lines, (board.getRows() * board.getCols()));
        placeCorners(board, pieces);
        placeSides(board, pieces);
        System.out.println(board);
    }
}
