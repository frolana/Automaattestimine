/**
 * Created by Anastasiaa on 9/24/2017.
 */

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
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

    @Test
    public void testCurrentWeatherIfFilesCreated() throws IOException {

        WeatherForecastService weatherForecastService = mock(WeatherForecastService.class);
        DataWriterRepository dataWriter = new DataWriterRepository();

        WeatherForecastRequest request = new WeatherForecastRequest("Tallinn", "EE");
        WeatherForecast response = new WeatherForecast();
        response.setCity("Tallinn");
        response.setCountry("EE");
        response.setCoordinates("0.000000;0.000000");
        response.setDate(new Date());

        // delete file if exists
        File file = new File(System.getProperty("user.dir") + "\\" + request.getCity().toLowerCase() + ".txt");
        if (file.exists() && !file.isDirectory()){
            file.delete();
        }

        when(weatherForecastService.getCurrentForecast(request)).thenReturn(response);
        response = weatherForecastService.getCurrentForecast(request);
        verify(weatherForecastService, times(1)).getCurrentForecast(request);

        dataWriter.writeDataToFile(request.getCity().toLowerCase() + ".txt", response.toString());
        file = new File(System.getProperty("user.dir") + "/resources/" + request.getCity().toLowerCase() + ".txt");
        assertTrue(file.exists());
    }

    @Test
    public void testBothRequestsIfFilesCreated() throws IOException {
        WeatherForecastService weatherForecastService = mock(WeatherForecastService.class);
        DataWriterRepository dataWriter = new DataWriterRepository();
        List<String> places = new ArrayList<>();
        places.add("Tallinn,EE");
        List<WeatherForecast> response = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            WeatherForecast rsp = new WeatherForecast();
            rsp.setCity("Tallinn");
            rsp.setCountry("EE");
            rsp.setCoordinates("0.000000;0.000000");
            rsp.setDate(new Date());
            response.add(rsp);
        }

        // delete file if exists
        File file = new File(System.getProperty("user.dir") + "/resources/Tallinn.txt");
        if (file.exists() && !file.isDirectory()){
            file.delete();
        }

        when(weatherForecastService.getForecastsForPlaces(places)).thenReturn(response);
        response = weatherForecastService.getForecastsForPlaces(places);
        verify(weatherForecastService, times(1)).getForecastsForPlaces(places);
        dataWriter.writeForecastsToFile(response);

        file = new File(System.getProperty("user.dir") + "/resources/Tallinn.txt");

        assertTrue(file.exists());
    }
}
