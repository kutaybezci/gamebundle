package com.bezcikutay.gamebundle.core;

import java.util.Random;

public class Dice {
	private static final Dice INSTANCE = new Dice();
	private Random random;

	private Dice() {
		random = new Random();
	}

	public static Dice getInstance() {
		return INSTANCE;
	}

	public int next(int min, int max) {
		int range = max - min;
		return min + random.nextInt(range);
	}

}
