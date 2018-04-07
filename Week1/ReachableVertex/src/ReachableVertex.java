import java.util.LinkedList;

/* Find a reachable vertex in a specified DiGraph that can include cycles. */
/* Strategy: 
 * 1. Find the strong components of the DiGraph
 * 2. Determine if there is a string component that can be reached from all other strong components.
 * 3. Can do this by constructing a DiGraph- identical to the original
 *    where the strong components are repesented by a single vertex with edges equal to the edges external to
 *    its associated string component (e.g., if string component 1 has an in-edge to sc 2 and an out-edge to
 *    sc 4, the replacement vertex representing sc 1 will have a single edge to sc 2 and a single edge from
 *    sc 4).
 * 4. The Graph will be a DAG. This DiGraph can then be processed using the previous solution to
 *    finding a reachable vertex in a DAG (i.e., topologically sort the reverse and conduct a DFS from the
 *    first, checking if all vertices are marked, indicating that it is a reachable vertex).
 * 5. The result will then be that any vertex in the original graph contained in the string component that is
 *    reachable by all others are all reachable vertices.
 */
public class ReachableVertex {

	final private DiGraph g;
	final private int[] strongComponents;
	final private boolean marked[];
	final LinkedList<Integer> rpo;
	int strongComponentIndex;
	private DiGraph scdg;
	private boolean hasReachableVertex;
	final LinkedList<Integer> reachableVertices;
	
	private void findRPO(DiGraph dg) {
		for (int v = 0; v < dg.V(); ++v) {
			if (!this.marked[v]) {
				this.marked[v] = true;
				findRPOHelper(dg, v);
				this.rpo.addFirst(v);
			}
		}
	}
	
	private void findRPOHelper(DiGraph dg, int v) {
		for (int w : dg.adj(v)) {
			if (!this.marked[w]) {
				this.marked[w] = true;
				findRPOHelper(dg, w);
				this.rpo.addFirst(w);
			}
		}
	}

	private void findSC(DiGraph dg) {
		resetMarks();
		this.strongComponentIndex = 0;
		
		for (int v : this.rpo) {
			if (!this.marked[v]) {
				this.marked[v] = true;
				findSCHelper(dg, v);
				this.strongComponents[v] = this.strongComponentIndex++;
			}
		}
	}
	
	private void findSCHelper(DiGraph dg, int v) {
		for (int w : dg.adj(v)) {
			if (!this.marked[w]) {
				this.marked[w] = true;
				findSCHelper(dg, w);
				this.strongComponents[w] = this.strongComponentIndex;
			}
		}
	}
	
	private void resetMarks() {
		for (int v = 0; v < this.g.V(); ++v) {
			this.marked[v] = false;
		}
	}

	private void findStrongComponents() {
			
		/* Get the reverse of g. */
		final DiGraph rg = g.reverse();
		
		/* Calculate the reverse postorder DFS of rg. */
		findRPO(rg);
		
		/* Now conduct a DFS in g using the RPO to find the strong components. */
		findSC(g);
	}
	
	public boolean hasReachableVertex() {
		return this.hasReachableVertex;
	}
	
	public Iterable<Integer> reachableVertices() {
		if (!this.hasReachableVertex) {
			throw new IllegalStateException();
		}
		return this.reachableVertices;
	}
	
	private void constructSCDAG() {
		this.scdg = new DiGraph(this.strongComponentIndex);
		
		/* Iterate through the vertices, adding edges to other strong components, */
		/* including the connection to itself. */
		for (int v = 0; v < this.g.V(); ++v) {
			final int sci = this.strongComponents[v];
			this.scdg.addEdge(sci, sci); /* The connection to itself. */
			for (int w : this.g.adj(v)) {
				this.scdg.addEdge(sci, this.strongComponents[w]); /* don't need to check if different sc's since we already have the connection to itself. */
			}
		}
	}
	
	public ReachableVertex(DiGraph g_) {
		this.g = g_;
		this.strongComponents = new int[this.g.V()];
		this.marked = new boolean[this.g.V()];
		for (int v = 0; v < this.g.V(); ++v) {
			this.strongComponents[v] = v;
			this.marked[v] = false;
		}
		this.rpo = new LinkedList<Integer>();
		this.hasReachableVertex = false;
		this.reachableVertices = new LinkedList<Integer>();
		
		/* 1. Find the string components of each of the vertices in g */
		findStrongComponents();
		
		/* 2. Create a DAG representing the strong components of the original. */
		constructSCDAG();
		
		/* We now have a DAG scdg which we can use to determine if there are any reachable vertices. */
		final ReachableVertexDAG rvdag = new ReachableVertexDAG(scdg);
		
		/* The reachable vertices will be the components of the reachable SC. */
		this.hasReachableVertex = rvdag.hasReachableVertex();
		final int reachableSCIndex = rvdag.reachableVertex();
		for (int v = 0; v < this.g.V(); ++v) {
			if (this.strongComponents[v] == reachableSCIndex) {
				this.reachableVertices.add(v);
			}
		}
	}
	
	public static void main(String[] args) {
		
		final DiGraph dg = new DiGraph(13);
		
		dg.addEdge(0, 12);
		dg.addEdge(0, 5);
		
		dg.addEdge(2, 0);
		dg.addEdge(2, 3);
		
		dg.addEdge(3, 2);
		dg.addEdge(3, 5);

		dg.addEdge(4, 2);
		dg.addEdge(4, 3);

		dg.addEdge(5, 4);
		
		dg.addEdge(6, 0);
		dg.addEdge(6, 4);
		dg.addEdge(6, 8);
		dg.addEdge(6, 9);
		
		dg.addEdge(7, 6);
		dg.addEdge(7, 9);
		
		dg.addEdge(8, 6);
		
		dg.addEdge(9, 10);
		dg.addEdge(9, 11);
		
		dg.addEdge(10, 1);
		
		dg.addEdge(11, 4);
		dg.addEdge(11, 1);
		
		dg.addEdge(1, 9);
		
		final ReachableVertex rv = new ReachableVertex(dg);
		
		if (rv.hasReachableVertex()) {
			System.out.println("Reachable vertices found:");
			for (int v : rv.reachableVertices) {
				System.out.print(v + " ");
			}
			System.out.println();
		} else {
			System.out.println("No reachable vertices found!");
		}
	}
}
