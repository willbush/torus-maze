import org.junit.Test;

public class mazeTest {

    @Test(expected = IllegalArgumentException.class)
    public void illegalPowerThrowsException() {
        TorusMaze m = new TorusMaze(7, 2);
    }

    @Test
    public void testMazeData() {
        TorusMaze m = new TorusMaze(2, 1);
        m.printMazeData();
        m.printRawMazeData();
    }
}
