package application;

import java.util.HashMap;
import java.util.ArrayList;
import java.util.Arrays;


public class Board {

	private Piece[][] board = new Piece [8][8];
	private char[][]colors = {{'w', 'b'}, {'b', 'w'}};
	private char c1;
	private char c2;
	private ArrayList<Piece> blackPieces;
	private ArrayList<Piece> whitePieces;
	private boolean firstPlayer;
	private Piece PieceEnPassant;
	int[] whiteKing;
	int[] blackKing;

	public Board(int color, boolean start, ArrayList<Piece> whitePieces, ArrayList<Piece> blackPieces) {
		this.PieceEnPassant = null;
		if(color == 0) {
			this.firstPlayer = true;
			this.whiteKing = new int[] {7, 4};
			this.blackKing = new int[] {0, 4};
			
		} else {
			this.firstPlayer = false;
			this.whiteKing = new int[] {0, 4};
			this.blackKing = new int[] {7, 4};
		}

		this.c1 = colors[color][1]; // 
		this.c2 = colors[color][0]; // 

		if(start) {
			this.blackPieces = new ArrayList<Piece>();
			this.setWhitePieces(new ArrayList<Piece>());

			this.board[0][0] = new Rock(this.c1, 0, 0, true, false, false, "Rock");
			this.board[0][1] = new Knight(this.c1, 0, 1, true, false, false, "Knight");
			this.board[0][2] = new Bishop(this.c1, 0, 2, true, false, false, "Bishop");
			this.board[0][3] = new Queen(this.c1, 0 , 3, true, false, false, "Queen");
			this.board[0][4] = new King(this.c1, 0, 4, true, false, false, "King");
			this.board[0][5] = new Bishop(this.c1, 0, 5, true, false, false, "Bishop");
			this.board[0][6] = new Knight(this.c1, 0, 6, true, false, false, "Knight");
			this.board[0][7] = new Rock(this.c1, 0, 7, true, false, false, "Rock");

			this.board[7][0] = new Rock(this.c2, 7, 0, false, false, false, "Rock");
			this.board[7][1] = new Knight(this.c2, 7, 1, false, false, false, "Knight");
			this.board[7][2] = new Bishop(this.c2, 7, 2, false, false, false, "Bishop");
			this.board[7][3] = new Queen(this.c2, 7, 3, false, false, false, "Queen");
			this.board[7][4] = new King(this.c2, 7, 4, false, false, false, "King");
			this.board[7][5] = new Bishop(this.c2, 7, 5, false, false, false, "Bishop");
			this.board[7][6] = new Knight(this.c2, 7, 6, false, false, false, "Knight");
			this.board[7][7] = new Rock(this.c2, 7, 7, false, false, false, "Rock");


			for(int i = 0; i < 8; i ++ ) {
				this.board[1][i] = new Pawn(this.c1, 1, i, true, false, false, "Pawn");
			}
			for(int i = 0; i < 8; i ++ ) {
				this.board[6][i] = new Pawn(this.c2, 6, i, false, false, false, "Pawn");
			}

			if(c1 == 'b') {
				for(int i = 0; i < 8; i ++ ) {
					this.blackPieces.add(this.board[0][i]);
					this.blackPieces.add(this.board[1][i]);
					this.getWhitePieces().add(this.board[7][i]);
					this.getWhitePieces().add(this.board[6][i]);

				}
			} else {
				for(int i = 0; i < 8; i ++ ) {
					this.blackPieces.add(this.board[7][i]);
					this.blackPieces.add(this.board[6][i]);
					this.getWhitePieces().add(this.board[0][i]);
					this.getWhitePieces().add(this.board[1][i]);
				}
			}
		} else {
			this.whitePieces = whitePieces;
			this.blackPieces = blackPieces;
			for(Piece elt : whitePieces) {
				this.board[elt.getX()][elt.getY()] = elt;
			}
			for(Piece elt : blackPieces) {
				this.board[elt.getX()][elt.getY()] = elt;
			}
		}

	}
	public static final String ANSI_RESET = "\u001B[33m";
	
	public static final String ANSI_BLACK = "\u001B[30m";
	
	public static final String ANSI_WHITE = "\u001B[34m";
	
	public static final String ANSI_BACK_WHITE = "\u001B[47m";
	
	public static final String ANSI_BACK_GREEN = "\033[1;42m";
	
	public String getOtherColor(String color) {
		if(color != ANSI_BACK_WHITE) {
			return ANSI_BACK_WHITE;
		} else {
			return ANSI_BACK_GREEN;
		}
	}
	
