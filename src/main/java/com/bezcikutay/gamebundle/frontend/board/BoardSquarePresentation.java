package com.bezcikutay.gamebundle.frontend.board;

import java.awt.Color;

public class BoardSquarePresentation {
	private Color backGround;
	private Color border;
	private Color fontColor;
	private String text;

	public Color getBackGround() {
		return backGround;
	}

	public void setBackGround(Color backGround) {
		this.backGround = backGround;
	}

	public Color getBorder() {
		return border;
	}

	public void setBorder(Color border) {
		this.border = border;
	}

	public Color getFontColor() {
		return fontColor;
	}

	public void setFontColor(Color fontColor) {
		this.fontColor = fontColor;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
}
