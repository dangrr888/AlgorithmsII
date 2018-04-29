import edu.princeton.cs.algs4.Topological;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;
import edu.princeton.cs.algs4.DirectedEdge;

public class AcyclicSP {

	private double distTo[];
	private DirectedEdge edgeTo[];
	private Topological topo;
	private int source;
	
	public AcyclicSP(EdgeWeightedDigraph G, int source) {
		
		// Initialize data members.
		this.topo = new Topological(G);
		if (!this.topo.hasOrder()) {
			throw new IllegalArgumentException("Digraph is not acyclic.");
		}

		this.distTo = new double[G.V()];
		this.edgeTo = new DirectedEdge[G.V()];
		this.source = source;
		
		for (int v = 0; v < G.V(); ++v) {
			this.distTo[v] = Double.POSITIVE_INFINITY;
			this.edgeTo[v] = null;
		}
		
		// Add source to SPT.
		this.distTo[this.source] = 0.0;

		// Relax adjacent edges of vertices, visited in topological order.
		for (int v : this.topo.order()) { // visit vertex v in topo order, hence no edges relaxed
			                              // after visiting v will point to v. Hence distTo[v]
										 // can only change before we visit v here.
										 // Hence since distTo[w] is monotonically decreasing (see
										 //  relax(...)) then distTo[w] ≤ distTo[v] + e.weight()
										 // is true as soon as we visit v, for any edge e: v->w,
										 // adjacent to v, which is relaxed when we visit v.
										 // Hence following termination of the loop, the optimality
										 // condition is satisfied for all members of the SPT.
			for (DirectedEdge e : G.adj(v)) {
				this.relax(e);
			}
		}
	}
	
	private void relax(DirectedEdge e) {
		final int w = e.to();
		final double newDist = this.distTo[e.from()] + e.weight();
		if (this.distTo[w] > newDist) { // distTo[w] is monotonically decreasing.
			this.distTo[w] = newDist;
			this.edgeTo[w] = e;
		}
	}
}
