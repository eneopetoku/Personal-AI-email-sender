public class WeatherForecast {

    private String[] dates;

    private String[] weekdays;

    private int[] rainProbabilities;

    private double[] maxTemperatures;

    private double[] minTemperatures;

    public WeatherForecast(
            String[] dates,
            String[] weekdays,
            int[] rainProbabilities,
            double[] maxTemperatures,
            double[] minTemperatures
    ) {

        this.dates = dates;

        this.weekdays = weekdays;

        this.rainProbabilities = rainProbabilities;

        this.maxTemperatures = maxTemperatures;

        this.minTemperatures = minTemperatures;
    }

    public String[] getDates() {
        return dates;
    }

    public String[] getWeekdays() {
        return weekdays;
    }

    public int[] getRainProbabilities() {
        return rainProbabilities;
    }

    public double[] getMaxTemperatures() {
        return maxTemperatures;
    }

    public double[] getMinTemperatures() {
        return minTemperatures;
    }
}