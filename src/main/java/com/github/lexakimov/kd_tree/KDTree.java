package com.github.lexakimov.kd_tree;

/**
 * k-dimensional tree data structure.
 *
 * @author akimov
 * created at 10.02.2022 20:21
 */
public class KDTree<T extends Comparable<T>> {
	private final int dimensions;
	private int size;
	
	public KDTree(int dimensions) {
		if (dimensions < 1) {
			throw new IllegalArgumentException("the number of measurements must be greater than zero");
		}
		this.dimensions = dimensions;
	}
	
	public int size() {
		return size;
	}
	
}
