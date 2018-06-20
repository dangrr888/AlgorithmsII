
public class StringTST<T> {

	private Node root = null;
	
	private static class Node {
		public Object val = null;
		public char key; 
		public Node[] next = new Node[3];
	}
	
	public StringTST() {
	}
	
	private Node put(Node x, String key, T val, int d) {

		final char c = key.charAt(d);

		if (x == null) {
			x = new Node();
			x.key = c;
		}

		if (c < x.key) {
			x.next[0] = this.put(x.next[0], key, val, d);
		} else if (c > x.key) {
			x.next[2] = this.put(x.next[2], key, val, d);
		} else if (d == key.length() - 1) {
			x.val = val;
		} else {
			x.next[1] = this.put(x.next[1], key, val, d+1);
		}

		return x;
	}
	
	public void put(String key, T val) {
		this.root = this.put(this.root, key, val, 0);
	}

	private Node get(Node x, String key, int d) {
		if (x == null) {
			return null;
		}
		
		final char c = key.charAt(d);
		Node ret = null;
		if (c < x.key) {
			ret = this.get(x.next[0], key, d);
		} else if (c > x.key) {
			ret = this.get(x.next[2], key, d);
		} else if (d == key.length() - 1) {
			ret = x;
		} else {
			ret = this.get(x.next[1], key, d+1);
		}
		
		return ret;
	}
	
	public T get(String key) {
		if (key == null || key.length() == 0) {
			return null;
		}
		
		final Node x = this.get(this.root, key, 0);
		if (x == null) {
			return null;
		}
		return (T)x.val;
	}
		
	public boolean contains(String key) {
		return this.get(key) != null;
	}
	
	private boolean hasChildren(Node x) {
		for (int i = 0; i < x.next.length; ++i) {
			if (x.next[i] != null) {
				return true;
			}
		}
		return false;
	}
	
	private Node delete(Node x, String key, int d) {
		if (x == null) {
			return null;
		}
		
		final char c = key.charAt(d);
		if (c < x.key) {
			x.next[0] = this.delete(x.next[0], key, d);
		} else if (c > x.key) {
			x.next[2] = this.delete(x.next[2], key, d);
		} else if (d == key.length() - 1) {
			x.val = null;
			if (!hasChildren(x)) {
				x = null;
			}
		} else {
			x.next[1] = this.delete(x.next[1], key, d+1);
		}
		
		return x;
	}
	
	public void delete(String key) {
		this.root = this.delete(this.root, key, 0);	
	}
}
