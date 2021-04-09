package com.bezcikutay.gamebundle.backend.numberTetris;

public class BoardExport {
	private int[][] board;
	private int score;
	private int special;
	private int nextNumber;
	private boolean turnOngoing;
	private State state;
	private int min;
	private int max;

	public int getMin() {
		return min;
	}

	public void setMin(int min) {
		this.min = min;
	}

	public int getMax() {
		return max;
	}

	public void setMax(int max) {
		this.max = max;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public boolean isTurnOngoing() {
		return turnOngoing;
	}

	public void setTurnOngoing(boolean turnOngoing) {
		this.turnOngoing = turnOngoing;
	}

	public int[][] getBoard() {
		return board;
	}

	public void setBoard(int[][] board) {
		this.board = board;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public int getNextNumber() {
		return nextNumber;
	}

	public void setNextNumber(int nextNumber) {
		this.nextNumber = nextNumber;
	}
}
