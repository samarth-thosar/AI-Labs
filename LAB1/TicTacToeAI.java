package LAB1;

import java.util.Scanner;

public class TicTacToeAI {
    private static final char HUMAN_PLAYER = 'X';
    private static final char AI_PLAYER = 'O';
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

    private static int evaluateBoard() {
        // Check for wins
        if (checkWin(AI_PLAYER)) {
            return 100;
        }
        if (checkWin(HUMAN_PLAYER)) {
            return -100;
        }

        int aiLines = countLines(AI_PLAYER);
        int humanLines = countLines(HUMAN_PLAYER);

        // Favor blocking opponent lines
        int heuristicValue = aiLines - humanLines;

        // Favor center and corners
        heuristicValue += centerAndCorners();

        return heuristicValue;
    }

    private static int countLines(char player) {
        int lines = 0;

        // Check rows
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == player && board[i][1] == player && board[i][2] == player) {
                lines++;
            }
        }

        // Check columns
        for (int i = 0; i < 3; i++) {
            if (board[0][i] == player && board[1][i] == player && board[2][i] == player) {
                lines++;
            }
        }

        // Check diagonals
        if (board[0][0] == player && board[1][1] == player && board[2][2] == player) {
            lines++;
        }
        if (board[0][2] == player && board[1][1] == player && board[2][0] == player) {
            lines++;
        }

        return lines;
    }

    private static int centerAndCorners() {
        int value = 0;
        if (board[1][1] == AI_PLAYER) {
            value += 3;
        }

        if (board[0][0] == AI_PLAYER || board[0][2] == AI_PLAYER ||
                board[2][0] == AI_PLAYER || board[2][2] == AI_PLAYER) {
            value += 2;
        }

        return value;
    }

    private static int minimax(int depth, boolean isMaximizing) {
        int score = evaluateBoard();

        if (score == 100) {
            return score - depth;
        }
        if (score == -100) {
            return score + depth;
        }

        if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int best = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY_CELL) {
                        board[i][j] = AI_PLAYER;
                        best = Math.max(best, minimax(depth + 1, false));
                        board[i][j] = EMPTY_CELL;
                    }
                }
            }
            return best;
        } else {
            int best = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == EMPTY_CELL) {
                        board[i][j] = HUMAN_PLAYER;
                        best = Math.min(best, minimax(depth + 1, true));
                        board[i][j] = EMPTY_CELL;
                    }
                }
            }
            return best;
        }
    }

    private static int[] findBestMove() {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = { -1, -1 };

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == EMPTY_CELL) {
                    board[i][j] = AI_PLAYER;
                    int moveScore = minimax(0, false);
                    board[i][j] = EMPTY_CELL;

                    if (moveScore > bestScore) {
                        bestScore = moveScore;
                        bestMove[0] = i;
                        bestMove[1] = j;
                    }
                }
            }
        }

        return bestMove;
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
                System.out.println("It's a draw!");
                break;
            }

            int[] aiMove = findBestMove();
            board[aiMove[0]][aiMove[1]] = AI_PLAYER;

            if (checkWin(AI_PLAYER)) {
                printBoard();
                System.out.println("AI wins!");
                break;
            }
        }

        scanner.close();
    }
}