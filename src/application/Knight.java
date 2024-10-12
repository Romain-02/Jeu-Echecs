package application;

import java.util.ArrayList;

public class Knight extends Piece {

	private String name;

	public Knight(char color, int x, int y, boolean firstPlayer, boolean enPassant, boolean alreadyPlayed, String name) {
		super(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		// TODO Auto-generated constructor stub
	}



	@Override
	public ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece[][] board, char color, int x, int y, boolean firstPlayer) {
		// TODO Auto-generated method stub
		ArrayList<int[]> moves = new ArrayList<int[]>();   // d�placements sans prendre de pions
		ArrayList<int[]> moves2 = new ArrayList<int[]>();  // d�placements o� l'on peut prendre
		if(x > 0) {
			if(y < 6) {
				int[] coord = {x - 1, y + 2};
				moves.add(coord);
			}
			if(y > 1) {
				int[] coord = {x - 1, y - 2};
				moves.add(coord);
			}
		}
		if(x > 1) {
			if(y < 7) {
				int[] coord = {x - 2, y + 1};
				moves.add(coord);
			}
			if(y > 0) {
				int[] coord = {x - 2, y - 1};
				moves.add(coord);
			}
		}
		if(x < 7) {
			if(y < 6) {
				int[] coord = {x + 1, y + 2};
				moves.add(coord);
			}
			if(y > 1) {
				int[] coord = {x + 1, y - 2};
				moves.add(coord);
			}
		}
		if(x < 6) {
			if(y < 7) {
				int[] coord = {x + 2, y + 1};
				moves.add(coord);
			}
			if(y > 0) {
				int[] coord = {x + 2, y - 1};
				moves.add(coord);
			}
		}
		for(int i = 0; i < moves.size(); i ++) {
			int x1 = moves.get(i)[0];
			int y1 = moves.get(i)[1];
			if(x1 < 8 && x1 > -1 && y1 < 8 && y1 > -1 && board[x1][y1] != null) {
				if(board[x1][y1].getColor() != color) {
					moves2.add(moves.get(i));
				}
			moves.remove(moves.get(i));
			}
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
		Piece Knight = new Knight(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		return Knight;
	}

}
