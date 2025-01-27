package Practical01;

import java.util.Scanner;

public class Fibonacci {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long start = System.nanoTime();
        for (int i = 0; i < n; i++) {
            System.out.print(fibonacci(i) + " ");
        }
        long end = System.nanoTime();
        System.out.println("\nExecution Time: " + (end - start) + " ns");
    }

    public static int fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
