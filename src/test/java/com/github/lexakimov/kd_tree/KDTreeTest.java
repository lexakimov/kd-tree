package com.github.lexakimov.kd_tree;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

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
	
	
}