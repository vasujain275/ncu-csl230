package Practical08;

import java.util.Scanner;
import java.util.Arrays;

public class TSP {

    // A large value for initialization (represents infinity)
    static final int INF = Integer.MAX_VALUE / 2; // Avoid overflow

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of cities
        System.out.print("Enter number of cities: ");
        int n = scanner.nextInt();

        // Read cost matrix
        int[][] cost = new int[n][n];
        System.out.println("Enter the cost matrix (each row separated by newline, costs separated by spaces): ");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                cost[i][j] = scanner.nextInt();
            }
        }

        // Record start time before running the DP algorithm
        long startTime = System.nanoTime();

        // DP array with dimensions: (1 << n) x n
        int[][] dp = new int[1 << n][n];

        // Initialize dp array with a large number
        for (int i = 0; i < (1 << n); i++) {
            Arrays.fill(dp[i], INF);
        }

        // Starting city is 0, so only city 0 is visited initially.
        dp[1][0] = 0;

        // Iterate over all subsets of cities
        for (int mask = 0; mask < (1 << n); mask++) {
            for (int i = 0; i < n; i++) {
                // If city i is in the current subset (mask)
                if ((mask & (1 << i)) != 0) {
                    for (int j = 0; j < n; j++) {
                        // If city j is not yet visited in the current mask
                        if ((mask & (1 << j)) == 0) {
                            int nextMask = mask | (1 << j);
                            dp[nextMask][j] = Math.min(dp[nextMask][j], dp[mask][i] + cost[i][j]);
                        }
                    }
                }
            }
        }

        // Find the optimal tour cost. We end in some city i and return to starting city 0.
        int result = INF;
        for (int i = 1; i < n; i++) {
            result = Math.min(result, dp[(1 << n) - 1][i] + cost[i][0]);
        }

        // Record end time after algorithm execution
        long endTime = System.nanoTime();
        long executionTime = endTime - startTime;

        // Output the result and execution time.
        System.out.println("\nMinimum tour cost = " + result);
        System.out.println("Execution Time: " + executionTime + " nanoseconds");

        scanner.close();
    }
}
