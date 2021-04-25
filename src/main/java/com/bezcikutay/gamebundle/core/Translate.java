package com.bezcikutay.gamebundle.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class Translate {
	private static final Translate INSTANCE = new Translate();
	private Properties messages = new Properties();
	private static final String fileName = "translate.game";

	private Translate() {
		messages = new Properties();
		File file = new File(fileName);
		if (file.exists()) {
			external();
		} else {
			internal();
		}
	}

	public static Translate getInstance() {
		return INSTANCE;
	}

	private void external() {
		try (FileInputStream fis = new FileInputStream(fileName);
				InputStreamReader reader = new InputStreamReader(fis, StandardCharsets.UTF_8)) {
			messages.load(reader);
		} catch (Exception ex) {
			internal();
		}
	}

	private void internal() {
		try {
			messages.load(getClass().getResourceAsStream("/" + fileName));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public String translate(String toBeTranslated) {
		if (messages.containsKey(toBeTranslated)) {
			return messages.getProperty(toBeTranslated);
		}
		return toBeTranslated;
	}

}
