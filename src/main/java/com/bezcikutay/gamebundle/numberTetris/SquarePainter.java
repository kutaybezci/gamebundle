package com.bezcikutay.gamebundle.numberTetris;

import java.awt.Color;

import com.bezcikutay.gamebundle.core.BoardSquarePresentation;
import com.bezcikutay.gamebundle.core.Palette;

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
			Color color = Palette.getInstance().getColor(value);
			presentation.setBackGround(color);
			presentation.setBorder(color);
		}
		return presentation;
	}

}
