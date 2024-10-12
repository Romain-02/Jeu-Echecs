package application;

import java.util.ArrayList;

public class Rock extends Piece {

	public Rock(char color, int x, int y, boolean firstPlayer, boolean EnPassant, boolean alreadyPlayed, String name) {
		super(color, x, y, firstPlayer, EnPassant, alreadyPlayed, name);
		// TODO Auto-generated constructor stub
	}

	private String name;

	@Override
	public ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece[][] board, char color, int x, int y, boolean firstPlayer) {
		// TODO Auto-generated method stub
		ArrayList<int[]> moves = new ArrayList<int[]>();   // d�placements sans prendre de pions
		ArrayList<int[]> moves2 = new ArrayList<int[]>();  // d�placements o� l'on peut prendre
		int x6 = x + 1;
		while(x6 < 8 && board[x6][y] == null) {
			int[] coord = {x6, y};
			moves.add(coord);
			x6 += 1;
		}
		if(x6 < 8 && board[x6][y].getColor() != color) {
			int[] coord = {x6, y};
			moves2.add(coord);
		}
		int y6 = y + 1;
		while(y6 < 8 && board[x][y6] == null) {
			int[] coord = {x, y6};
			moves.add(coord);
			y6 += 1;
		}
		if(y6 < 8 && board[x][y6].getColor() != color) {
			int[] coord = {x, y6};
			moves2.add(coord);
		}
		int x7 = x - 1;
		while(x7 > -1 && board[x7][y] == null) {
			int[] coord = {x7, y};
			moves.add(coord);
			x7 -= 1;
		}
		if(x7 > -1 && board[x7][y].getColor() != color) {
			int[] coord = {x7, y};
			moves2.add(coord);
		}
		int y7 = y - 1;
		while(y7 > -1 && board[x][y7] == null) {
			int[] coord = {x, y7};
			moves.add(coord);
			y7 -= 1;
		}
		if(y7 > -1 && board[x][y7].getColor() != color) {
			int[] coord = {x, y7};
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
		Piece Rock = new Rock(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		return Rock;
	}

}
