package com.bezcikutay.gamebundle.core;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import com.bezcikutay.gamebundle.numberTetris.FrmNumberTetris;

public class FrmLauncher extends JFrame {
	private static final long serialVersionUID = 7018740048852281096L;
	private Translate translate = Translate.getInstance();

	public FrmLauncher() throws IOException {
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setPreferredSize(new Dimension(200, 200));
		getContentPane().setBackground(Color.white);
		setLayout(new GridLayout(5, 1));
		add(new JLabel(translate.translate("ChooseGame")));
		JButton btnNumberTetris = new JButton(translate.translate("NumberTetris"));
		btnNumberTetris.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new FrmNumberTetris();
			}
		});
		add(btnNumberTetris);
		JButton btnInfo = new JButton(translate.translate("Info"));
		btnInfo.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JOptionPane.showMessageDialog(null, "Kutay Bezci kutay.bezci@gmail.com",
						translate.translate("Developer"), JOptionPane.INFORMATION_MESSAGE);
			}
		});
		add(btnInfo);
		pack();
	}

	public static void main(String a[]) throws IOException {
		new FrmLauncher().setVisible(true);
	}

}
