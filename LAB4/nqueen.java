package LAB4;

public class nqueen {

    private int size;
    private int[] queens;

    public nqueen(int size) {
        this.size = size;
        this.queens = new int[size];
    }

    public boolean solve() {
        return placeQueens(0);
    }

    private boolean placeQueens(int row) {
        if (row == size) {
            return true;
        }

        for (int col = 0; col < size; col++) {
            if (isSafe(row, col)) {
                queens[row] = col;
                if (placeQueens(row + 1)) {
                    return true;
                }
            }
        }

        return false;
    }

    private boolean isSafe(int row, int col) {
        for (int prevRow = 0; prevRow < row; prevRow++) {
            int prevCol = queens[prevRow];
            if (col == prevCol || Math.abs(row - prevRow) == Math.abs(col - prevCol)) {
                return false;
            }
        }
        return true;
    }

    public void printSolution() {
        for (int row = 0; row < size; row++) {
            for (int col = 0; col < size; col++) {
                if (queens[row] == col) {
                    System.out.print("Q ");
                } else {
                    System.out.print(". ");
                }
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int size = 15;
        nqueen nQueens = new nqueen(size);
        if (nQueens.solve()) {
            System.out.println("Solution found:");
            nQueens.printSolution();
        } else {
            System.out.println("No solution exists.");
        }
    }
}
