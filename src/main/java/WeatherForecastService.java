import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class WeatherForecastService {

    private String apiBaseUrl;
    private String apiKey;

    public WeatherForecastService(){
        apiBaseUrl = "http://api.openweathermap.org/data/2.5/";
        apiKey = "84ca155245fbded1dcb35c2b0d491ec9";
    }

    public WeatherForecast getCurrentForecast(WeatherForecastRequest request)
    {
        try {
            JSONObject response = makeRequest(request, "weather");

            if (response == null){
                return null;
            }

            WeatherForecast forecast = new WeatherForecast();

            forecast.setCity(response.getString("name"));
            forecast.setCountry(response.getJSONObject("sys").getString("country"));
            forecast.setTemp(response.getJSONObject("main").getDouble("temp"));
            forecast.setMinTemp(response.getJSONObject("main").getDouble("temp_min"));
            forecast.setMaxTemp(response.getJSONObject("main").getDouble("temp_max"));
            forecast.setCoordinates(response.getJSONObject("coord").getDouble("lat") + ":" + response.getJSONObject("coord").getDouble("lon"));
            return forecast;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public List<WeatherForecast> getThreeDaysForecast(WeatherForecastRequest request)
    {
        List<WeatherForecast> allForecasts = new ArrayList<>();
        List<List<WeatherForecast>> sortedByDateForecasts = new ArrayList<>();
        List<WeatherForecast> responseForecasts = new ArrayList<>();

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int currentDay = cal.get(Calendar.DAY_OF_MONTH);

        JSONObject response = makeRequest(request, "forecast");

        if (response == null){
            return null;
        }

        try {
            JSONArray list = response.getJSONArray("list");

            for (int i = 0; i < list.length(); i++){
                JSONObject respWeatherObject = (JSONObject)list.get(i);
                Date forecastDate = new java.util.Date(respWeatherObject.getLong("dt")*1000);
                cal.setTime(forecastDate);

                // truncate time information.
                cal.set(Calendar.HOUR_OF_DAY, 0);
                cal.set(Calendar.MINUTE, 0);
                cal.set(Calendar.SECOND, 0);
                cal.set(Calendar.MILLISECOND, 0);

                WeatherForecast forecast = new WeatherForecast();
                forecast.setDate(cal.getTime());
                forecast.setCity(response.getJSONObject("city").getString("name"));
                forecast.setCountry(response.getJSONObject("city").getString("country"));
                forecast.setTemp(respWeatherObject.getJSONObject("main").getDouble("temp"));
                forecast.setMinTemp(respWeatherObject.getJSONObject("main").getDouble("temp_min"));
                forecast.setMaxTemp(respWeatherObject.getJSONObject("main").getDouble("temp_max"));
                forecast.setCoordinates(response.getJSONObject("city").getJSONObject("coord").getDouble("lat") + ":" + response.getJSONObject("city").getJSONObject("coord").getDouble("lon"));

                int forecastDay = cal.get(Calendar.DAY_OF_MONTH);
                int day = (forecastDay - currentDay);

                if (day > 0 && day <= 3) {
                    if (sortedByDateForecasts.size() < day || sortedByDateForecasts.size() == 0) {
                        sortedByDateForecasts.add(new ArrayList<>());

                    }
                    sortedByDateForecasts.get(sortedByDateForecasts.size() - 1).add(forecast);
                }
            }

            for (List<WeatherForecast> forecasts: sortedByDateForecasts) {
                WeatherForecast thisDayMin = Collections.min(forecasts, Comparator.comparingDouble(WeatherForecast::getTemp));
                WeatherForecast thisDayMax = Collections.max(forecasts, Comparator.comparingDouble(WeatherForecast::getTemp));

                WeatherForecast forecast = new WeatherForecast();
                forecast.setCountry(thisDayMax.getCountry());
                forecast.setCity(thisDayMax.getCity());
                forecast.setCoordinates(thisDayMax.getCoordinates());
                forecast.setTemp(thisDayMax.getTemp());
                forecast.setMaxTemp(thisDayMax.getTemp());
                forecast.setMinTemp(thisDayMin.getTemp());
                forecast.setDate(thisDayMax.getDate());

                responseForecasts.add(forecast);
            }

            return responseForecasts;
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return allForecasts;
    }

    private JSONObject makeRequest(WeatherForecastRequest request, String method){
        try {

            URL url = new URL(apiBaseUrl + method+ "?q=" + request.getCity() + "," + request.getCountry() + "&units=" + request.getUnits() + "&APPID=" + apiKey);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Accept", "application/json");

            if (conn.getResponseCode() != 200) {
                throw new RuntimeException("Failed : HTTP error code : "
                        + conn.getResponseCode());
            }

            BufferedReader streamReader = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            StringBuilder responseStrBuilder = new StringBuilder();

            String inputStr;
            while ((inputStr = streamReader.readLine()) != null)
                responseStrBuilder.append(inputStr);

            JSONObject jsonObject = new JSONObject(responseStrBuilder.toString());

            conn.disconnect();

            return jsonObject;

        } catch (Exception e) {

            e.printStackTrace();
            return null;
        }
    }
}
