package com.bezcikutay.gamebundle.backend.scoreboard;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class ScoreBoard {
	private String gameName;
	private static final int MAX_SCORE_SIZE = 10;
	private List<ScoreItem> scoreItemList = new ArrayList<>();

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public List<ScoreItem> getScoreItemList() {
		return scoreItemList;
	}

	public void setScoreItemList(List<ScoreItem> scoreItemList) {
		this.scoreItemList = scoreItemList;
	}

	public ScoreBoard(String gameName) {
		this.gameName = gameName;
	}

	public void normalize() {
		scoreItemList.sort(Comparator.comparing(ScoreItem::getScore));
		if (scoreItemList.size() > MAX_SCORE_SIZE) {
			this.scoreItemList = scoreItemList.subList(0, MAX_SCORE_SIZE);
		}
	}

	public boolean isScoreEnough(int score) {
		normalize();
		if (scoreItemList.size() < MAX_SCORE_SIZE) {
			return true;
		}
		int min = scoreItemList.stream().min(Comparator.comparing(ScoreItem::getScore)).get().getScore();
		if (min < score) {
			return true;
		}
		return false;
	}

	public void addScore(int score, String name) {
		scoreItemList.add(new ScoreItem(name, score));
		normalize();
	}

}
