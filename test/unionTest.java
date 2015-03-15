import org.junit.Test;

public class unionTest {

    @Test
    public void canCreateUnionFindObject() {
        UnionFind u = UnionFind.make(10);
    }

    @Test(expected = IllegalArgumentException.class)
    public void negativeSizeThrowsException() {
        UnionFind u = UnionFind.make(-1);
    }
}
