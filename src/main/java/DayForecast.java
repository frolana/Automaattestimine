import java.util.Date;

/**
 * Created by Anastasiaa on 9/24/2017.
 */
public class DayForecast {

    private int minTemp;
    private int maxTemp;

    public DayForecast(int minTemp, int maxTemp, Date date) {
        this.maxTemp = maxTemp;
        this.minTemp = minTemp;
    }

    public int getMinTemp() {
        return minTemp;
    }

    public int getMaxTemp(){
        return maxTemp;
    }
}
