import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DijkstraSPTest {

	@Test
	void test() {
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

		// Find shortest paths from vertex 0.
		final DijkstraSP sp = new DijkstraSP(G, 0);
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
				"1: distance = 5.0, path = 0 -> 1 : 5.0 \n" +
				"2: distance = 14.0, path = 5 -> 2 : 1.0 4 -> 5 : 4.0 0 -> 4 : 9.0 \n" +
				"3: distance = 17.0, path = 2 -> 3 : 3.0 5 -> 2 : 1.0 4 -> 5 : 4.0 0 -> 4 : 9.0 \n" +
				"4: distance = 9.0, path = 0 -> 4 : 9.0 \n" +
				"5: distance = 13.0, path = 4 -> 5 : 4.0 0 -> 4 : 9.0 \n" +
				"6: distance = 25.0, path = 2 -> 6 : 11.0 5 -> 2 : 1.0 4 -> 5 : 4.0 0 -> 4 : 9.0 \n" +
				"7: distance = 8.0, path = 0 -> 7 : 8.0 \n");
	}

}
