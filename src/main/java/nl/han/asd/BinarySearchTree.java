package nl.han.asd;

public class BinarySearchTree {

    private static class Node {
        int value;
        Node left, right;

        Node(int value) {
            this.value = value;
        }
    }
    private Node root;

    // ---------------------------------
    // 1) Find
    // ---------------------------------
    public Node find(int value) {
        return find(root, value);
    }

    private Node find(Node node, int value) {
        if (node == null) {
            return null;
        }
        if (node.value == value) {
            return node;
        }
        if (value < node.value) {
            return find(node.left, value);
        } else {
            return find(node.right, value);
        }
    }

    // ---------------------------------
    // 2) Insert
    // ---------------------------------
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
        }
        // If value == node.value, ignore or handle duplicates as desired
        return node;
    }

    // ---------------------------------
    // 3) Remove
    // ---------------------------------
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
            // Found the node to remove
            if (node.left == null) {
                return node.right;
            } else if (node.right == null) {
                return node.left;
            } else {
                // Two children: find the min in the right subtree, replace current node value, remove that min
                Node minNode = findMin(node.right);
                node.value = minNode.value;
                node.right = remove(node.right, minNode.value);
            }
        }
        return node;
    }

    // ---------------------------------
    // 4) Find Min / Find Max
    // ---------------------------------
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

    // ---------------------------------
    // 5) Print In-Order Traversal (optional)
    // ---------------------------------
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

    // ---------------------------------
    // 6) Print Tree Structure (optional)
    // ---------------------------------
    public void printTreeStructure() {
        printTreeStructure(root, 0);
    }

    private void printTreeStructure(Node node, int level) {
        if (node != null) {
            printTreeStructure(node.right, level + 1);
            System.out.println(repeat("  ", level) + node.value);
            printTreeStructure(node.left, level + 1);
        }
    }

    // A simple custom repeat() method for Java 8 or earlier
    private String repeat(String s, int times) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < times; i++) {
            sb.append(s);
        }
        return sb.toString();
    }

    // ---------------------------------
    // 7) Main for Quick Demo (Optional)
    // ---------------------------------
    public static void main(String[] args) {
        BinarySearchTree bst = new BinarySearchTree();
        int[] values = {10, 20, 30, 40, 50, 25};

        // Insert
        for (int v : values) {
            bst.insert(v);
        }

        // Print the tree
        System.out.println("BST structure after inserts:");
        bst.printTreeStructure();

        // Find values
        System.out.println("\nFind 30: " + (bst.find(30) != null));
        System.out.println("Find 15: " + (bst.find(15) != null));

        // Min and Max
        System.out.println("\nMin value: " + bst.findMin());
        System.out.println("Max value: " + bst.findMax());

        // Remove values
        bst.remove(40);
        bst.remove(10);

        // Print the tree after removals
        System.out.println("\nBST structure after removals:");
        bst.printTreeStructure();
    }
}
