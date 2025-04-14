package Practical07;

import java.util.Scanner;

public class Knapsack {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // Read number of items and knapsack capacity
        System.out.print("Enter number of items: ");
        int n = scanner.nextInt();
        System.out.print("Enter maximum capacity of knapsack: ");
        int capacity = scanner.nextInt();

        int[] weights = new int[n];
        int[] values = new int[n];

        // Read weights and values for each item
        for (int i = 0; i < n; i++) {
            System.out.print("Enter weight for item " + (i + 1) + ": ");
            weights[i] = scanner.nextInt();
            System.out.print("Enter value for item " + (i + 1) + ": ");
            values[i] = scanner.nextInt();
        }

        // Record the start time of the algorithm
        long startTime = System.nanoTime();

        // DP table where dp[i][w] represents the maximum value
        // attainable with the first i items and capacity w.
        int[][] dp = new int[n + 1][capacity + 1];

        // Populate the DP table
        for (int i = 1; i <= n; i++) {
            for (int w = 1; w <= capacity; w++) {
                if (weights[i - 1] <= w) {
                    dp[i][w] = Math.max(values[i - 1] + dp[i - 1][w - weights[i - 1]], dp[i - 1][w]);
                } else {
                    dp[i][w] = dp[i - 1][w];
                }
            }
        }

        // Record the end time of the algorithm
        long endTime = System.nanoTime();

        // Calculate the execution time in nanoseconds
        long executionTime = endTime - startTime;

        // Output the result and execution time
        System.out.println("\nMaximum value in knapsack = " + dp[n][capacity]);
        System.out.println("Execution Time: " + executionTime + " nanoseconds");

        scanner.close();
    }
}

