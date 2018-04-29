package monotonicshortestpath;

import java.util.Stack;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.IndexMinPQ;

public class MonotonicShortestPath {
	private double distTo[];
	private DirectedEdge edgeTo[];
	private IndexMinPQ<Double> pq;
	private int source;
	private boolean increasing;

	public MonotonicShortestPath(EdgeWeightedDigraph G, int source, boolean increasing) {
		
		// Initialize data members.
		this.distTo = new double[G.V()];
		this.edgeTo = new DirectedEdge[G.V()];
		
		for (int v = 0; v < G.V(); ++v) {
			this.distTo[v] = Double.POSITIVE_INFINITY;
			this.edgeTo[v] = null;
		}
		
		this.source = source;
		this.distTo[this.source] = 0.0;

		this.increasing = increasing;
		
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
			if (this.edgeTo[e.from()] == null ||
				(this.increasing ? (e.weight() > this.edgeTo[e.from()].weight()) : (e.weight() < this.edgeTo[e.from()].weight()))) {
				this.distTo[w] = newDist;
				this.edgeTo[w] = e;
				if (this.pq.contains(w)) {
					this.pq.decreaseKey(w, this.distTo[w]);
				} else {
					this.pq.insert(w, this.distTo[w]);
				}
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
	
	public static void main(String[] args) {
		// Build graph.
		final EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		G.addEdge(new DirectedEdge(0, 1, 5.0));
		G.addEdge(new DirectedEdge(0, 4, 9.0));
		G.addEdge(new DirectedEdge(0, 7, 8.0));
		G.addEdge(new DirectedEdge(1, 2, 12.0));
		G.addEdge(new DirectedEdge(1, 3, 15.0));
		G.addEdge(new DirectedEdge(1, 7, 4.0));
		G.addEdge(new DirectedEdge(2, 3, 3.0));
		G.addEdge(new DirectedEdge(2, 6, 11.0));
		G.addEdge(new DirectedEdge(3, 6, 9.0));
		G.addEdge(new DirectedEdge(4, 5, 4.0));
		G.addEdge(new DirectedEdge(4, 6, 20.0));
		G.addEdge(new DirectedEdge(4, 7, 5.0));
		G.addEdge(new DirectedEdge(5, 2, 1.0));
		G.addEdge(new DirectedEdge(5, 6, 13.0));
		G.addEdge(new DirectedEdge(7, 5, 6.0));
		G.addEdge(new DirectedEdge(7, 2, 7.0));

		StringBuilder sb = new StringBuilder();

		// Find shortest paths from vertex 0 that are monotonically increasing.
		final MonotonicShortestPath sp = new MonotonicShortestPath(G, 0, true);
		for (int v = 0; v < G.V(); ++v) {
			if (sp.hasPathTo(v)) {
				sb.append(v + ": distance = " + sp.distTo(v) + ", path = ");
				for (DirectedEdge e : sp.pathTo(v)) {
					sb.append(e + " ");
				}
				sb.append("\n");
			} else if (v == sp.source()) {
				sb.append(v + ": distance = " + 0.0 + "\n");
			} else {
				sb.append("No path found to " + v + "\n");
			}
		}
		
		System.out.println(sb.toString());
	}
}
