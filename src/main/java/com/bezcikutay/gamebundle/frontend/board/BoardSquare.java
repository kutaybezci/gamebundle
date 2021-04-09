package com.bezcikutay.gamebundle.frontend.board;

import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.bezcikutay.gamebundle.backend.BoardIndex;

public class BoardSquare extends JPanel {
	private static final long serialVersionUID = 238541079917828967L;
	private BoardIndex index;
	private BoardSquarePresentation presentation;
	private JLabel label = new JLabel();

	public BoardSquare(BoardIndex index, BoardSquarePresentation presentation) {
		this.index = index;
		this.presentation = presentation;
		this.add(label);
		paint();
	}

	public void paint() {
		this.setLayout(new GridBagLayout());
		this.setBackground(this.presentation.getBackGround());
		this.label.setText(this.presentation.getText());
		this.label.setForeground(this.presentation.getFontColor());
		if (this.presentation.getBorder() != null
				&& this.presentation.getBorder() != this.presentation.getBackGround()) {
			this.setBorder(BorderFactory.createLineBorder(this.presentation.getBorder()));
		} else {
			this.setBorder(BorderFactory.createEmptyBorder());
		}
	}

	public BoardIndex getIndex() {
		return index;
	}

	public void setIndex(BoardIndex index) {
		this.index = index;
	}

	public BoardSquarePresentation getPresentation() {
		return presentation;
	}

	public void setPresentation(BoardSquarePresentation presentation) {
		this.presentation = presentation;
	}

	public JLabel getLabel() {
		return label;
	}

	public void setLabel(JLabel label) {
		this.label = label;
	}
}
