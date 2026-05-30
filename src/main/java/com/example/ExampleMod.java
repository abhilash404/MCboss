package com.example;

import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.ai.OllamaClient;

public class ExampleMod implements ModInitializer {
	public static final String MOD_ID = "modid";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("AI Boss Mod loading...");

		OllamaClient client = new OllamaClient();
		client.ask("Reply with only this JSON and nothing else: {\"action\": \"test\"}")
				.thenAccept(response -> {
					LOGGER.info("Ollama response: " + response);
				});
	}
}