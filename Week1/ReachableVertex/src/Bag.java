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

	private class ItemForwardIterator implements Iterator<Item> {

		private Node curr;
		
		public ItemForwardIterator() {
			this.curr = head;
		}
		
		@Override
		public boolean hasNext() {
			return curr != null;
		}

		@Override
		public Item next() {
			if (!hasNext()) {
				throw new IllegalStateException();
			}
			
			final Item retval = this.curr.val;
			this.curr = this.curr.next;
			
			return retval;
		}
	}
	
	@Override
	public Iterator<Item> iterator() {
		return new ItemForwardIterator();
	}
	
	public static void main(String[] args) {
		Bag<Integer> b = new Bag<Integer>();
		
		try {
			for (int i = 0; i < 10; ++i)
			{
				if(b.size() != i) {
					throw new IllegalStateException();
				}
				b.add(i);
			}
		} catch (IllegalStateException exc) {
			System.out.println("Incorrect size!");
			return;
		}
		
		final String expected = "9876543210";
		final StringBuilder sb = new StringBuilder();
		for (Integer i : b) {
			sb.append(i);
		}
		final String actual = sb.toString();
		
		System.out.println("expected: " + expected);		
		System.out.println("actual: " + actual);
		
		if (actual.compareTo(expected) != 0)
		{
			System.out.println("Incorrect result!");
		}	
		else
		{
			System.out.println("Tests passed!!!");
		}
	}
}
