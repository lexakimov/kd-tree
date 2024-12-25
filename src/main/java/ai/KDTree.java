package ai;

import java.util.List;

public class KDTree<T extends Comparable<T>> {

    private Node<T> root;
    private final int k;

    public KDTree(int k) {
        this.k = k;
    }

    public void insert(List<T> point) {
        root = insertRecursive(root, point, 0);
    }

    private Node<T> insertRecursive(Node<T> root, List<T> point, int depth) {
        if (root == null) {
            return new Node<>(point);
        }

        int cd = depth % k;

        if (point.get(cd).compareTo(root.point.get(cd)) < 0) {
            root.left = insertRecursive(root.left, point, depth + 1);
        } else {
            root.right = insertRecursive(root.right, point, depth + 1);
        }

        return root;
    }

    public boolean search(List<T> point) {
        return searchRecursive(root, point, 0);
    }

    private boolean searchRecursive(Node<T> root, List<T> point, int depth) {
        if (root == null) {
            return false;
        }
        if (root.point.equals(point)) {
            return true;
        }

        int cd = depth % k;

        if (point.get(cd).compareTo(root.point.get(cd)) < 0) {
            return searchRecursive(root.left, point, depth + 1);
        } else {
            return searchRecursive(root.right, point, depth + 1);
        }
    }

    public List<T> nearestNeighbor(List<T> point) {
        return nearestNeighborRecursive(root, point, 0, root).point;
    }

    private Node<T> nearestNeighborRecursive(Node<T> root, List<T> point, int depth, Node<T> best) {
        if (root == null) {
            return best;
        }

        if (distance(root.point, point) < distance(best.point, point)) {
            best = root;
        }

        int cd = depth % k;
        Node<T> goodSide, badSide;

        if (point.get(cd).compareTo(root.point.get(cd)) < 0) {
            goodSide = root.left;
            badSide = root.right;
        } else {
            goodSide = root.right;
            badSide = root.left;
        }

        best = nearestNeighborRecursive(goodSide, point, depth + 1, best);

        if (Math.abs(Double.parseDouble(root.point.get(cd).toString()) - Double.parseDouble(point.get(cd).toString())) < distance(best.point, point)) {
            best = nearestNeighborRecursive(badSide, point, depth + 1, best);
        }

        return best;
    }

    private double distance(List<T> p1, List<T> p2) {
        double sum = 0;
        for (int i = 0; i < k; i++) {
            double diff = Double.parseDouble(p1.get(i).toString()) - Double.parseDouble(p2.get(i).toString());
            sum += Math.pow(diff, 2);
        }
        return Math.sqrt(sum);
    }

    private static class Node<T> {
        List<T> point;
        Node<T> left, right;

        Node(List<T> point) {
            this.point = point;
        }
    }

}
