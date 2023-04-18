package listClass;

import java.util.Iterator;

public class BasicLinkedList<T> implements Iterable<T> {

	/*
	 * Defines the structure of a node.
	 * A node maintains a reference to the next node in the linked list, as well as 
	 * a refernence to data of the parameterized type T
	 */
	private class Node<T> {
		private T data;
		private Node<T> next;
		
		public Node(T data){
			this.data = data;
			next = null;
		}
	}
	
	private Node<T> head; // head of this list
	private Node<T> tail; // tail of this list
	private int size; // # of nodes in the list
	
	/*
	 * Default constructor initializes a length 0 list. Head and rtail are both set to null.
	 */
	public BasicLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/*
	 * Returns size/length of list.
	 */
	public int getSize() {
		return size;
	}
	
	/*
	 * Adds a new node with a reference to the parameter to the end of the list.
	 * Tail is set to the new node.
	 */
	public BasicLinkedList<T> addToEnd(T data){
		Node<T> node = new Node<>(data);
		if(size == 0) {
			head = node;
			tail = node;
		} else {
			tail.next = node;
			tail = node;
		}
		size++;
		return this;
	}
	
	/*
	 * Adds a new node with a reference to the parameter to the front of the list.
	 * Head is set to the new node.
	 */
	public BasicLinkedList<T> addToFront(T data){
		Node<T> node = new Node<>(data);
		if(size == 0) {
			head = node;
			tail = node;
		} else {
			node.next = head;
			head = node;
		}
		size++;
		return this;
	}
	
	/*
	 * Returns head of the list, but does not remove.
	 * If list is size 0, returns null.
	 */
	public T getFirst() {
		if(head == null) {
			return null;
		}
		return head.data;
	}
	
	/*
	 * Returns tail of the list, but does not remove.
	 * If list is size 0, returns null.
	 */
	public T getLast() {
		if(tail == null) {
			return null;
		}
		return tail.data;
	}
	
	/*
	 * Returns head of the list and removes it. Head is set to next node.
	 * If list is size 0, returns null.
	 */
	public T retrieveFirstElement() {
		if(head == null) {
			return null;
		}
		T headData = head.data;
		if(head == tail) {
			tail = null;
		}
		head = head.next;
		size--;
		return headData;
	}
	
	/*
	 * Returns tail of the list and removes it. Tail is set to previous node.
	 * If list is size 0, returns null.
	 */
	public T retrieveLastElement() {
		if(tail == null) { // check null
			return null;
		}
		// save tail's data for when tail reference is changed.
		T tailData = tail.data;
		if(head == tail) { // size 1 case
			head = null;
			tail = null;
			size--;
			return tailData;
		}
		for(Node<T> curr = head; curr.next != null; curr = curr.next) {
			//iterates through the list. If next node is tail...
			if(curr.next == tail) { // Sets next to null, ending the list.
				tail = curr;
				curr.next = null;
				size--;
				return tailData;
			}
		}
		
		//should never run!
		return null;
	}
	
	/*
	 * Removes all nodes with data that is equal to the parameter.
	 */
	public BasicLinkedList<T> removeAllInstances(T targetData){
		
		// If head node contains the data set head to next node
		while(head != null && head.data.equals(targetData)) {
			head = head.next;
			size--;
		}
		
		// If list is empty, stop!
		if(head == null) {
			tail = null;
			return this;
		}
		
		Node<T> curr = head;
		while(curr != null && curr.next != null) { // while curr is not null and next is not null
			if(curr.next.data.equals(targetData)) { // if next should be removed
				if(curr.next != tail) { // and if next is not the tail
					curr.next = curr.next.next; // set next to the next's next
				} else { //if next is the tail
					tail = curr; //set tail to the current node
					curr.next = null; //set the next node (former tail) to null
				}
				size --; //always reduce size
			} else {
				curr = curr.next; //if next should NOT be removed
			}
		}
		return this;
	}
	
	@Override
	public Iterator<T> iterator(){
		return new Iterator<T>() {
			Node<T> curr = head; // node to be returned by the iterator
			
			@Override
			public boolean hasNext() { // returns true if the current node exists
				return curr != null;
			}
			
			@Override
			public T next() { // returns current node and moves to the following node
				T currData = curr.data;
				curr = curr.next;
				return currData;
			}
		};
	}
	
	/*
	 * Returns a String representation of the linked list.
	 * Note: Used for testing purposes!
	 */
	public String toString() {
		String output = "[";
		Iterator<T> iterator = this.iterator();
		while(iterator.hasNext()) {
			output += iterator.next().toString();
			if(iterator.hasNext()) {
				output += ", ";
			}
		}
		output += "]";
		return output;
	}
}
