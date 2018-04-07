import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

	private Node head;
	private int sz;
	
	private class Node {
		public Item val;
		public Node next;
		
		public Node(Item val_, Node next_) {
			this.val = val_;
			this.next = next_;
		}
	}

	private class ForwardItemIterator implements Iterator<Item> {

		private Node n;
		
		public ForwardItemIterator() {
			this.n = head; 
		}
		
		@Override
		public boolean hasNext() {
			return n != null;
		}

		@Override
		public Item next() {
			if (!this.hasNext()) {
				throw new IllegalStateException();
			}
			
			final Item ret = this.n.val;
			this.n = this.n.next;
			return ret;
		}
	}
	
	public Bag() {
		this.head = null;
		this.sz = 0;
	}
	
	public int size() {
		return this.sz;
	}
	
	public void add(Item val_) {
		this.head = new Node(val_, this.head);
		++this.sz;
	}
	
	public void remove(Item val_) {
		if (this.head == null) {
			throw new IllegalStateException();
		}
		
		Node n = this.head;
		if (n.val == val_) {
			this.head = this.head.next;
		} else {
			while(n.next != null && n.next.val != val_) {
				n = n.next;
			}
			if (n.next != null) {
				n.next = n.next.next;
			}
		}
		
		--this.sz;
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ForwardItemIterator();
	}
}
