import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class LinkedBinaryTree<E> {

    private Node<E> root;
    private int size;



    public static void main(String[] args) {
        Random rnd = new Random();
        rnd.setSeed(20204935); // set the seed to 20204935

        int[] sizes = {10, 50, 100, 200, 500, 1000, 2000, 5000, 10000, 50000};
        for (int n : sizes) {
            int numTrees = 500;
            int totalHeight = 0;
            int totalDiameter = 0;
            for (int i = 0; i < numTrees; i++) {
                LinkedBinaryTree<Integer> bt = new LinkedBinaryTree<>();
                bt.constructRandom(n);
                totalHeight += bt.height(bt.root());
                totalDiameter += bt.diameter();
            }
            double avgHeight = (double) totalHeight / numTrees;
            double avgDiameter = (double) totalDiameter / numTrees;
            System.out.println("Size: " + n + " nodes");
            System.out.println("Average height: " + avgHeight);
            System.out.println("Average diameter: " + avgDiameter);
            System.out.println();
        }
    }
    public int height(Node<E> node) {
        if (node == null) return -1;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        return 1 + Math.max(leftHeight, rightHeight);
    }

    public int diameter() {
        if (isEmpty()) return 0;
        return diameter(root);
    }

    private int diameter(Node<E> node) {
        if (node == null) return 0;
        int leftHeight = height(node.getLeft());
        int rightHeight = height(node.getRight());
        int leftDiameter = diameter(node.getLeft());
        int rightDiameter = diameter(node.getRight());
        return Math.max(leftHeight + rightHeight + 2, Math.max(leftDiameter, rightDiameter));
    }


    private static class Node<E> {
        private E element;
        private Node<E> parent;
        private Node<E> left;
        private Node<E> right;

        public Node(E element, Node<E> parent, Node<E> left, Node<E> right) {
            this.element = element;
            this.parent = parent;
            this.left = left;
            this.right = right;
        }

        public E getElement() {
            return element;
        }

        public Node<E> getParent() {
            return parent;
        }

        public Node<E> getLeft() {
            return left;
        }

        public Node<E> getRight() {
            return right;
        }

        public void setElement(E element) {
            this.element = element;
        }

        public void setParent(Node<E> parent) {
            this.parent = parent;
        }

        public void setLeft(Node<E> left) {
            this.left = left;
        }

        public void setRight(Node<E> right) {
            this.right = right;
        }
    }

    public LinkedBinaryTree() {
        root = null;
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Node<E> root() {
        return root;
    }

    public Node<E> parent(Node<E> node) {
        if (node == null) return null;
        return node.getParent();
    }

    public Node<E> left(Node<E> node) {
        if (node == null) return null;
        return node.getLeft();
    }

    public Node<E> right(Node<E> node) {
        if (node == null) return null;
        return node.getRight();
    }

    public Node<E> addRoot(E element) throws IllegalStateException {
        if (!isEmpty()) throw new IllegalStateException("Tree is not empty");
        root = new Node<>(element, null, null, null);
        size++;
        return root;
    }

    public Node<E> addLeft(Node<E> node, E element) throws IllegalArgumentException {
        if (node == null) throw new IllegalArgumentException("Node is null");
        if (node.getLeft() != null) throw new IllegalArgumentException("Node already has a left child");
        Node<E> child = new Node<>(element, node, null, null);
        node.setLeft(child);
        size++;
        return child;
    }

    public Node<E> addRight(Node<E> node, E element) throws IllegalArgumentException {
        if (node == null) throw new IllegalArgumentException("Node is null");
        if (node.getRight() != null) throw new IllegalArgumentException("Node already has a right child");
        Node<E> child = new Node<>(element, node, null, null);
        node.setRight(child);
        size++;
        return child;
    }

    public E set(Node<E> node, E element) throws IllegalArgumentException {
        if (node == null) throw new IllegalArgumentException("Node is null");
        E old = node.getElement();
        node.setElement(element);
        return old;
    }

    public String toBinaryTreeString() {
        StringBuilder sb = new StringBuilder();
        if (!isEmpty()) {
            toStringRec(root, sb, "", "");
        }
        return sb.toString();
    }

    private void toStringRec(Node<E> node, StringBuilder sb, String prefix, String childrenPrefix) {
        sb.append(prefix);
        sb.append(node.getElement());
        sb.append("\n");

        Node<E> left = node.getLeft();
        Node<E> right = node.getRight();
        if (left != null && right != null) {
            toStringRec(left, sb, childrenPrefix + "├── ", childrenPrefix + "│   ");
            toStringRec(right, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        } else if (left != null) {
            toStringRec(left, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        } else if (right != null) {
            toStringRec(right, sb, childrenPrefix + "└── ", childrenPrefix + "    ");
        }
    }

    public void constructRandom(int n) {
        if (!isEmpty()) return;
        AtomicInteger key = new AtomicInteger(0);
        root = randomTree(null, n, key);
    }

    private Node<E> randomTree(Node<E> parent, Integer n, AtomicInteger key) {
        if (n == 0) return null;
        Random rnd = new Random();
        Integer leftCount = rnd.nextInt(n); // split the number of nodes
        Node<E> node = new Node<E>((E) ((Integer) key.get()), parent, null, null);
        size++;
        key.getAndIncrement();
        node.setLeft(randomTree(node, leftCount, key));
        key.getAndIncrement();
        node.setRight(randomTree(node, n - leftCount - 1, key));
        return node;
    }
}
