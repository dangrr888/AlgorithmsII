import java.util.Scanner;

public class Graph {

	private int V;
	private int E;
	private Bag<Integer>[] adjLists;
	
	@SuppressWarnings("unchecked")
	public Graph(int V_) {
		this.V = V_;
		if (this.V <= 0) {
			throw new IllegalArgumentException();
		}
		
		adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			adjLists[v] = new Bag<Integer>();
		}		
	}
	
	public Graph(Scanner sc) {
		
		// Read the number of vertices.
		this.V = sc.nextInt();
		if (this.V <= 0) {
			throw new IllegalArgumentException();
		}
		
		// Read the number of edges.
		this.E = sc.nextInt();
		if (E < 0) {
			throw new IllegalArgumentException();
		}
		
		// Sequentially read the E pairwise connections.
		for (int i = 0; i < this.E; ++i) {
			// Read vertex, v
			final int v = sc.nextInt();
			this.checkIndex(v);
			
			// Read vertex, w
			final int w = sc.nextInt();
			this.checkIndex(w);
			
			// Add the edge.
			this.addEdge(v, w);			
		}
	}
	
	private void checkIndex(int v) {
		if (v < 0 || v >= this.V) {
			throw new IllegalArgumentException();
		}
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public void addEdge(int v, int w) {
		
		checkIndex(v);
		checkIndex(w);
		
		if (this.E > 0) {
			for (int x : this.adjLists[v]) {
				if (x == w) {
					return; // edge already exists.
				}
			}
		}
		
		this.adjLists[v].add(w);
		
		if (v != w) {
			this.adjLists[w].add(v); // add reverse edge
		}
		
		++this.E;
	}
	
	public Iterable<Integer> adj(int v) {
		this.checkIndex(v);
		return this.adjLists[v];
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int v = 0; v < this.V; ++v) {
			for (int w : this.adj(v)) {
				sb.append(v + "-" + w + "\n");
			}
		}
		return sb.toString();
	}
}
