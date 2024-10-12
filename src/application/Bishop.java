package application;

import java.util.ArrayList;

public class Bishop extends Piece {

	private String name;

	public Bishop(char color, int x, int y, boolean firstPlayer, boolean EnPassant, boolean alreadyPlayed, String name) {
		super(color, x, y, firstPlayer, EnPassant, alreadyPlayed, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece[][] board, char color, int x, int y, boolean firstPlayer) {
		// TODO Auto-generated method stub
		ArrayList<int[]> moves = new ArrayList<int[]>(); // d�placements sans prendre de pions
		ArrayList<int[]> moves2 = new ArrayList<int[]>(); // d�placements o� l'on peut prendre
		int x2 = x + 1;
		int y2 = y + 1;
		while(x2 < 8 && y2 < 8 && board[x2][y2] == null) {
			int[] coord = {x2, y2};
			moves.add(coord);
			x2 += 1;
			y2 += 1;
		}
		if(x2 < 8 && y2 < 8 && board[x2][y2].getColor() != color) {
			int[] coord = {x2, y2};
			moves2.add(coord);
		}
		int x3 = x - 1;
		int y3 = y + 1;
		while(x3 > -1 && y3 < 8 && board[x3][y3] == null) {
			int[] coord = {x3, y3};
			moves.add(coord);
			x3 -= 1;
			y3 += 1;
		}
		if(x3 > -1 && y3 < 8 && board[x3][y3].getColor() != color) {
			int[] coord = {x3, y3};
			moves2.add(coord);
		}
		int x4 = x - 1;
		int y4 = y - 1;
		while(x4 > -1 && y4 > -1 && board[x4][y4] == null) {
			int[] coord = {x4, y4};
			moves.add(coord);
			x4 -= 1;
			y4 -= 1;
		}
		if(x4 > -1 && y4 > -1 && board[x4][y4].getColor() != color) {
			int[] coord = {x4, y4};
			moves2.add(coord);
		}
		int x5 = x + 1;
		int y5 = y - 1;
		while(x5 < 8 && y5 > -1 && board[x5][y5] == null) {
			int[] coord = {x5, y5};
			moves.add(coord);
			x5 += 1;
			y5 -= 1;
		}
		if(x5 < 8 && y5 > -1 && board[x5][y5].getColor() != color) {
			int[] coord = {x5, y5};
			moves2.add(coord);
		}
	ArrayList<ArrayList<int[]>> allMoves = new ArrayList<ArrayList<int[]>>();
	allMoves.add(moves);
	allMoves.add(moves2);
	return allMoves;
	}

	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return name;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		this.name = name;
	}

	@Override
	public Piece copyPiece(){
		Piece Bishop = new Bishop(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		return Bishop;
	}

}

