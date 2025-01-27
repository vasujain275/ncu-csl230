package Practical01;

import java.util.Scanner;

public class IFactorial {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        long start = System.nanoTime();
        System.out.println(factorial(n));
        long end = System.nanoTime();
        System.out.println("Execution Time: " + (end - start) + " ns");
    }

    public static long factorial(int n) {
        long fact = 1;
        for (int i = 2; i <= n; i++) {
            fact *= i;
        }
        return fact;
    }
}
