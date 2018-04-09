import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.UF;

public class KruskalMST {

	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;

	public KruskalMST(EdgeWeightedGraph G) {
		
		// Initialize
		MinPQ<Edge> pq = new MinPQ<Edge>();
		UF uf = new UF(G.V());
		
		// Add all edges to the pq
		for (Edge e : G.edges()) {
			pq.insert(e);
		}
		
		// Iterate through sorted edges. If it
		// doesn't introduce a cycle in the
		// current mst then add to the current
		// mst until there are G.V() - 1 edges.
		while (!pq.isEmpty() && mst.size() < G.V()-1) {
			final Edge e = pq.delMin();
			if (!uf.connected(e.either(), e.other())) {
				this.mst.enqueue(e);
				this.mstWeight += e.weight();
				uf.union(e.either(), e.other());
			}
		}
	}
	
	public double weight() {
		return this.mstWeight;
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}	
}
