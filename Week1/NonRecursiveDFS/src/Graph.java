import java.util.Scanner;

public class Graph {
	
	private Bag<Integer>[] adjLists;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public Graph(int V_) {
		this.V = V_;
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int i = 0; i < this.V; ++i) {
			this.adjLists[i] = new Bag<Integer>();
		}
	}
	
	private void checkIndex(int v) {
		if (v < 0 || v >= this.V) {
			throw new IllegalArgumentException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph(Scanner sc) {
	
		// read the number of vertices.
		this.V = sc.nextInt();
		if (this.V <= 0) {
			throw new IllegalArgumentException();
		}
		adjLists = (Bag<Integer>[]) new Bag[this.V];
		
		// read the number of edges.
		this.E = sc.nextInt();
		if (this.E < 0) {
			throw new IllegalArgumentException();
		}
		
		// read the pairwise connections defining each edge.
		for (int i = 0; i < this.E; ++i) {
			
			// First Vertex.
			final int v = sc.nextInt();
			this.checkIndex(v);

			// Second Vertex.
			final int w = sc.nextInt();
			this.checkIndex(w);

			this.addEdge(v, w);
		}	
	}
	
	public void addEdge(int v, int w) {

		this.checkIndex(v);
		this.checkIndex(w);
		
		if (this.E() > 0) {
			// Check if edge already exists.
			for (Integer vadj : this.adj(v)) {
				if (vadj == w) {
					return;
				}
			}
		}
		
		this.adjLists[v].add(w);
		
		if (v != w) {
			// add reverse connection, if unique.
			this.adjLists[w].add(v);
		}
		
		++this.E;
	}
	
	public Iterable<Integer> adj(int v) {
		this.checkIndex(v);
		
		return this.adjLists[v];
	}
	
	public int V() {
		return this.V;
	}

	public int E() {
		return this.E;
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
