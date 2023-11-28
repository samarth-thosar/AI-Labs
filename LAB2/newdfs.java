package LAB2;

import java.util.*;

// DFS 
//one path as deeply as possible before backtracking
//stack to implement DFS and set 

class state_node {
    int[][] state;
    state_node parent;
    int zero_row;
    int zero_col;
    int depth;
    String decision;

    state_node(int[][] state, state_node parent, int zero_row, int zero_col, String decision, int depth) {
        this.state = state;
        this.parent = parent;
        this.zero_col = zero_col;
        this.zero_row = zero_row;
        this.decision = decision;
        this.depth = depth;
    }
}

public class newdfs {
    static int[][] initial = new int[3][3];
    static int[][] goal = new int[3][3];

    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        System.out.println("Enter present state of board: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                initial[i][j] = input.nextInt();
            }
        }
        System.out.println("Enter final state of board: ");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                goal[i][j] = input.nextInt();
            }
        }
        display(initial);
        play();
    }

    public static void display(int[][] state) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(state[i][j] + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public static String findzero(int[][] state) {
        for (int i = 0; i < state.length; i++) {
            for (int j = 0; j < state[0].length; j++) {
                if (state[i][j] == 0) {
                    return i + " " + j;
                }
            }
        }
        return "not found";
    }

    public static void play() {
        Stack<state_node> stack = new Stack<>();
        Set<String> visited = new HashSet<String>();
        String zeros = findzero(initial);
        int row = Character.getNumericValue(zeros.charAt(0));
        int col = Character.getNumericValue(zeros.charAt(2));
        state_node root = new state_node(initial, null, row, col, "initial", 0);
        stack.add(root);
        int depth = 0;
        while (!stack.isEmpty()) {
            state_node state = stack.pop();
            display(state.state);
            if (state.depth >= 10) {
                continue; // Skip this state if depth exceeds 10
            }
            if (goalstate(state.state, goal)) {
                System.out.println("Puzzle Solved!");
                return;
            }
            ArrayList<state_node> branches = neighbor(state);
            for (state_node i : branches) {
                if (!visited.contains(Arrays.deepToString(i.state))) {
                    stack.push(state);
                    stack.push(i);
                    visited.add(Arrays.deepToString(i.state));
                    depth++;
                    break;
                }
            }
        }
        System.out.println("No goal state");
        return;

    }

    public static ArrayList<state_node> neighbor(state_node state) {
        String zeros = findzero(state.state);
        int row = Character.getNumericValue(zeros.charAt(0));
        int col = Character.getNumericValue(zeros.charAt(2));
        int[][] change = { { 1, 0 }, { -1, 0 }, { 0, 1 }, { 0, -1 } };
        ArrayList<state_node> newarr = new ArrayList<>();
        for (int[] arr : change) {
            int newrow = row + arr[0];
            int newcol = col + arr[1];
            if (newrow < 3 && newrow >= 0 && newcol < 3 && newcol >= 0) {
                int[][] newstate = new int[3][3];
                for (int i = 0; i < state.state.length; i++) {
                    for (int j = 0; j < state.state[0].length; j++) {
                        newstate[i][j] = state.state[i][j];
                    }
                }
                int val = newstate[newrow][newcol];
                newstate[row][col] = val;
                newstate[newrow][newcol] = 0;
                newarr.add(new state_node(newstate, state, newrow, newcol, "Moved", state.depth + 1));
            }
        }
        return newarr;
    }

    public static boolean goalstate(int[][] state, int[][] goal) {
        return Arrays.deepEquals(state, goal);
    }
}
