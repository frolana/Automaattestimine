/**
 * Created by Anastasiaa on 9/24/2017.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;

import java.util.List;

public class WeatherForecastTest {

    WeatherForecastService weatherForecastService;

    @Before
    public void setUp() throws Exception {
        weatherForecastService = new WeatherForecastService();
    }

    @Test
    public void testGetCurrentWeather() {
        WeatherForecastRequest request = new WeatherForecastRequest("Tallinn", "EE", "metric");
        WeatherForecast response = weatherForecastService.getCurrentForecast(request);

        assertNotNull(response);
        assertEquals(request.getCity(), response.getCity());
        assertEquals(request.getCountry(), response.getCountry());
    }

    @Test
    public void testGetThreeDaysForecast() {
        WeatherForecastRequest request = new WeatherForecastRequest("Tallinn", "EE", "metric");
        List<WeatherForecast> response = weatherForecastService.getThreeDaysForecast(request);

        assertNotNull(response);
        assertEquals(3, response.size());
    }

    @Test
    public void testGeoCoordinatesExists() {
        WeatherForecastRequest request = new WeatherForecastRequest("Tallinn", "EE", "metric");
        WeatherForecast response = new WeatherForecastService().getCurrentForecast(request);

        assertNotNull(response);
        assertNotNull(response.getCoordinates());
    }
}
