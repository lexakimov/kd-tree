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
	
	public boolean isEmpty() {
		return size == 0;
	}
	
	public void add(T... items) {
		new Element<T>(items);
		size++;
	}
	
	public void add(Element<T> element) {
	
	}
	
	public class Element<T> {
		private final T[] items;
		
		private Element(T[] items) {
			if (items.length == 0) {
				throw new IllegalArgumentException("the number of items must be equal " + dimensions + ", not zero");
			}
			if (items.length != dimensions) {
				throw new IllegalArgumentException(
						"the number of items in an element must be equal to the number of dimensions (" + dimensions + ")"
				);
			}
			this.items = items;
		}
		
		public T[] getItems() {
			return items;
		}
	}

//	public static <T> Element<T> createElement(T... values) {
//		return new Element<T>(values);
//	}
	
	
}
