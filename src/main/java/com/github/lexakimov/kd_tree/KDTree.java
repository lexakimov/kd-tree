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
	
	public void add(T... items) {
		var itemsObj = new Items(items);
		if (root == null) {
			root = new Node(itemsObj, null, null);
		} else {
			addElementIntoTree(root, itemsObj, 0);
		}
	}
	
	private void addElementIntoTree(Node parent, Items child, int treeDepth) {
		var left = parent.left;
		var right = parent.right;
		
		if (left == null && right == null) {
			var isParentGreaterThanAdded = parent.items.compareTo(child, treeDepth) > 0;
			if (isParentGreaterThanAdded) {
				parent.left = new Node(child, null, null);
			} else {
				parent.right = new Node(child, null, null);
			}
			return;
		}
		
		if (left != null) {
			var isLeftGreaterThanAdded = left.items.compareTo(child, 0) > 0;
			if (isLeftGreaterThanAdded) {
				addElementIntoTree(left, child, ++treeDepth);
			} else {
				parent.right = new Node(child, null, null);
			}
		}
		
		if (right != null) {
			var isRightLessThanAdded = right.items.compareTo(child, 0) < 0;
		}

	}
	
	public T[] getRoot() {
		if (isEmpty()) {
			throw new EmptyTreeException();
		}
		return root.items.valuesByDimensions;
	}
	
	class Items {
		final T[] valuesByDimensions;
		
		private Items(T[] valuesByDimensions) {
			if (valuesByDimensions.length == 0) {
				throw new IllegalArgumentException("the number of items must be equal " + dimensions + ", not zero");
			}
			if (valuesByDimensions.length != dimensions) {
				throw new IllegalArgumentException(
						"the number of items in an element must be equal to the number of dimensions (" + dimensions + ")"
				);
			}
			this.valuesByDimensions = valuesByDimensions;
			size++;
		}
		
		/**
		 * @param treeLevel
		 * @param items
		 * @return a negative integer, zero, or a positive integer as the current element is less than, equal to,
		 * or greater than the element in parameter.
		 */
		private int compareTo(Items items, int treeLevel) {
			var currentDimension = treeLevel % dimensions;
			var item1 = this.valuesByDimensions[currentDimension];
			var item2 = items.valuesByDimensions[currentDimension];
			return item1.compareTo(item2);
		}
	}
	
	class Node {
		final Items items;
		Node left;
		Node right;
		
		private Node(Items items, Node left, Node right) {
			this.items = items;
			this.left = left;
			this.right = right;
		}
	}
	
	public static class EmptyTreeException extends RuntimeException {}
	
}
