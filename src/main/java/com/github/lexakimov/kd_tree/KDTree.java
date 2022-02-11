package com.github.lexakimov.kd_tree;

/**
 * k-dimensional tree data structure.
 *
 * @author akimov
 * created at 10.02.2022 20:21
 */
public final class KDTree<T extends Comparable<T>> {
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
	
	public void add(T... valuesByDimensions) {
		if (root == null) {
			root = new Node(valuesByDimensions, null, null);
			size++;
		} else {
			addElementIntoTree(root, valuesByDimensions, 0);
		}
	}
	
	private void addElementIntoTree(Node parent, T[] child, int treeDepth) {
		var left = parent.left;
		var right = parent.right;
		
		if (left == null && right == null) {
			var isParentGreaterThanAdded = parent.compareTo(child, treeDepth) > 0;
			if (isParentGreaterThanAdded) {
				parent.left = new Node(child, null, null);
			} else {
				parent.right = new Node(child, null, null);
			}
			size++;
			return;
		}
		
		if (left != null) {
			var isLeftGreaterThanAdded = left.compareTo(child, treeDepth) > 0;
			if (isLeftGreaterThanAdded) {
				addElementIntoTree(left, child, ++treeDepth);
			} else {
				parent.right = new Node(child, null, null);
				size++;
			}
		}
		
		if (right != null) {
			var isRightLessThanAdded = right.compareTo(child, treeDepth) < 0;
			if (isRightLessThanAdded) {
			
			} else {
			
			}
		}

	}
	
	class Node {
		final T[] valuesByDimensions;
		Node left;
		Node right;
		
		private Node(T[] valuesByDimensions, Node left, Node right) {
			if (valuesByDimensions.length == 0) {
				throw new IllegalArgumentException("the number of items must be equal " + dimensions + ", not zero");
			}
			if (valuesByDimensions.length != dimensions) {
				throw new IllegalArgumentException(
						"the number of items in an element must be equal to the number of dimensions (" + dimensions + ")"
				);
			}
			this.valuesByDimensions = valuesByDimensions;
			this.left = left;
			this.right = right;
		}
		
		/**
		 * @param valuesByDimensions
		 * @param treeLevel
		 * @return a negative integer, zero, or a positive integer as the current element is less than, equal to,
		 * or greater than the element in parameter.
		 */
		private int compareTo(T[] valuesByDimensions, int treeLevel) {
			var currentDimension = treeLevel % dimensions;
			var thisValue = this.valuesByDimensions[currentDimension];
			var value = valuesByDimensions[currentDimension];
			return thisValue.compareTo(value);
		}
	}
	
}
