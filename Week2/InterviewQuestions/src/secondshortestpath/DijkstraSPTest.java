package secondshortestpath;

//import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class DijkstraSPTest {

	@Test
	void test() {
		final String s =  "8\n" +
					    "15\n" +
						"0 2 2.5\n" +
						"0 4 3.75\n" +
						"0 6 5.5\n" +
						"0 7 1.5\n" +
						"1 2 3.5\n" +
						"1 3 2.75\n" +
						"1 5 3\n" +
						"1 7 2\n" +
						"2 3 1.75\n" +
						"2 6 4\n" +
						"2 7 3.25\n" +
						"3 6 5\n" +
						"4 5 3.25\n" +
						"4 7 3.5\n" +
						"5 7 2.5";
		final Scanner sc = new Scanner(s);
		final EdgeWeightedDigraph G = new EdgeWeightedDigraph(sc);
		sc.close();
		final DijkstraSP sp = new DijkstraSP(G, 0);
		for (int v = 0; v < G.V(); ++v) {
			if (sp.hasPathTo(v)) {
				System.out.println("Shortest path to " + v + ": " + sp.distance(v));			
				for (DirectedEdge e : sp.pathTo(v)) {
					System.out.println("\t" + e);
				}
			} else {
				System.out.println("No path to " + v);
			}
		}
	}
}
