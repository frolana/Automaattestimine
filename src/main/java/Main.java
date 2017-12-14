import java.io.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        try {

            WeatherForecastService weatherForecastService = new WeatherForecastService();
            DataReaderRepository dataReaderRepository = new DataReaderRepository();
            List<String> places = dataReaderRepository.getByUserInput();

            List<WeatherForecast> forecasts = weatherForecastService.getForecastsForPlaces(places);
            DataWriterRepository dataWriterRepository = new DataWriterRepository();
            dataWriterRepository.WriteForecastsToFile(forecasts);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}