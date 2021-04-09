package com.bezcikutay.gamebundle.frontend.numberTetris;

import com.bezcikutay.gamebundle.backend.FileOperation;
import com.bezcikutay.gamebundle.backend.scoreboard.ScoreBoard;

public class NumberTetrisData {
	private static String NAME = "NumberTetris";
	private ScoreBoard scoreBoard = new ScoreBoard(NAME);

	public NumberTetrisData() {

	}

	public ScoreBoard getScoreBoard() {
		return scoreBoard;
	}

	public void setScoreBoard(ScoreBoard scoreBoard) {
		this.scoreBoard = scoreBoard;
	}

	public void save() {
		FileOperation.getIntance().save(NAME, this);
	}

	public static NumberTetrisData load() {
		NumberTetrisData numberTetrisData = FileOperation.getIntance().load(NAME, NumberTetrisData.class);
		if (numberTetrisData == null) {
			numberTetrisData = new NumberTetrisData();
		}
		return numberTetrisData;
	}
}
