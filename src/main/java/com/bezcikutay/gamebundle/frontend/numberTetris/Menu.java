package com.bezcikutay.gamebundle.frontend.numberTetris;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JToggleButton;

import com.bezcikutay.gamebundle.backend.Translate;
import com.bezcikutay.gamebundle.backend.numberTetris.BoardExport;
import com.bezcikutay.gamebundle.backend.scoreboard.ScoreBoard;
import com.bezcikutay.gamebundle.frontend.FrmScoreBoard;

public class Menu extends JPanel {
	private static final long serialVersionUID = 6903789211971935883L;
	private Translate translate = Translate.getInstance();
	private JTextField txtScore = new JTextField("0");
	private JTextField txtSpecial = new JTextField("0");
	private JTextField txtMin = new JTextField("0");
	private JTextField txtMax = new JTextField("0");
	private JToggleButton btnCrush = new JToggleButton(translate.translate("Crush"));
	// private JButton btnRevert;
	private JButton btnInfo;
	private JToggleButton btnChange = new JToggleButton(translate.translate("Change"));
	private JLabel state = new JLabel();

	public Menu() {
		setBackground(GameConfig.BACKGROUND);
		setPreferredSize(new Dimension(200, 350));
		setLayout(new GridLayout(14, 1));
		JLabel lblScore = new JLabel(translate.translate("Score"));
		add(lblScore);
		this.txtScore = new JTextField("0");
		this.txtScore.setEditable(false);
		add(txtScore);
		JLabel lblSpecial = new JLabel(translate.translate("Special"));
		add(lblSpecial);
		this.txtSpecial = new JTextField("0");
		this.txtSpecial.setEditable(false);
		add(txtSpecial);
		JButton btnRestart = new JButton(translate.translate("Restart"));
		btnRestart.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int option = JOptionPane.showConfirmDialog(NumberTetris.INSTANCE, translate.translate("Restart"),
						translate.translate("Restart"), JOptionPane.OK_CANCEL_OPTION);
				if (option == JOptionPane.OK_OPTION) {
					NumberTetris.INSTANCE.createGame();
				}
			}
		});
		add(btnRestart);
		JLabel lblMin = new JLabel(translate.translate("Min"));
		add(lblMin);
		txtMin.setEditable(false);
		add(txtMin);
		JLabel lblMax = new JLabel(translate.translate("Max"));
		add(lblMax);
		txtMax.setEditable(false);
		add(txtMax);
		add(btnCrush);
		add(btnChange);
		// this.btnRevert = new JButton("Revert");
		// add(btnRevert);
		JButton btnBoard = new JButton(translate.translate("ScoreBoard"));
		btnBoard.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ScoreBoard scoreBoard = NumberTetris.INSTANCE.getNumberTetrisData().getScoreBoard();
				new FrmScoreBoard(scoreBoard).setVisible(true);
			}
		});
		add(btnBoard);
		this.btnInfo = new JButton(translate.translate("Info"));
		this.btnInfo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(NumberTetris.INSTANCE, "Kutay Bezci. kutay.bezci@gmail.com");
			}
		});
		add(btnInfo);
		this.state = new JLabel();
		add(this.state);
	}

	public void paint(BoardExport boardExport) {
		this.txtScore.setText(String.valueOf(boardExport.getScore()));
		this.txtSpecial.setText(String.valueOf(boardExport.getSpecial()));
		this.state.setText(boardExport.getState().name());
		boolean hasSpecial = boardExport.getSpecial() > 0;
		this.btnCrush.setEnabled(hasSpecial);
		this.btnChange.setEnabled(hasSpecial);
		// this.btnRevert.setEnabled(hasSpecial);
		this.txtMin.setText(String.valueOf(boardExport.getMin()));
		this.txtMax.setText(String.valueOf(boardExport.getMax()));
	}

	public JToggleButton getBtnCrush() {
		return btnCrush;
	}

	public JToggleButton getBtnChange() {
		return btnChange;
	}
}
