import static org.mockito.Mockito.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;


public class DataWriterRepositoryTest {

    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void testFileWriting() throws IOException {

        DataWriterRepository dataWriter = new DataWriterRepository();
        String fileName = "testFile.txt";
        dataWriter.writeDataToFile(fileName, "some value");

        String inputFile = System.getProperty("user.dir") + "/resources/" + fileName;
        BufferedReader fileIn = new BufferedReader(new FileReader(inputFile));
        assertNotNull(fileIn.readLine());
    }

    @Test
    public void TestFileWritingMocked() throws IOException {
        DataWriterRepository dataWriter = mock(DataWriterRepository.class);
        dataWriter.writeDataToFile(anyString(), anyString());
        verify(dataWriter, times(1)).writeDataToFile(anyString(), anyString());
    }

    @Test(expected=IOException.class)
    public void TestFileWritingMockedException() throws IOException {
        DataWriterRepository dataWriter = mock(DataWriterRepository.class);
        doThrow(new IOException()).when(dataWriter).writeDataToFile(anyString(), anyString());
        dataWriter.writeDataToFile(anyString(), anyString());
    }
}
