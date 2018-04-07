import java.util.LinkedList;

public class ReachableVertexDAG {

	private DiGraph rdg;
	private boolean hasReachableVertex;
	private int reachableVertex;
	private LinkedList<Integer> sortedOrder;
	private boolean marked[];
	
	public ReachableVertexDAG(DiGraph dg_) {
		/* Topological Sort reverse DiGraph. */
		/* Conduct DFS in reverse digraph from first entry in sorted list. */
		/* Check if all vertices are marked following DFS */
		/* if so, then it is a reachable vertex. */
		
		/* Initialise data members. */
		this.marked = new boolean[dg_.V()];
		this.sortedOrder = new LinkedList<Integer>();
		
		/* Obtain reverse DiGraph. */
		this.rdg = dg_.reverse();
		
		/* Conduct sequential DFS until all vertices are marked, storing reverse post order. */
		topologicalSort();
				
		/* Conduct DFS from first entry in sorted order (without resorting). */

		/* Reset all marks. */
		for (int v = 0; v < this.rdg.V(); ++v) {
			this.marked[v] = false;
		}

		/* Get first entry and mark it. */
		final int first = this.sortedOrder.getFirst();
		this.marked[first] = true;

		/* Conduct DFS. */
		DFSHelper(first, false);
		
		/* Check if all vertices are marked, in which case DiGraph has a reachable vertex. */
		this.hasReachableVertex = true;
		for (int v = 0; v < this.rdg.V(); ++v) {
			if (!this.marked[v]) {
				this.hasReachableVertex = false;
				break;
			}
		}
		
		/* Set the reachable vertex index. */
		if (this.hasReachableVertex) {
			this.reachableVertex = first;
		} else {
			this.reachableVertex = -1;
		}
	}
	
	private void DFSHelper(int v, boolean sort) {
		for (int w : this.rdg.adj(v)) {
			if (!this.marked[w]) {
				this.marked[w] = true;
				DFSHelper(w, sort);
				if (sort) {
					this.sortedOrder.addFirst(w);
				}
			}
		}
	}
	
	private void topologicalSort() {
		for (int v = 0; v < this.rdg.V(); ++v) {
			if (!this.marked[v]) {
				this.marked[v] = true;
				DFSHelper(v, true);
				this.sortedOrder.addFirst(v);
			}
		}
	}
		
	public boolean hasReachableVertex() {
		return this.hasReachableVertex;
	}
	
	public int reachableVertex() {
		if (!hasReachableVertex()) {
			throw new IllegalStateException();
		}
		
		return this.reachableVertex;
	}
	
	private static void check(DiGraph dag, boolean expectedHasReachableVertex, Integer expectedReachableVertex) throws Exception {
		
		final ReachableVertexDAG rvdag = new ReachableVertexDAG(dag);
		final boolean actualHasReachableVertex = rvdag.hasReachableVertex();
		final Integer actualReachableVertex = actualHasReachableVertex ? rvdag.reachableVertex() : null;

		if (expectedHasReachableVertex != actualHasReachableVertex) {
			throw new Exception("Incorrect hasReachableVertex!");
		} else if (expectedHasReachableVertex && expectedReachableVertex != actualReachableVertex) {
			throw new Exception("Incorrect reachableVertex! Got " + actualReachableVertex + " expected " + expectedReachableVertex);
		}
	}
	
	private static void test1() throws Exception {
		final DiGraph dag = new DiGraph(1);
		check(dag, true, 0);
	}
	
	private static void test2() throws Exception {
		final DiGraph dag = new DiGraph(2);
		dag.addEdge(0, 1);
		check(dag, true, 1);
	}

	private static void test3() throws Exception {
		final DiGraph dag = new DiGraph(2);
		check(dag, false, null);
	}

	private static void test4() throws Exception {
		final DiGraph dag = new DiGraph(3);
		dag.addEdge(0, 2);
		dag.addEdge(0, 1);
		dag.addEdge(1, 2);
		
		check(dag, true, 2);
	}
	
	public static void main(String[] args) throws Exception {
		test1();
		test2();
		test3();
		test4();
		
		System.out.println("All tests passed!!!");
	}
}
