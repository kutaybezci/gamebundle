package com.bezcikutay.gamebundle.core;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class FrmGame extends JFrame {
	private static final long serialVersionUID = 6424513867293559783L;
	protected static final Logger logger = LoggerFactory.getLogger(FrmGame.class);
	protected Translate translate = Translate.getInstance();
	protected GameConfig gameConfig;
	protected Board board;
	protected Timer timer;
	protected JPanel menu;
	protected JTextField txtScore = new JTextField("0");
	protected FileOperation<ScoreBoard> scoreFile;
	protected ScoreBoard scoreBoard;

	public FrmGame(GameConfig gameConfig) {
		this.gameConfig = gameConfig;
		init();
	}

	public void init() {
		logger.info("New game started:{}", gameConfig.getTitle());
		setResizable(false);
		setTitle(translate.translate(gameConfig.getTitle()));
		getContentPane().setBackground(gameConfig.getBackground());
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(gameConfig.getGameSize());
		setLayout(new FlowLayout(FlowLayout.LEFT));
		BoardSquarePresentation presentation = new BoardSquarePresentation();
		presentation.setBackGround(gameConfig.getBoardColor());
		presentation.setText("");
		presentation.setFontColor(gameConfig.getBoardColor());
		board = new Board(gameConfig.getSquareSize(), gameConfig.getBoardSizeBySquare(), presentation);
		add(board);
		scoreFile = new FileOperation<ScoreBoard>(new File(gameConfig.getTitle() + ".score"), ScoreBoard.class);
		scoreBoard = scoreFile.load();
		if (scoreBoard == null) {
			scoreBoard = new ScoreBoard(gameConfig.getTitle());
			scoreFile.save(scoreBoard);
		}
		menu = new JPanel();
		menu.setBackground(gameConfig.getBackground());
		menu.setPreferredSize(gameConfig.getMenuSize());
		menu.setLayout(new GridLayout(gameConfig.getMenuGridSize(), 1));
		menu.add(new JLabel(translate.translate("Score")));
		txtScore.setEditable(false);
		menu.add(txtScore);
		JButton btnScoreBoard = new JButton(translate.translate("ScoreBoard"));
		btnScoreBoard.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrmScoreBoard(scoreBoard).setVisible(true);
			}
		});
		menu.add(btnScoreBoard);
		add(menu);
	}

	public void addMouseInput() {
		board.addMouseListener(new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
			}

			@Override
			public void mousePressed(MouseEvent e) {
				BoardSquare square = board.choose(e.getPoint());
				if (square != null) {
					eventMousePressed(square);
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
			}

			@Override
			public void mouseEntered(MouseEvent e) {
			}

			@Override
			public void mouseClicked(MouseEvent e) {
			}
		});
	}

	public void addTimer() {
		timer = new Timer(this.gameConfig.getTimerDelay(), new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				eventTimer();
			}
		});
	}

	public void eventMousePressed(BoardSquare boardSquare) {
		logger.info("Selected R:{} C:{}", boardSquare.getIndex().getRow(), boardSquare.getIndex().getColumn());
	}

	public void eventTimer() {
		logger.info("timer event");
	}

}
