import java.util.Arrays;
import java.util.Random;

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


    public UnionFind(int size) {
        if (size > 0) {
            sets = new int[size];
            setsRemaining = size;
            totalPathLength = 0;
            totalCallsToFind = 0;
            initializeSets();
        } else
            throw new IllegalArgumentException("size must be greater than zero");
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

class TorusMaze {
    private int[][] adjacencyMatrix; // to keep track of edges
    private UnionFind u;
    private final int numOfNodes;
    private final int powerOf2;
    private final int maxWeight;

    TorusMaze(int power, int maxWeight) {
        if (isInRange(power) && maxWeight > 0) {
            powerOf2 = (int) Math.pow(2, power);
            this.maxWeight = maxWeight;

            numOfNodes = powerOf2 * powerOf2;
            adjacencyMatrix = new int[numOfNodes][numOfNodes];
            u = new UnionFind(numOfNodes);
            constructMaze();
        } else {
            String message = "Power must be in range [1, 6] and max weight must be positive.";
            throw new IllegalArgumentException(message);
        }
    }

    private boolean isInRange(int power) {
        return power > 0 && power < 7;
    }

    private enum Neighbor {
        ABOVE, BELOW, LEFT, RIGHT;

        private static Neighbor getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private void constructMaze() {
        Random r = new Random();

        while (u.getSetsRemaining() > 1) {
            // random int on interval [0, numOfNodes)
            int node = r.nextInt(numOfNodes);
            int neighbor = getLegalNeighbor(node, Neighbor.getRandom());

            int nodeRoot = u.find(node);
            int neighborRoot = u.find(neighbor);

            if (nodeRoot != neighborRoot) {
                u.union(nodeRoot, neighborRoot);
                addEdge(nodeRoot, neighborRoot);
            }
        }
    }

    private int getLegalNeighbor(int node, Neighbor neighbor) {
        int legalNeighbor = -1;

        switch (neighbor) {
            case ABOVE:
                legalNeighbor = getAbove(node);
                break;

            case BELOW:
                legalNeighbor = getBelow(node);
                break;

            case LEFT:
                legalNeighbor = getLeft(node);
                break;

            case RIGHT:
                legalNeighbor = getRight(node);
                break;
        }
        return legalNeighbor;
    }

    private int getRight(int node) {
        if (((node + 1) % powerOf2) == 0)
            return node - (powerOf2 - 1);
        else
            return node + 1;
    }

    private int getLeft(int node) {
        if ((node % powerOf2) == 0)
            return node + powerOf2 - 1;
        else
            return node - 1;
    }

    private int getBelow(int node) {
        if ((node + powerOf2) > (numOfNodes - 1))
            return node - (numOfNodes - powerOf2);
        else
            return node + powerOf2;
    }

    private int getAbove(int node) {
        if ((node - powerOf2) < 0)
            return node + numOfNodes - powerOf2;
        else
            return node - powerOf2;
    }

    private void addEdge(int x, int y) {
        Random r = new Random();
        int weight = r.nextInt(maxWeight) + 1; // on interval [1, maxWeight]

        if (x < y)
            adjacencyMatrix[x][y] = weight;
        else
            adjacencyMatrix[y][x] = weight;
    }

    public void printMazeData() {
        // the number of neighbor nodes with a higher key value than the current node.
        int[][] neighbors = new int[numOfNodes][3]; // at most 3 neighbors with higher key values
        int higherKeyNeighbors = 0;
        int currentWeight = 0;

        for (int row = 0; row < numOfNodes; row++) {
            for (int col = 0; col < numOfNodes; col++) {
                if (adjacencyMatrix[row][col] > 0) {
                    neighbors[row][higherKeyNeighbors] = col;
                    currentWeight = adjacencyMatrix[row][col];
                    higherKeyNeighbors++;
                }
                System.out.println(higherKeyNeighbors + " ");
                higherKeyNeighbors = 0;
            }
        }
    }

    public void printRawMazeData() {
        for (int row = 0; row < numOfNodes; row++) {
            for (int col = 0; col < numOfNodes; col++) {
                System.out.print(adjacencyMatrix[row][col]);
                System.out.print("\t");
            }
            System.out.println();
        }
    }
}

public class WIBUP3 {

}
