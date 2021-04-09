package com.bezcikutay.gamebundle.backend.scoreboard;

import java.util.Date;

public class ScoreItem implements Comparable<ScoreItem> {
	private String name;
	private int score;
	private Date date;

	public ScoreItem(String name, int score) {
		this.name = name;
		this.score = score;
		this.date = new Date(System.currentTimeMillis());
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public int compareTo(ScoreItem o) {
		return this.score - o.score;
	}
}
