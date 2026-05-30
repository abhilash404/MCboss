package com.example.ai;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.concurrent.CompletableFuture;

public class OllamaClient {

    private static final String OLLAMA_URL = "http://localhost:11434/api/generate";
    private static final String MODEL = "llama3.2:3b";
    private final HttpClient httpClient;

    public OllamaClient() {
        this.httpClient = HttpClient.newHttpClient();
    }

    // Calls Ollama asynchronously so the game thread doesn't freeze
    public CompletableFuture<String> ask(String prompt) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Build the JSON request body
                JsonObject requestBody = new JsonObject();
                requestBody.addProperty("model", MODEL);
                requestBody.addProperty("prompt", prompt);
                requestBody.addProperty("stream", false);

                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create(OLLAMA_URL))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                        .build();

                HttpResponse<String> response = httpClient.send(
                        request,
                        HttpResponse.BodyHandlers.ofString()
                );

                // Parse the response and extract the text
                JsonObject jsonResponse = JsonParser.parseString(response.body()).getAsJsonObject();
                return jsonResponse.get("response").getAsString();

            } catch (Exception e) {
                // If Ollama is down or fails, return a fallback action
                return "{\"action\": \"idle\"}";
            }
        });
    }
}