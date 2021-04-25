package com.bezcikutay.gamebundle.core;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;

import javax.swing.JPanel;

public class Board extends JPanel {
	private static final long serialVersionUID = -604188851670197275L;
	public BoardSquare squares2d[][];

	public BoardSquare[][] getSquares2d() {
		return squares2d;
	}

	public void setSquares2d(BoardSquare[][] squares2d) {
		this.squares2d = squares2d;
	}

	public Board(int pixel, BoardIndex boardSize, BoardSquarePresentation presentation) {
		this.setPreferredSize(new Dimension(pixel * boardSize.getColumn(), pixel * boardSize.getRow()));
		this.setLayout(new GridLayout(boardSize.getRow(), boardSize.getColumn()));
		this.squares2d = new BoardSquare[boardSize.getRow()][boardSize.getColumn()];
		for (int r = 0; r < boardSize.getRow(); r++) {
			for (int c = 0; c < boardSize.getColumn(); c++) {
				BoardIndex index = new BoardIndex(r, c);
				BoardSquare boardSquare = new BoardSquare(index, presentation);
				boardSquare.paint();
				this.add(boardSquare);
				this.squares2d[r][c] = boardSquare;
			}
		}
	}

	public BoardSquare choose(Point point) {
		Component component = getComponentAt(point);
		if (component != null && component instanceof BoardSquare) {
			return (BoardSquare) component;
		}
		return null;
	}

	public void paintSquare(BoardIndex boardIndex, BoardSquarePresentation presentation) {
		BoardSquare square = squares2d[boardIndex.getRow()][boardIndex.getColumn()];
		square.setPresentation(presentation);
		square.paint();
	}
}
