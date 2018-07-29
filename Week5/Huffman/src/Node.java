import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.MinPQ;

public class Node implements Comparable<Node> {

	private int freq;
	private final Node left;
	private final Node right;
	private char ch = '\0';
	private static int R = 256;
	
	public Node(char ch, int freq, Node left, Node right) {
		this.ch = ch;
		this.freq = freq;
		this.left = left;
		this.right = right;
	}
	
	@Override
	public int compareTo(Node that) {
		return this.freq - that.freq;
	}

	public boolean isLeaf() {
		return left == null && right == null;
	}
	
	private static Node readTrie() {
		
		if (BinaryStdIn.readBoolean()) {
			return new Node(BinaryStdIn.readChar(), 0, null, null);
		}
		
		final Node left = Node.readTrie();
		final Node right = Node.readTrie();
		
		return new Node('\0', 0, left, right);
	}
	
	public static void expand() {
		// Read encoding Trie.
		final Node root = Node.readTrie();
		
		// Read the number f characters in the input.
		final int N = BinaryStdIn.readInt();
		
		for (int i = 0; i < N; ++i) {
			 Node x = root;
			 while (!x.isLeaf()) {
				 x = BinaryStdIn.readBoolean() ? x.right : x.left;
			 }
			 BinaryStdOut.write(x.ch);
		}
		
		BinaryStdOut.close();
	}
	
	public static void writeTrie(Node x) {
		
		if (x.isLeaf()) {
			BinaryStdOut.write(true);
			BinaryStdOut.write(x.ch);
			return;
		}
		
		BinaryStdOut.write(false);
		Node.writeTrie(x.left);
		Node.writeTrie(x.right);
	}
	
	public static Node buildTrie(int[] freq) {
		
		Node root = null;
		
		MinPQ<Node> minPQ = new MinPQ<Node>(Node.R);
		
		for (char i = 0; i < Node.R; ++i) {
			if (freq[i] > 0) {
				minPQ.insert(new Node(i, freq[i], null, null));
			}
		}
		
		while(minPQ.size() > 1) {
			final Node left = minPQ.delMin();
			final Node right = minPQ.delMin();
			root = new Node('\0', left.freq + right.freq, left, right);
			minPQ.insert(root);
		}
		
		if (!minPQ.isEmpty()) {
			return minPQ.delMin();
		}
		
		return null;
	}
}
