package application;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		System.out.println("Répondez 0 si vous voulez jouer les blancs et 1 dans le cas inverse");
		Scanner myObj = new Scanner(System.in);
		int binary = Integer.parseInt(myObj.nextLine());
		Board board = new Board(binary, true, null, null);
		HashMap<Piece, ArrayList<int[]>> moves = board.possibleMoves('w');
		board.showBoard();
		board.getAllMoves('w');
		while(!board.checkmate('w') || !board.checkmate('b')) {
			System.out.println("Donnez la coordonnée de la pièce à déplacer");
			String coordPiece = myObj.nextLine();
			int[] coord1 = {coordPiece.charAt(1)-49, coordPiece.charAt(0)-97};
			System.out.println("Donnez la coordonnée ou vous voulez déplacer la pièce");
			String coordMove = myObj.nextLine();
			int[] coord2 = {coordMove.charAt(1)-49, coordMove.charAt(0)-97};
			board.movePiece(coord1, coord2);
			board.showBoard();
			int[] minmax = board.minmax('b', 1, -1000, 1000, false);
			System.out.println(Arrays.toString(minmax));
			System.out.println("minmax");
			int[] coordBot1 = {minmax[1], minmax[2]};
			int[] coordBot2 = {minmax[3], minmax[4]};
			board.movePiece(coordBot1, coordBot2);
			board.showBoard();
		}
	}

}
