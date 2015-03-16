class UnionFind {
    /*
    The union data structure is implemented by an array. Positive numbers are directed edges
    whose value points to the parent vertex key value. Note that the key values are the same
    as the index of the vertex. Negative numbers represent root vertices and the absolute value
    of that negative number represents the number of vertices connected to that root through unions.
     */
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

    public void union(int x, int y) {
        if (x == y) return; // nothing to union

        int xRoot = find(x);
        int yRoot = find(y);

        if (getTotalVertices(yRoot) <= getTotalVertices(xRoot))
            connectRoots(yRoot, xRoot);
        else
            connectRoots(xRoot, yRoot);
    }

    private int getTotalVertices(int root) {
        return -sets[root];
    }

    public int find(int element) {
        if (sets[element] < 0)
            return element;

        return sets[element] = find(sets[element]);
    }

    private void connectRoots(int childRoot, int parentRoot) {
        if (childRoot != parentRoot) {
            sets[parentRoot] += sets[childRoot];
            sets[childRoot] = parentRoot;
        }
    }

    public void printSets() {
        for (int value : sets)
            System.out.print(value + " ");
        System.out.println();
    }
}

public class WIBUP3 {

}
