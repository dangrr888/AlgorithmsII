import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class EdgeWeightedDigraph {
	private Bag<DirectedEdge>[] adj;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(int V) {
		this.V = V;
		this.E = 0;
		this.adj = (Bag<DirectedEdge>[]) new Bag[this.V()];
		for (int v = 0; v < this.V(); ++v) {
			this.adj[v] = new Bag<DirectedEdge>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedDigraph(In in) {
		this.V = Integer.valueOf(in.readLine());
		this.E = 0;
		this.adj = (Bag<DirectedEdge>[]) new Bag[this.V()];
		for (int v = 0; v < this.V(); ++v) {
			this.adj[v] = new Bag<DirectedEdge>();
		}
		final int numEdges = Integer.valueOf(in.readLine());
		for (int e = 0; e < numEdges; ++e) {
			final In lineIn = new In(in.readLine());
			final int v = lineIn.readInt();
			final int w = lineIn.readInt();
			final double weight = lineIn.readDouble();
			this.addEdge(new DirectedEdge(v, w, weight));
			lineIn.close();
		}
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public void addEdge(DirectedEdge edge) {
		this.adj[edge.from()].add(edge);
		++this.E;
	}
	
	public Iterable<DirectedEdge> adj(int v) {
		return this.adj[v];
	}
	
	public Iterable<DirectedEdge> edges() {
		final Stack<DirectedEdge> st = new Stack<DirectedEdge>();
		for (int v = 0; v < this.V(); ++v) {
			for (DirectedEdge e : this.adj[v]) {
				st.push(e);
			}
		}
		return st;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (int v = 0; v < this.V(); ++v) {
			for (DirectedEdge e : this.adj[v]) {
				sb.append(e.toString());
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}
