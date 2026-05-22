public class PromptBuilder {

    public String buildWeatherPrompt(
            WeatherForecast forecast
    ) {

        StringBuilder prompt =
                new StringBuilder();

        prompt.append(
                "Write a concise weather summary.\n"
        );

        prompt.append(
                "Mention:\n"
        );

        prompt.append(
                "- expected rain\n"
        );

        prompt.append(
                "- temperature trends\n"
        );

        prompt.append(
                "- unstable weather\n\n"
        );

        prompt.append(
                "Forecast:\n\n"
        );

        for (int i = 0;
             i < forecast.getDates().length;
             i++) {

            prompt.append(
                    forecast.getWeekdays()[i]
            );

            prompt.append(
                    " ("
            );

            prompt.append(
                    forecast.getDates()[i]
            );

            prompt.append(
                    ")\n"
            );

            prompt.append(
                    "Rain probability: "
            );

            prompt.append(
                    forecast
                            .getRainProbabilities()[i]
            );

            prompt.append("%\n");

            prompt.append(
                    "Max temp: "
            );

            prompt.append(
                    forecast
                            .getMaxTemperatures()[i]
            );

            prompt.append("C\n");

            prompt.append(
                    "Min temp: "
            );

            prompt.append(
                    forecast
                            .getMinTemperatures()[i]
            );

            prompt.append("C\n\n");
        }

        return prompt.toString();
    }
}