package assignment3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.ListIterator;
import java.util.NoSuchElementException;

public class BasicDoubleLinkedList<T> {
	public Node head;
	public Node tail;
	protected int size;
	
	public BasicDoubleLinkedList() {
		head = null;
		tail = null;
		size = 0;
	}
	
	/**
	 * Node class - allows Nodes to be created for a Linked list
	 * @author Halim Dogrusoz
	 *
	 */
	class Node{
		T data;
		Node next;
		Node prev;
		
		Node(T d){
			data = d;
		}
	}
	
	
	class iterator implements ListIterator<T> {
		
		Node currentHead;
		Node currentTail;
		Node current;
		int size;
		int index = 0;
		
		/**
		 * Constructs the iterator
		 * @param head - the head of the list
		 * @param tail - the tail of the list
		 */
		public iterator(Node head, Node tail, int s) {
			currentHead = head;
			currentTail = tail;
			current = head;
			size = s;
		}
		
		/**
		 * Checks if the current node going forward is not null
		 */
		@Override
		public boolean hasNext() {
			return index < size;
		}

		/**
		 * Returns the current data and updates the position of the front node
		 */
		@Override
		public T next() throws NoSuchElementException{
			if(hasNext()) {
				T data = current.data;
				current = current.next;
				index++;
				return data;
			}
			else
				throw new NoSuchElementException();
			
		}

		/**
		 * Checks if the current node going backward is null
		 */
		@Override
		public boolean hasPrevious() {
			return index > 0;
		}

		/**
		 * Returns the current data and updates the position of the back node
		 */
		@Override
		public T previous() throws NoSuchElementException{
			if(hasPrevious()) {
				T data;
				if(index >= size) {
					current = currentTail;
					data = current.data;
				}
				else {
					current = current.prev;
					data = current.data;
				}
				index--;
				return data;
				
			}
			else
				throw new NoSuchElementException();
		}

		// NOT USED
		@Override
		public int nextIndex() throws UnsupportedOperationException{
			throw new UnsupportedOperationException();
		}

		// NOT USED
		@Override
		public int previousIndex() throws UnsupportedOperationException{
			throw new UnsupportedOperationException();
		}

		// NOT USED
		@Override
		public void remove() throws UnsupportedOperationException{
			throw new UnsupportedOperationException();	
		}

		// NOT USED
		@Override
		public void set(T e) throws UnsupportedOperationException {
			throw new UnsupportedOperationException();	
		}

		// NOT USED
		@Override
		public void add(T e) throws UnsupportedOperationException{
			throw new UnsupportedOperationException();
		}
	}
	
	/**
	 * constructs a new iterator object
	 * @author Halim Dogrusoz
	 * @return the iterator object
	 * @throws UnsupportedOperationException
	 * @throws NoSuchElementException
	 */
	public ListIterator<T> iterator() throws UnsupportedOperationException, NoSuchElementException{
		iterator it = new iterator(this.head, this.tail, this.size);
		return it;
	}
	
	
	/**
	 * returns the size of the linked list
	 * @author Halim Dogrusoz
	 * @return size - the size of the LL
	 */
	public int getSize() {
		return size;
	}
	
	/**
	 * returns the data in the node head points to
	 * @author Halim Dogrusoz
	 * @return null if the head node points to null
	 */
	public T getFirst() {
		return this.head.data;
	}
	
	/**
	 * returns the data in the node the tail points to
	 * @return null if the tail node points to null
	 */
	public T getLast() {
		return this.tail.data;
	}
	
	/**
	 * Make a new Node and add it to the start of the list
	 * @author Halim Dogrusoz
	 * @param data - the data to be added to the front
	 * @return the updated linked list
	 */
	public BasicDoubleLinkedList<T> addToFront(T data) {
		
		// If the Linked list is empty add the first node
		if(head == null && tail == null) {
			Node firstNode = new Node(data);
			firstNode.next = null;
			firstNode.prev = null;
			head = tail = firstNode;
			size++;
		}
		
		// Add the node to the front of the Linked list
		else {
			Node firstNode = new Node(data);
			head.prev = firstNode;
			firstNode.next = head;
			firstNode.prev = null;
			head = firstNode;
			size++;
		}
		return this;
	}
	
