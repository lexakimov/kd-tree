package com.github.lexakimov.kd_tree;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class KDTreeTest {

    @ParameterizedTest
    @ValueSource(ints = {0, -1, -2, -10})
    void validateDimensionsCount_bad(int dimensions) {
        assertThrows(IllegalArgumentException.class, () -> new KDTree<Integer>(dimensions));
    }

    @ParameterizedTest
    @ValueSource(ints = {1, 2, 3, 10})
    void validateDimensionsCount_good(int dimensions) {
        assertDoesNotThrow(() -> new KDTree<Integer>(dimensions));
    }

    @Test
    void getSizeOfEmpty() {
        var tree = new KDTree<Integer>(2);
        assertThat(tree.size()).isEqualTo(0);
        assertThat(tree.isEmpty()).isTrue();
    }

    @Test
    void insertElement_bad() {
        var tree = new KDTree<Integer>(2);
        assertThrows(IllegalArgumentException.class, () -> tree.insert(null));
        assertThat(tree.size()).isEqualTo(0);
        assertThrows(IllegalArgumentException.class, () -> tree.insert(null, 1));
        assertThat(tree.size()).isEqualTo(0);
        assertThrows(IllegalArgumentException.class, () -> tree.insert(null, 1, 3, 2));
        assertThat(tree.size()).isEqualTo(0);
    }

    @Test
    void insertElement_good() {
        var tree = new KDTree<Integer>(2);
        assertDoesNotThrow(() -> tree.insert(null, 2, 3));
        assertThat(tree.size()).isEqualTo(1);
        assertDoesNotThrow(() -> tree.insert(null, 4, 5));
        assertThat(tree.size()).isEqualTo(2);
    }

    private <T extends Number & Comparable<T>> void checkNodeValue(KDTree<T>.Node root, T... values) {
        assertNotNull(root);
        var point = root.point;
        assertNotNull(point);
        assertThat(point.length).isEqualTo(values.length);
        assertThat(point).isEqualTo(values);
    }

    @Nested
    @DisplayName("K = 1")
    class OneDimension {

        @Test
        void addThreeElements_5_4_6() {
            var tree = new KDTree<Integer>(1);
            tree.insert(null, 5);
            tree.insert(null, 4);
            tree.insert(null, 6);
            assertThat(tree.size()).isEqualTo(3);

            var root = tree.root;
            checkNodeValue(root, 5);
            var left = root.left;
            checkNodeValue(left, 4);
            var right = root.right;
            checkNodeValue(right, 6);
        }

        @Test
        void addThreeElements_5_4_3() {
            var tree = new KDTree<Integer>(1);
            tree.insert(null, 5);
            tree.insert(null, 4);
            tree.insert(null, 3);
            assertThat(tree.size()).isEqualTo(3);

            var root = tree.root;
            checkNodeValue(root, 5);
            var left = root.left;
            checkNodeValue(left, 4);
            var left2 = left.left;
            checkNodeValue(left2, 3);
        }
    }

    @Nested
    @DisplayName("K = 2")
    class TwoDimensions {

        @Test
        void testEmptyTree() {
            var tree = new KDTree<Integer>(2);

            assertThat(tree.search(10, 19)).isFalse();
            assertThat(tree.nearestNeighbor(8, 14)).isNull();
        }

        @Test
        void insertFirstElement() {
            var tree = new KDTree<Integer>(2);
            tree.insert(null, 1, 2);
            assertThat(tree.root.point).containsExactly(1, 2);
        }

        @Test
        void testSinglePointTree() {
            var tree = new KDTree<Integer>(2);
            tree.insert(null, 3, 6);

            assertThat(tree.search(3, 6)).isTrue();
            assertThat(tree.search(10, 19)).isFalse();

            var nearest = tree.nearestNeighbor(8, 14);
            assertThat(nearest.point).containsExactly(3, 6);
        }

        @Test
        void testInsertAndSearch() {
            var tree = new KDTree<Integer>(2);
            tree.insert(null, 3, 6);
            tree.insert(null, 17, 15);
            tree.insert(null, 13, 15);
            tree.insert(null, 6, 12);
            tree.insert(null, 9, 1);
            tree.insert(null, 2, 7);
            tree.insert(null, 10, 19);

            assertThat(tree.search(10, 19)).isTrue();
            assertThat(tree.search(12, 19)).isFalse();
        }

        @Test
        void testNearestNeighbor() {
            var tree = new KDTree<Integer>(2);
            tree.insert(null, 3, 6);
            tree.insert(null, 17, 15);
            tree.insert(null, 13, 15);
            tree.insert(null, 6, 12);
            tree.insert(null, 9, 1);
            tree.insert(null, 2, 7);
            tree.insert(null, 10, 19);

            var nearest = tree.nearestNeighbor(8, 14);
            assertThat(nearest.point).containsExactly(6, 12);
        }
    }

    @Nested
    @DisplayName("K = 3")
    class ThreeDimensions {

        @Test
        void addThreeElements() {
            var tree = new KDTree<Integer>(3);
            tree.insert(null, 5, -3, 7);
            tree.insert(null, 3, 2, 8);
            tree.insert(null, 2, 5, 1);
            assertThat(tree.size()).isEqualTo(3);

            var root = tree.root;
            checkNodeValue(root, 5, -3, 7);
            var left = root.left;
            checkNodeValue(root, 5, -3, 7);
            var right = root.right;
        }
    }

}
