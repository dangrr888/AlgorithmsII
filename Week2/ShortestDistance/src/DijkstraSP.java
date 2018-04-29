import java.util.Stack;

import edu.princeton.cs.algs4.IndexMinPQ;

public class DijkstraSP {
	private double distTo[];
	private DirectedEdge edgeTo[];
	private IndexMinPQ<Double> pq;

	private int source;
	
	public DijkstraSP(EdgeWeightedDigraph G, int source) {
		
		// Initialize data members.
		this.distTo = new double[G.V()];
		this.edgeTo = new DirectedEdge[G.V()];
		
		for (int v = 0; v < G.V(); ++v) {
			this.distTo[v] = Double.POSITIVE_INFINITY;
			this.edgeTo[v] = null;
		}
		
		this.source = source;
		this.distTo[this.source] = 0.0;

		this.pq = new IndexMinPQ<Double>(G.V());
		
		// Add source to PQ.
		pq.insert(this.source, 0.0);
		
		// Process PQ
		while (!pq.isEmpty()) {			
			final int v = pq.delMin();			
			for (DirectedEdge e : G.adj(v)) {
				this.relax(e);
			}
		}
	}
	
	private void relax(DirectedEdge e) {
		final int w = e.to();
		final double newDist = this.distTo[e.from()] + e.weight();
		if (this.distTo[w] > newDist) {
			this.distTo[w] = newDist;
			this.edgeTo[w] = e;
			if (this.pq.contains(w)) {
				this.pq.decreaseKey(w, this.distTo[w]);
			} else {
				this.pq.insert(w, this.distTo[w]);
			}
		}
	}
	
	public int source() {
		return this.source;
	}
	
	public boolean hasPathTo(int v) {
		return this.edgeTo[v] != null;
	}
	
	public double distTo(int v) {
		if (v != this.source && !this.hasPathTo(v)) {
			throw new IllegalArgumentException("No path from " + this.source + " to " + v);
		}
		return this.distTo[v];
	}
	
	public Iterable<DirectedEdge> pathTo(int v) {
		if (!this.hasPathTo(v)) {
			throw new IllegalArgumentException("No path from " + this.source + " to " + v);
		}
		final Stack<DirectedEdge> path = new Stack<DirectedEdge>();
		for (DirectedEdge e = this.edgeTo[v]; e != null; e = this.edgeTo[e.from()]) {
			path.push(e);
		}
		return path;
	}
}
