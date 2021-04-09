package com.bezcikutay.gamebundle.backend.numberTetris;

import java.util.ArrayList;
import java.util.List;

import com.bezcikutay.gamebundle.backend.BoardIndex;

public class GameBoard {
	private int maxRowCount;
	private List<List<Integer>> boardColumnList = new ArrayList<>();

	public GameBoard(int maxRowCount, int columnCount) {
		this.maxRowCount = maxRowCount;
		for (int i = 0; i < columnCount; i++) {
			boardColumnList.add(new ArrayList<>());
		}
	}

	public int getMaxRowCount() {
		return maxRowCount;
	}

	public int getColumnCount() {
		return this.boardColumnList.size();
	}

	public void setMaxRowCount(int maxRowCount) {
		this.maxRowCount = maxRowCount;
	}

	public boolean contains(BoardIndex boardIndex) {
		if (boardIndex.getRow() < 0 || boardIndex.getColumn() < 0) {
			return false;
		}
		if (boardIndex.getColumn() >= this.boardColumnList.size()) {
			return false;
		}
		List<Integer> boardColumn = this.boardColumnList.get(boardIndex.getColumn());
		if (boardIndex.getRow() >= boardColumn.size()) {
			return false;
		}
		return true;
	}

	private void addNeighbourIfExists(List<BoardIndex> neighbours, BoardIndex boardIndex, int row, int column) {
		BoardIndex neighbour = boardIndex.copy();
		neighbour.move(row, column);
		if (contains(neighbour)) {
			neighbours.add(neighbour);
		}
	}

	public List<BoardIndex> getNeighbours(BoardIndex boardIndex) {
		List<BoardIndex> neighbours = new ArrayList<>();
		addNeighbourIfExists(neighbours, boardIndex, 1, 0);
		addNeighbourIfExists(neighbours, boardIndex, -1, 0);
		addNeighbourIfExists(neighbours, boardIndex, 0, 1);
		addNeighbourIfExists(neighbours, boardIndex, 0, -1);
		return neighbours;
	}

	public List<Integer> getColumn(int column) {
		return this.boardColumnList.get(column);
	}

	public int getValue(BoardIndex boardIndex) {
		return getColumn(boardIndex.getColumn()).get(boardIndex.getRow());
	}

	public void setValue(BoardIndex boardIndex, int value) {
		getColumn(boardIndex.getColumn()).set(boardIndex.getRow(), value);
	}

	public List<BoardIndex> remove(BoardIndex boardIndex) {
		List<Integer> boardColumn = getColumn(boardIndex.getColumn());
		boardColumn.remove(boardIndex.getRow());
		List<BoardIndex> upperSquares = new ArrayList<>();
		for (int r = boardIndex.getRow(); r < boardColumn.size(); r++) {
			BoardIndex upper = new BoardIndex(boardIndex.getColumn(), r);
			upperSquares.add(upper);
		}
		return upperSquares;
	}

	public boolean isColumnOver(int column) {
		return getColumn(column).size() >= this.maxRowCount;
	}

	public int top(int column) {
		List<Integer> boardColumn = getColumn(column);
		if (boardColumn.isEmpty()) {
			return 0;
		}
		return boardColumn.get(boardColumn.size() - 1);
	}

	public boolean canPush(int column, int value) {
		List<Integer> boardColumn = getColumn(column);
		return boardColumn.size() < this.maxRowCount - 1 || value == top(column);
	}
}
