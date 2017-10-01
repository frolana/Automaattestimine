import java.util.Date;

/**
 * Created by Anastasiaa on 9/24/2017.
 */
public class WeatherForecast {

    private int minTemp;
    private int maxTemp;
    private Date date;
    private String unit;
    private String city;
    private String country;
    private String cordinates;

    public int getMinTemp() {
        return minTemp;
    }

    public void setMinTemp(int minTemp) {
        this.minTemp = minTemp;
    }

    public int getMaxTemp() {
        return maxTemp;
    }

    public void setMaxTemp(int maxTemp) {
        this.maxTemp = maxTemp;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCoordinates() {
        return cordinates;
    }

    public void setCoordinates(String coordinates) {
        this.cordinates = coordinates;
    }
}
