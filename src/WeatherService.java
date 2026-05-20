import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WeatherService {

    private static final double LATITUDE = 41.3275;
    private static final double LONGITUDE = 19.8187;

    public boolean willRainTomorrow() {

        try {

            String urlString =
                    "https://api.open-meteo.com/v1/forecast" +
                            "?latitude=" + LATITUDE +
                            "&longitude=" + LONGITUDE +
                            "&daily=precipitation_probability_max" +
                            "&timezone=auto";

            URL url = new URL(urlString);

            HttpURLConnection connection =
                    (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    connection.getInputStream()
                            )
                    );

            StringBuilder response = new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }

            reader.close();

            String json = response.toString();

            System.out.println(json);

            // Find the SECOND occurrence
            // (the real precipitation array)

            int firstIndex =
                    json.indexOf("precipitation_probability_max");

            int secondIndex =
                    json.indexOf(
                            "precipitation_probability_max",
                            firstIndex + 1
                    );

            String sub =
                    json.substring(secondIndex);

            String numbers =
                    sub.substring(
                            sub.indexOf("[") + 1,
                            sub.indexOf("]")
                    );

            String[] values = numbers.split(",");

            int tomorrowProbability =
                    Integer.parseInt(values[1].trim());

            System.out.println(
                    "Tomorrow rain probability: "
                            + tomorrowProbability + "%"
            );

            return tomorrowProbability >= 50;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}