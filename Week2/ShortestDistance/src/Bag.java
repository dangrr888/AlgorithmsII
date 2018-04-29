import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

	private int sz;
	private Node head;
	
	private class Node {
		public Node next;
		public T val;
		
		public Node(T val, Node next) {
			this.val = val;
			this.next = next;
		}
	}
	
	private class ForwardIterator implements Iterator<T> {
		private Node n;
		
		public ForwardIterator() {
			this.n = head; 
		}

		@Override
		public boolean hasNext() {
			return this.n != null;
		}

		@Override
		public T next() {
			final T val = this.n.val;
			this.n = this.n.next; 
			return val;
		}
	}
	
	public Bag() {
		this.head = null;
		this.sz = 0;
	}
	
	public void add(T val) {
		this.head = new Node(val, this.head);
		++this.sz;
	}

	public int size() {
		return this.sz;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ForwardIterator();
	}
}
