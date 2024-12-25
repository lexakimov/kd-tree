package com.github.lexakimov.kd_tree;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        var tree = new KDTree<Integer>(2);

        tree.insert(null, 3, 6);
        tree.insert(null, 17, 15);
        tree.insert(null, 13, 15);
        tree.insert(null, 6, 12);
        tree.insert(null, 9, 1);
        tree.insert(null, 2, 7);
        tree.insert(null, 10, 19);

        System.out.println(tree.search(10, 19)); // true
        System.out.println(tree.search(12, 19)); // false

        KDTree<Integer>.Node nearest = tree.nearestNeighbor(8, 14);
        System.out.println(Arrays.toString(nearest.point)); // [6, 12]
    }

}
