package monotonicshortestpath;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.princeton.cs.algs4.DirectedEdge;
import edu.princeton.cs.algs4.EdgeWeightedDigraph;

class MonotonicShortestPathTest {

	@Test
	void increasing() {
		// Build graph.
		final EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		G.addEdge(new DirectedEdge(0, 1, 5.0));
		G.addEdge(new DirectedEdge(0, 4, 9.0));
		G.addEdge(new DirectedEdge(0, 7, 8.0));
		G.addEdge(new DirectedEdge(1, 2, 12.0));
		G.addEdge(new DirectedEdge(1, 3, 15.0));
		G.addEdge(new DirectedEdge(1, 7, 4.0));
		G.addEdge(new DirectedEdge(2, 3, 3.0));
		G.addEdge(new DirectedEdge(2, 6, 11.0));
		G.addEdge(new DirectedEdge(3, 6, 9.0));
		G.addEdge(new DirectedEdge(4, 5, 4.0));
		G.addEdge(new DirectedEdge(4, 6, 20.0));
		G.addEdge(new DirectedEdge(4, 7, 5.0));
		G.addEdge(new DirectedEdge(5, 2, 1.0));
		G.addEdge(new DirectedEdge(5, 6, 13.0));
		G.addEdge(new DirectedEdge(7, 5, 6.0));
		G.addEdge(new DirectedEdge(7, 2, 7.0));

		StringBuilder sb = new StringBuilder();

		// Find shortest paths from vertex 0 that are monotonically increasing.
		final MonotonicShortestPath sp = new MonotonicShortestPath(G, 0, true);
		for (int v = 0; v < G.V(); ++v) {
			if (sp.hasPathTo(v)) {
				sb.append(v + ": distance = " + sp.distTo(v) + ", path = ");
				for (DirectedEdge e : sp.pathTo(v)) {
					sb.append(e + " ");
				}
				sb.append("\n");
			} else if (v == sp.source()) {
				sb.append(v + ": distance = " + 0.0 + "\n");
			} else {
				sb.append("No path found to " + v + "\n");
			}
		}
		
		assertEquals(sb.toString(),
				"0: distance = 0.0\n" +
				"1: distance = 5.0, path = 0->1 5.00\n" +
				"2: distance = 17.0, path = 1->2 12.00 0->1 5.00\n" +
				"3: distance = 20.0, path = 1->3 15.00 0->1 5.00\n" +
				"4: distance = 9.0, path = 0->4 9.00\n" +
				"No path found to 5\n" +
				"6: distance = 29.0, path = 4->6 20.00 0->4 9.00\n" +
				"7: distance = 8.0, path = 0->7 8.00\n");
	}
	
	@Test
	void decreasing() {
		// Build graph.
		final EdgeWeightedDigraph G = new EdgeWeightedDigraph(8);
		G.addEdge(new DirectedEdge(0, 1, 5.0));
		G.addEdge(new DirectedEdge(0, 4, 9.0));
		G.addEdge(new DirectedEdge(0, 7, 8.0));
		G.addEdge(new DirectedEdge(1, 2, 12.0));
		G.addEdge(new DirectedEdge(1, 3, 15.0));
		G.addEdge(new DirectedEdge(1, 7, 4.0));
		G.addEdge(new DirectedEdge(2, 3, 3.0));
		G.addEdge(new DirectedEdge(2, 6, 11.0));
		G.addEdge(new DirectedEdge(3, 6, 9.0));
		G.addEdge(new DirectedEdge(4, 5, 4.0));
		G.addEdge(new DirectedEdge(4, 6, 20.0));
		G.addEdge(new DirectedEdge(4, 7, 5.0));
		G.addEdge(new DirectedEdge(5, 2, 1.0));
		G.addEdge(new DirectedEdge(5, 6, 13.0));
		G.addEdge(new DirectedEdge(7, 5, 6.0));
		G.addEdge(new DirectedEdge(7, 2, 7.0));

		StringBuilder sb = new StringBuilder();

		// Find shortest paths from vertex 0 that are monotonically increasing.
		final MonotonicShortestPath sp = new MonotonicShortestPath(G, 0, false);
		for (int v = 0; v < G.V(); ++v) {
			if (sp.hasPathTo(v)) {
				sb.append(v + ": distance = " + sp.distTo(v) + ", path = ");
				for (DirectedEdge e : sp.pathTo(v)) {
					sb.append(e + " ");
				}
				sb.append("\n");
			} else if (v == sp.source()) {
				sb.append(v + ": distance = " + 0.0 + "\n");
			} else {
				sb.append("No path found to " + v + "\n");
			}
		}
		
		assertEquals(sb.toString(),
				"0: distance = 0.0\n" +
				"1: distance = 5.0, path = 0->1 5.00\n" +
				"2: distance = 14.0, path = 5->2 1.00 4->5 4.00 0->4 9.00\n" +
				"No path found to 3\n" +
				"4: distance = 9.0, path = 0->4 9.00\n" +
				"5: distance = 13.0, path = 4->5 4.00 0->4 9.00\n" +
				"No path found to 6\n" +
				"7: distance = 8.0, path = 0->7 8.00\n");
	}

}
