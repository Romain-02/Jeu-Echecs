package application;

import java.util.ArrayList;
import java.util.Arrays;

public abstract class Piece {

	protected char color;
	protected int x;
	protected int y;
	protected boolean firstPlayer;
	protected boolean enPassant;
	protected boolean alreadyPlayed;
	protected char other;

	public Piece(char color, int x, int y, boolean firstPlayer, boolean enPassant, boolean alreadyPlayed, String name) {
		this.color = color;
		this.x = x;
		this.y = y;
		this.firstPlayer = firstPlayer;
		this.enPassant = enPassant;
		this.setAlreadyPlayed(alreadyPlayed);
		this.setName(name);
		this.other = 'o';
	}

	public abstract ArrayList<ArrayList<int[]>> getMovements(Board Board, Piece [][] board, char color, int x, int y, boolean firstPlayer);
	public abstract String getName();
	public abstract void setName(String name);
	public abstract Piece copyPiece();
	
	public boolean equalPiece(Piece p) {
		return this.x == p.x && this.y == p.y && this.getName().equals(p.getName()) && this.color == p.color;
	}
	
	public char getColor() {
		return color;
	}

	public void setColor(char color) {
		this.color = color;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean isFirstPlayer() {
		return firstPlayer;
	}

	public void setFirstPlayer(boolean firstPlayer) {
		this.firstPlayer = firstPlayer;
	}

	public boolean isEnPassant() {
		return enPassant;
	}

	public void setEnPassant(boolean enPassant) {
		this.enPassant = enPassant;
	}

	public boolean getAlreadyPlayed() {
		return alreadyPlayed;
	}

	public void setAlreadyPlayed(boolean alreadyPlayed) {
		this.alreadyPlayed = alreadyPlayed;
	}

	public char getOther() {
		return this.other;
	}

	@Override
	public String toString() {
		return "Piece [color=" + color + ", x=" + x + ", y=" + y + ", firstPlayer=" + firstPlayer + ", enPassant="
				+ enPassant + ", alreadyPlayed=" + alreadyPlayed + ", other=" + other + "]";
	}
}

