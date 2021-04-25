package com.bezcikutay.gamebundle.core;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;

import com.google.gson.Gson;

public class FileOperation<T> {
	private File file;
	private Class<T> type;

	public FileOperation(File file, Class<T> type) {
		this.file = file;
		this.type = type;
	}

	public T load() {
		try {
			if (file.exists()) {
				byte[] bytes = Files.readAllBytes(file.toPath());
				bytes = Base64.getDecoder().decode(new String(bytes, StandardCharsets.UTF_8));
				return new Gson().fromJson(new String(bytes, StandardCharsets.UTF_8), type);
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public void save(T t) {
		try {
			String data = new Gson().toJson(t);
			String encoded = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
			Files.write(file.toPath(), encoded.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
