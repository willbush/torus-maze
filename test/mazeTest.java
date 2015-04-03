import org.junit.Test;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import static junit.framework.Assert.assertEquals;

public class mazeTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Test
    public void testMazeData() {
        TorusMaze m = new TorusMaze(2, 5);
        m.printMazeData();
    }

    @Test
    public void canProcessInput1() throws IOException {
        File input = new File("test/testData/p3in1.txt");
        InputStream in = new FileInputStream(input);
        WIBUP3 program = new WIBUP3(in);

        PrintStream ps = System.out; // back up print stream
        System.setOut(new PrintStream(out));
        program.run();

        byte[] encoded = Files.readAllBytes(Paths.get("test/testData/p3out1.txt"));
        String expected = new String(encoded, StandardCharsets.UTF_8);
        assertEquals(expected, out.toString());
        System.setOut(ps); // restore print stream.
    }
}
