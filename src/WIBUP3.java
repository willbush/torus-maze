class UnionFind {
    private int[] sets;

    public static UnionFind make(int size) {
        if (size > 0)
            return new UnionFind(size);
        else
            throw new IllegalArgumentException("size must be greater than zero");
    }

    private UnionFind(int size) {
        sets = new int[size];

        initializeSets();
    }

    private void initializeSets() {
        for (int i = 0; i < sets.length; i++)
            sets[i] = -1;
    }

    public int find(int i) {
        if (sets[i] < 0)
            return i;
        return sets[i];
    }

    public void union(int x, int y) {
        if (isOutOfBounds(x, y))
            throw new IndexOutOfBoundsException("the x or y in union(x, y) is out of bounds");

        sets[y] = x;
    }

    private boolean isOutOfBounds(int x, int y) {
        return x > sets.length - 1 || y > sets.length - 1;
    }

    public void printSets() {
        for (int value : sets)
            System.out.print(value + " ");
        System.out.println();
    }
}

public class WIBUP3 {

}
