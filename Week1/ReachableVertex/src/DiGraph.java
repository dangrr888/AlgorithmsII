import java.util.Scanner;

public class DiGraph {

	private Bag<Integer>[] adjLists;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public DiGraph(int V_) {
		this.V = V_;
		if (this.V < 1) {
			throw new IllegalArgumentException();
		}
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		this.E = 0;
		for (int v = 0; v < this.V; ++v) {
			adjLists[v] = new Bag<Integer>();
		}
	}
	
	public void checkIndex(int v) {
		if (v < 0 || v >= this.V) {
			throw new IllegalArgumentException();
		}
	}
	
	public void addEdge(int v, int w) {
		checkIndex(v);
		checkIndex(w);
		
		for (int x : adjLists[v]) {
			if (x == w) {
				return;
			}
		}
		
		adjLists[v].add(w);
		++this.E;
	}
	
	public Iterable<Integer> adj(int v) {
		checkIndex(v);
		return this.adjLists[v];
	}
	
	@SuppressWarnings("unchecked")
	public DiGraph(Scanner sc) {
		this.V = sc.nextInt();
		if (this.V < 1) {
			throw new IllegalArgumentException();
		}
		final int numEdges = sc.nextInt();
		if (numEdges < 0) {
			throw new IllegalArgumentException();
		}
		
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			adjLists[v] = new Bag<Integer>();
		}
		
		for (int e = 0; e < numEdges; ++e) {
			final int v = sc.nextInt();
			final int w = sc.nextInt();
			
			this.addEdge(v, w);
		}
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public DiGraph reverse() {
		final DiGraph rev = new DiGraph(this.V);
		for (int v = 0; v < this.V; ++v) {
			for (int w : this.adjLists[v]) {
				rev.addEdge(w, v);
			}
		}
		return rev;
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int v = 0; v < this.V; ++v) {
			for (int w : this.adjLists[v]) {
				sb.append(v + "->" + w);
			}
		}

		return sb.toString();
	}
}
