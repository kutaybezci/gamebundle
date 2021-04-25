package com.bezcikutay.gamebundle.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.text.SimpleDateFormat;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class FrmScoreBoard extends JFrame {

	private static final long serialVersionUID = 6524192827719038208L;
	private Translate translate = Translate.getInstance();

	public FrmScoreBoard(ScoreBoard scoreBoard) {

		setPreferredSize(new Dimension(750, 500));
		setTitle(scoreBoard.getGameName());
		getContentPane().setBackground(Color.white);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setResizable(false);
		setLayout(new FlowLayout());
		JScrollPane scrollPane = new JScrollPane();
		add(scrollPane);
		String[] columnNames = { translate.translate("Order"), translate.translate("Score"),
				translate.translate("Name"), translate.translate("Date") };
		Object[][] data = new Object[scoreBoard.getScoreItemList().size()][5];
		for (int r = 0; r < scoreBoard.getScoreItemList().size(); r++) {
			ScoreItem scoreItem = scoreBoard.getScoreItemList().get(r);
			Object[] row = new Object[] { r + 1, scoreItem.getScore(), scoreItem.getName(),
					new SimpleDateFormat("dd.MM.yyyy HH:Hmm:ss").format(scoreItem.getDate()) };
			data[r] = row;
		}
		JTable table = new JTable(data, columnNames);
		table.setBackground(Color.white);
		table.setBorder(BorderFactory.createEmptyBorder());
		scrollPane.setViewportView(table);
		scrollPane.setPreferredSize(new Dimension(750, 500));
		pack();
		setVisible(true);
	}

}
