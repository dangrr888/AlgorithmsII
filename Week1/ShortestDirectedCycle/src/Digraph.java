import java.util.Scanner;

public class Digraph {

	private Bag<Integer>[] adjLists;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public Digraph(int V_) {
		if (V_ <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.V = V_;
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];
		this.E = 0;
	}
	
	public void checkIndex(int v) {
		if (v < 0 || v >= this.V) {
			throw new IllegalArgumentException();
		}
	}
	@SuppressWarnings("unchecked")
	public Digraph(Scanner sc) {
		this.V = sc.nextInt();
		if (this.V <= 0) {
			throw new IllegalArgumentException();
		}
		
		this.E = sc.nextInt();
		if (this.E < 0) {
			throw new IllegalArgumentException();
		}
		this.adjLists = (Bag<Integer>[]) new Bag[this.V];

		for (int i = 0; i < this.E; ++i) {
			final int v = sc.nextInt();
			this.checkIndex(v);
			final int w = sc.nextInt();
			this.checkIndex(w);
			
			this.addEdge(v, w);
		}
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public void addEdge(int v, int w) {
		for (int x : this.adj(v)) {
			if (x == w) {
				return;
			}
		}
		
		adjLists[v].add(w);
		++this.E;
	}
	
	public Iterable<Integer> adj(int v) {
		this.checkIndex(v);
		return this.adjLists[v];
	}
}
