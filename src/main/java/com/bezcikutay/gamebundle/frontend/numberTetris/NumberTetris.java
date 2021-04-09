package com.bezcikutay.gamebundle.frontend.numberTetris;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.bezcikutay.gamebundle.backend.BoardIndex;
import com.bezcikutay.gamebundle.backend.Translate;
import com.bezcikutay.gamebundle.backend.numberTetris.BoardExport;
import com.bezcikutay.gamebundle.backend.numberTetris.NumberTetrisGame;
import com.bezcikutay.gamebundle.backend.numberTetris.State;
import com.bezcikutay.gamebundle.backend.scoreboard.ScoreBoard;
import com.bezcikutay.gamebundle.frontend.FrmScoreBoard;
import com.bezcikutay.gamebundle.frontend.board.Board;
import com.bezcikutay.gamebundle.frontend.board.BoardSquare;
import com.bezcikutay.gamebundle.frontend.board.BoardSquarePresentation;

public class NumberTetris extends JFrame {

	private static final Logger logger = LoggerFactory.getLogger(NumberTetris.class);
	private Translate translate = Translate.getInstance();
	private static final long serialVersionUID = -2583886169627258125L;
	public static final NumberTetris INSTANCE = new NumberTetris();
	private Board board;
	private NumberTetrisGame game;
	private Timer timer;
	private Menu menu = new Menu();
	private BoardIndex selectedForChange;
	private NumberTetrisData numberTetrisData = NumberTetrisData.load();

	public NumberTetrisData getNumberTetrisData() {
		return numberTetrisData;
	}

	public void createGame() {
		this.game = new NumberTetrisGame(GameConfig.GAME_ROW_COUNT, //
				GameConfig.COLUMN_COUNT, //
				GameConfig.INPUT_RANGE, //
				GameConfig.DISTINCT_NUMBER);
		paintBoard();
	}

	private void playPush(BoardSquare square) {
		game.startTurn(square.getIndex().getColumn());
	}

	private void playCrush(BoardSquare square) {
		if (this.game.isFilled(square.getIndex())) {
			this.menu.getBtnCrush().setSelected(false);
			game.crush(square.getIndex());
		} else {
			JOptionPane.showMessageDialog(this, translate.translate("SelectSquare"));
		}
	}

	private void playChange(BoardSquare square) {
		if (this.game.isFilled(square.getIndex())) {
			if (this.selectedForChange == null) {
				this.selectedForChange = square.getIndex();
			} else if (this.selectedForChange.equals(square.getIndex())) {
				this.selectedForChange = null;
			} else {
				this.menu.getBtnChange().setSelected(false);
				game.change(this.selectedForChange, square.getIndex());
				this.selectedForChange = null;
			}
		} else {
			JOptionPane.showMessageDialog(this, translate.translate("SelectSquare"));
		}
	}

	private void play(BoardSquare square) {
		if (this.menu.getBtnCrush().isEnabled() && this.menu.getBtnCrush().isSelected()) {
			playCrush(square);
		} else if (this.menu.getBtnChange().isEnabled() && this.menu.getBtnChange().isSelected()) {
			playChange(square);
		} else {
			playPush(square);
		}
	}

	private NumberTetris() {
		logger.info("New game started");
		this.setResizable(false);
		this.setTitle(translate.translate("NumberTetris"));
		this.getContentPane().setBackground(GameConfig.BACKGROUND);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setPreferredSize(new Dimension(500, 500));
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		this.board = new Board(GameConfig.SQUARE_SIZE,
				new BoardIndex(GameConfig.GUI_ROW_COUNT, GameConfig.COLUMN_COUNT),
				SquarePainter.getInstance().getPresentation(0));

		this.board.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {

			}

			@Override
			public void mousePressed(MouseEvent e) {
				Component component = board.getComponentAt(e.getPoint());
				if (component != null && component instanceof BoardSquare) {
					BoardSquare square = (BoardSquare) component;
					play(square);
					paintBoard();
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
		createGame();
		this.add(board);
		this.add(menu);
		this.timer = new Timer(250, new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (game.getState() == State.InTurn) {
					game.subTurn();
					paintBoard();
				} else if (game.getState() == State.GameOver) {
					ScoreBoard scoreBoard = numberTetrisData.getScoreBoard();
					if (scoreBoard.isScoreEnough(game.getScore())) {
						String sign = JOptionPane.showInputDialog("Congrats");
						if (sign != null) {
							scoreBoard.addScore(game.getScore(), sign);
							numberTetrisData.save();
						}
					}
					new FrmScoreBoard(numberTetrisData.getScoreBoard()).setVisible(true);
					createGame();
				}

			}
		});
		this.timer.start();
		this.pack();
	}

	public void paint(BoardIndex boardIndex, int value) {
		BoardSquarePresentation presentation = SquarePainter.getInstance().getPresentation(value);
		if (this.selectedForChange != null && boardIndex.equals(selectedForChange)) {
			presentation.setBorder(Color.white);
		}
		BoardSquare square = this.board.getSquares2d()[boardIndex.getRow()][boardIndex.getColumn()];
		square.setPresentation(presentation);
		square.paint();
	}

	public void paintBoard() {
		BoardExport boardExport = game.export();
		for (int r = 0; r < boardExport.getBoard().length - 1; r++) {
			for (int c = 0; c < boardExport.getBoard()[r].length; c++) {
				BoardIndex boardIndex = new BoardIndex(r, c);
				int value = boardExport.getBoard()[r][c];
				paint(boardIndex, value);
			}
		}
		BoardIndex lastRowMidde = new BoardIndex(GameConfig.GAME_ROW_COUNT - 1, GameConfig.COLUMN_COUNT / 2);
		paint(lastRowMidde, boardExport.getNextNumber());
		this.menu.paint(boardExport);
	}

	public static void main(String arg[]) {
		INSTANCE.setVisible(true);
	}
}
