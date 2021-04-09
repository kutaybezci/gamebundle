package com.bezcikutay.gamebundle.backend.numberTetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bezcikutay.gamebundle.backend.BoardIndex;

public class NumberTetrisGame {
	private static final Logger logger = LoggerFactory.getLogger(NumberTetrisGame.class);
	private int score = 0;

	private int special = 0;
	private int base = 1;
	private int distinctBlockCount;
	private int inputRange;
	private Random random = new Random();
	private int nextNumber;
	private State state;

	private Stack<BoardIndex> changeStack = new Stack<>();
	private int currentMaxValue;

	private GameBoard gameBoard;

	public NumberTetrisGame(int rowCount, int columnCount, int inputRange, int distinctBlockCount) {
		this.gameBoard = new GameBoard(rowCount, columnCount);
		this.distinctBlockCount = distinctBlockCount;
		this.state = State.Playing;
		this.inputRange = inputRange;
		this.currentMaxValue = this.inputRange;
		this.nextNumber = getNextNumber();
	}

	public int getScore() {
		return score;
	}

	private int getNextNumber() {
		int rand = random.nextInt(inputRange);
		return this.base + rand;
	}

	public BoardExport export() {
		BoardExport boardExport = new BoardExport();
		boardExport.setBoard(new int[this.gameBoard.getMaxRowCount()][this.gameBoard.getColumnCount()]);
		for (int c = 0; c < this.gameBoard.getColumnCount(); c++) {
			List<Integer> boardColumn = this.gameBoard.getColumn(c);
			for (int r = 0; r < boardColumn.size(); r++) {
				boardExport.getBoard()[r][c] = boardColumn.get(r);
			}
		}
		boardExport.setScore(this.score);
		boardExport.setSpecial(this.special);
		boardExport.setNextNumber(nextNumber);
		boardExport.setState(this.state);
		boardExport.setMin(this.base);
		boardExport.setMax(this.base + this.inputRange - 1);
		return boardExport;
	}

	public void startTurn(int column) {
		if (this.state != State.Playing) {
			return;
		}
		if (!this.gameBoard.canPush(column, this.nextNumber)) {
			return;
		}
		List<Integer> boardColumn = this.gameBoard.getColumn(column);
		boardColumn.add(nextNumber);
		BoardIndex boardIndex = new BoardIndex(boardColumn.size() - 1, column);
		changeStack.push(boardIndex);
		this.nextNumber = getNextNumber();
		this.state = State.InTurn;
	}

	public String boardText(BoardExport boardExport) {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		for (int r = 0; r < boardExport.getBoard().length; r++) {
			int[] row = boardExport.getBoard()[r];
			for (int c = 0; c < row.length; c++) {
				int value = row[c];
				sb.append(String.format("%5d", value));
			}
			sb.append(System.lineSeparator());
		}
		sb.append("CS:");
		changeStack.stream().forEach(t -> {
			sb.append("r:");
			sb.append(t.getRow());
			sb.append("c:");
			sb.append(t.getColumn());
			sb.append("; ");
		});
		sb.append(System.lineSeparator());
		sb.append("MIN:");
		sb.append(boardExport.getMin());
		sb.append(" MAX:");
		sb.append(boardExport.getMax());
		sb.append(System.lineSeparator());
		sb.append("Special:");
		sb.append(boardExport.getSpecial());
		sb.append("CurrentMAX:");
		sb.append(this.currentMaxValue);
		sb.append(System.lineSeparator());
		sb.append(boardExport.getState().name());
		return sb.toString();
	}

	public synchronized void subTurn() {
		logger.info(boardText(export()));
		if (changeStack.isEmpty()) {
			Optional<BoardIndex> lessThanMin = getLessThanMin();
			if (lessThanMin.isPresent()) {
				remove(lessThanMin.get());
				subTurn();
				return;
			}
			if (isGameOver()) {
				this.state = State.GameOver;
				return;
			}
			this.state = State.Playing;
			return;
		}
		BoardIndex boardIndex = changeStack.pop();
		if (!gameBoard.contains(boardIndex)) {
			return;
		}
		int value = this.gameBoard.getValue(boardIndex);
		List<BoardIndex> neighbours = gameBoard.getNeighbours(boardIndex);
		List<BoardIndex> toBeRemoveds = new ArrayList<>();
		boolean match = false;
		for (BoardIndex neighbour : neighbours) {
			int neighbourValue = this.gameBoard.getValue(neighbour);
			if (value == neighbourValue) {
				this.score += value;
				changeStack.push(neighbour);
				changeStack.push(boardIndex);

				int mergeValue = gameBoard.getValue(boardIndex) + 1;
				if (mergeValue > currentMaxValue) {
					this.special += mergeValue - currentMaxValue;
					currentMaxValue = mergeValue;

					if (currentMaxValue > base + distinctBlockCount) {
						base++;
					}
				}
				this.gameBoard.setValue(boardIndex, mergeValue);
				toBeRemoveds.add(neighbour);
				match = true;
			}
		}
		for (BoardIndex toBeRemoved : toBeRemoveds) {
			remove(toBeRemoved);
		}
		if (!match) {
			subTurn();
		}
	}

	public void remove(BoardIndex toBeRemoved) {
		List<BoardIndex> uppers = this.gameBoard.remove(toBeRemoved);
		uppers.stream().forEach(t -> changeStack.push(t));
	}

	public void crush(BoardIndex index) {
		if (this.special > 0 && this.state == State.Playing) {
			this.special--;
			this.changeStack.add(index);
			remove(index);
			this.state = State.InTurn;
		}
	}

	public Optional<BoardIndex> getLessThanMin() {
		for (int c = 0; c < this.gameBoard.getColumnCount(); c++) {
			List<Integer> boardColumn = this.gameBoard.getColumn(c);
			for (int r = 0; r < boardColumn.size(); r++) {
				BoardIndex boardIndex = new BoardIndex(r, c);
				int value = this.gameBoard.getValue(boardIndex);
				if (value < base) {
					return Optional.of(boardIndex);
				}
			}
		}
		return Optional.empty();

	}

	public State getState() {
		return state;
	}

	private boolean isGameOver() {
		for (int c = 0; c < this.gameBoard.getColumnCount(); c++) {
			if (this.gameBoard.canPush(c, nextNumber)) {
				return false;
			}
		}
		return true;
	}

	public boolean isFilled(BoardIndex boardIndex) {
		return this.gameBoard.contains(boardIndex);
	}

	public void change(BoardIndex first, BoardIndex second) {
		if (this.state == State.Playing && //
				this.gameBoard.contains(first) && //
				this.gameBoard.contains(second)) {
			int value = this.gameBoard.getValue(first);
			this.gameBoard.setValue(first, this.gameBoard.getValue(second));
			this.gameBoard.setValue(second, value);
			this.changeStack.add(first);
			this.changeStack.add(second);
			this.state = State.InTurn;
		}
	}

}
