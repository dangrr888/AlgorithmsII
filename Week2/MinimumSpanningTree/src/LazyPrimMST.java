import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;

public class LazyPrimMST {

	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;
	private boolean marked[];
	private MinPQ<Edge> pq;
	
	private void visit(int v, EdgeWeightedGraph G) {
		this.marked[v] = true;
		for (Edge e : G.adj(v)) {
			final int w = e.either() == v ? e.other() : e.either();
			if (!this.marked[w]) {
				this.pq.insert(e);
			}
		}
	}
	
	public LazyPrimMST(EdgeWeightedGraph G) {

		this.pq = new MinPQ<Edge>();
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
		}
		
		this.visit(0, G);
		
		while (!this.pq.isEmpty() && this.mst.size() < G.V()-1) {
			final Edge e = this.pq.delMin();
			if (!this.marked[e.either()]) {
				this.visit(e.either(), G);
			} else if (!this.marked[e.other()]) {
				this.visit(e.other(), G);
			} else {
				continue;
			}
			this.mst.enqueue(e);
			this.mstWeight += e.weight();
		}
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}
	
	public double weight() {
		return this.mstWeight;
	}
}
