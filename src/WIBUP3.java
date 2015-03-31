import java.util.Random;
import java.util.Scanner;

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
        if (x == y) return; // nothing to u

        int xRoot = find(x);
        int yRoot = find(y);

        if (getTotalVertices(yRoot) <= getTotalVertices(xRoot))
            connectRoots(yRoot, xRoot);
        else
            connectRoots(xRoot, yRoot);
    }

    /**
     * @return root vertex
     */
    public int find(int element) {
        totalPathLength++;
        totalCallsToFind++;

        return findAndPathCompress(element);
    }

    private int findAndPathCompress(int element) {
        if (sets[element] < 0)
            return element;

        totalPathLength++;
        return sets[element] = findAndPathCompress(sets[element]);
    }

    private void connectRoots(int childRoot, int parentRoot) {
        if (childRoot != parentRoot) {
            sets[parentRoot] += sets[childRoot];
            sets[childRoot] = parentRoot;
            setsRemaining--;
            printRootAndSize(parentRoot);
        }
    }

    private void printRootAndSize(int root) {
        System.out.println(root + " " + getTotalVertices(root));
    }

    private int getTotalVertices(int root) {
        return -sets[root];
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
    private int[][] adjacencyMatrix; // to keep track of undirected edges
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


    private enum Direction {
        UP, DOWN, LEFT, RIGHT;

        private static Direction getRandom() {
            return values()[(int) (Math.random() * values().length)];
        }
    }

    private void constructMaze() {
        Random r = new Random();

        while (u.getSetsRemaining() > 1) {
            // random int on interval [0, numOfNodes)
            int randomNode = r.nextInt(numOfNodes);
            int neighbor = getLegalNeighbor(randomNode, Direction.getRandom());

            if (!isMemberOfSameSet(randomNode, neighbor)) {
                u.union(randomNode, neighbor);
                addEdge(randomNode, neighbor);
            }
        }
    }

    private int getLegalNeighbor(int node, Direction direction) {
        int legalNeighbor = -1;

        switch (direction) {
            case UP:
                legalNeighbor = getAbove(node);
                break;

            case DOWN:
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

    private int getAbove(int node) {
        if (isOnTopEdge(node))
            return getOppositeOfTopEdge(node);
        else
            return node - powerOf2;
    }

    private boolean isOnTopEdge(int node) {
        return (node - powerOf2) < 0;
    }

    private int getOppositeOfTopEdge(int node) {
        return node + numOfNodes - powerOf2;
    }

    private int getBelow(int node) {
        if (isOnBottomEdge(node))
            return getOppositeOfBottomEdge(node);
        else
            return node + powerOf2;
    }

    private boolean isOnBottomEdge(int node) {
        return (node + powerOf2) > (numOfNodes - 1);
    }

    private int getOppositeOfBottomEdge(int node) {
        return node - (numOfNodes - powerOf2);
    }

    private int getLeft(int node) {
        if (isOnLeftEdge(node))
            return getOppositeOfLeftEdge(node);
        else
            return node - 1;
    }

    private boolean isOnLeftEdge(int node) {
        return (node % powerOf2) == 0;
    }

    private int getOppositeOfLeftEdge(int node) {
        return node + powerOf2 - 1;
    }

    private int getRight(int node) {
        if (isOnRightEdge(node))
            return getOppositeOfRightEdge(node);
        else
            return node + 1;
    }

    private boolean isOnRightEdge(int node) {
        return ((node + 1) % powerOf2) == 0;
    }

    private int getOppositeOfRightEdge(int node) {
        return node - (powerOf2 - 1);
    }

    private boolean isMemberOfSameSet(int randomNode, int neighbor) {
        return u.find(randomNode) == u.find(neighbor);
    }

    private void addEdge(int x, int y) {
        Random r = new Random();
        int weight = r.nextInt(maxWeight) + 1; // on interval [1, maxWeight]

        if (x < y)
            adjacencyMatrix[x][y] = weight;
        else
            adjacencyMatrix[y][x] = weight;
    }

    /*
      Note col = row + 1 in inner loop causes only the upper right half of
      the matrix to be looped over (excluding the main diagonal)
      since only that data is used for undirected edges.
      If there are n nodes, then the loop will take n*n/2 - n interations.
     */
    public void printMazeData() {
        final int maxNeighborsWithHigherKey = 3;
        int[] neighborsWithHigherKey;
        int[] neighborWeights;

        int numHigherKeyNeighbors;
        for (int row = 0; row < numOfNodes; row++) {
            numHigherKeyNeighbors = 0;
            neighborsWithHigherKey = new int[maxNeighborsWithHigherKey];
            neighborWeights = new int[maxNeighborsWithHigherKey];

            for (int col = row + 1; col < numOfNodes; col++) {
                if (adjacencyMatrix[row][col] > 0) {
                    neighborsWithHigherKey[numHigherKeyNeighbors] = col;
                    neighborWeights[numHigherKeyNeighbors] = adjacencyMatrix[row][col];
                    numHigherKeyNeighbors++;
                }
            }
            printRow(numHigherKeyNeighbors, neighborsWithHigherKey, neighborWeights);
        }
    }

    private void printRow(int numHigherKeyNeighbors, int[] neighbors, int[] weights) {
        if (numHigherKeyNeighbors > 0) {
            StringBuilder neighborString = new StringBuilder();
            StringBuilder weightString = new StringBuilder();

            neighborString.append(numHigherKeyNeighbors);
            for (int i = 0; i < neighbors.length; i++) {
                if (neighbors[i] > 0) {
                    neighborString.append(" ");
                    neighborString.append(neighbors[i]);
                    weightString.append(" ");
                    weightString.append(weights[i]);
                }
            }
            String outputString = neighborString.toString() + weightString.toString();
            System.out.println(outputString);

        } else
            System.out.println(0);
    }

    public void printRawMazeData() {
        for (int row = 0; row < numOfNodes; row++) {
            alignLeft(row);
            for (int col = row + 1; col < numOfNodes; col++) {
                System.out.print(adjacencyMatrix[row][col]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    private void alignLeft(int row) {
        for (int i = row; i > 0; i--)
            System.out.print("  ");
    }

    public void printStats() {
        u.printStats();
    }

    public void printSets() {
        u.printSets();
    }

    public int find(int element) {
        return u.find(element);
    }

    public void union(int x, int y) {
        u.union(x, y);
    }
}

public class WIBUP3 {
    private Scanner input;
    private UnionFind u;
    private TorusMaze m;
    private boolean mazeCreated;

    WIBUP3(java.io.InputStream in) {
        input = new Scanner(in);
        mazeCreated = false;
    }

    public void run() {
        String[] tokens;

        do {
            String line = input.nextLine();
            tokens = line.split(" ");
            performCommands(tokens);
        } while (inputHasNext(tokens[0]));
    }

    private void performCommands(String[] tokens) {
        switch (tokens[0]) {
            case "n":
                System.out.println("William Bush");
                break;

            case "d":
                u = new UnionFind(Integer.valueOf(tokens[1]));
                break;

            case "u":
                union(tokens);
                break;

            case "f":
                find(tokens);
                break;

            case "p":
                printSets();
                break;

            case "s":
                printStats();
                break;

            case "m":
                createMazeAndPrintData(tokens);
                break;
        }
    }

    private boolean inputHasNext(String command) {
        return !command.equals("e");
    }

    private void union(String[] tokens) {
        int x = Integer.valueOf(tokens[1]);
        int y = Integer.valueOf(tokens[2]);

        if (mazeCreated)
            m.union(x, y);
        else
            u.union(x, y);
    }

    private void find(String[] tokens) {
        Integer x = Integer.valueOf(tokens[1]);
        if (mazeCreated)
            System.out.println(m.find(x));
        else
            System.out.println(u.find(x));
    }

    private void printSets() {
        if (mazeCreated)
            m.printSets();
        else
            u.printSets();
    }

    private void printStats() {
        if (mazeCreated)
            m.printStats();
        else
            u.printStats();
    }

    private void createMazeAndPrintData(String[] tokens) {
        int power = Integer.valueOf(tokens[1]);
        int maxWeight = Integer.valueOf(tokens[2]);
        m = new TorusMaze(power, maxWeight);
        m.printMazeData();
        mazeCreated = true;
    }

    public static void main(String[] args) {
        WIBUP3 program = new WIBUP3(System.in);
        program.run();
    }
}
