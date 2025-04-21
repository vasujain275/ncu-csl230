package Practical10;

import java.util.*;

public class ZeroOneKnapsackBranchAndBound {
    static class Item implements Comparable<Item> {
        int weight, profit;
        double ratio;

        Item(int weight, int profit) {
            this.weight = weight;
            this.profit = profit;
            this.ratio = (double) profit / weight;
        }

        @Override
        public int compareTo(Item other) {
            return Double.compare(other.ratio, this.ratio);
        }
    }

    static class Node {
        int level;
        int profit;
        int weight;
        double bound;

        Node(int level, int profit, int weight) {
            this.level = level;
            this.profit = profit;
            this.weight = weight;
        }
    }

    // Compute upper bound on maximum profit in subtree rooted at `node`
    private static double computeBound(Node node, int n, int capacity, Item[] items) {
        if (node.weight >= capacity) {
            return 0;
        }
        double bound = node.profit;
        int totalWeight = node.weight;
        int idx = node.level + 1;

        // include items fractionally for bounding
        while (idx < n && totalWeight + items[idx].weight <= capacity) {
            totalWeight += items[idx].weight;
            bound += items[idx].profit;
            idx++;
        }
        if (idx < n) {
            int remain = capacity - totalWeight;
            bound += items[idx].ratio * remain;
        }
        return bound;
    }

    public static int knapsack(Item[] items, int capacity) {
        Arrays.sort(items);
        int n = items.length;
        Queue<Node> pq = new PriorityQueue<>(Comparator.comparingDouble((Node x) -> -x.bound));

        Node root = new Node(-1, 0, 0);
        root.bound = computeBound(root, n, capacity, items);
        pq.offer(root);

        int maxProfit = 0;

        while (!pq.isEmpty()) {
            Node node = pq.poll();
            if (node.bound <= maxProfit) continue;

            // Next level: include the item
            Node with = new Node(node.level + 1,
                    node.profit + items[node.level + 1].profit,
                    node.weight + items[node.level + 1].weight);
            if (with.weight <= capacity) {
                maxProfit = Math.max(maxProfit, with.profit);
                with.bound = computeBound(with, n, capacity, items);
                if (with.bound > maxProfit) {
                    pq.offer(with);
                }
            }

            // Next level: exclude the item
            Node without = new Node(node.level + 1, node.profit, node.weight);
            without.bound = computeBound(without, n, capacity, items);
            if (without.bound > maxProfit) {
                pq.offer(without);
            }
        }

        return maxProfit;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter number of items: ");
        int n = scanner.nextInt();

        Item[] items = new Item[n];
        System.out.println("Enter weight and profit for each item:");
        for (int i = 0; i < n; i++) {
            System.out.print("Item " + (i+1) + " weight: ");
            int w = scanner.nextInt();
            System.out.print("Item " + (i+1) + " profit: ");
            int p = scanner.nextInt();
            items[i] = new Item(w, p);
        }

        System.out.print("Enter knapsack capacity: ");
        int capacity = scanner.nextInt();
        scanner.close();

        long start = System.currentTimeMillis();
        int maxProfit = knapsack(items, capacity);
        long end = System.currentTimeMillis();

        System.out.println("Maximum profit: " + maxProfit);
        System.out.println("Execution time: " + (end - start) + " ms");
    }
}
