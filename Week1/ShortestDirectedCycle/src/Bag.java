import java.util.Iterator;

public class Bag<Item> implements Iterable<Item> {

	private class Node {
		
		public Item val;
		Node next;
		
		public Node(Item val_, Node next_) {
			this.val = val_;
			this.next = next_;
		}
	}

	private Node head;
	private int sz;
	
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

	private class ForwardItemIterator implements Iterator<Item> {
		
		private Node curr;
		
		public ForwardItemIterator() {
			this.curr = head;
		}

		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
			final Item ret = curr.val;
			curr = curr.next;
			return ret;
		}
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ForwardItemIterator();
	}
}
