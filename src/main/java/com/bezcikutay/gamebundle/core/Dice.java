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
		int range = max - min + 1;
		return min + random.nextInt(range);
	}

	public static void main(String a[]) {
		Dice dice = Dice.getInstance();
		for (int i = 0; i < 20; i++) {
			System.out.println(dice.next(10, 15));
		}
	}

}
