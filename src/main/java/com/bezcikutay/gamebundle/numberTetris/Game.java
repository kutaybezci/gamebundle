package com.bezcikutay.gamebundle.numberTetris;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Stack;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bezcikutay.gamebundle.core.BoardIndex;
import com.bezcikutay.gamebundle.core.Dice;

public class Game {
	private static final Logger logger = LoggerFactory.getLogger(Game.class);
	private int score;
	private int special;
	private int inputMin;
	private int inputMax;
	private int nextNumber;
	private State state;
	private Stack<BoardIndex> changeStack = new Stack<>();
	private int currentMaxValue;
	private GameBoard gameBoard;
	private int distinctNumberCount;

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

	public int getInputMin() {
		return inputMin;
	}

	public void setInputMin(int inputMin) {
		this.inputMin = inputMin;
	}

	public int getInputMax() {
		return inputMax;
	}

	public void setInputMax(int inputMax) {
		this.inputMax = inputMax;
	}

	public int getNextNumber() {
		return nextNumber;
	}

	public void setNextNumber(int nextNumber) {
		this.nextNumber = nextNumber;
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Stack<BoardIndex> getChangeStack() {
		return changeStack;
	}

	public void setChangeStack(Stack<BoardIndex> changeStack) {
		this.changeStack = changeStack;
	}

	public int getCurrentMaxValue() {
		return currentMaxValue;
	}

	public void setCurrentMaxValue(int currentMaxValue) {
		this.currentMaxValue = currentMaxValue;
	}

	public GameBoard getGameBoard() {
		return gameBoard;
	}

	public void setGameBoard(GameBoard gameBoard) {
		this.gameBoard = gameBoard;
	}

	public int getDistinctNumberCount() {
		return distinctNumberCount;
	}

	public void setDistinctNumberCount(int distinctNumberCount) {
		this.distinctNumberCount = distinctNumberCount;
	}

	public void startTurn(int column) {
		if (state != State.Playing) {
			return;
		}
		if (!gameBoard.canPush(column, nextNumber)) {
			return;
		}
		List<Integer> boardColumn = this.gameBoard.getColumn(column);
		boardColumn.add(nextNumber);
		BoardIndex boardIndex = new BoardIndex(boardColumn.size() - 1, column);
		changeStack.push(boardIndex);
		setNextNumber();
		state = State.InTurn;
	}

	public void logBoard() {
		StringBuilder sb = new StringBuilder(System.lineSeparator());
		for (int c = 0; c < gameBoard.getColumnCount(); c++) {
			List<Integer> col = gameBoard.getColumn(c);
			for (int r = 0; r < col.size(); r++) {
				sb.append(String.format("%5d", col.get(r)));
			}
			sb.append(System.lineSeparator());
		}
		sb.append("CS:");
		for (BoardIndex bi : changeStack) {
			sb.append("r:");
			sb.append(bi.getRow());
			sb.append("c:");
			sb.append(bi.getColumn());
			sb.append(" ");
		}
		logger.info(sb.toString());
	}

	public synchronized void subTurn() {
		if (changeStack.isEmpty()) {
			Optional<BoardIndex> lessThanMin = getLessThanMin();
			if (lessThanMin.isPresent()) {
				remove(lessThanMin.get());
				subTurn();
				return;
			}
			if (isGameOver()) {
				state = State.GameOver;
				return;
			}
			this.state = State.Playing;
			return;
		}
		BoardIndex boardIndex = changeStack.pop();
		if (!gameBoard.contains(boardIndex)) {
			return;
		}
		logBoard();
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
					if (currentMaxValue > inputMin + distinctNumberCount) {
						inputMin++;
						inputMax++;
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
		if (special > 0 && state == State.Playing) {
			special--;
			changeStack.add(index);
			remove(index);
			state = State.InTurn;
		}
	}

	public Optional<BoardIndex> getLessThanMin() {
		for (int c = 0; c < gameBoard.getColumnCount(); c++) {
			List<Integer> boardColumn = gameBoard.getColumn(c);
			for (int r = 0; r < boardColumn.size(); r++) {
				BoardIndex boardIndex = new BoardIndex(r, c);
				int value = gameBoard.getValue(boardIndex);
				if (value < inputMin) {
					return Optional.of(boardIndex);
				}
			}
		}
		return Optional.empty();
	}

	private boolean isGameOver() {
		for (int c = 0; c < gameBoard.getColumnCount(); c++) {
			if (gameBoard.canPush(c, nextNumber)) {
				return false;
			}
		}
		return true;
	}

	public void change(BoardIndex first, BoardIndex second) {
		if (this.state == State.Playing && //
				this.gameBoard.contains(first) && //
				this.gameBoard.contains(second)) {
			int value = this.gameBoard.getValue(first);
			gameBoard.setValue(first, this.gameBoard.getValue(second));
			gameBoard.setValue(second, value);
			changeStack.add(first);
			changeStack.add(second);
			special--;
			state = State.InTurn;
		}
	}

	public void setNextNumber() {
		nextNumber = Dice.getInstance().next(this.inputMin, this.inputMax);
	}

	public Game copy() {
		Game game = new Game();
		game.setCurrentMaxValue(currentMaxValue);
		game.setDistinctNumberCount(distinctNumberCount);
		game.setInputMax(inputMax);
		game.setInputMin(inputMin);
		game.setNextNumber(nextNumber);
		game.setScore(score);
		game.setSpecial(special);
		game.setState(state);
		game.setGameBoard(gameBoard.copy());
		return game;
	}

}
