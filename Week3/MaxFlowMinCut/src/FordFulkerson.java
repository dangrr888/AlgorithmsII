
public class FordFulkerson {

	private boolean marked[]; // marked[v] = true if there is a path in residual network from s to v, false otherwise.
	private FlowEdge[] edgeTo; // edgeTo[v] is the FlowEdge that connects s to v of the next path in the residual network.
	private double value; // The value of the flow of the next path from s to t in the residual network.

	public FordFulkerson(FlowNetwork G, int s, int t) {
		this.marked = new boolean[G.V()];
		this.edgeTo = new FlowEdge[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
			this.edgeTo[v] = null;
		}

		// FlowNetwork expected to have flow of all its FlowEdge's equal to zero.
		this.value = 0.0;

		while (this.hasAugmentingPath(G, s, t)) {
			// Calculate bottleneck capacity along next augmenting path
			double bottle = Double.POSITIVE_INFINITY;
			for (int v = t; v != s; v = this.edgeTo[v].other(v)) {
				bottle = Math.min(bottle, this.edgeTo[v].residualCapacityTo(v));
			}

			// Augment the flow by reducing residual capacity of every edge along augmenting path
			// by the bottleneck capacity (i.e., leaving a single full-forward edge or an empty backward one in the path).
			for (int v = t; v != s; v = this.edgeTo[v].other(v)) {
				this.edgeTo[v].addResidualFlowTo(v, bottle);
			}

			this.value += bottle;
		}
	}

	private boolean DFS(FlowNetwork G, int v, int t) {

		// Terminating condition.
		if (v == t) {
			return true;
		}

		// Loop over adjacent edges of v
		for (FlowEdge edge : G.adj(v)) {
			// Check that other end of edge is not marked.
			if (!this.marked[edge.other(v)]) {
				// Check if edge has residual capacity
				if (edge.residualCapacityTo(edge.other(v)) > 0) {
					// update edgeTo
					this.edgeTo[edge.other(v)] = edge;
					// mark destination vertex
					this.marked[edge.other(v)] = true;
					// Proceed finding path to target
					if (this.DFS(G, edge.other(v), t)) {
						return true;
					}
					// Didn't find path using this edge,
					// continue to explore v's other adjacent
					// edges.
				}
			}
		}

		// No path found through v.
		return false;
	}

	private boolean hasAugmentingPath(FlowNetwork G, int s, int t) {
		/*
		 * find path using DFS from s to t in residual network
		 */
		for (int v = 0; v < G.V(); ++v) {
			this.edgeTo[v] = null;
			this.marked[v] = false;
		}

		this.marked[s] = true;
		return DFS(G, s, t);
	}

	public double value() {
		return this.value;
	}

	/*
	 * is v reachable from s in residual network?
	 */
	public boolean inCut(int v) {
		return this.marked[v];
	}

}
