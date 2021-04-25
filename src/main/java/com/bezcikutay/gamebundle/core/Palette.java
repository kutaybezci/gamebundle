package com.bezcikutay.gamebundle.core;

import java.awt.Color;

public class Palette {
	public static final Color[] DISTINCT_COLORS = new Color[] { /* new Color(0, 0, 0), new Color(255, 255, 255), */
			new Color(230, 25, 75), new Color(60, 180, 75), new Color(255, 225, 25), new Color(0, 130, 200),
			new Color(245, 130, 48), new Color(145, 30, 180), new Color(70, 240, 240), new Color(240, 50, 230),
			new Color(210, 245, 60), new Color(250, 190, 212), new Color(0, 128, 128), new Color(220, 190, 255),
			new Color(170, 110, 40), new Color(255, 250, 200), new Color(128, 0, 0), new Color(170, 255, 195),
			new Color(128, 128, 0), new Color(255, 215, 180), new Color(0, 0, 128), new Color(128, 128, 128) };

	private static final Palette INSTANCE = new Palette();

	private Palette() {

	}

	public static Palette getInstance() {
		return INSTANCE;
	}

	public Color getColor(int order) {
		int modOrder = order % DISTINCT_COLORS.length;
		return DISTINCT_COLORS[modOrder];
	}

}
