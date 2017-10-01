/**
 * Created by Anastasiaa on 9/24/2017.
 */

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Before;
import java.util.List;

public class WeatherForecastTest {

    private WeatherForecastService weatherForecastService;
    private WeatherForecastRequest commonRequest;

    @Before
    public void setUp() throws Exception {
        weatherForecastService = new WeatherForecastService();
        commonRequest = new WeatherForecastRequest("Tallinn", "EE");
    }

    @Test
    public void testGetCurrentWeather() {
        WeatherForecast response = weatherForecastService.getCurrentForecast(commonRequest);

        assertNotNull(response);
    }

    @Test
    public void testGetCurrentWeatherCityAndCountryMatch() {
        WeatherForecast response = weatherForecastService.getCurrentForecast(commonRequest);

        assertEquals(commonRequest.getCity(), response.getCity());
        assertEquals(commonRequest.getCountry(), response.getCountry());
    }

    @Test
    public void testGetThreeDaysForecast() {
        List<WeatherForecast> response = weatherForecastService.getThreeDaysForecast(commonRequest);

        assertNotNull(response);
        assertEquals(3, response.size());

        for (WeatherForecast e : response) {
            assertNotNull(e.getCity());
        }
    }

    @Test
    public void testGeoCoordinatesExistsForCurrentWeather() {
        WeatherForecast response = new WeatherForecastService().getCurrentForecast(commonRequest);

        assertNotNull(response);
        assertNotNull(response.getCoordinates());
    }

    @Test
    public void testGeoCoordinatesExistsForForecast() {
        List<WeatherForecast> response = new WeatherForecastService().getThreeDaysForecast(commonRequest);

        for (WeatherForecast e : response) {
            assertNotNull(e.getCoordinates());
        }
    }
}
