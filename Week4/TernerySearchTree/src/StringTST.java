import java.util.LinkedList;
import java.util.Stack;

public class StringTST<T> {

	private Node root = null;
	
	private static class Node {
		Object val = null;
		char key; 
		Node[] next = new Node[3];
	}
	
	private Node put(Node x, String key, T val, int d) {

		// Retrieve dth character of key.
		final char c = key.charAt(d);

		if (x == null) {
			// Null Node, assign Node key to c.
			x = new Node();
			x.key = c;
		}

		// Compare Node key and c and recurse.
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
	
	// Wrapper
	public void put(String key, T val) {
		this.root = this.put(this.root, key, val, 0);
	}

	private Node get(Node x, String key, int d) {
		
		if (x == null) {
			// TST doesn't contain key.
			return null;
		}
		
		// Retrieve dth character of key
		final char c = key.charAt(d);
		
		// Return the terminating Node of the associated key.
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
	
	// Wrapper
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
			// TST doesn't contain key.
			return null;
		}
		
		// Retrieve dth character of key.
		final char c = key.charAt(d);
		
		// Compare and recurse.
		if (c < x.key) {
			x.next[0] = this.delete(x.next[0], key, d);
		} else if (c > x.key) {
			x.next[2] = this.delete(x.next[2], key, d);
		} else if (d == key.length() - 1) {
			// Found key matching key.
			x.val = null; // delete value.
			if (!hasChildren(x)) {
				// Node terminating key has no
				// children hence can delete
				// without effecting other keys.
				x = null;
			}
		} else {
			x.next[1] = this.delete(x.next[1], key, d+1);
		}
		
		return x;
	}
	
	// Wrapper
	public void delete(String key) {
		this.root = this.delete(this.root, key, 0);	
	}
	
	private String print(LinkedList<Character> ll) {
		final StringBuilder sb = new StringBuilder();
		for (char c : ll) {
			sb.append(c);
		}
		return sb.toString();
	}
	
	private boolean isMatching(String s1, String s2) {
		if (s1 == null || s2 == null) {
			return false;
		}
		
		final int l = s1.length();
		if (l != s2.length()) {
			return false;
		}
		
		for (int i = 0; i < l; ++i) {
			final char ch1 = s1.charAt(i);
			final char ch2 = s2.charAt(i);
			
			if (ch1 == '.' || ch2 == '.') {
				continue;
			} else if (ch1 != ch2) {
				return false;
			}
		}
		
		return true;
	}
	
	private void prefixProcess(String key, String prefix, Stack<String> st) {
		if (key.startsWith(prefix)) {
			st.push(key);
		}
	}

	private void longestPrefixProcess(String key, String longestPrefix, Stack<String> st) {
		if (longestPrefix.startsWith(key)) {
			if (st.isEmpty()) {
				st.push(key);
			} else if (key.length() > st.peek().length()) {
				st.pop();
				st.push(key);
			}
		}
	}

	private void wildCardProcess(String key, String wildCard, Stack<String> st) {
		if (this.isMatching(key, wildCard)) {
			st.push(key);
		}
	}

	private void allKeysProcess(String key, Stack<String> st) {
		st.push(key);
	}

	private void inOrderTraversalPrefix( Node x
			                           , LinkedList<Character> ll
			                           , Stack<String> st
			                           , boolean usePrefix
			                           , String prefix
			                           , boolean useWildCard
			                           , String wildCard
			                           , boolean useLongestPrefix
			                           , String longestPrefix
			                           )
	{
		
		if (x == null) {
			return;
		}
		
		// 1. Add key of current Node
		ll.add(x.key);

		if (x.val != null) {	
			// 2. Found key. Determine if key should be added to stack.			
			final String key = this.print(ll);
			if (usePrefix) {
				this.prefixProcess(key, prefix, st);
			} else if (useLongestPrefix) {
				this.longestPrefixProcess(key, longestPrefix, st);
			} else if (useWildCard) {
				this.wildCardProcess(key, wildCard, st);
			} else {
				this.allKeysProcess(key, st);
			}
		}
		
		// 3. Traverse left.
		ll.removeLast();
		this.inOrderTraversalPrefix(x.next[0], ll, st, usePrefix, prefix, useWildCard, wildCard, useLongestPrefix, longestPrefix);
		
		// 4. Traverse middle.
		ll.add(x.key);
		this.inOrderTraversalPrefix(x.next[1],ll, st, usePrefix, prefix, useWildCard, wildCard, useLongestPrefix, longestPrefix);
		ll.removeLast();
		
		// 5. Traverse right.
		this.inOrderTraversalPrefix(x.next[2], ll, st, usePrefix, prefix, useWildCard, wildCard, useLongestPrefix, longestPrefix);
	}
	
	public Iterable<String> keys() {
		if (this.root == null) {
			return null;
		}
		
		final LinkedList<Character> ll = new LinkedList<Character>();
		final Stack<String> st = new Stack<String>();
		this.inOrderTraversalPrefix(this.root, ll, st, false, null, false, null, false, null);
		return st;
	}
	
	/*
	 * Return the keys that possesses a prefix equal to s.
	 */
	Iterable<String> keysWithPrefix(String prefix) {
		if (this.root == null) {
			return null;
		}
		
		final LinkedList<Character> ll = new LinkedList<Character>();
		final Stack<String> st = new Stack<String>();
		this.inOrderTraversalPrefix(this.root, ll, st, true, prefix, false, null, false, null);
		return st;
	}
	
	/*
	 * Keys that match the search string s (where '.' is a wildCard)
	 */
	Iterable<String> keysThatMatch(String wildCard) {
		if (this.root == null) {
			return null;
		}
		
		final LinkedList<Character> ll = new LinkedList<Character>();
		final Stack<String> st = new Stack<String>();
		this.inOrderTraversalPrefix(this.root, ll, st, false, null, true, wildCard, false, null);
		return st;
	}

	/*
	 * @brief Return the longest key that is a prefix of longestPrefix.
	 */
	String longestPrefixOf(String longestPrefix) {
		if (this.root == null) {
			return null;
		}
		
		final LinkedList<Character> ll = new LinkedList<Character>();
		final Stack<String> st = new Stack<String>();
		this.inOrderTraversalPrefix(this.root, ll, st, false, null, false, null, true, longestPrefix);
		return st.isEmpty() ? null : st.pop();
	}
}
