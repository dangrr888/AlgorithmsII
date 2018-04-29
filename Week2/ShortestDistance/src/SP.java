import java.util.Stack;

public class SP {
	private double distTo[];
	private DirectedEdge edgeTo[];
	private int source;
	
	public SP(EdgeWeightedDigraph G, int source) {
		
		// Initialize data members
		this.distTo = new double[G.V()];
		this.edgeTo = new DirectedEdge[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.distTo[v] = Double.POSITIVE_INFINITY;
			this.edgeTo[v] = null;
		}	
		this.source = source;
		this.distTo[this.source] = 0;
		
		// ...
	}

	@SuppressWarnings("unused")
	private void relax(DirectedEdge e) {
		final int w = e.to();
		final double newDist = this.distTo(e.from()) + e.weight();
		if (newDist < this.distTo(w)) {
			this.distTo[w] = newDist;
			this.edgeTo[w] = e;
		}
	}
	
	public double distTo(int v) {
		if (!this.hasPathTo(v)) {
			throw new IllegalStateException("No path from " + this.source + " to " + v);
		}
		
		return this.distTo[v];
	}
	
	public boolean hasPathTo(int v) {
		return this.distTo[v] != Double.POSITIVE_INFINITY;
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
