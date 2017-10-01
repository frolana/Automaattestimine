public class WeatherForecastRequest {
    private String city;
    private String country;
    private String metric;

    public WeatherForecastRequest(String city, String country) {
        this.city = city;
        this.country = country;
        this.metric = "metric";
    }

    public String getCity() { return this.city; }

    public String getCountry() { return this.country; }

    public String getUnits() { return this.metric; }
}
