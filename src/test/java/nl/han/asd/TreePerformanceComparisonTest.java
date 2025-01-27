package nl.han.asd;

import java.util.Random;

public class TreePerformanceComparisonTest {

    // Configure different sizes to test
    private static final int[] SIZES = {1000, 5000, 10000};

    public static void main(String[] args) {
        System.out.println("=== Tree Performance Comparison ===");

        for (int n : SIZES) {
            System.out.println("\n--- Testing with n = " + n + " ---");

            // 1) Generate sorted array
            int[] sortedData = generateSortedArray(n);
            // 2) Generate random array
            int[] randomData = generateRandomArray(n);

            // -------------------------------------------------------
            // Measure Insertion (Sorted Data)
            // -------------------------------------------------------
            {
                AVLTree avl = new AVLTree();
                long start = System.nanoTime();
                for (int value : sortedData) {
                    avl.insert(value);
                }
                long end = System.nanoTime();
                long avlInsertSortedTime = end - start;

                BinarySearchTree bst = new BinarySearchTree();
                start = System.nanoTime();
                for (int value : sortedData) {
                    bst.insert(value);
                }
                end = System.nanoTime();
                long bstInsertSortedTime = end - start;

                System.out.println("Insertion (sorted data):");
                System.out.println("  AVL   : " + avlInsertSortedTime + " ns");
                System.out.println("  BST   : " + bstInsertSortedTime + " ns");
            }

            // -------------------------------------------------------
            // Measure Insertion (Random Data)
            // -------------------------------------------------------
            {
                AVLTree avl = new AVLTree();
                long start = System.nanoTime();
                for (int value : randomData) {
                    avl.insert(value);
                }
                long end = System.nanoTime();
                long avlInsertRandomTime = end - start;

                BinarySearchTree bst = new BinarySearchTree();
                start = System.nanoTime();
                for (int value : randomData) {
                    bst.insert(value);
                }
                end = System.nanoTime();
                long bstInsertRandomTime = end - start;

                System.out.println("Insertion (random data):");
                System.out.println("  AVL   : " + avlInsertRandomTime + " ns");
                System.out.println("  BST   : " + bstInsertRandomTime + " ns");
            }

            // -------------------------------------------------------
            // Measure Search (random data)
            //   We'll re-use the randomData arrays we already created.
            //   Insert them first, then search for each element.
            // -------------------------------------------------------
            {
                // Prepare trees
                AVLTree avl = new AVLTree();
                for (int value : randomData) {
                    avl.insert(value);
                }
                BinarySearchTree bst = new BinarySearchTree();
                for (int value : randomData) {
                    bst.insert(value);
                }

                // Measure AVL search
                long start = System.nanoTime();
                for (int value : randomData) {
                    avl.find(value);
                }
                long end = System.nanoTime();
                long avlSearchTime = end - start;

                // Measure BST search
                start = System.nanoTime();
                for (int value : randomData) {
                    bst.find(value);
                }
                end = System.nanoTime();
                long bstSearchTime = end - start;

                System.out.println("Search (random data):");
                System.out.println("  AVL   : " + avlSearchTime + " ns");
                System.out.println("  BST   : " + bstSearchTime + " ns");
            }

            // -------------------------------------------------------
            // Measure Removal (random data)
            //   We'll insert, then remove the same elements.
            // -------------------------------------------------------
            {
                // Build AVL with random data
                AVLTree avl = new AVLTree();
                for (int value : randomData) {
                    avl.insert(value);
                }

                // Measure AVL removal
                long start = System.nanoTime();
                for (int value : randomData) {
                    avl.remove(value);
                }
                long end = System.nanoTime();
                long avlRemoveTime = end - start;

                // Build BST with random data
                BinarySearchTree bst = new BinarySearchTree();
                for (int value : randomData) {
                    bst.insert(value);
                }

                // Measure BST removal
                start = System.nanoTime();
                for (int value : randomData) {
                    bst.remove(value);
                }
                end = System.nanoTime();
                long bstRemoveTime = end - start;

                System.out.println("Removal (random data):");
                System.out.println("  AVL   : " + avlRemoveTime + " ns");
                System.out.println("  BST   : " + bstRemoveTime + " ns");
            }
        }

        System.out.println("\n=== Test Completed ===");
    }

    // -------------------------------------------------------
    // Helper: Generate a sorted array of n elements
    // -------------------------------------------------------
    private static int[] generateSortedArray(int n) {
        int[] arr = new int[n];
        for (int i = 0; i < n; i++) {
            arr[i] = i;
        }
        return arr;
    }

    // -------------------------------------------------------
    // Helper: Generate a random array of n elements
    // -------------------------------------------------------
    private static int[] generateRandomArray(int n) {
        int[] arr = new int[n];
        Random rand = new Random();
        for (int i = 0; i < n; i++) {
            arr[i] = rand.nextInt(Integer.MAX_VALUE);
        }
        return arr;
    }
}
