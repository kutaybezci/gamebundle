package com.bezcikutay.gamebundle.frontend.numberTetris;

import java.awt.Color;

public class GameConfig {
	public static final int SQUARE_SIZE = 50;
	public static final int GUI_ROW_COUNT = 8;
	public static final int COLUMN_COUNT = 5;
	public static final int DISTINCT_NUMBER = 12;
	public static final int INPUT_RANGE = 6;
	public static final int GAME_ROW_COUNT = GUI_ROW_COUNT;
	public static final Color BACKGROUND = Color.white;

	public static final Color[] DISTINCT_COLORS = new Color[] { /* new Color(0, 0, 0), new Color(255, 255, 255), */
			new Color(230, 25, 75), new Color(60, 180, 75), new Color(255, 225, 25), new Color(0, 130, 200),
			new Color(245, 130, 48), new Color(145, 30, 180), new Color(70, 240, 240), new Color(240, 50, 230),
			new Color(210, 245, 60), new Color(250, 190, 212), new Color(0, 128, 128), new Color(220, 190, 255),
			new Color(170, 110, 40), new Color(255, 250, 200), new Color(128, 0, 0), new Color(170, 255, 195),
			new Color(128, 128, 0), new Color(255, 215, 180), new Color(0, 0, 128), new Color(128, 128, 128) };

}
