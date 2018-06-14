public class StringST<Value> {
	
	private static int R = 256;
	private Node root = new Node();
		
	private static class Node {
		Object val = null;
		Node[] next = new Node[StringST.R]; 
	}
	
	private Node get(Node n, String key, int d) {
		if (n == null) {
			return null;
		}
		
		if (key.length() == d) {
			return n;
		}
		
		final char c = key.charAt(d);
		return this.get(n.next[c], key, d+1);
	}
	
	@SuppressWarnings("unchecked")
	public Value get(String key) {
		final Node tmp = this.get(this.root, key, 0);
		if (tmp == null) {
			return null;
		}
		return (Value)tmp.val;
	}
	
	private Node put(Node n, String key, Value val, int d) {
		if (n == null) {
			n = new Node();
		}
		
		if (key.length() == d) {
			n.val = val;
		} else {
			final char c = key.charAt(d);
			n.next[c] = this.put(n.next[c], key, val, d+1);
		}
		
		return n;
	}
	
 	public void put(String key, Value val) {
		this.root = this.put(this.root, key, val, 0);
	}
	
 	public boolean contains(String key) {
 		return this.get(key) != null;
 	}
 	
 	private boolean allNullLinks(Node n) {
 		for (int i = 0; i < StringST.R; ++i) {
 			if (n.next[i] != null) {
 				return false;
 			}
 		}
 		return true;
 	}
	private Node delete(Node n, String key, int d) {
		if (n == null) {
			return null;
		}
		
		if (key.length() == d) {
			n.val = null;			
		}

		if (this.allNullLinks(n)) {
			return null;
		}
		
		final char c = key.charAt(d);
		n.next[c] = this.delete(n.next[c], key, d+1);
		
		return n;
	}

	public void delete(String key) {
		this.root = this.delete(this.root, key, 0);
	}
}