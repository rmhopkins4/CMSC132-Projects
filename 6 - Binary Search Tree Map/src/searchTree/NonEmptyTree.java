package searchTree;

import java.util.Collection;

/**
 * This class represents a non-empty search tree. An instance of this class
 * should contain:
 * <ul>
 * <li>A key
 * <li>A value (that the key maps to)
 * <li>A reference to a left Tree that contains key:value pairs such that the
 * keys in the left Tree are less than the key stored in this tree node.
 * <li>A reference to a right Tree that contains key:value pairs such that the
 * keys in the right Tree are greater than the key stored in this tree node.
 * </ul>
 *  
 */
 public class NonEmptyTree<K extends Comparable<K>, V> implements Tree<K, V> {
	
	private Tree<K,V> left, right;
	private K key;
	private V value;

	public NonEmptyTree(K key, V value, Tree<K,V> left, Tree<K,V> right) { 
		this.key = key;
		this.value = value;
		this.left = left;
		this.right = right;
	}

	/**
	 * Return the value corresponding to the key
	 * 
	 * @return value corresponding to the key
	 */
	public V search(K key) {
		int result = key.compareTo(this.key);
		if(result == 0) { // key is found
			return value;
		}
		if(result > 0) { // key is greater than this key, search right
			return right.search(key);
		} else { // key is less than this key, search left
			return left.search(key);
		}
	}
	
	/**
	 * Return the version of the tree after the insertion of the key-value pair provided
	 * 
	 * @return version of the tree after the insertion of the key-value pair provided
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		int result = key.compareTo(this.key);
		if(result == 0) { // key is found, replace value
			this.value = value;
		} else if(result > 0) { // key is greater than this key, insert right
			right = right.insert(key, value);
		} else { // key is less than this key, insert left
			left = left.insert(key, value);
		}
		return this;
	}
	
	/**
	 * Return the version of the tree after the removal of the key
	 * 
	 * @return the version of the tree after the removal of the key
	 */
	public Tree<K, V> delete(K key) {
		int result = key.compareTo(this.key);
		if(result == 0) { // key to delete is found 
			// if element is leaf, take it out. (set to singleton)
			// if element only has right child, return right child.
			// if element has left child: 
				// copy left's max to here
				// delete node copied
				// (equally efficient to method shown in class)
			 
			try { // if left exists, it will have a max, otherwise, 
				K replaceKey = left.max();
				return new NonEmptyTree<K, V>(replaceKey, search(replaceKey), left.delete(replaceKey), right);
			} catch(TreeIsEmptyException e) {
				return right;
			}
		}
		
		if(result > 0) { // key is greater than this key, delete from right
			right = right.delete(key);
			return this;
		} else { // key is less than this key, delete from left
			left = left.delete(key);
			return this;
		}
	}

	/**
	 * Return the maximum key in the tree
	 * 
	 * @return maximum key in the tree
	 */
	public K max() {
		try {
			return right.max();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	/**
	 * Return the minimum key in the tree
	 * 
	 * @return minimum key in the tree
	 */
	public K min() {
		try {
			return left.min();
		} catch (TreeIsEmptyException e) {
			return key;
		}
	}

	/**
	 * Return the size of the tree
	 * 
	 * @return size of the tree
	 */
	public int size() {
		return 1 + left.size() + right.size();
	}

	/**
	 * Adds keys to the collection c in the parameter. 
	 * No return value.
	 */
	public void addKeysToCollection(Collection<K> c) {
		// added IN-ORDER! (left, current, right)
		left.addKeysToCollection(c);
		c.add(key);
		right.addKeysToCollection(c);
	}
	
	/**
	 * Returns a sub-tree between the two parameters, inclusive. 
	 * Tree's elements will be between fromKey and toKey.
	 * 
	 * @return sub-tree of tree between the two parameters, inclusive.
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		// stored to reduce potential future calculation. 
		int compareFrom = key.compareTo(fromKey); // greater than or equal to zero if key will be included.
		int compareTo = key.compareTo(toKey); // less than or equal to zero if key will be included.
		
		// OUTSIDE OF RANGE, TOO LOW
		if(compareFrom < 0) {
			return right.subTree(fromKey, toKey); // return potential sub-tree to the right of current node.
		}
		// OUTSIDE OF RANGE, TOO HIGH
		if(compareTo > 0) {
			return left.subTree(fromKey, toKey); // return potential sub-tree to the left of current node
		}
		
		// IN RANGE 
		
		// return new NonEmptyTree maintaining key and value, and recursing through subTree for left and right children.
		return new NonEmptyTree<K, V>(key, value, left.subTree(fromKey, toKey), right.subTree(fromKey, toKey)); 
	}
}