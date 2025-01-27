package nl.han.asd;

public class AVLTree {

    // Node class
    private static class Node {
        int value;
        int height;
        Node left, right;

        Node(int value) {
            this.value = value;
            this.height = 1;
        }
    }
    private Node root;

    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null || node.value == value) {
            return node;
        }
        if (value < node.value) {
            return find(node.left, value);
        } else {
            return find(node.right, value);
        }
    }

    public int findMin() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMin(root).value;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public int findMax() {
        if (root == null) {
            throw new IllegalStateException("Tree is empty");
        }
        return findMax(root).value;
    }

    private Node findMax(Node node) {
        while (node.right != null) {
            node = node.right;
        }
        return node;
    }

    public void insert(int value) {
        root = insert(root, value);
    }

    private Node insert(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.value) {
            node.left = insert(node.left, value);
        } else if (value > node.value) {
            node.right = insert(node.right, value);
        } else {
            return node;
        }

        updateHeight(node);
        return balance(node);
    }

    public void remove(int value) {
        root = remove(root, value);
    }

    private Node remove(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (value < node.value) {
            node.left = remove(node.left, value);
        } else if (value > node.value) {
            node.right = remove(node.right, value);
        } else {
            if (node.left == null || node.right == null) {
                node = (node.left != null) ? node.left : node.right;
            } else {
                Node minNode = findMin(node.right);
                node.value = minNode.value;
                node.right = remove(node.right, minNode.value);
            }
        }

        if (node == null) {
            return null;
        }

        updateHeight(node);
        return balance(node);
    }

    private void updateHeight(Node node) {
        node.height = 1 + Math.max(height(node.left), height(node.right));
    }


    private Node balance(Node node) {
        int balanceFactor = getBalanceFactor(node);

        if (balanceFactor > 1) {
            if (getBalanceFactor(node.left) < 0) {
                node.left = rotateLeft(node.left);
            }
            return rotateRight(node);
        }
        if (balanceFactor < -1) {
            if (getBalanceFactor(node.right) > 0) {
                node.right = rotateRight(node.right);
            }
            return rotateLeft(node);
        }

        return node;
    }

    private Node rotateLeft(Node z) {
        Node y = z.right;
        Node T2 = y.left;

        y.left = z;
        z.right = T2;

        updateHeight(z);
        updateHeight(y);

        return y;
    }

    private Node rotateRight(Node z) {
        Node y = z.left;
        Node T3 = y.right;

        y.right = z;
        z.left = T3;

        updateHeight(z);
        updateHeight(y);

        return y;
    }


    public void printTree() {
        printTree(root);
        System.out.println();
    }

    private void printTree(Node node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(node.value + " ");
            printTree(node.right);
        }
    }

    public void printTreeStructure() {
        printTreeStructure(root, 0);
    }

    private void printTreeStructure(Node node, int level) {
        if (node != null) {
            printTreeStructure(node.right, level + 1);
            System.out.println(repeat("  ", level) + node.value + " (h=" + node.height + ")");
            printTreeStructure(node.left, level + 1);
        }
    }
    private String repeat(String str, int count) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < count; i++) {
            sb.append(str);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        AVLTree tree = new AVLTree();

        System.out.println("=== AVL Tree Operations ===");

        // Step 1: Insert values
        System.out.println("\n--- Insertions ---");
        int[] valuesToInsert = {30, 20, 40, 10, 25, 35, 50};
        for (int value : valuesToInsert) {
            System.out.println("\nInserting " + value + "...");
            tree.insert(value);
            tree.printTreeStructure(); // Visualize the tree structure after each insertion
            System.out.println("Balance Factors after insertion:");
            printBalanceFactors(tree.root); // Pass the tree's root to print balance factors
        }

        // Step 2: Search for nodes
        System.out.println("\n--- Search Tests ---");
        System.out.println("Find 25: " + (tree.find(25) != null)); // Should return true
        System.out.println("Find 15: " + (tree.find(15) != null)); // Should return false

        // Step 3: Find min and max
        System.out.println("\n--- Min and Max Tests ---");
        System.out.println("Min value: " + tree.findMin()); // Should return 10
        System.out.println("Max value: " + tree.findMax()); // Should return 50

        // Step 4: Remove values
        System.out.println("\n--- Deletion Tests ---");
        int[] valuesToRemove = {20, 30, 40}; // Test different types of deletions
        for (int value : valuesToRemove) {
            System.out.println("\nRemoving " + value + "...");
            tree.remove(value);
            tree.printTreeStructure(); // Visualize the tree structure after each deletion
            System.out.println("Balance Factors after removal:");
            printBalanceFactors(tree.root); // Pass the tree's root to print balance factors
        }

        // Final verification: Print in-order traversal
        System.out.println("\n--- In-Order Traversal ---");
        System.out.print("Tree in-order: ");
        tree.printTree(); // Should display sorted values
        System.out.println("\nNote: In-order traversal confirms the values are sorted, verifying the AVL tree property.");
    }

    /**
     * Helper method to print the balance factors of all nodes in the tree.
     * Traverses the tree and prints the balance factor for each node.
     */
    private static void printBalanceFactors(AVLTree.Node node) {
        if (node != null) {
            printBalanceFactors(node.left); // Traverse left subtree
            int balanceFactor = getBalanceFactor(node); // Compute balance factor
            System.out.println("Node " + node.value + " has Balance Factor = " + balanceFactor);
            printBalanceFactors(node.right); // Traverse right subtree
        }
    }

    /**
     * Helper method to compute the balance factor of a node using AVLTree's height method.
     */
    private static int getBalanceFactor(AVLTree.Node node) {
        return (node == null) ? 0 : height(node.left) - height(node.right);
    }

    /**
     * Utility to compute the height of a node.
     */
    private static int height(AVLTree.Node node) {
        return (node == null) ? 0 : node.height;
    }

}