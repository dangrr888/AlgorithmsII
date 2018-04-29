package secondshortestpath;

import java.util.Stack;

import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP {

	private int source;
	private EdgeWeightedDigraph G;
	private IndexMinPQ<Double> pq;
	private double distTo[];
	private DirectedEdge edgeTo[];
	
	public DijkstraSP(EdgeWeightedDigraph G, int s) {
		this.source = s;
		this.G = G; 
		this.pq = new IndexMinPQ<Double>(this.G.V());
		this.distTo = new double[this.G.V()];
		this.edgeTo = new DirectedEdge[this.G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.distTo[v] = Double.POSITIVE_INFINITY;
			this.edgeTo[v] = null;
		}
		
		this.distTo[this.source] = 0.0;
		this.pq.insert(this.source, this.distTo[this.source]);
		
		while (!this.pq.isEmpty()) {
			final int v = this.pq.delMin();
			for (DirectedEdge w : this.G.adj(v)) {
				this.relax(w);
			}
		}
	}
	
	private void relax(DirectedEdge e) {
		if (this.distTo[e.from()] + e.weight() < this.distTo[e.to()]) {
			this.distTo[e.to()] = this.distTo[e.from()] + e.weight();
			this.edgeTo[e.to()] = e;
			if (this.pq.contains(e.to())) {
				this.pq.decreaseKey(e.to(), this.distTo[e.to()]);
			} else {
				this.pq.insert(e.to(), this.distTo[e.to()]);
			}
		}
	}
	
	public int source() {
		return this.source;
	}
	
	public boolean hasPathTo(int v) {
		return this.distTo[v] != Double.POSITIVE_INFINITY;
	}
	
	public double distance(int v) {
		if (!this.hasPathTo(v)) {
			throw new IllegalStateException("No path from " + this.source + " to " + v);
		}
		return this.distTo[v];
	}
	
	public Iterable<DirectedEdge> pathTo(int v) {
		if (!this.hasPathTo(v)) {
			throw new IllegalStateException("No path from " + this.source + " to " + v);
		}
		final Stack<DirectedEdge> st = new Stack<DirectedEdge>();
		for (DirectedEdge e = this.edgeTo[v]; e != null; e = this.edgeTo[e.from()]) {
			st.push(e);
		}
		return st;
	}
}
