package com.bezcikutay.gamebundle.numberTetris;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.bezcikutay.gamebundle.core.BoardIndex;
import com.bezcikutay.gamebundle.core.BoardSquare;
import com.bezcikutay.gamebundle.core.BoardSquarePresentation;
import com.bezcikutay.gamebundle.core.FrmGame;
import com.bezcikutay.gamebundle.core.FrmScoreBoard;
import com.bezcikutay.gamebundle.core.GameConfig;
import com.google.gson.Gson;

public class FrmNumberTetris extends FrmGame {
	private static final long serialVersionUID = -2069542008642361175L;
	private Game game;
	private Game backup;
	private JTextField txtSpecial = new JTextField();
	private JButton btnRevert = new JButton(translate.translate("Revert"));
	private JToggleButton btnCrush = new JToggleButton(translate.translate("Crush"));
	private JToggleButton btnChange = new JToggleButton(translate.translate("Change"));
	private BoardIndex toBeChanged;
	private BoardIndex nextNumber;

	public FrmNumberTetris() {
		super(gameConfig());
		addMouseInput();
		addTimer();
		fillMenu();
		createGame();
		refreshView();
		setVisible(true);
	}

	public static GameConfig gameConfig() {
		GameConfig gameConfig = new GameConfig();
		gameConfig.setBackground(Color.white);
		gameConfig.setBoardColor(Color.black);
		gameConfig.setBoardSizeBySquare(new BoardIndex(8, 5));
		gameConfig.setGameSize(new Dimension(450, 450));
		gameConfig.setSquareSize(50);
		gameConfig.setTimerDelay(250);
		gameConfig.setTitle("NumberTetris");
		gameConfig.setMenuSize(new Dimension(150, 300));
		gameConfig.setMenuGridSize(9);
		return gameConfig;
	}

	public void createGame() {
		game = new Game();
		game.setScore(0);
		game.setCurrentMaxValue(6);
		game.setDistinctNumberCount(7);
		game.setGameBoard(new GameBoard(8, 5));
		game.setInputMax(6);
		game.setInputMin(1);
		game.setNextNumber();
		game.setSpecial(0);
		game.setState(State.Playing);
	}

	public void fillMenu() {
		menu.add(new JLabel(translate.translate("Special")));
		txtSpecial.setEditable(false);
		menu.add(txtSpecial);
		addRestartButton();
		addRevertButton();
		addCrushButton();
		addChangeButton();
	}

	private void addChangeButton() {
		btnChange.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnCrush.setSelected(false);
				toBeChanged = null;
			}
		});
		menu.add(btnChange);
	}

	private void addCrushButton() {
		btnCrush.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnChange.setSelected(false);
				toBeChanged = null;
			}
		});
		menu.add(btnCrush);
	}

	private void addRevertButton() {
		btnRevert.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (backup != null && game.getState() == State.Playing && backup.getSpecial() > 0) {
					game = backup;
					backup = null;
					game.setSpecial(game.getSpecial() - 1);
					refreshView();
				}
			}
		});
		menu.add(btnRevert);
	}

	private void addRestartButton() {
		JButton btnRestart = new JButton(translate.translate("Restart"));
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createGame();
				refreshView();
			}
		});
		menu.add(btnRestart);
	}

	private void paintBoard() {
		int rowCount = game.getGameBoard().getMaxRowCount();
		int colCount = game.getGameBoard().getColumnCount();
		SquarePainter painter = SquarePainter.getInstance();
		for (int r = 0; r < rowCount; r++) {
			for (int c = 0; c < colCount; c++) {
				BoardIndex bi = new BoardIndex(r, c);
				int value = -1;
				if (bi.equals(nextNumber)) {
					value = game.getNextNumber();
				} else if (game.getGameBoard().contains(bi)) {
					value = game.getGameBoard().getValue(bi);
				}
				BoardSquarePresentation presentation = painter.getPresentation(value);
				if (bi.equals(toBeChanged)) {
					presentation.setBorder(gameConfig.getBackground());
				}
				board.paintSquare(bi, presentation);
			}
		}
		BoardSquarePresentation nextPresentation = painter.getPresentation(game.getNextNumber());
		BoardIndex nextIndex = new BoardIndex(game.getGameBoard().getMaxRowCount() - 1,
				game.getGameBoard().getColumnCount() / 2);
		board.paintSquare(nextIndex, nextPresentation);
	}

	public void refreshView() {
		paintBoard();
		txtScore.setText(String.valueOf(game.getScore()));
		txtSpecial.setText(String.valueOf(game.getSpecial()));
		if (backup != null && backup.getSpecial() > 0) {
			btnRevert.setEnabled(true);
		} else {
			btnRevert.setEnabled(false);
		}
		if (game.getSpecial() > 0) {
			btnChange.setEnabled(true);
			btnCrush.setEnabled(true);
		} else {
			btnChange.setEnabled(false);
			btnCrush.setEnabled(false);
		}
		pack();
	}

	@Override
	public void eventMousePressed(BoardSquare boardSquare) {
		BoardIndex boardIndex = boardSquare.getIndex();
		if (game.getState() == State.Playing) {
			if (btnChange.isEnabled() && btnChange.isSelected()) {
				if (checkSelectedSquare(boardIndex)) {
					if (toBeChanged == null) {
						toBeChanged = boardIndex;
					} else if (toBeChanged.equals(boardIndex)) {
						toBeChanged = null;
						btnChange.setSelected(false);
					} else {
						game.change(toBeChanged, boardIndex);
						toBeChanged = null;
						btnChange.setSelected(false);
						timer.start();
					}
				}
			} else if (btnCrush.isEnabled() && btnCrush.isSelected()) {
				if (checkSelectedSquare(boardIndex)) {
					game.crush(boardSquare.getIndex());
					timer.start();

				}
			} else {
				backup = game.copy();
				logger.info(new Gson().toJson(game));
				game.startTurn(boardSquare.getIndex().getColumn());
				timer.start();
			}
			refreshView();
		}
	}

	public boolean checkSelectedSquare(BoardIndex boardIndex) {
		if (!game.getGameBoard().contains(boardIndex)) {
			JOptionPane.showMessageDialog(this, translate.translate("SelectSquare"));
			return false;
		}
		return true;
	}

	@Override
	public void eventTimer() {
		if (game.getState() == State.InTurn) {
			game.subTurn();
			if (game.getState() == State.Playing) {
				timer.stop();
			}
			refreshView();
			if (game.getState() == State.GameOver) {
				if (scoreBoard.isScoreEnough(game.getScore())) {
					String sign = JOptionPane.showInputDialog(translate.translate("Congrats"));
					if (sign != null) {
						scoreBoard.addScore(game.getScore(), sign);
						scoreFile.save(scoreBoard);
					}
				}
				new FrmScoreBoard(scoreBoard).setVisible(true);
			}
		}
	}

	public static void main(String a[]) {
		new FrmNumberTetris();
	}

}
