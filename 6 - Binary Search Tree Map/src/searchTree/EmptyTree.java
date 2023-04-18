package searchTree;

import java.util.Collection;

/**
 * This class is used to represent the empty search tree: a search tree that
 * contains no entries.
 * 
 * This class is a singleton class: since all empty search trees are the same,
 * there is no need for multiple instances of this class. Instead, a single
 * instance of the class is created and made available through the static field
 * SINGLETON.
 * 
 * The constructor is private, preventing other code from mistakenly creating
 * additional instances of the class.
 *  
 */
 public class EmptyTree<K extends Comparable<K>,V> implements Tree<K,V> {
	/**
	 * This static field references the one and only instance of this class.
	 * We won't declare generic types for this one, so the same singleton
	 * can be used for any kind of EmptyTree.
	 */
	private static EmptyTree SINGLETON = new EmptyTree();

	public static  <K extends Comparable<K>, V> EmptyTree<K,V> getInstance() {
		return SINGLETON;
	}

	/**
	 * Constructor is private to enforce it being a singleton
	 *  
	 */
	private EmptyTree() {
		// Nothing to do
	}
	
	/**
	 * Returns null, as an empty tree cannot have any keys in it
	 * 
	 * @return null
	 */
	public V search(K key) {
		return null;
	}
	
	/**
	 * Returns new NonEmptyTree containing key-value pair
	 * Children of new NonEmptyTree are EmptyTree SINGLETON objects
	 * 
	 * @return NonEmptyTree containing key-value pair
	 */
	public NonEmptyTree<K, V> insert(K key, V value) {
		return new NonEmptyTree<K,V>(key, value, this, this);
	}

	/**
	 * Returns SINGLETON, since there will never be a key to delete.
	 * 
	 * @return SINGLETON
	 */
	public Tree<K, V> delete(K key) {
		return this;
	}
	
	/**
	 * Throws exception, use try-catch to handle flow of code in this scenario
	 * 
	 * @throws TreeIsEmptyException whenever run
	 */
	public K max() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * Throws exception, use try-catch to handle flow of code in this scenario
	 * 
	 * @throws TreeIsEmptyException whenever run
	 */
	public K min() throws TreeIsEmptyException {
		throw new TreeIsEmptyException();
	}

	/**
	 * Always returns 0, size of EmptyTree is 0
	 * 
	 * @return 0
	 */
	public int size() {
		return 0;
	}

	/**
	 * Does nothing. There are no keys to add to the collection
	 * No return value
	 */
	public void addKeysToCollection(Collection<K> c) {
		return;
	}

	/**
	 * Returns SINGLETON, as there is no subTree to be returned..
	 * 
	 * @return SINGLETON
	 */
	public Tree<K,V> subTree(K fromKey, K toKey) {
		return SINGLETON;
	}
}