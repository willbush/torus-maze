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

    public void union(int x, int y) {
        if (x == y) return; // nothing to union

        if (isOutOfBounds(x, y))
            throw new IndexOutOfBoundsException("the x or y in union(x, y) is out of bounds");

        int xRoot = find(x);
        int yRoot = find(y);
        int xTreeSize = Math.abs(sets[xRoot]);
        int yTreeSize = Math.abs(sets[yRoot]);

        if (yTreeSize <= xTreeSize)
            directChildToParent(y, x, yRoot, xRoot);
        else
            directChildToParent(x, y, xRoot, yRoot);
    }

    private void directChildToParent(int child, int parent, int childRoot, int parentRoot) {
        if (sets[child] != parent) {
            sets[parentRoot] += sets[childRoot];
            sets[child] = parent;
        }
    }

    public int find(int element) {
        int root = element;
        while (sets[root] >= 0) {
            root = sets[root];
        }
        return root;
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
