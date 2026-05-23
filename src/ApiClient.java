public class ApiClient {

    private static final int DEFAULT_MAX_RETRIES = 3;
    private static final long DEFAULT_DELAY_MS = 2000;
    private static final double DEFAULT_BACKOFF_MULTIPLIER = 1.5;
    private static final long DEFAULT_MAX_DELAY_MS = 120000;

    // SIMPLE CALL (uses all defaults)
    public String get(String url) throws Exception {
        return get(url, DEFAULT_MAX_RETRIES, DEFAULT_DELAY_MS, DEFAULT_BACKOFF_MULTIPLIER);
    }

    // CUSTOM RETRIES + DELAY, DEFAULT MULTIPLIER
    public String get(String url, int maxRetries, long initialDelayMs) throws Exception {
        return get(url, maxRetries, initialDelayMs, DEFAULT_BACKOFF_MULTIPLIER);
    }

    // FULL CONTROL VERSION
    public String get(
            String url,
            int maxRetries,
            long initialDelayMs,
            double backoffMultiplier
    ) throws Exception {

        int attempt = 0;
        long currentDelay = initialDelayMs;

        while (attempt < maxRetries) {

            try {
                return makeRequest(url);

            } catch (Exception e) {

                if (!shouldRetry(e)) {
                    throw new Exception("Non-retryable error: " + e.getMessage(), e);
                }

                attempt++;

                if (attempt >= maxRetries) {
                    throw new Exception("API failed after retries", e);
                }

                System.out.println("Attempt " + attempt + " failed. Retrying...");

                Thread.sleep(currentDelay);

                currentDelay = Math.min(
                        (long)(currentDelay * backoffMultiplier),
                        DEFAULT_MAX_DELAY_MS
                );
            }
        }

        throw new Exception("Unexpected error");
    }

    private String makeRequest(String urlString) throws Exception {

        java.net.URL url = new java.net.URL(urlString);

        java.net.HttpURLConnection conn =
                (java.net.HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setConnectTimeout(5000);
        conn.setReadTimeout(5000);

        int status = conn.getResponseCode();

        if (status >= 200 && status < 300) {
            return readResponse(conn.getInputStream());
        }

        // read error response (optional but useful)
        String errorBody = "";
        try {
            errorBody = readResponse(conn.getErrorStream());
        } catch (Exception ignored) {}

        throw new ApiException("HTTP error: " + errorBody, status);
    }

    private String readResponse(java.io.InputStream stream) throws Exception {
        try (java.io.BufferedReader reader =
                     new java.io.BufferedReader(
                             new java.io.InputStreamReader(stream)
                     )) {

            StringBuilder result = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                result.append(line);
            }

            return result.toString();
        }
    }
    private boolean shouldRetry(Exception e) {

        if (e instanceof ApiException apiEx) {
            int code = apiEx.getStatusCode();
            return code >= 500 && code < 600;
        }

        if (e instanceof java.io.IOException) {
            return true;
        }

        return false;
    }
}