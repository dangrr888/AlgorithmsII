import java.util.Iterator;
import java.util.NoSuchElementException;

public class Bag<Item> implements Iterable<Item>
{
	private class Node {
		
		private Item val;
		private Node next;

		public Node(Item val_, Node next_) {
			this.val = val_;
			this.next = next_;
		}
	}
	
	private class ForwardItemIterator implements Iterator<Item> {

		private Node n;
		
		public ForwardItemIterator() {
			n = root;
		}
		
		@Override
		public boolean hasNext() {
			return n != null;
		}

		@Override
		public Item next() {
			if (!this.hasNext()) {
				throw new NoSuchElementException();
			}
			final Item val = n.val;
			n = n.next;
			return val;
		}
	}
	
	private Node root;
	private int sz;
	
	public Bag() {
		root = null;
		sz = 0;
	}
	
	public void add(Item x) {
		this.root = new Node(x, this.root);
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