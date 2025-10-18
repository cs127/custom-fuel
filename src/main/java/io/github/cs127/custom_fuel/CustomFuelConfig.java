package io.github.cs127.custom_fuel;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.Map;
import java.util.Set;

public class CustomFuelConfig {
	private static final Gson GSON = new Gson();

	public Map<String, Integer> include = Collections.emptyMap();
	public Set<String> exclude = Collections.emptySet();

	public static CustomFuelConfig getConfig(File file) throws IOException {
		CustomFuelConfig config;

		// create a config file if there isn't one
		file.createNewFile();

		FileInputStream stream = new FileInputStream(file);
		InputStreamReader reader = new InputStreamReader(stream);

		config = GSON.fromJson(reader, CustomFuelConfig.class);

		if (config == null) config = new CustomFuelConfig();

		return config;
	}
}
