package com.bezcikutay.gamebundle.core;

import java.awt.Color;
import java.awt.Dimension;

public class GameConfig {
	private Dimension gameSize;
	private String title;
	private Color background;
	private int squareSize;
	private BoardIndex boardSizeBySquare;
	private int timerDelay;
	private Color boardColor;
	private Dimension menuSize;
	private int menuGridSize;

	public int getMenuGridSize() {
		return menuGridSize;
	}

	public void setMenuGridSize(int menuGridSize) {
		this.menuGridSize = menuGridSize;
	}

	public Dimension getMenuSize() {
		return menuSize;
	}

	public void setMenuSize(Dimension menuSize) {
		this.menuSize = menuSize;
	}

	public Color getBoardColor() {
		return boardColor;
	}

	public void setBoardColor(Color boardColor) {
		this.boardColor = boardColor;
	}

	public Dimension getGameSize() {
		return gameSize;
	}

	public void setGameSize(Dimension gameSize) {
		this.gameSize = gameSize;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Color getBackground() {
		return background;
	}

	public void setBackground(Color background) {
		this.background = background;
	}

	public int getSquareSize() {
		return squareSize;
	}

	public void setSquareSize(int squareSize) {
		this.squareSize = squareSize;
	}

	public BoardIndex getBoardSizeBySquare() {
		return boardSizeBySquare;
	}

	public void setBoardSizeBySquare(BoardIndex boardSizeBySquare) {
		this.boardSizeBySquare = boardSizeBySquare;
	}

	public int getTimerDelay() {
		return timerDelay;
	}

	public void setTimerDelay(int timerDelay) {
		this.timerDelay = timerDelay;
	}
}
