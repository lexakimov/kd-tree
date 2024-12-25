package ai;

import java.util.Arrays;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        var tree = new KDTree<Integer>(2);

        tree.insert(Arrays.asList(3, 6));
        tree.insert(Arrays.asList(17, 15));
        tree.insert(Arrays.asList(13, 15));
        tree.insert(Arrays.asList(6, 12));
        tree.insert(Arrays.asList(9, 1));
        tree.insert(Arrays.asList(2, 7));
        tree.insert(Arrays.asList(10, 19));

        System.out.println(tree.search(Arrays.asList(10, 19))); // true
        System.out.println(tree.search(Arrays.asList(12, 19))); // false

        List<Integer> nearest = tree.nearestNeighbor(Arrays.asList(8, 14));
        System.out.println(nearest); // [6, 12]
    }

}
