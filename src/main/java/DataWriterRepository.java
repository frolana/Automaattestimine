import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class DataWriterRepository {

    private String outputDir = System.getProperty("user.dir") + "\\src\\main\\";

    public void writeDataToFile(String fileName, String data) throws IOException {
        String outputFile = outputDir + fileName;
        BufferedWriter bw = new BufferedWriter(new FileWriter(outputFile));
        bw.write(data);
        bw.close();
    }

    public void WriteForecastsToFile(List<WeatherForecast> forecasts) throws IOException {
        Map<String, List<WeatherForecast>> groupedList = forecasts.stream().collect(Collectors.groupingBy(WeatherForecast::getCity));

        for (Map.Entry<String, List<WeatherForecast>> group : groupedList.entrySet()){
            String fileName = group.getKey() + ".txt";
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < group.getValue().size(); i++) {
                WeatherForecast element = group.getValue().get(i);
                if (i == 0) {
                    sb.append(element.toString() + " \n");
                } else {
                    sb.append(element.getDateString() + " ");
                    sb.append("min: " + String.valueOf(element.getMinTemp()) + " ");
                    sb.append("max: " + String.valueOf(element.getMaxTemp()) + " \n");
                }
            }
            writeDataToFile(fileName, sb.toString());
        }
    }
}

