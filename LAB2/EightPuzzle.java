package LAB2;

import java.util.*;

//Breadth-First Search (BFS) algorithm
//queue to perform a BFS search and a set (visited) to keep track of visited states.
//finds the optimal solution from the initial state to the goal state.

class PuzzleNode {
    int[][] state;
    PuzzleNode parent;
    int zeroRow;
    int zeroCol;
    String action;

    public PuzzleNode(int[][] state, PuzzleNode parent, int zeroRow, int zeroCol, String action) {
        this.state = state;
        this.parent = parent;
        this.zeroRow = zeroRow;
        this.zeroCol = zeroCol;
        this.action = action;
    }
}

public class EightPuzzle {
    public static final int SIZE = 3;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[][] initial = new int[SIZE][SIZE];
        int[][] goal = new int[SIZE][SIZE];

        System.out.println("Enter the initial state (3x3 matrix):");
        readMatrix(scanner, initial);

        System.out.println("Enter the goal state (3x3 matrix):");
        readMatrix(scanner, goal);

        scanner.close();

        solvePuzzle(initial, goal);
    }

    public static void readMatrix(Scanner scanner, int[][] matrix) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                matrix[i][j] = scanner.nextInt();
            }
        }
    }

    public static void solvePuzzle(int[][] initial, int[][] goal) {
        Queue<PuzzleNode> queue = new LinkedList<>();
        Set<String> visited = new HashSet<>();

        queue.add(new PuzzleNode(initial, null, findZeroRow(initial), findZeroCol(initial), "Initial"));
        visited.add(Arrays.deepToString(initial));

        while (!queue.isEmpty()) {
            PuzzleNode currentNode = queue.poll();
            printState(currentNode.state);

            if (isGoalState(currentNode.state, goal)) {
                System.out.println("Puzzle Solved!");
                printSolution(currentNode);
                return;
            }

            List<PuzzleNode> neighbors = generateNeighbors(currentNode);
            for (PuzzleNode neighbor : neighbors) {
                if (!visited.contains(Arrays.deepToString(neighbor.state))) {
                    queue.add(neighbor);
                    visited.add(Arrays.deepToString(neighbor.state));
                }
            }
        }

        System.out.println("No solution found!");
    }

    public static boolean isGoalState(int[][] state, int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }

    public static List<PuzzleNode> generateNeighbors(PuzzleNode node) {
        List<PuzzleNode> neighbors = new ArrayList<>();

        int[][] directions = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        for (int[] dir : directions) {
            int newRow = node.zeroRow + dir[0];
            int newCol = node.zeroCol + dir[1];

            if (newRow >= 0 && newRow < SIZE && newCol >= 0 && newCol < SIZE) {
                int[][] newState = new int[SIZE][SIZE];
                for (int i = 0; i < SIZE; i++) {
                    for (int j = 0; j < SIZE; j++) {
                        newState[i][j] = node.state[i][j];
                    }
                }

                newState[node.zeroRow][node.zeroCol] = newState[newRow][newCol];
                newState[newRow][newCol] = 0;

                neighbors.add(new PuzzleNode(newState, node, newRow, newCol, "Move"));
            }
        }

        return neighbors;
    }

    public static int findZeroRow(int[][] state) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (state[i][j] == 0) {
                    return i;
                }
            }
        }
        return -1;
    }

    public static int findZeroCol(int[][] state) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (state[i][j] == 0) {
                    return j;
                }
            }
        }
        return -1;
    }

    public static void printState(int[][] state) {
        System.out.println("Current State:");
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static void printSolution(PuzzleNode node) {
        List<String> actions = new ArrayList<>();
        while (node != null) {
            actions.add(node.action);
            node = node.parent;
        }

        Collections.reverse(actions);
        for (String action : actions) {
            System.out.println(action);
        }
    }
}
