import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.*;

public class unionTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    UnionFind u;

    @Before
    public void arrange() {
        u = UnionFind.make(10);
        System.setOut(new PrintStream(out));
    }

    @After
    public void cleanUpOutStream() {
        System.setOut(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeSizeThrowsException() {
        UnionFind.make(-1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unionFirstArgOutOfBoundsThrowsException() {
        u.union(10, 1);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unionSecondArgOutOfBoundsThrowsException() {
        u.union(1, 10);
    }

    @Test
    public void canFindNumber() {
        assertEquals(2, u.find(2));
        assertEquals(3, u.find(3));
        u.union(3, 1);
        assertEquals(3, u.find(1));
    }

    @Test
    public void canPrintSets() {
        u.printSets();
        String expected = "-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 \n";
        assertEquals(expected, out.toString());
    }
}
