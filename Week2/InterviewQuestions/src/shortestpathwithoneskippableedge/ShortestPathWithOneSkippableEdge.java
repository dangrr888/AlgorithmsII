package shortestpathwithoneskippableedge;

import java.util.Stack;

public class ShortestPathWithOneSkippableEdge {

	private DijkstraSP sps;
	private DijkstraSP spt;
	private DirectedEdge skippedEdge;
	private EdgeWeightedDigraph G;
	private Stack<DirectedEdge> path;
	
	private DirectedEdge findEdge(int from, int to) {
		for (DirectedEdge e : this.G.adj(from)) {
			if (e.to() == to) {
				return e;
			}
		}
		return null;
	}
		
	private double pathWeight(Iterable<DirectedEdge> path) {
		double weight = 0.0;
		for (DirectedEdge e : path) {
			weight += e.weight();
		}
		return weight;
	}
	
	public ShortestPathWithOneSkippableEdge(EdgeWeightedDigraph G,
			                                int source, int target,
			                                int skippedEdgeFrom, int skippedEdgeTo) {
		
		// Initialize data members.
		this.G = G; 
		this.sps = new DijkstraSP(this.G, source);
		this.spt = new DijkstraSP(this.G.reverse(), target);
		
		// Validate arguments.
		this.skippedEdge = this.findEdge(skippedEdgeFrom, skippedEdgeTo);
		if (this.skippedEdge == null) {
			throw new IllegalArgumentException("Skipped edge not in Digraph.");
		}
	
		if (!this.sps.hasPathTo(target)) {
			throw new IllegalArgumentException("No path from source to target.");
		}
		
		// Get shortest path and check if it contains skipped edge.
		boolean skippedEdgeInSP = false;
		this.path = new Stack<DirectedEdge>();
		for (DirectedEdge e : sps.pathTo(target)) {
			if (e == this.skippedEdge) {
				skippedEdgeInSP = true;
			}
			this.path.push(e);
		}
		
		if (!skippedEdgeInSP) {
			// if shortest path includes skipped edge then its still the
			// shortest path when that edge is skipped, otherwise check
			// if the sp from source to skipped edge start + shortest path from skipped
			// edge end to target is less than the shortest overall path with no edges
			// skipped. If so, then the former represents the new shortest path, otherwise
			// return the latter.
			
			// Check there is a path from source/target to start/end of skipped edge
			if (this.sps.hasPathTo(this.skippedEdge.from()) &&
				this.spt.hasPathTo(this.skippedEdge.to())) {
				
				// Calculate weight of shortest path from source to target with no edges skipped.
				final double spWeight = this.pathWeight(this.path);
	
				// Calculate shortest distance from source to skipped edge start.
				final double sToFromWeight = this.pathWeight(this.sps.pathTo(this.skippedEdge.from()));
				
				// calculate shortest distance from skipped edge end to target.
				final double tToToWeight = this.pathWeight(this.spt.pathTo(this.skippedEdge.to()));
				
				// If the sum of (1) + (2) is shorter than sp, its the new shortest path.
				if (sToFromWeight + tToToWeight < spWeight) {
					this.path = new Stack<DirectedEdge>();
					
					// Add path from target to skippedEdge.to() with edges reversed.
					Stack<DirectedEdge> tmp = new Stack<DirectedEdge>();
					for (DirectedEdge e : this.spt.pathTo(this.skippedEdge.to())) {
						tmp.push(e.reverse());
					}
					while(!tmp.isEmpty()) {
						this.path.push(tmp.pop());
					}
					
					// Add the skipped edge with the weight set to 0
					this.path.push(new DirectedEdge(this.skippedEdge.from(), this.skippedEdge.to(), 0.0));
					
					// Add path from skipped edge start to source.
					for (DirectedEdge e : this.sps.pathTo(this.skippedEdge.from())) {
						tmp.push(e);
					}
					while (!tmp.isEmpty()) {
						this.path.push(tmp.pop());
					}
				}
			}
		}
	}
		
	public Iterable<DirectedEdge> shortestPath() {
		return this.path;
	}
}
