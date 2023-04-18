import java.util.ArrayList;
import java.util.Iterator;

/** 
 * The MyHashSet API is similar to the Java Set interface. This
 * collection is backed by a hash table.
 */
public class MyHashSet<E> implements Iterable<E> {

	/** Unless otherwise specified, the table will start as
	 * an array (ArrayList) of this size.*/
	private final static int DEFAULT_INITIAL_CAPACITY = 4;

	/** When the ratio of size/capacity exceeds this
	 * value, the table will be expanded. */
	private final static double MAX_LOAD_FACTOR = 0.75;

	public ArrayList<Node<E>> hashTable;

	private int size;  // number of elements in the table

	public static class Node<T> {
		private T data;
		public Node<T> next;

		private Node(T data) {
			this.data = data;
			next = null;
		}
	}

	/**
	 * Initializes an empty table with the specified capacity.  
	 *
	 * @param initialCapacity initial capacity (length) of the 
	 * underlying table
	 */
	public MyHashSet(int initialCapacity) {
		hashTable = new ArrayList<Node<E>>();
		for(int index = 0; index < initialCapacity; index++) {
			hashTable.add(null);
		}
	}

	/**
	 * Initializes an empty table of length equal to 
	 * DEFAULT_INITIAL_CAPACITY
	 */
	public MyHashSet() {
		this(DEFAULT_INITIAL_CAPACITY);
	}

	/**
	 * Returns the number of elements stored in the table.
	 * @return number of elements in the table
	 */
	public int size(){
		return size;
	}

	/**
	 * Returns the length of the table (the number of buckets).
	 * @return length of the table (capacity)
	 */
	public int getCapacity(){
		return hashTable.size();
	}

	/** 
	 * Looks for the specified element in the table.
	 * 
	 * @param element to be found
	 * @return true if the element is in the table, false otherwise
	 */
	public boolean contains(Object element) {
		int bucketIndex = Math.abs(element.hashCode() % getCapacity());
		Node<E> bucketHead = hashTable.get(bucketIndex);
		for(Node<E> bucketCurr = bucketHead; bucketCurr!= null; bucketCurr = bucketCurr.next) {
			if(bucketCurr.data.equals(element)) {
				return true;
			}
		}
		return false;
	}

	/** Adds the specified element to the collection, if it is not
	 * already present.  If the element is already in the collection, 
	 * then this method does nothing.
	 * 
	 * @param element the element to be added to the collection
	 */
	public void add(E element) {
		if(contains(element)) {
			return;
		}
		int bucketIndex = Math.abs(element.hashCode() % getCapacity());
		Node<E> elemNode = new Node<E>(element); // create new node
		elemNode.next = hashTable.get(bucketIndex); // implicitly sets new node to head of bucket list
		hashTable.set(bucketIndex, elemNode); // places new node into the bucket
		size++;
		if(((float)size / (float)getCapacity()) > MAX_LOAD_FACTOR) {
			rehash();
		}
	}

	/** Rehashes the list, doubling the size. 
	 * 
	 */
	private void rehash() {
		int doubledCapacity = getCapacity() * 2; // capacity to be used in expanded array.
		ArrayList<Node<E>> rehashTable = new ArrayList<>();
		for(int index = 0; index < doubledCapacity; index++) { // initialize expanded array
			rehashTable.add(null);
		}
		for(E element : this) { // iterates through current hashSet using defined Iterator.
			// rehashes item using increased capacity to generate index.
			int bucketIndex = Math.abs(element.hashCode() % doubledCapacity);
			Node<E> elemNode = new Node<E>(element);
			
			// places new node in its position in the new hashSet. "rehashSet"
			elemNode.next = rehashTable.get(bucketIndex);
			rehashTable.set(bucketIndex, elemNode);
		}
		hashTable = rehashTable;
	}

	/** Removes the specified element from the collection.  If the
	 * element is not present then this method should do nothing (and
	 * return false in this case).
	 *
	 * @param element the element to be removed
	 * @return true if an element was removed, false if no element 
	 * removed
	 */
	public boolean remove(Object element) {
		if(!contains(element)) {
			return false;
		}
		// find bucket which contains the element
		int bucketIndex = Math.abs(element.hashCode() % getCapacity());
		Node<E> bucketHead = hashTable.get(bucketIndex);

		// if bucket's first node should be deleted, delete it!
		if(bucketHead.data.equals(element)) {
			hashTable.set(bucketIndex, bucketHead.next);
			size--;
			return true;
		}
		
		// if bucket head is not to be deleted, iterate through the bucket. 
		for(Node<E> curr = bucketHead; curr.next != null; curr = curr.next) {
			if(curr.next.data.equals(element)) {
				curr.next = curr.next.next; // skip next element (remove)
				size--;
				return true;
			}
		}
		return false;
		
	}

	/** Returns an Iterator that can be used to iterate over
	 * all of the elements in the collection.
	 * 
	 * The order of the elements is unspecified.
	 */
	@Override
	public Iterator<E> iterator() {
		return new Iterator<E>() {

			Node<E> currNode = null;
			int bucketIndex = -1;

			/** Starts looking for the next data in the hashset. 
			 * Begins by checking the rest of the current bucket.
			 * If bucket contains no more data, iterates through the remainder of the array.
			 * 
			 * @return next value of type E contained in the hashset.
			 */
			@Override
			public E next() {
				if(currNode != null && currNode.next != null) { // fails if end of bucket
					currNode = currNode.next; // if not end of bucket, return next node in bucket
				} else { // otherwise, increment the bucket index
					do {
						bucketIndex++; 
						currNode = hashTable.get(bucketIndex);
					} while(currNode == null); // increment until new bucket with data is found
				}
				return currNode.data; // return data found in new bucket
			}
			
			/** Exclusively determines whether there is remaining data for the next method
			 * to find.
			 * Does not locate the data or move 
			 * 
			 * @return true if there is a next element to be visited.
			 */
			@Override
			public boolean hasNext() { 
				if(currNode != null && currNode.next != null) { // if inside a bucket and...
					return true; //elements remain in the current bucket
				}
				// no elements remain in the current bucket
				// start at next bucket and traverse onward
				for(int index = bucketIndex + 1; index < getCapacity(); index++) { 
					if(hashTable.get(index) != null) { // if non-empty bucket is found
						return true;
					}
				}
				return false;
			}
		};
	}

}
