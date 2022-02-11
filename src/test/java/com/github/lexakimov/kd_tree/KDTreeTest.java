package com.github.lexakimov.kd_tree;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.arrayContaining;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author akimov
 * created at 10.02.2022 19:39
 */
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
		assertThat(tree.size(), is(0));
		assertThat(tree.isEmpty(), is(true));
	}
	
	@Test
	void addElement_bad() {
		var tree = new KDTree<Integer>(2);
		assertThrows(IllegalArgumentException.class, () -> tree.add());
		assertThat(tree.size(), is(0));
		assertThrows(IllegalArgumentException.class, () -> tree.add(1));
		assertThat(tree.size(), is(0));
		assertThrows(IllegalArgumentException.class, () -> tree.add(1, 3, 2));
		assertThat(tree.size(), is(0));
	}
	
	@Test
	void addElement_good() {
		var tree = new KDTree<Integer>(2);
		assertDoesNotThrow(() -> tree.add(2, 3));
		assertThat(tree.size(), is(1));
		assertDoesNotThrow(() -> tree.add(4, 5));
		assertThat(tree.size(), is(2));
	}
	
	@Test
	void getRootOfEmptyTree() {
		var tree = new KDTree<Integer>(2);
		assertThrows(KDTree.EmptyTreeException.class, () -> tree.getRoot());
	}
	
	@Test
	void addFirstElement() {
		var tree = new KDTree<Integer>(2);
		tree.add(1, 2);
		assertThat(tree.getRoot(), arrayContaining(1, 2));
	}
	
	@Nested
	class OneDimension {
		
		@Test
		void addThreeElements_5_4_6() {
			var tree = new KDTree<Integer>(1);
			tree.add(5);
			tree.add(4);
			tree.add(6);
			assertThat(tree.size(), is(3));
			
			var root = tree.root;
			assertNotNull(root);
			{
				var items = root.items;
				assertNotNull(items);
				var valuesByDimensions = items.valuesByDimensions;
				assertNotNull(valuesByDimensions);
				assertThat(valuesByDimensions.length, is(1));
				assertThat(valuesByDimensions[0], is(5));
			}
			
			{
				var left = root.left;
				assertNotNull(left);
				var items = left.items;
				assertNotNull(items);
				var valuesByDimensions = items.valuesByDimensions;
				assertNotNull(valuesByDimensions);
				assertThat(valuesByDimensions.length, is(1));
				assertThat(valuesByDimensions[0], is(4));
			}
			
			{
				var right = root.right;
				assertNotNull(right);
				var items = right.items;
				assertNotNull(items);
				var valuesByDimensions = items.valuesByDimensions;
				assertNotNull(valuesByDimensions);
				assertThat(valuesByDimensions.length, is(1));
				assertThat(valuesByDimensions[0], is(6));
			}
			
		}
		
	}
	
}