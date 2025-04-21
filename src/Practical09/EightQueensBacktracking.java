package Practical09;

import java.util.Scanner;

public class EightQueensBacktracking {
    private static int solutionCount = 0;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the number of queens (e.g., 8): ");
        int N = scanner.nextInt();
        scanner.close();

        int[] board = new int[N];  // board[row] = col position of queen in that row

        long startTime = System.currentTimeMillis();
        solve(board, 0, N);
        long endTime = System.currentTimeMillis();

        System.out.println("Total solutions found: " + solutionCount);
        System.out.println("Execution time: " + (endTime - startTime) + " ms");
    }

    // Recursive backtracking
    private static void solve(int[] board, int row, int N) {
        if (row == N) {
            printSolution(board, N);
            solutionCount++;
            return;
        }
        for (int col = 0; col < N; col++) {
            if (isSafe(board, row, col)) {
                board[row] = col;
                solve(board, row + 1, N);
            }
        }
    }

    // Check if placing at (row, col) is safe
    private static boolean isSafe(int[] board, int row, int col) {
        for (int i = 0; i < row; i++) {
            int placedCol = board[i];
            // same column or same diagonal
            if (placedCol == col || Math.abs(placedCol - col) == Math.abs(i - row)) {
                return false;
            }
        }
        return true;
    }

    // Print one solution
    private static void printSolution(int[] board, int N) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.out.print(board[i] == j ? "Q " : ". ");
            }
            System.out.println();
        }
        System.out.println();
    }
}
