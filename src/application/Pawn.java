package application;

import java.util.ArrayList;

public class Pawn extends Piece {

	private boolean alreadyPlayed;
	private String name;

	public Pawn(char color, int x, int y, boolean firstPlayer, boolean enPassant, boolean alreadyPlayed, String name) {
		super(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);

		// TODO Auto-generated constructor stub
	}



	public boolean getAlreadyPlayed() {
		return alreadyPlayed;
	}

	public void setAlreadyPlayed(boolean alreadyPlayed) {
		this.alreadyPlayed = alreadyPlayed;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
	public ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece[][] board, char color, int x, int y, boolean firstPlayer) {
		// TODO Auto-generated method stub

		ArrayList<int[]> moves = new ArrayList<int[]>(); // pour la prise avoir les coord du pion d�plac� apr�s l'avoir d�plac�
		ArrayList<int[]> moves2 = new ArrayList<int[]>(); // pour la prise avoir les coord du pion d�plac� apr�s prise
		
		if(color == 'b' && firstPlayer || color == 'w' && !firstPlayer) {
			if(x == 1 && board[x + 1][y] == null && board[x + 2][y] == null) {
				int[] coord = {x + 2, y};
				moves.add(coord);
			}
			if(x < 7){
					if(y < 7){
						if(board[x + 1][y + 1] != null && board[x + 1][y + 1].getColor() != color) {
							int[] coord = {x + 1, y + 1};
							moves2.add(coord);
						} else {
							if(board[x][y + 1] != null && board[x][y + 1].getColor() != color && board[x][y + 1].getName() == "Pawn" && board[x][y + 1].isEnPassant()) {
								int[] coord = {x + 1, y + 1, x, y + 1};
								moves2.add(coord);
							}
						}
					}
					if(y > 0){
						if(board[x + 1][y - 1] != null && board[x + 1][y - 1].getColor() != color) {
							int[] coord = {x + 1, y - 1};
							moves2.add(coord);
						} else {
							if (board[x][y - 1] != null && board[x][y - 1].getColor() != color && board[x][y - 1].getName() == "Pawn" && board[x][y - 1].isEnPassant()) {
								int[] coord = {x + 1, y - 1, x, y - 1};
								moves2.add(coord);
							}
						}
					}
					if(board[x + 1][y] == null) {
						int[] coord = {x + 1, y};
						moves.add(coord);
					}
			}
		} else {
			if(color == 'w' && firstPlayer || color == 'b' && !firstPlayer) {
				if(x == 6 && board[x - 1][y] == null && board[x - 2][y] == null) {
					int[] coord = {x - 2, y};
					moves.add(coord);
				}
				if(x > 0){
						if(y < 7){
							if(board[x - 1][y + 1] != null && board[x - 1][y + 1].getColor() != color) {
								int[] coord = {x - 1, y + 1};
								moves2.add(coord);
							} else {
								if(board[x][y + 1] != null && board[x][y + 1].getColor() != color && board[x][y + 1].getName() == "Pawn" && board[x][y + 1].isEnPassant()) {
									int[] coord = {x - 1, y + 1, x, y + 1};
									moves2.add(coord);
								}
							}
						}
						if(y > 0){
							if(board[x - 1][y - 1] != null && board[x - 1][y - 1].getColor() != color) {
								int[] coord = {x - 1, y - 1};
								moves2.add(coord);
							} else {
								if(board[x][y - 1] != null && board[x][y - 1].getColor() != color && board[x][y - 1].getName() == "Pawn" && board[x][y - 1].isEnPassant()) {
									int[] coord = {x - 1, y - 1, x, y - 1};
									moves2.add(coord);
								}
							}
						}
					if(board[x - 1][y] == null) {
						int[] coord = {x - 1, y};
						moves.add(coord);
					}
				}
			}
		}
	ArrayList<ArrayList<int[]>> allMoves = new ArrayList<ArrayList<int[]>>();
	allMoves.add(moves);
	allMoves.add(moves2);
	return allMoves;
	}

	@Override
	public Piece copyPiece(){
		Piece Pawn = new Pawn(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		return Pawn;
	}


}
