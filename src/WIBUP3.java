class UnionFind {
    /*
    The union data structure is implemented by an array. Positive numbers are directed edges
    whose value points to the parent vertex key value. Note that the key values are the same
    as the index of the vertex. Negative numbers represent root vertices and the absolute value
    of that negative number represents the number of vertices connected to that root through unions.
     */
    private int[] sets;

    private int setsRemaining;
    private int totalPathLength;
    private int totalCallsToFind;

    public static UnionFind makeSets(int size) {
        if (size > 0)
            return new UnionFind(size);
        else
            throw new IllegalArgumentException("size must be greater than zero");
    }

    private UnionFind(int size) {
        sets = new int[size];
        setsRemaining = size;
        totalPathLength = 0;
        totalCallsToFind = 0;
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

    /**
     * @return root vertex
     */
    public int find(int element) {
        totalPathLength++;
        totalCallsToFind++;

        return recursiveFind(element);
    }

    /**
     * finds root and performs path compression in the process
     *
     * @return root vertex
     */
    private int recursiveFind(int element) {
        if (sets[element] < 0)
            return element;

        totalPathLength++;
        return sets[element] = recursiveFind(sets[element]);
    }

    private void connectRoots(int childRoot, int parentRoot) {
        if (childRoot != parentRoot) {
            sets[parentRoot] += sets[childRoot];
            sets[childRoot] = parentRoot;
            setsRemaining--;
        }
    }

    public void printSets() {
        for (int value : sets)
            System.out.print(value + " ");
        System.out.println();
    }

    public void printStats() {
        double meanPathLength = (double) totalPathLength / totalCallsToFind;

        System.out.printf("Number of sets remaining = %4d\n", setsRemaining);
        System.out.printf("Mean path length in find = %6.2f\n", meanPathLength);
    }

    public int getSetsRemaining() {
        return setsRemaining;
    }
}

public class WIBUP3 {

}
