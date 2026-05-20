public class Main {

    public static void main(String[] args) {

        WeatherService weatherService =
                new WeatherService();

        boolean willRain =
                weatherService.willRainTomorrow();

        if (willRain) {
            System.out.println("It will probably rain tomorrow.");
        } else {
            System.out.println("No significant rain expected tomorrow.");
        }
    }
}