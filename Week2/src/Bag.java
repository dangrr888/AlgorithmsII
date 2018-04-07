import java.util.Iterator;

public class Bag<T> implements Iterable<T> {

	private class Node {
		
		public T val;
		public Node next;
		
		public Node(T val, Node next) {
			this.val = val;
			this.next = next;
		}
	}

	private class ForwardNodeIterator implements Iterator<T> {

		private Node n;

		public ForwardNodeIterator() {
			this.n = root;
		}
		
		@Override
		public boolean hasNext() {
			return this.n != null;
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new IllegalStateException();
			}
			
			final T retVal = this.n.val;
			this.n = this.n.next;
			
			return retVal;
		}		
	}
	
	private Node root;
	private int sz;
	
	public Bag() {
		this.root = null;
		this.sz = 0;
	}
	
	public void add(T val) {
		this.root = new Node(val, this.root);
		++this.sz;
	}

	public int sz() {
		return this.sz;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ForwardNodeIterator();
	}
}
