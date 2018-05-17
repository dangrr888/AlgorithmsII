import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

	private Node head;
	private int sz;
	
	public Bag() {
		this.head = null;
		this.sz = 0;
	}
	
	private class Node {
		public Node next;
		public T val;
		
		public Node(T val, Node next) {
			this.val = val;
			this.next = next;
		}
	}
	
	private class NodeIterator implements Iterator<T> {

		private Node cur;

		public NodeIterator() {
			this.cur = head;
		}
		
		@Override
		public boolean hasNext() {
			return this.cur != null;
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new IllegalStateException("No more entries in Bag.");
			}
			final T val = this.cur.val;
			this.cur = this.cur.next;
			return val;
		}
	}
	
	public void add(T val) {
		this.head = new Node(val, this.head);
	}
	
	public int size() {
		return this.sz;
	}

	@Override
	public Iterator<T> iterator() {
		return new NodeIterator();
	}
}
