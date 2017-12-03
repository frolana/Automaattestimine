import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Main {

    private static String inputFile =  System.getProperty("user.dir")+"\\src\\main\\input.txt";
    private static String outputDir = System.getProperty("user.dir")+"\\src\\main\\";

    public static void main(String[] args) {
        BufferedReader br = null;
        BufferedWriter bw = null;
        FileWriter fw = null;
        try {

            WeatherForecastRequest request = null;
            br = new BufferedReader(new InputStreamReader(System.in));
            WeatherForecastService weatherForecastService = new WeatherForecastService();
            List<String> countryInfo = null;

            label:
            while (true) {
                System.out.print("Enter a city from console(1) or read from file(2): ");
                String choice = br.readLine();

                switch (choice) {
                    case "1":
                        System.out.print("Enter a city in format \"City, CC\" where CC is country code:");
                        inputFile = br.readLine();

                        countryInfo = Arrays.asList(inputFile.split("\\s*,\\s*"));
                        request = new WeatherForecastRequest(countryInfo.get(0), countryInfo.get(1));
                        break label;
                    case "2":
                        BufferedReader fileIn = new BufferedReader(new FileReader(inputFile));
                        String line;
                        while ((line = fileIn.readLine()) != null) {
                             countryInfo = Arrays.asList(line.split("\\s*,\\s*"));
                            request = new WeatherForecastRequest(countryInfo.get(0), countryInfo.get(1));
                        }
                        fileIn.close();
                        break label;
                    default:
                        System.out.println("Something went wrong!");
                        continue;
                }
            }

            String outputFile = outputDir + countryInfo.get(0) + ".txt";

            fw = new FileWriter(outputFile);
            bw = new BufferedWriter(new FileWriter(outputFile));
            WeatherForecast currentResponse = weatherForecastService.getCurrentForecast(request);
            bw.write(currentResponse.toString());
            bw.newLine();

            List<WeatherForecast> threeDaysResponse = weatherForecastService.getThreeDaysForecast(request);
            for (WeatherForecast element : threeDaysResponse) {
                bw.write(element.getDateString() + " ");
                bw.write("min: " + String.valueOf(element.getMinTemp()) + " ");
                bw.write("max: " + String.valueOf(element.getMaxTemp()));
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                    if (bw != null)
                        bw.close();

                    if (fw != null)
                        fw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}