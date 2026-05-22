import java.net.http.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

public class AIService {

    public String generateSummary(String prompt) {
        try {

            String safePrompt = prompt
                    .replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", " ");

            String json = """
            {
              "model": "llama2:13b",
              "prompt": "%s",
              "stream": false
            }
            """.formatted(safePrompt);

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:11434/api/generate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(json))
                    .build();

            HttpClient client = HttpClient.newHttpClient();

            HttpResponse<String> response =
                    client.send(request, HttpResponse.BodyHandlers.ofString());

            return response.body();

        } catch (Exception e) {
            e.printStackTrace();
            return "Error generating AI summary.";
        }
    }
}