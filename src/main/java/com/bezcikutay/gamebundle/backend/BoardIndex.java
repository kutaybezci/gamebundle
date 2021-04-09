package com.bezcikutay.gamebundle.backend;

public class BoardIndex {
	private int row;
	private int column;

	public BoardIndex(int row, int column) {
		this.row = row;
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public BoardIndex copy() {
		return new BoardIndex(this.row, this.column);
	}

	public boolean equals(BoardIndex boardIndex) {
		return this.row == boardIndex.row && this.column == boardIndex.column;
	}

	public void move(int rowDiff, int columnDiff) {
		this.row += rowDiff;
		this.column += columnDiff;
	}
}
