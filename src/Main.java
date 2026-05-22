public class Main {

    public static void main(String[] args) {

        WeatherService weatherService =
                new WeatherService();

        WeatherForecast forecast =
                weatherService.getForecast();

        if (forecast == null) {

            System.out.println(
                    "Could not fetch forecast."
            );

            return;
        }

        int tomorrowRain =
                forecast.getRainProbabilities()[1];

        boolean heavyRainTomorrow =
                tomorrowRain >= 70;

        if (heavyRainTomorrow) {

            System.out.println(
                    "SEND SEPARATE RAIN ALERT EMAIL"
            );
        }

        PromptBuilder promptBuilder =
                new PromptBuilder();

        String prompt =
                promptBuilder.buildWeatherPrompt(
                        forecast
                );

        System.out.println(prompt);

        AIService aiService =
                new AIService();

        String summary =
                aiService.generateSummary(
                        prompt
                );

        System.out.println(
                "AI WEATHER SUMMARY:"
        );

        System.out.println(summary);
        System.out.println("finished");
    }
}