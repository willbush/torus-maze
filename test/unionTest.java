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
    public void simpleFindTest() {
        assertEquals(2, u.find(2));
        assertEquals(3, u.find(3));
    }

    @Test
    public void testUnion() {
        testUnion(7, 6, "-1 -1 -1 -1 -1 -1 7 -2 -1 -1 \n");
        testUnion(3, 1, "-1 3 -1 -2 -1 -1 7 -2 -1 -1 \n");
        testUnion(0, 2, "-2 3 0 -2 -1 -1 7 -2 -1 -1 \n");
        testUnion(0, 2, "-2 3 0 -2 -1 -1 7 -2 -1 -1 \n"); // union same thing should do nothing
        testUnion(1, 4, "-2 3 0 -3 1 -1 7 -2 -1 -1 \n");
        testUnion(5, 6, "-2 3 0 -3 1 6 7 -3 -1 -1 \n");
        testUnion(8, 9, "-2 3 0 -3 1 6 7 -3 -2 8 \n");
        testUnion(2, 3, "-2 3 3 -5 1 6 7 -3 -2 8 \n");
    }

    private void testUnion(int x, int y, String expected) {
        u.union(x, y);
        u.printSets();
        assertEquals(expected, out.toString());
        out.reset();
    }

    @Test
    public void canPrintSets() {
        u.printSets();
        String expected = "-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 \n";
        assertEquals(expected, out.toString());
    }
}
