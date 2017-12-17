import org.junit.Test;
import java.io.IOException;
import static org.junit.Assert.*;

public class DataReaderRepositoryTest {

    private DataReaderRepository dataReaderRepository = new DataReaderRepository();

    @Test
    public void testGetDataFromInputFile() throws IOException {
        assertTrue(dataReaderRepository.getDataFromFile("input.txt").size() > 0);
    }
}
