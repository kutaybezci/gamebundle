package com.bezcikutay.gamebundle.frontend.board;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;

import com.bezcikutay.gamebundle.backend.BoardIndex;
import com.bezcikutay.gamebundle.frontend.numberTetris.SquarePainter;

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

	public static void main(String arg[]) {
		JFrame jFrame = new JFrame();
		jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFrame.setLayout(new FlowLayout(FlowLayout.LEFT));
		jFrame.setPreferredSize(new Dimension(500, 500));
		jFrame.setResizable(false);
		BoardSquarePresentation presentation = SquarePainter.getInstance().getPresentation(3);
		presentation.setBorder(Color.white);
		Board board = new Board(50, new BoardIndex(7, 5), presentation);
		jFrame.add(board);
		jFrame.pack();
		jFrame.setVisible(true);
	}

}
