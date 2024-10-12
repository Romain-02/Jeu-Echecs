package application;

import java.util.ArrayList;

public class King extends Piece {

	private String name;

	public King(char color, int x, int y, boolean firstPlayer, boolean enPassant, boolean alreadyPlayed, String name) {
		super(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		// TODO Auto-generated constructor stub
	}

	@Override
	public ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece[][] board, char color, int x, int y, boolean firstPlayer) {
		// TODO Auto-generated method stub
		ArrayList<int[]> moves = new ArrayList<int[]>(); // d�placements sans prendre de pions
		ArrayList<int[]> moves2 = new ArrayList<int[]>(); // d�placements o� l'on peut prendre
		ArrayList<int[]> moves3 = new ArrayList<int[]>(); // Le Rock
		if(x < 7) {
			if(y > 0) {
				int[] coord = {x + 1, y - 1};
				moves.add(coord);
			}
			if(y < 7) {
				int[] coord = {x + 1, y + 1};
				moves.add(coord);
			}
		int[] coord = {x + 1, y};
		moves.add(coord);
		}
		if(x > 0) {
			if(y > 0) {
				int[] coord = {x - 1, y - 1};
				moves.add(coord);
			}
			if(y < 7) {
				int[] coord = {x - 1, y + 1};
				moves.add(coord);
			}
		int[] coord = {x - 1, y};
		moves.add(coord);
		}
		if(y > 0) {
			int[] coord = {x, y - 1};
			moves.add(coord);
		}
		if(y < 7) {
			int[] coord = {x, y + 1};
			moves.add(coord);
		}
		ArrayList<int[]> freeMoves = new ArrayList<int[]>();
		for(int i = 0; i < moves.size(); i ++) {
			int x1 = moves.get(i)[0];
			int y1 = moves.get(i)[1];
			if(x1 < 8 && x1 > -1 && y1 < 8 && y1 > -1 && board[x1][y1] != null) {
				if(board[x1][y1].getColor() != color) {
					moves2.add(moves.get(i));
				}
			} else {
				freeMoves.add(moves.get(i));
			}
		}
		boolean flag = false;
		if (firstPlayer) {
			if(board[x][y] != null && !board[x][y].getAlreadyPlayed() && y == 4) {
				if(x == 7) {
				if(board[x][7] != null && board[x][7].getName() == "Rock" && !board[x][7].getAlreadyPlayed()) {
					if(board[7][5] == null || board[7][6] == null) {
						flag = true;
						for(int i = 4; i < 7; i ++) {
							if(Board.isAttackedRock(board[x][y].getColor(), 7, i)) {
								flag = false;
							}
						}
					}
				}
				if(flag) {
					int[] move = new int[] {7, 6, 7, 5, 7, 7,    0, 0, 0, 0};
					moves3.add(move);
				}
				} else {
					if(x == 0){
						if(board[x][7] != null && board[x][7].getName() == "Rock" && !board[x][7].getAlreadyPlayed()) {
							if(board[0][5] == null || board[0][6] == null) {
								flag = true;
								for(int i = 4; i < 7; i ++) {
										if(Board.isAttackedRock(board[x][y].getColor(), 0, i)) {
											flag = false;
										}
									}
								}
					}
					if(flag) {
						int[] move = new int[] {0, 6, 0, 5, 0, 7,    0, 0, 0, 0};
						moves3.add(move);
					}
					}
					
			}
			}
		} else {
			if(board[x][y] != null && !board[x][y].getAlreadyPlayed() && y == 3) {
				if(x == 7) {
				if(board[x][0] != null && board[x][0].getName() == "Rock" && !board[x][0].getAlreadyPlayed()) {
					flag = true;
					if(board[7][1] == null || board[7][2] == null || board[7][3] == null) {
						for(int i = 1; i < 5; i ++) {
								if(Board.isAttackedRock(board[x][y].getColor(), 7, i)) {
									flag = false;
								}
							}
					}
				}
				if(flag) {
					int[] move = new int[] {7, 2, 7, 3, 7, 0,    0, 0, 0, 0};
					moves3.add(move);
				}
				} else {
					if(x == 0){
						if(board[x][0] != null && board[x][0].getName() == "Rock" && !board[x][0].getAlreadyPlayed()) {
							flag = true;
							if(board[0][1] == null || board[0][2] == null || board[0][3] == null) {
								for(int i = 1; i < 5; i ++) {
									if(Board.isAttackedRock(board[x][y].getColor(), 70, i)) {
										flag = false;
									}
								}
							}
					}
					}
					if(flag) {
						int[] move = new int[] {0, 2, 0, 3, 0, 0,    0, 0, 0, 0};
						moves3.add(move);
					}
					
			}
			}
		}
		
		ArrayList<ArrayList<int[]>> allMoves = new ArrayList<ArrayList<int[]>>();
		allMoves.add(freeMoves);
		allMoves.add(moves2);
		allMoves.add(moves3);
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
		Piece King = new King(color, x, y, firstPlayer, enPassant, alreadyPlayed, name);
		return King;
	}


}

