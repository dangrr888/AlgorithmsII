import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

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
			return this.n != null;
		}

		@Override
		public Item next() {
			Item ret = this.n.val;
			this.n = this.n.next;
			return ret;
		}
	}
	
	private Node head;
	private int sz;
	
	public Bag() {
		this.head = null;
		this.sz = 0;
	}
	
	public void add(Item val) {
		this.head = new Node(val, this.head);
		++this.sz;
	}
	
	public int size() {
		return this.sz;
	}

	@Override
	public Iterator<Item> iterator() {
		return new ForwardItemIterator();
	}	
}
