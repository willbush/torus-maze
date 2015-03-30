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
        u = new UnionFind(10);
        System.setOut(new PrintStream(out));
    }

    @Test(expected = IllegalArgumentException.class)
    public void invalidSizeThrowsException() {
        new UnionFind(-1);
    }

    @Test
    public void simpleFindTest() {
        assertEquals(2, u.find(2));
        assertEquals(3, u.find(3));
    }

    @Test
    public void testComplexSets() {
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

        u.union(1, 9);
        assertEquals(3, u.find(8));
        assertEquals(3, u.find(9));
        assertEquals(3, u.find(7));
        u.printSets();
        assertEquals("3 3 3 -10 3 7 7 3 3 3 \n", out.toString());
        assertEquals(1, u.getSetsRemaining());

        out.reset();
        u.printStats();
        String expected = "Number of sets remaining =    1\n" +
                "Mean path length in find =   1.68\n";
        assertEquals(expected, out.toString());
    }

    @Test
    public void testComplexSets2() {
        u.union(1, 2);
        u.union(3, 4);
        u.union(5, 6);
        u.union(7, 8);
        u.union(2, 8);
        u.union(6, 1);
        u.printSets();
        assertEquals("-1 -6 1 -2 3 1 5 1 7 -1 \n", out.toString());
        u.find(3);
        u.find(6);
        u.find(8);
        u.find(2);
        u.find(1);
        u.find(6);
        u.find(8);
        out.reset();
        u.printSets();
        assertEquals("-1 -6 1 -2 3 1 1 1 1 -1 \n", out.toString());

        out.reset();
        u.printStats();
        String expected = "Number of sets remaining =    4\n" +
                "Mean path length in find =   1.53\n";
        assertEquals(expected, out.toString());
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
