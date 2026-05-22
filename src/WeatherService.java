import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.Locale;

public class WeatherService {

    private static final double LATITUDE = 41.3275;

    private static final double LONGITUDE = 19.8187;

    public WeatherForecast getForecast() {

        try {

            String urlString =
                    "https://api.open-meteo.com/v1/forecast" +
                            "?latitude=" + LATITUDE +
                            "&longitude=" + LONGITUDE +
                            "&daily=precipitation_probability_max," +
                            "temperature_2m_max," +
                            "temperature_2m_min" +
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

            StringBuilder response =
                    new StringBuilder();

            String line;

            while ((line = reader.readLine()) != null) {

                response.append(line);
            }

            reader.close();

            String json = response.toString();

            int[] rainProbabilities =
                    extractIntArray(
                            json,
                            "precipitation_probability_max"
                    );

            double[] maxTemperatures =
                    extractDoubleArray(
                            json,
                            "temperature_2m_max"
                    );

            double[] minTemperatures =
                    extractDoubleArray(
                            json,
                            "temperature_2m_min"
                    );

            int numberOfDays =
                    rainProbabilities.length;

            String[] dates =
                    new String[numberOfDays];

            String[] weekdays =
                    new String[numberOfDays];

            LocalDate today =
                    LocalDate.now();

            for (int i = 0; i < numberOfDays; i++) {

                LocalDate date =
                        today.plusDays(i);

                dates[i] =
                        date.toString();

                weekdays[i] =
                        date.getDayOfWeek()
                                .getDisplayName(
                                        TextStyle.FULL,
                                        Locale.ENGLISH
                                );
            }

            return new WeatherForecast(
                    dates,
                    weekdays,
                    rainProbabilities,
                    maxTemperatures,
                    minTemperatures
            );

        } catch (Exception e) {

            e.printStackTrace();

            return null;
        }
    }

    private int[] extractIntArray(
            String json,
            String field
    ) {

        int firstIndex =
                json.indexOf(field);

        int secondIndex =
                json.indexOf(
                        field,
                        firstIndex + 1
                );

        String sub =
                json.substring(secondIndex);

        String numbers =
                sub.substring(
                        sub.indexOf("[") + 1,
                        sub.indexOf("]")
                );

        String[] values =
                numbers.split(",");

        int[] result =
                new int[values.length];

        for (int i = 0; i < values.length; i++) {

            result[i] =
                    Integer.parseInt(
                            values[i].trim()
                    );
        }

        return result;
    }

    private double[] extractDoubleArray(
            String json,
            String field
    ) {

        int firstIndex =
                json.indexOf(field);

        int secondIndex =
                json.indexOf(
                        field,
                        firstIndex + 1
                );

        String sub =
                json.substring(secondIndex);

        String numbers =
                sub.substring(
                        sub.indexOf("[") + 1,
                        sub.indexOf("]")
                );

        String[] values =
                numbers.split(",");

        double[] result =
                new double[values.length];

        for (int i = 0; i < values.length; i++) {

            result[i] =
                    Double.parseDouble(
                            values[i].trim()
                    );
        }

        return result;
    }
}