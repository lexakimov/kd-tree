package com.github.lexakimov.kd_tree;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author akimov
 * created at 10.02.2022 19:39
 */
class KdTreeTest {
	
	@ParameterizedTest
	@ValueSource(ints = {0, -1, -2, -10})
	void validateDimensionsCount_bad(int dimensions) {
		assertThrows(IllegalArgumentException.class, () -> new KDTree<Integer>(dimensions));
	}
	
}