package com.github.lexakimov.kd_tree;

import java.util.Arrays;

/**
 * k-dimensional tree data structure.
 */
public final class KDTree<T extends Number & Comparable<T>> {
    private final int dimensions;
    private int size;
    Node root;

    public KDTree(int dimensions) {
        if (dimensions < 1) {
            throw new IllegalArgumentException("the number of measurements must be greater than zero");
        }
        this.dimensions = dimensions;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void insert(Object value, T... keysByDimensions) {
        root = insertRecursive(root, value, keysByDimensions, 0);
    }

    private Node insertRecursive(Node root, Object value, T[] point, int depth) {
        if (root == null) {
            var node = new Node(point, value);
            size++;
            return node;
        }

        int cd = depth % dimensions;

        if (point[cd].compareTo(root.point[cd]) < 0) {
            root.left = insertRecursive(root.left, value, point, depth + 1);
            return root;
        }

        if (point[cd].compareTo(root.point[cd]) > 0) {
            root.right = insertRecursive(root.right, value, point, depth + 1);
            return root;
        }

        return root;
    }

    public boolean search(T... point) {
        return searchRecursive(root, point, 0);
    }

    private boolean searchRecursive(Node root, T[] point, int depth) {
        if (root == null) {
            return false;
        }
        if (Arrays.equals(root.point, point)) {
            return true;
        }

        int cd = depth % dimensions;

        if (point[cd].compareTo(root.point[cd]) < 0) {
            return searchRecursive(root.left, point, depth + 1);
        } else {
            return searchRecursive(root.right, point, depth + 1);
        }
    }

    public Node nearestNeighbor(T... point) {
        return nearestNeighborRecursive(root, point, 0, root);
    }

    private Node nearestNeighborRecursive(Node root, T[] point, int depth, Node best) {
        if (root == null) {
            return best;
        }

        if (distance(root.point, point) < distance(best.point, point)) {
            best = root;
        }

        int cd = depth % dimensions;
        Node goodSide, badSide;

        if (point[cd].compareTo(root.point[cd]) < 0) {
            goodSide = root.left;
            badSide = root.right;
        } else {
            goodSide = root.right;
            badSide = root.left;
        }

        best = nearestNeighborRecursive(goodSide, point, depth + 1, best);


        if (Math.abs(root.point[cd].doubleValue() - point[cd].doubleValue()) < distance(best.point, point)) {
            best = nearestNeighborRecursive(badSide, point, depth + 1, best);
        }

        return best;
    }

    private double distance(T[] p1, T[] p2) {
        double sum = 0;
        for (int i = 0; i < dimensions; i++) {
            double diff = p1[i].doubleValue() - p2[i].doubleValue();
            sum += Math.pow(diff, 2);
        }
        return Math.sqrt(sum);
    }

    public class Node {
        final Object object;
        final T[] point;
        Node left, right;

        Node(T[] point, Object object) {
            if (point.length == 0) {
                throw new IllegalArgumentException("The number of keys must be equal " + dimensions + ", not zero");
            }
            if (point.length != dimensions) {
                throw new IllegalArgumentException(
                        "the number of keys in an element must be equal to the number of dimensions (" + dimensions + ")"
                );
            }
            this.point = point;
            this.object = object;
        }
    }

}
