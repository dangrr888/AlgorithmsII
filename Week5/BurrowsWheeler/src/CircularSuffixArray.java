public class CircularSuffixArray {
	
	private final static int R = 256; // radix

	private String s; // original String
	
	private final int len; // string length

	private final Node[] suffixes; // heads of the permuted strings
	
	private class Node {
		Node(int offset, Node next) {
			this.offset = offset;
			this.next = next;
		}
		
		int offset;
		Node next;
	}
	
	// circular suffix array of s
	public CircularSuffixArray(String s) {

		if (s == null) {
			throw new IllegalArgumentException("null string provided.");
		}
		
		this.s = s;
		
		// Cache length.
		this.len = s.length();
				
		// Construct suffix array.
		this.suffixes = new Node[this.len];
		
		if (!s.isEmpty()) {
			
			// Construct original string.
			Node head = null;
			Node tail = null;
			for (int i = this.len-1; i >= 0; --i) {
				head = new Node(i, head);
				if (i == this.len-1) {
					tail = head;
				}
	
				this.suffixes[i] = head;
			}
			
			tail.next = head;
			
			// Conduct LSD sort of suffixes.
			Node[] aux = new Node[this.len];
			
			for (int d = this.len-1; d >= 0; --d) {
				int[] count = new int[CircularSuffixArray.R+1];
				
				for (int i = 0; i < this.len; ++i) {
					final char c = this.s.charAt((d + this.suffixes[i].offset) % this.len);
					++count[c+1];
				}
				
				for (int i = 0; i < CircularSuffixArray.R; ++i) {
					count[i+1] += count[i];
				}
				
				for (int i = 0; i < this.len; ++i) {
					aux[count[this.s.charAt((d + this.suffixes[i].offset) % this.len)]++] = this.suffixes[i];
				}
				
				for (int i = 0; i < this.len; ++i) {
					this.suffixes[i] = aux[i];
				}
			}
		}
	}

	// length of s
	public int length() {
		return this.len;
	}

	// returns index of ith sorted suffix
	public int index(int i) {
		if (i < 0 || i > this.len - 1) {
			throw new IllegalArgumentException("Index is out of range.");
		}
		return this.suffixes[i].offset;
	}

	// unit testing (required)
	public static void main(String[] args) {
		final String s = "ABRACADABRA!";
		final CircularSuffixArray csa = new CircularSuffixArray(s);
		if (csa.length() != s.length()) {
			throw new RuntimeException("lengths unequal!");
		}
		final int[] expectedIndices = {11, 10, 7, 0, 3, 5, 8, 1, 4, 6, 9, 2};
		for (int i = 0; i < s.length(); ++i) {
			if ((csa.index(i) != expectedIndices[i])) {
				throw new RuntimeException("indices unequal!");
			}
		}
	}
}