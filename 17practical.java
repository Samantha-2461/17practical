/*I acknowledge the use of a large language model called Deepseek to assist me to understand what is being 
asked in the practical, debug my code and understand more about binary search trees
  */
/**
 * Practical 7 - Binary Search Tree
 * Simplified version with all code in one class
 */

import java.util.*;

public class tryBST {
    
    // Node class as inner class
    static class tNode {
        int key;
        tNode left;
        tNode right;
        
        tNode(int key) {
            this.key = key;
            this.left = null;
            this.right = null;
        }
    }
    
    private tNode root;
    
    public tryBST() {
        this.root = null;
    }
    
    public void insert(int key) {
        root = insertRec(root, key);
    }
    
    private tNode insertRec(tNode node, int key) {
        if (node == null) return new tNode(key);
        if (key < node.key) node.left = insertRec(node.left, key);
        else if (key > node.key) node.right = insertRec(node.right, key);
        return node;
    }
    
    public boolean isBST() {
        return isBSTRec(root, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }
    
    private boolean isBSTRec(tNode node, int min, int max) {
        if (node == null) return true;
        if (node.key < min || node.key > max) return false;
        return isBSTRec(node.left, min, node.key - 1) &&
               isBSTRec(node.right, node.key + 1, max);
    }
    
    public void buildBalancedBST(int n) {
        int totalKeys = (int) Math.pow(2, n) - 1;
        root = null;
        buildBalancedRec(1, totalKeys);
    }
    
    private void buildBalancedRec(int start, int end) {
        if (start > end) return;
        int middle = start + (end - start) / 2;
        insert(middle);
        buildBalancedRec(start, middle - 1);
        buildBalancedRec(middle + 1, end);
    }
    
    public int removeEvens() {
        int[] count = new int[1];
        root = removeEvensRec(root, count);
        return count[0];
    }
    
    private tNode removeEvensRec(tNode node, int[] count) {
        if (node == null) return null;
        node.left = removeEvensRec(node.left, count);
        node.right = removeEvensRec(node.right, count);
        if (node.key % 2 == 0) {
            count[0]++;
            return deleteNode(node);
        }
        return node;
    }
    
    private tNode deleteNode(tNode node) {
        if (node.left == null && node.right == null) return null;
        if (node.left == null) return node.right;
        if (node.right == null) return node.left;
        tNode successor = findMin(node.right);
        node.key = successor.key;
        node.right = deleteMin(node.right);
        return node;
    }
    
    private tNode findMin(tNode node) {
        while (node.left != null) node = node.left;
        return node;
    }
    
    private tNode deleteMin(tNode node) {
        if (node.left == null) return node.right;
        node.left = deleteMin(node.left);
        return node;
    }
    
    public int size() {
        return sizeRec(root);
    }
    
    private int sizeRec(tNode node) {
        if (node == null) return 0;
        return 1 + sizeRec(node.left) + sizeRec(node.right);
    }
    
    static class TimingStats {
        double mean;
        double stdDev;
        
        TimingStats(long[] times) {
            long sum = 0;
            for (long t : times) sum += t;
            mean = sum / (double) times.length;
            double variance = 0;
            for (long t : times) variance += Math.pow(t - mean, 2);
            variance /= times.length;
            stdDev = Math.sqrt(variance);
        }
    }
    
    public static void main(String[] args) {
        System.out.println("=== Practical 7: Binary Search Tree ===\n");
        
        // Test with small n
        System.out.println("Test with n=4 (15 keys):");
        tryBST tree = new tryBST();
        tree.buildBalancedBST(4);
        System.out.println("  Tree size: " + tree.size());
        System.out.println("  Is valid BST? " + tree.isBST());
        int removed = tree.removeEvens();
        System.out.println("  Removed even nodes: " + removed);
        System.out.println("  Is valid BST after removal? " + tree.isBST());
        System.out.println();
        
        // Timing tests
        int repetitions = 30;
        System.out.println("Timing Tests (" + repetitions + " repetitions each):");
        System.out.println("=".repeat(80));
        System.out.printf("%-20s | %-10s | %-15s | %-15s\n", 
                          "Method", "n (keys)", "Avg Time (ms)", "Std Dev");
        System.out.println("-".repeat(80));
        
        // Test different n values
        int[] testNs = {15, 16, 17, 18};
        
        for (int n : testNs) {
            int totalKeys = (int) Math.pow(2, n) - 1;
            
            // Time populate
            long[] populateTimes = new long[repetitions];
            for (int rep = 0; rep < repetitions; rep++) {
                tryBST t = new tryBST();
                long start = System.nanoTime();
                t.buildBalancedBST(n);
                long end = System.nanoTime();
                populateTimes[rep] = (end - start) / 1_000_000;
            }
            TimingStats populateStats = new TimingStats(populateTimes);
            
            // Time remove evens
            long[] removeTimes = new long[repetitions];
            for (int rep = 0; rep < repetitions; rep++) {
                tryBST t = new tryBST();
                t.buildBalancedBST(n);
                long start = System.nanoTime();
                t.removeEvens();
                long end = System.nanoTime();
                removeTimes[rep] = (end - start) / 1_000_000;
            }
            TimingStats removeStats = new TimingStats(removeTimes);
            
            System.out.printf("%-20s | n=%-7d | %-15.2f | %-15.2f\n", 
                              "Populate tree", n, populateStats.mean, populateStats.stdDev);
            System.out.printf("%-20s | n=%-7d | %-15.2f | %-15.2f\n", 
                              "Remove evens", n, removeStats.mean, removeStats.stdDev);
            System.out.println("-".repeat(80));
        }
        
        System.out.println("\n✓ Test complete!");
        System.out.println("Note: For times > 1000ms, use n=17 or higher.");
        System.out.println("      n=17 gives 131,071 keys");
        System.out.println("      n=18 gives 262,143 keys");
    }
}
