package LAB3;

import java.util.*;

//priority queue (openSet)- order in which positions are explored. 
//The positions prioritized - heuristic values (Manhattan)

class Tile {
    int row, col;

    public Tile(int row, int col) {
        this.row = row;
        this.col = col;
    }
}

public class bfs {
    static int[][] grid = {
            { 0, 0, 1, 0, 0 },
            { 0, 1, 0, 0, 0 },
            { 0, 0, 0, 1, 0 },
            { 0, 1, 1, 0, 0 },
            { 0, 0, 0, 0, 0 }
    };

    static int[][] directions = {
            { -1, 0 }, { 1, 0 }, { 0, -1 }, { 0, 1 }
    };

    static boolean isValid(Tile tile) {
        int row = tile.row;
        int col = tile.col;
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length && grid[row][col] == 0;
    }

    static int heuristic(Tile tile, Tile goal) {
        return Math.abs(tile.row - goal.row) + Math.abs(tile.col - goal.col);
    }

    public static List<Tile> findPath(Tile start, Tile goal) {
        PriorityQueue<Tile> openSet = new PriorityQueue<>(Comparator.comparingInt(tile -> heuristic(tile, goal)));
        Set<Tile> closedSet = new HashSet<>();
        Map<Tile, Tile> parentMap = new HashMap<>();

        openSet.add(start);
        parentMap.put(start, null);

        while (!openSet.isEmpty()) {
            Tile current = openSet.poll();

            if (current.row == goal.row && current.col == goal.col) {
                // Reconstruct the path
                List<Tile> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = parentMap.get(current);
                }
                Collections.reverse(path);
                return path;
            }

            closedSet.add(current);

            for (int[] dir : directions) {
                int newRow = current.row + dir[0];
                int newCol = current.col + dir[1];
                Tile neighbor = new Tile(newRow, newCol);

                if (!isValid(neighbor) || closedSet.contains(neighbor)) {
                    continue;
                }

                if (!openSet.contains(neighbor)) {
                    openSet.add(neighbor);
                    parentMap.put(neighbor, current);
                }
            }
        }

        return Collections.emptyList(); // No path found
    }

    public static void main(String[] args) {
        Tile start = new Tile(0, 0);
        Tile goal = new Tile(4, 4);

        List<Tile> path = findPath(start, goal);

        if (path.isEmpty()) {
            System.out.println("No path found.");
        } else {
            System.out.println("Path:");
            for (Tile tile : path) {
                System.out.println("(" + tile.row + ", " + tile.col + ")");
            }
        }
    }
}