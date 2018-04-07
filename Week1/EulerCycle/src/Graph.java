import java.util.Scanner;

public class Graph {

	private int V;
	private int E;
	private Bag<Integer>[] adjLists;
	
	private void checkIndex(int v) {
		if (v < 0 || v >= this.V) {
			throw new IllegalArgumentException();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph(int V_) {
		if (V_ <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.V = V_;
		this.E = 0;
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adjLists[v] = new Bag<Integer>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph(Graph G_) {
		this.V = G_.V;
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adjLists[v] = new Bag<Integer>();
		}
		
		// Add the edges.
		this.E = 0;
		for (int v = 0; v < this.V; ++v) {
			for (int w : G_.adj(v)) {
				this.addEdge(v, w);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public Graph(Scanner sc) {
		this.V = sc.nextInt();
		if (this.V <= 0) {
			throw new IllegalArgumentException();
		}

		this.E = sc.nextInt();
		if (this.E <= 0) {
			throw new IllegalArgumentException();
		}

		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adjLists[v] = new Bag<Integer>();
		}

		for (int e = 0; e < this.E; ++e) {
			final int v = sc.nextInt();
			this.checkIndex(v);
			
			final int w = sc.nextInt();
			this.checkIndex(w);
			
			this.addEdge(v, w);
		}
	}
	
	public int degree(int v) {
		this.checkIndex(v);
		return this.adjLists[v].size();
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public Iterable<Integer> adj(int v) {
		this.checkIndex(v);
		return this.adjLists[v];
	}
	
	public void addEdge(int v, int w) {
		this.checkIndex(v);
		this.checkIndex(w);
		
		if (this.E > 0) {
			for (int x : this.adj(v)) {
				if (x == w) {
					return;
				}
			}
		}
		
		this.adjLists[v].add(w);
		
		if (v != w) {
			this.adjLists[w].add(v);
		}
		
		++this.E;
	}
	
	public void removeEdge(int v, int w) {
		this.checkIndex(v);
		this.checkIndex(w);
		
		for (int x : this.adjLists[v]) {
			if (x == w) {
				this.adjLists[v].remove(w);
			}
		}
		
		for (int x : this.adjLists[w]) {
			if (x == v) {
				this.adjLists[w].remove(v);
			}
		}
		
		--this.E;
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
