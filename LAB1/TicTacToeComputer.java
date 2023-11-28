package LAB1;

import java.util.Scanner;

public class TicTacToeComputer {
    private static final char HUMAN_PLAYER = 'X';
    private static final char COMPUTER_PLAYER = 'O';
    private static final char EMPTY_CELL = ' ';

    private static char[][] board = {
            { EMPTY_CELL, EMPTY_CELL, EMPTY_CELL },
            { EMPTY_CELL, EMPTY_CELL, EMPTY_CELL },
            { EMPTY_CELL, EMPTY_CELL, EMPTY_CELL }
    };

    private static boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    return false;
                }
            }
        }
        return true;
    }

    private static boolean checkWin(char player) {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                return true;
            }
        }

        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                return true;
            }
        }

        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            return true;
        }

        return board[0][2] == player && board[1][1] == player && board[2][0] == player;
    }

    private static void makeComputerMove() {
        // Check for a winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = COMPUTER_PLAYER;
                    if (checkWin(COMPUTER_PLAYER)) {
                        return;
                    }
                    board[i][j] = EMPTY_CELL;
                }
            }
        }

        // Check for blocking the human's winning move
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = HUMAN_PLAYER;
                    if (checkWin(HUMAN_PLAYER)) {
                        board[i][j] = COMPUTER_PLAYER;
                        return;
                    }
                    board[i][j] = EMPTY_CELL;
                }
            }
        }

        // Claim the center if available
        if (board[1][1] == EMPTY_CELL) {
            board[1][1] = COMPUTER_PLAYER;
            return;
        }

        // Claim a corner if available
        int[][] corners = { { 0, 0 }, { 0, 2 }, { 2, 0 }, { 2, 2 } };
        for (int[] corner : corners) {
            int row = corner[0];
            int col = corner[1];
            if (board[row][col] == EMPTY_CELL) {
                board[row][col] = COMPUTER_PLAYER;
                return;
            }
        }

        // Claim any available cell
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = COMPUTER_PLAYER;
                    return;
                }
            }
        }
    }

    private static void printBoard() {
        for (int i = 0; i < 3; i++) {
            System.out.println(" " + board[i][0] + " | " + board[i][1] + " | " + board[i][2]);
            if (i < 2) {
                System.out.println("---+---+---");
            }
        }
        System.out.println();
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            printBoard();

            if (isBoardFull()) {
                System.out.println("It's a draw!");
                break;
            }

            System.out.print("Enter row (0-2): ");
            int row = scanner.nextInt();

            System.out.print("Enter column (0-2): ");
            int col = scanner.nextInt();

            if (row < 0 || row > 2 || col < 0 || col > 2 || board[row][col] != EMPTY_CELL) {
                System.out.println("Invalid move. Try again.");
                continue;
            }

            board[row][col] = HUMAN_PLAYER;

            if (checkWin(HUMAN_PLAYER)) {
                printBoard();
                System.out.println("You win!");
                break;
            }

            if (isBoardFull()) {
                printBoard();
                System.out.println("It's a draw!");
                break;
            }

            makeComputerMove();

            if (checkWin(COMPUTER_PLAYER)) {
                printBoard();
                System.out.println("Computer wins!");
                break;
            }
        }

        scanner.close();
    }
}