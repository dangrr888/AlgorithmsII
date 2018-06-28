package PrefixFreeCodes;

class BinaryTrie {

	private static final int R = 2;
	private Node root;
	private class Node {
		public Node[] next = new Node[R];
		public Boolean val = null;
	}
	
	public BinaryTrie() {
		this.root = new Node();
	}
	
	private Node put(Node x, String key, boolean val, int d) {
		if (x == null) {
			x = new Node();
		}
		
		if (d == key.length()) {
			x.val = val;
			return x;
		}
		
		final int c = key.charAt(d)-'0';
		x.next[c] = this.put(x.next[c], key, val, d+1);
		return x;
	}
	
	public void put(String key, boolean val) {
		this.root = this.put(this.root, key, val, 0);
	}
	
	private Node get(Node x, String key, int d) {
		if (x == null) {
			return null;
		}
		
		if (d == key.length()) {
			return x;
		}
		
		final int c = key.charAt(d)-'0';
		return this.get(x.next[c], key, d+1);
	}
	
	public Boolean get(String key) {
		final Node x = this.get(this.root, key, 0);
		if (x != null) {
			return x.val;
		}
		return null;
	}
	
	public boolean contains(String key) {
		return this.get(key) != null;
	}
	
	private Node delete(Node x, String key, int d) {
		if (x == null) {
			return x;
		}
		
		if (d == key.length()) {
			x.val = null;
			
			if (x.next[0] == null && x.next[1] == null) {
				return null;
			}
		}
		
		final int c = key.charAt(d)-'0';
		x.next[c] = this.delete(x.next[c], key, d+1);
		return x;		
	}
	
	public void delete(String key) {
		this.root = this.delete(this.root, key, 0);
	}
	
	private boolean prefixFree(Node x, Boolean pf) {
		if (!pf) {
			return false;
		}
		
		if (x == null) {
			return pf;
		}
		
		if (x.val != null) {
			if (x.next[0] != null || x.next[1] != null) {
				return false;
			}
		}
		
		if (!this.prefixFree(x.next[0], pf) || !this.prefixFree(x.next[1], pf)) {
			return false;
		}
		
		return true;
	}
	
	public boolean prefixFree() {
		return this.prefixFree(this.root, true);
	}
}

public class PrefixFreeCodes {
	
	private BinaryTrie t = new BinaryTrie();
	private boolean prefixFree = true;
	
	public PrefixFreeCodes(String[] binaryCodes) {
		
		// Validate arguments.
		
		if (binaryCodes == null) {
			throw new IllegalArgumentException("null argument.");
		}
		
		for (String code : binaryCodes) {
			for (int i = 0; i < code.length(); ++i) {
				final char c = code.charAt(i);
				if (c != '0' && c != '1') {
					throw new IllegalArgumentException("Argument " + code + " is not a binary string.");
				}
			}
		}
		
		// Populate trie with bianry codes.
		for (String code : binaryCodes) {
			this.t.put(code, true);
		}
		
		// Conduct in-order traversal of trie to check if any key has children.
		this.prefixFree = this.t.prefixFree();
	}
	
	public boolean havePrefixFreeCodes() {
		return this.prefixFree;
	}
}
