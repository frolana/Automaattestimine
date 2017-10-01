import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anastasiaa on 9/24/2017.
 */
public class WeatherForecastService {

    public WeatherForecast getCurrentForecast(WeatherForecastRequest request)
    {
        return new WeatherForecast();
    }

    public List<WeatherForecast> getThreeDaysForecast(WeatherForecastRequest request)
    {
        List<WeatherForecast> forecasts = new ArrayList<>();

        return forecasts;
    }

    public String getCordinates(WeatherForecastRequest request)
    {
        return "";
    }
}
