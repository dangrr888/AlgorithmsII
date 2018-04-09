import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;

/*
 * Maintain a min pq of vertices ordered based upon the
 * minimum weight of its edges that connect it to the
 * current mst.
 * When a new vertex is added to the mst, its adjacent
 * edges are added to the pq. If any of its edges connect
 * it to a vertex already in the pq and possesses a weight
 * less than the associated key of that vertex, then the
 * key of the vertex needs to be updated via the IndexPQ
 * API to be the new lighter edge.
 * Vertices are marked to determine whether they require
 * processing.
 */
public class EagerPrimMST {

	private IndexMinPQ<Edge> pq;
	private boolean marked[];
	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;
	
	private void visit(int v, EdgeWeightedGraph G) {
		this.marked[v] = true;
		
		for (Edge e : G.adj(v)) {
			
			// Find connecting vertex that isn't v
			final int w = e.either() == v ? e.other() : e.either();
			
			// Check if connecting vertex is already in mst
			if (this.marked[w]) {
				continue;
			}

			// Check if connecting vertex is on pq (but not in mst).
			if (this.pq.contains(w)) {
				if (e.weight() < this.pq.keyOf(w).weight()) {
					
					// We have found an edge connecting the mst (from v) to
					// w to be lighter than that previously found.
					this.pq.decreaseKey(w, e);
				}
			} else {
				// w not contained in pq, insert it with current edge v-w as key.
				this.pq.insert(w, e);
			}
		}
	}
	
	public EagerPrimMST(EdgeWeightedGraph G) {
		
		// Initialize data members.
		this.pq = new IndexMinPQ<Edge>(G.V());
		
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
		}
		
		// Add edges incident to vertex 0 to the MST
		// using its minimum edge as key.
		this.visit(0, G);
		
		while (!this.pq.isEmpty() && this.mst.size() < G.V()-1) {
			final Edge e = this.pq.minKey();
			this.mst.enqueue(e);
			this.mstWeight += e.weight();

			final int v = this.pq.delMin();
			this.visit(v, G);
		}
	}

	public Iterable<Edge> edges() {
		return this.mst;
	}
	
	public double weight() {
		return this.mstWeight;
	}
}
