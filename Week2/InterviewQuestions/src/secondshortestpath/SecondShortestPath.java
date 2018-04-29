package secondshortestpath;

import java.util.HashSet;
import java.util.Stack;

public class SecondShortestPath {

	private int s;
	private int t;
	private 	Stack<DirectedEdge> ssp;
	private double nextBestDist;
	
	public SecondShortestPath(EdgeWeightedDigraph G, int s, int t) {
		
		this.s = s;
		this.t = t;
		this.ssp = new Stack<DirectedEdge>();
		this.nextBestDist = Double.POSITIVE_INFINITY;
		
		final DijkstraSP sps = new DijkstraSP(G, this.s);
		if (!sps.hasPathTo(t)) {
			throw new IllegalArgumentException("No path to target: " + this.t);
		}
		
		final DijkstraSP spt = new DijkstraSP(G.reverse(), this.t);
		if (!sps.hasPathTo(this.s)) {
			throw new IllegalArgumentException("No path to source: " + this.s);
		}
		
		// Calculate distances from s to t via a vertex v. Any
		// v lying on the shortest path will yield the same
		// optimal distance. Hence exclude such vertices from the
		// analysis.
		final HashSet<Integer> spvertices = new HashSet<Integer>();
		spvertices.add(this.t);
		spvertices.add(this.s);
		for (DirectedEdge e : sps.pathTo(this.t)) {
			spvertices.add(e.from());
		}
		
		final double distTo[] = new double[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			distTo[v] = Double.POSITIVE_INFINITY;
		}
		for (int v = 0; v < G.V(); ++v) {
			if (!spvertices.contains(v)) {
				if (sps.hasPathTo(v)) {
					if (spt.hasPathTo(v)) {
						distTo[v] = sps.distance(v) + spt.distance(v);
					}
				}
			}
		}
		
		// Determine v corresponding to second shortest path.
		int nextBestV = -1;
		this.nextBestDist = Double.POSITIVE_INFINITY;
		for (int v = 0; v < G.V(); ++v) {
			if (distTo[v] < this.nextBestDist) {
				this.nextBestDist = distTo[v];
				nextBestV = v;
			}
		}
		
		if (nextBestV == -1) {
			throw new IllegalArgumentException("No second shortest path.");
		}
		
		// Construct second shortest path.
		final Stack<DirectedEdge> st = new Stack<DirectedEdge>();
		for (DirectedEdge e : spt.pathTo(nextBestV)) {
			st.push(e.reverse());
		}
		while(!st.isEmpty()) {
			this.ssp.push(st.pop());
		}
		for (DirectedEdge e : sps.pathTo(nextBestV)) {
			this.ssp.push(e);
		}
	}
	
	public boolean hasNextBestPath() {
		return !this.ssp.isEmpty();
	}
	
	public double nextBestDist() {
		if (!hasNextBestPath()) {
			throw new IllegalStateException("No next best path.");
		}
		return this.nextBestDist;
	}
	
	public Iterable<DirectedEdge> nextBestPath() {
		if (!hasNextBestPath()) {
			throw new IllegalStateException("No next best path.");
		}		
		return this.ssp;
	}
	
	public int source() {
		return this.s;
	}
	
	public int target() {
		return this.t;
	}
}
