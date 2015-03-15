class UnionFind {

    public static UnionFind make(int size) {
        if (size > 0)
            return new UnionFind(size);
        else
            throw new IllegalArgumentException("size cannot be negative");
    }

    private UnionFind(int size) {

    }

}

public class WIBUP3 {

}
