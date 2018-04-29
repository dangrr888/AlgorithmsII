package shortestpathwithoneskippableedge;

import java.util.Scanner;
import java.util.Stack;

public class EdgeWeightedDigraph {

	private Bag<DirectedEdge>[] adj;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int V) {
		this.V = V;
		this.E = 0;
		this.adj = (Bag<DirectedEdge>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adj[v] = new Bag<DirectedEdge>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(Scanner sc) {
		this.V = sc.nextInt();
		this.E = 0;
		this.adj = (Bag<DirectedEdge>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adj[v] = new Bag<DirectedEdge>();
		}
		final int numEdges = sc.nextInt();
		for (int e = 0; e < numEdges; ++e) {
			final int v = sc.nextInt();
			final int w = sc.nextInt();
			final double weight = sc.nextDouble();
			this.add(new DirectedEdge(v, w, weight));
		}
	}
	
	public void add(DirectedEdge e) {
		this.adj[e.from()].add(e);
		++this.E;
	}
	
	public Iterable<DirectedEdge> adj(int v) {
		return this.adj[v];
	}
	
	public Iterable<DirectedEdge> edges() {
		final Stack<DirectedEdge> st = new Stack<DirectedEdge>();
		for (int v = 0; v < this.V; ++v) {
			for (DirectedEdge e : this.adj(v)) {
				st.push(e);
			}
		}
		return st;
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public EdgeWeightedDigraph reverse() {
		final EdgeWeightedDigraph G = new EdgeWeightedDigraph(this.V());
		for (DirectedEdge e : this.edges()) {
			G.add(e.reverse());
		}
		return G;
	}
}
