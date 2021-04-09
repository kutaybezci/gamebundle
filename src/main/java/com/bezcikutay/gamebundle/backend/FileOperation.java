package com.bezcikutay.gamebundle.backend;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import com.google.gson.Gson;

public class FileOperation {
	private static final FileOperation INSTANCE = new FileOperation();
	private String fileSuffix = ".game";

	private FileOperation() {

	}

	public static FileOperation getIntance() {
		return INSTANCE;
	}

	public Path getPath(String gameName) {
		String path = gameName + fileSuffix;
		return Paths.get(path);
	}

	public <T> T load(String gameName, Class<T> type) {
		try {
			Path path = getPath(gameName);
			if (path.toFile().exists()) {
				byte[] bytes = Files.readAllBytes(getPath(gameName));
				bytes = Base64.getDecoder().decode(new String(bytes, StandardCharsets.UTF_8));
				return new Gson().fromJson(new String(bytes, StandardCharsets.UTF_8), type);
			}
			return null;
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	public <T> void save(String gameName, T t) {
		try {
			String data = new Gson().toJson(t);
			String encoded = Base64.getEncoder().encodeToString(data.getBytes(StandardCharsets.UTF_8));
			Files.write(getPath(gameName), encoded.getBytes(StandardCharsets.UTF_8));
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
}
