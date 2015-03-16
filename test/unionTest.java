import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.Assert.assertEquals;

public class unionTest {
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();
    UnionFind u;

    @Before
    public void arrange() {
        u = UnionFind.make(10);
        System.setOut(new PrintStream(out));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSizeThrowsException() {
        UnionFind.make(-1);
    }

    @Test
    public void simpleFindTest() {
        assertEquals(2, u.find(2));
        assertEquals(3, u.find(3));
    }

    @Test
    public void testComplexTree() {
        testUnion(0, 0, "-1 -1 -1 -1 -1 -1 -1 -1 -1 -1 \n"); // union same vertex does nothing
        testUnion(7, 6, "-1 -1 -1 -1 -1 -1 7 -2 -1 -1 \n");
        testUnion(3, 1, "-1 3 -1 -2 -1 -1 7 -2 -1 -1 \n");
        testUnion(0, 2, "-2 3 0 -2 -1 -1 7 -2 -1 -1 \n");
        testUnion(0, 2, "-2 3 0 -2 -1 -1 7 -2 -1 -1 \n"); // double union same vertices does nothing
        testUnion(1, 4, "-2 3 0 -3 3 -1 7 -2 -1 -1 \n");
        testUnion(5, 6, "-2 3 0 -3 3 7 7 -3 -1 -1 \n");
        testUnion(8, 9, "-2 3 0 -3 3 7 7 -3 -2 8 \n");
        testUnion(2, 3, "3 3 0 -5 3 7 7 -3 -2 8 \n");
        testUnion(6, 8, "3 3 0 -5 3 7 7 -5 7 8 \n");

        assertEquals(7, u.find(7));
        assertEquals(7, u.find(5));
        assertEquals(3, u.find(2));
        assertEquals(7, u.find(6));
        assertEquals(3, u.find(1));
        assertEquals(7, u.find(5));
        assertEquals(7, u.find(9));
        assertEquals(3, u.find(0));
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