	/**
	 * Make a new Node and add it to the end of the list
	 * @author Halim Dogrusoz
	 * @param data - the data to be added to the end
	 * @return the updated linked list
	 */
	public BasicDoubleLinkedList<T> addToEnd(T data){
		
		// If the Linked list is empty add the first node
		if(head == null && tail == null) {
			Node lastNode = new Node(data);
			lastNode.next = null;
			lastNode.prev = null;
			head = tail = lastNode;
			size++;
		}
		
		// Add the node to the end of the Linked List
		else {
			Node lastNode = new Node(data);
			tail.next = lastNode;
			lastNode.next = null;
			lastNode.prev = tail;
			tail = lastNode;
			size++;
		}
		return this;
	}
	
	/**
	 * Removes the node in the list with the key being the data in the node
	 * @param targetData - the key
	 * @param comparator - the comparator implementation
	 * @return this
	 */
	public BasicDoubleLinkedList<T> remove(T targetData, Comparator<T> comparator){
		
		// if the linked list is empty
		if(this.head == null && this.tail == null)
			return null;
		
		else if(this.head == this.tail && comparator.compare(this.tail.data, targetData) == 0 && comparator.compare(this.head.data, targetData) == 0) {
			this.head = null;
			this.tail = null;
			size--;
		}
			
		else {
			
			Node temp = null;
			Node current = this.head;
			
			// Search the entire linked list once to find the node
			while(current != null) {
				if(comparator.compare(current.data, targetData) == 0) {
					temp = current;
				}
				current = current.next;
			}
			
			// If node has not been found
			if(temp == null) {
				System.out.println("Item is not found");
			}
			
			// If the node is the last node
			else if(temp == this.tail) {
				this.tail = this.tail.prev;
				this.tail.next = null;
				size--;
			}
			
			// if the node is the first node
			else if(temp == this.head) {
				this.head = this.head.next;
				this.head.prev = null;
				size--;
			}
			
			// if the node is between the first and last node
			else {
				temp.prev.next = temp.next;
				temp.next.prev = temp.prev;
				size--;
			}	
		}
		return this;
	}
	
	/**
	 * delete the first node in the list
	 * @return the element of the removed node
	 */
	public T retrieveFirstElement() {
		T element;
		
		// If the linked list is empty do nothing
		if(head == null && tail == null)
			return null;
		
		else if(head == tail) {
			element = head.data;
			head = null;
			tail = null;
			size--;
		}
		// collect the element of the first node and "delete" it
		else {
			element = head.data;
			head = head.next;
			head.prev = null;
			size--;
		}
		return element;
	}
	
	/**
	 * delete the last node in the list
	 * @return the element of the removed node
	 */
	public T retrieveLastElement() {
		T element;
		
		// If the linked list is empty do nothing
		if(head == null && tail == null)
			return null;
		
		else if(head == tail) {
			element = tail.data;
			head = null;
			tail = null;
			size--;
		}
		
		// collect the element of the last node and "delete" it
		else {
			element = tail.data;
			tail = tail.prev;
			tail.next = null;
			size--;
		}
		return element;
	}
	
	/**
	 * takes all the data from the Linked list and puts them into an ArrayList
	 * @return The arrayList filled with all the data
	 */
	public ArrayList<T> toArrayList(){
		
		// If the linked list is empty do nothing
		if(head == null && tail == null) {
			return new ArrayList<T>();
		}
		
		// Make a new arrayList and go through the linked list and add all the data in the 
		// nodes to the arrayList.
		else {
			ArrayList<T> linkedArray = new ArrayList<T>();
			Node current = head;
			
			while(current != null) {
				linkedArray.add(current.data);
				current = current.next;
			}
			
			return linkedArray;
		}
	}
}
