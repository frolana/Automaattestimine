import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataReaderRepository {

    private static String inputFile = System.getProperty("user.dir") + "\\src\\main\\input.txt";

    public List<String> getByUserInput() throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.print("Enter a city from console(1) or read from file(2): ");
        String choice = br.readLine();

        if(Objects.equals(choice, "1")) {
            try {
                System.out.print("Enter a city in format \"City, CC\" where CC is country code:");
                List<String> res = new ArrayList<>();
                res.add(br.readLine().trim());
                return res;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (Objects.equals(choice, "2")) {
            try {
                return getDataFromFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid input");
            return null;
        }

        System.out.println("Something went wrong!");
        return null;
    }


    public List<String> getDataFromFile() throws IOException {

        List<String> countryInfo = new ArrayList<>();
        BufferedReader fileIn = new BufferedReader(new FileReader(inputFile));
        String line;
        while ((line = fileIn.readLine()) != null) {
            countryInfo.add(line);
        }
        fileIn.close();

        return countryInfo;
    }
}
