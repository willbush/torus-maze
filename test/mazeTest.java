import org.junit.Test;

public class mazeTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalPowerThrowsException() {
        TorusMaze m = new TorusMaze(7, 2);
    }

    @Test
    public void testMazeData() {
        System.setOut(System.out);
        TorusMaze m = new TorusMaze(2, 2);
        m.printMazeData();
        m.printRawMazeData();
    }
}