	public int[] getKing(char color) {
		if(color == 'w') {
			return this.whiteKing;
		}
		return this.blackKing;
	}
	
	public void setKing(char color, int[] coord) {
		if(color == 'w') {
			this.whiteKing = coord;
		}
		this.blackKing = coord;
	}
	
	public void showBoard(){
		HashMap<String, String> mapValuePiece = new HashMap<String, String>();
		mapValuePiece.put("Queen", "9");
		mapValuePiece.put("King", "100");
		mapValuePiece.put("Rock", "5");
		mapValuePiece.put("Bishop", "3.1");
		mapValuePiece.put("Knight", "3");
		mapValuePiece.put("Pawn", "1");
		mapValuePiece.put("null", "0");
		HashMap<String, String> mapColor = new HashMap<String, String>();
		mapColor.put("b", ANSI_BLACK);
		mapColor.put("w", ANSI_WHITE);
		String color = ANSI_BACK_WHITE;
		for(int i = 0; i < 8; i ++){
			System.out.println(color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        ");
			for(int j = 0; j < 8; j ++){
				if(this.getBoard()[i][j] != null){
					System.out.print(this.convertNumber(mapValuePiece.get(this.getBoard()[i][j].getName()), mapColor.get(String.valueOf(this.getBoard()[i][j].getColor())), color));
				} else {
					System.out.print(color + ANSI_RESET + "   0    " );
				}
				color = this.getOtherColor(color);
			}
			System.out.println("");
			System.out.println(color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        " + color + "        " + this.getOtherColor(color) + "        ");
			color = this.getOtherColor(color);
		}
	}
	
	public String convertNumber(String number, String colorPiece, String colorCase){
		if(number.length() == 1){
			return colorPiece + colorCase + "   " + number + "    "; 
		} else {
			if(number.length() == 3){
				return colorPiece + colorCase + "  " + number + "   "; 
			}
		}
		return colorPiece + colorCase + " " + number + " ";
	}

	public char otherColor(char color) {
		if(color == 'w') {
			return 'b';
		}
		return 'w';
	}

	public ArrayList<Piece> getList(char color){
		if(color == 'w') {
			return getWhitePieces();
		}
		return blackPieces;
	}

	public Piece getPiece(int x, int y) {
		return this.board[x][y];
	}
	
	public Piece findPieceInList(ArrayList<Piece> List, int[] coord) {
		for(Piece p : List) {
			int x = p.getX();
			int y = p.getY();
			if(x == coord[0] && y == coord[1]) {
				return p;
			}
		}
		return null;
	}

	public void movePiece(int[] coord1, int[] coord2) {
		if(coord1[0] != coord2[0] || coord1[1] != coord2[1]){
			if(this.board[coord1[0]][coord1[1]] != null){
				char color = this.board[coord1[0]][coord1[1]].getColor();
				if(this.board[coord2[0]][coord2[1]] != null || coord2.length == 4) {
					if(color == 'b' && this.getWhitePieces().contains(this.board[coord2[0]][coord2[1]])) {		
						if (coord2.length == 4) {											// v�rifie si il ne s'agit pas d'une prise en passant
							this.getWhitePieces().remove(this.board[coord2[2]][coord2[3]]);
							this.board[coord2[2]][coord2[3]] = null;
						} else {
							this.getWhitePieces().remove(this.board[coord2[0]][coord2[1]]);	// enl�ve la pi�ce de la liste des pi�ces par couleur
						}
					} else {
						if(color == 'w' && this.blackPieces.contains(this.board[coord2[0]][coord2[1]])) {
							if (coord2.length == 4) {
								this.blackPieces.remove(this.board[coord2[2]][coord2[3]]);
								this.board[coord2[2]][coord2[3]] = null;
						} else {
							this.blackPieces.remove(this.board[coord2[0]][coord2[1]]);
						}
				}
				}
				}
				if(coord2.length == 10) {
					// le roi
					int[] coordKing = {coord2[0], coord2[1]};
					this.setKing(this.getBoard()[coord1[0]][coord1[1]].getColor(), coordKing);	
					
					// la tour
					this.board[coord2[4]][coord2[5]].setX(coord2[2]);
					this.board[coord2[4]][coord2[5]].setY(coord2[3]);
					Piece piece = this.board[coord2[4]][coord2[5]];
					this.board[coord2[2]][coord2[3]] = piece.copyPiece();
					
					if(color == 'w') {
						this.getWhitePieces().set(this.getWhitePieces().indexOf(this.board[coord2[4]][coord2[5]]), this.board[coord2[2]][coord2[3]]);
					} else {
						this.getBlackPieces().set(this.getBlackPieces().indexOf(this.board[coord2[4]][coord2[5]]), this.board[coord2[2]][coord2[3]]);
					}
					this.board[coord2[4]][coord2[5]] = null;
					
				} else if(coord2.length == 4){
					this.board[coord1[0]][coord1[1]].setX(coord2[0]);
					this.board[coord1[0]][coord1[1]].setY(coord2[1]);
					Piece piece = this.board[coord1[0]][coord1[1]];
					this.board[coord2[0]][coord2[1]] = piece.copyPiece();
					
					if(color == 'w') {
						this.getWhitePieces().set(this.getWhitePieces().indexOf(this.board[coord1[0]][coord1[1]]), this.board[coord2[0]][coord2[1]]);
					} else {
						this.getBlackPieces().set(this.getBlackPieces().indexOf(this.board[coord1[0]][coord1[1]]), this.board[coord2[0]][coord2[1]]);
					}
					this.board[coord2[2]][coord2[3]] = null;
					this.board[coord1[0]][coord1[1]] = null;
				
				} else {
				
					if(this.getBoard()[coord1[0]][coord1[1]].getName() == "King") {
						this.setKing(this.getBoard()[coord1[0]][coord1[1]].getColor(), coord2);	
					}
					
					this.board[coord1[0]][coord1[1]].setX(coord2[0]);
					this.board[coord1[0]][coord1[1]].setY(coord2[1]);
					Piece piece = this.board[coord1[0]][coord1[1]];
					this.board[coord2[0]][coord2[1]] = piece.copyPiece();
					
					if(color == 'w') {
						this.getWhitePieces().set(this.getWhitePieces().indexOf(this.board[coord1[0]][coord1[1]]), this.board[coord2[0]][coord2[1]]);
					} else {
						this.getBlackPieces().set(this.getBlackPieces().indexOf(this.board[coord1[0]][coord1[1]]), this.board[coord2[0]][coord2[1]]);
					}
					this.board[coord1[0]][coord1[1]] = null;
				}
				
				if(this.PieceEnPassant != null) {			//enl�ve la prise en passant si un pion venait d'�tre avanc� de deux
					if(this.board[this.PieceEnPassant.getX()][this.PieceEnPassant.getY()] != null) {
						this.board[this.PieceEnPassant.getX()][this.PieceEnPassant.getY()].setEnPassant(false);
					}
					this.PieceEnPassant = null;
				}
	
				if(this.board[coord2[0]][coord2[1]] != null && this.board[coord2[0]][coord2[1]].getAlreadyPlayed() == false) {
					this.board[coord2[0]][coord2[1]].setAlreadyPlayed(true);
					if(Math.abs(coord2[0] - coord1[0]) == 2) {
						this.PieceEnPassant = this.board[coord2[0]][coord2[1]];
						this.board[coord2[0]][coord2[1]].setEnPassant(true);
					}
				} 
			}
		}
	}


	public HashMap<Piece, ArrayList<ArrayList<int[]>>> getAllMoves(char color) {
		HashMap<Piece, ArrayList<ArrayList<int[]>>> map = new HashMap<Piece, ArrayList<ArrayList<int[]>>>();
		for(Piece p: this.getList(color)) {
			map.put(p, p.getMovements(this, this.board, color, p.getX(), p.getY(), this.firstPlayer));
		}
		return map;
	}
	
	public HashMap<Piece, ArrayList<int[]>> getLegalMovesBoard(char color){
		HashMap<Piece, ArrayList<ArrayList<int[]>>> map = this.getAllMoves(color);
		HashMap<Piece, ArrayList<int[]>> newMap = new HashMap<Piece, ArrayList<int[]>>();
		for(Piece p : map.keySet()) {
			ArrayList<int[]> listMoves = new ArrayList<int[]>();
				for(ArrayList<int[]> list : map.get(p)) {
					for(int[] coord : list) {
						int[] coordPiece = {p.getX(), p.getY()};
						if(!this.copyAndPlay(color, coordPiece, coord).check(color)) {
							listMoves.add(coord);
						}
				}
			}
			if(!listMoves.isEmpty()) {
				newMap.put(p, listMoves);
			}
		}
		return newMap;
	}
	public ArrayList<int[]> getLegalMovesPiece(char color, int x, int y){
		ArrayList<ArrayList<int[]>> listMoves = this.board[x][y].getMovements(this, this.board, this.board[x][y].getColor(), x, y, this.getFirstPlayer());
		ArrayList<int[]> newMoves = new ArrayList<int[]>();
		int[] coordPiece = {x, y};
		for(ArrayList<int[]> list : listMoves) {
			for(int[] coord : list) {
				if(!this.copyAndPlay(color, coordPiece, coord).check(color)) {
					newMoves.add(coord);
				}
			}
		}
		return newMoves;
	}

	public boolean check(char color) {
		int[] coordKing = this.getKing(color);
		HashMap<Piece, ArrayList<ArrayList<int[]>>> map = this.getAllMoves(this.otherColor(color));
		for(Piece p : map.keySet()) {
			for(int[] coord : map.get(p).get(1)) {
				if(coord[0] == coordKing[0] && coord[1] == coordKing[1]){
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean isAttackedRock(char color, int x, int y) {
		HashMap<Piece, ArrayList<ArrayList<int[]>>> map = new HashMap<Piece, ArrayList<ArrayList<int[]>>>();
		for(Piece p: this.getList(this.otherColor(color))) {
			if(p.getName() != "King") {
				map.put(p, p.getMovements(this, this.board, color, p.getX(), p.getY(), this.firstPlayer));
			}
		}
		for(Piece p : map.keySet()) {
			if(p.getName() != "King") {
				for(int[] coord : map.get(p).get(1)) {
					if(coord[0] == x && coord[1] == y){
						return true;
					}
				}
			}
		}
		return false;
	}
	
	public ArrayList<Piece> copyListPieces(ArrayList<Piece> List){
		ArrayList<Piece> newList = new ArrayList<Piece>();
		for(Piece p : List) {
			newList.add(p.copyPiece());
		}
		return newList;
	}

	public Board copyAndPlay(char color, int[] coord1, int[] coord2){
		ArrayList<Piece> whitePiecesBis = this.copyListPieces(this.getWhitePieces());
		ArrayList<Piece> blackPiecesBis = this.copyListPieces(this.getBlackPieces());
		if(this.firstPlayer) {
		    Board board = new Board(0, false, whitePiecesBis, blackPiecesBis);
			board.movePiece(coord1, coord2);
			return board;
		} else {
			Board board = new Board(1, false, whitePiecesBis, blackPiecesBis);
			board.movePiece(coord1, coord2);
			this.setWhitePieces(whitePiecesBis);
			this.setBlackPieces(blackPiecesBis);
			return board;
		}


	}

	public HashMap<Piece, ArrayList<int[]>> possibleMoves(char color){
		HashMap<Piece, ArrayList<ArrayList<int[]>>> map = this.getAllMoves(color);
		HashMap<Piece, ArrayList<int[]>> legal_moves = new HashMap<Piece, ArrayList<int[]>>();
		for(Piece p : map.keySet()) {
			ArrayList<int[]> new_moves = new ArrayList<int[]>();
			int[] coord1 = {p.getX(), p.getY()};
			for(ArrayList<int[]> type_move : map.get(p)) {
				for(int[] coord2 : type_move) {
					Board new_board = this.copyAndPlay(color, coord1, coord2);
				 	if(!new_board.check(color)) {
				 		new_moves.add(coord2);
				}
			}
		}
			if(!new_moves.isEmpty()) {
				legal_moves.put(p, new_moves);
			}
		}
		return legal_moves;
	}
	
	public Piece[][] copyBoard(Piece[][] boardToCopy) {
		Piece[][] newBoard = new Piece[8][8];
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (boardToCopy[i][j] != null){
					newBoard[i][j] = boardToCopy[i][j].copyPiece();
				}
			}
		}
		return newBoard;
	}
	
	public boolean equalBoard(Piece[][] board) {
		
		for(int i = 0; i < 8; i++) {
			for(int j = 0; j < 8; j++) {
				if (!((this.board[i][j] == null && board[i][j] == null) || ((this.board[i][j] != null && board[i][j] != null) && this.board[i][j].equalPiece(board[i][j])))) return false;
			}
		}
		return true;
	}

	public boolean checkmate(char color) {
		return this.getLegalMovesBoard(color).isEmpty() && this.check(color);
	}

	public boolean pat(char color) {
		return this.possibleMoves(color).isEmpty() && !this.check(color);
	}

	public void setBoard(Piece[][] board) {
		this.board = board;
	}

	public Piece[][] getBoard(){
		return this.board;
	}

	public ArrayList<Piece> getWhitePieces() {
		return whitePieces;
	}

	public void setWhitePieces(ArrayList<Piece> whitePieces) {
		this.whitePieces = whitePieces;
	}

	public ArrayList<Piece> getBlackPieces() {
		return blackPieces;
	}

	public void setBlackPieces(ArrayList<Piece> blackPieces) {
		this.blackPieces = blackPieces;
	}

	public boolean getFirstPlayer() {
		return this.firstPlayer;
	}
	
	public char getColor() {
		return this.c1;
	}
	
	public char getOtherColor() {
		return this.c2;
	}
	
	public char getOther(char other) {
		if(other == 'o') {
			return 'n';
		}
		return 'o';
	}
	
	public char getOtherColor2(char color) {
		if(color == 'w') {
			return 'b';
		}
		return 'w';
	}
	
	public void setPiece(int[] coord, Piece piece){
		this.board[coord[0]][coord[1]] = piece;
	}
	
	public int evaluationBoard() {
		HashMap<String, String> mapValuePiece = new HashMap<String, String>();
		ArrayList<ArrayList<int[]>> dep;
		mapValuePiece.put("Queen", "9");
		mapValuePiece.put("King", "100");
		mapValuePiece.put("Rock", "5");
		mapValuePiece.put("Bishop", "3.1");
		mapValuePiece.put("Knight", "3");
		mapValuePiece.put("Pawn", "1");
		mapValuePiece.put("null", "0");
		HashMap<String, String> mapMultiplicator = new HashMap<String, String>();
		mapMultiplicator.put("b", "-1");
		mapMultiplicator.put("w", "1");
		board = this.getBoard();
		int signe;
		int counter = 0;
		for(int i = 0; i < 8; i ++) {
			for(int j = 0; j < 8; j ++) {
				if(board[i][j] != null) {
					signe = Integer.parseInt(mapMultiplicator.get(String.valueOf(board[i][j].getColor())));
					counter += signe * Math.round(Float.parseFloat(mapValuePiece.get(board[i][j].getName()))) * 10;
				}
			}
		}
		if(this.getFirstPlayer()){
			return -counter;
		} else {
			return counter;
		}
	}
	
	public int[] minmax(char color, int depth, int alpha, int beta, boolean maximazingPlayer){
		int[] play = {0, 0, 0, 0} ;
		if(depth == 0) {
			int[] resultat = {this.evaluationBoard(), 0, 0, 0, 0};
			return resultat;
		}else if(this.checkmate(color)) {
			if(maximazingPlayer) {
				int[] resultat = {-100000, 0, 0, 0, 0};
				return resultat;
			}
			int[] resultat = {100000, 0, 0, 0, 0};
			return resultat;
		} else { 
			if(maximazingPlayer) {
				int maxNote = -1000;
				HashMap<Piece, ArrayList<int[]>> moves = this.getLegalMovesBoard(color);
				for(Piece p : moves.keySet()) {
					for(int[] coord2 : moves.get(p)) {
						int[] coord1 = {p.getX(), p.getY()};
						Board newBoard = this.copyAndPlay(color, coord1, coord2);
						int evalMinmax = newBoard.minmax(this.getOtherColor2(color), depth-1, alpha, beta, false)[0];
						if(maxNote < evalMinmax) {
							maxNote = evalMinmax;
							play = new int[] {coord1[0], coord1[1], coord2[0], coord2[1]};
						} else if (maxNote < evalMinmax && Math.random() > 0.3) {
							maxNote = evalMinmax;
							play = new int[] {coord1[0], coord1[1], coord2[0], coord2[1]};
						}
						alpha =  Math.max(alpha, evalMinmax);
						if(alpha >= beta) {
							break;
						}
					}
				}
			int[] resultat = new int[] {maxNote, play[0], play[1], play[2], play[3]};
			return resultat;
			}
			else {
				int minNote = 1000;
				HashMap<Piece, ArrayList<int[]>> moves = this.getLegalMovesBoard(color);
				
				for(Piece p : moves.keySet()) {
					for(int[] coord2 : moves.get(p)) {
						int[] coord1 = {p.getX(), p.getY()};
						Board newBoard = this.copyAndPlay(color, coord1, coord2);
						int evalMinmax = newBoard.minmax(this.getOtherColor2(color), depth-1, alpha, beta, true)[0];
						if(minNote > evalMinmax) {
							minNote = evalMinmax;
							play = new int[] {coord1[0], coord1[1], coord2[0], coord2[1]};
						}
						beta =  Math.min(beta, evalMinmax);
						if(alpha >= beta) {
							break;
						}			
					}
				}
				int[] resultat = new int[] {minNote, play[0], play[1], play[2], play[3]};
				return resultat;
			}
		}
	}
}


