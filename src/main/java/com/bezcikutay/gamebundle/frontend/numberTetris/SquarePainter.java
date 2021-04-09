package com.bezcikutay.gamebundle.frontend.numberTetris;

import java.awt.Color;

import com.bezcikutay.gamebundle.frontend.board.BoardSquarePresentation;

public class SquarePainter {
	private static final SquarePainter INSTANCE = new SquarePainter();

	private SquarePainter() {

	}

	public static SquarePainter getInstance() {
		return INSTANCE;
	}

	public BoardSquarePresentation getPresentation(int value) {
		BoardSquarePresentation presentation = new BoardSquarePresentation();
		presentation.setBackGround(Color.black);
		presentation.setBorder(Color.black);
		presentation.setFontColor(Color.black);
		presentation.setText("");
		if (value > 0) {
			presentation.setText(String.valueOf(value));
			int colorOrder = value % GameConfig.DISTINCT_COLORS.length;
			Color color = GameConfig.DISTINCT_COLORS[colorOrder];
			presentation.setBackGround(color);
			presentation.setBorder(color);
		}
		return presentation;
	}

}
