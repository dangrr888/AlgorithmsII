import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class EdgeWeightedGraphTest {

	@Test
	void testConstructEmpty() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(0);
		assertEquals(G.V(), 0);
		assertEquals(G.E(), 0);
	}
	
	@Test
	void testConstructNoEdges() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		assertEquals(G.E(), 0);		
	}

	@Test
	void testConstructOneEdge() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		G.addEdge(new Edge(1,2,4.5));
		assertEquals(G.E(), 1);		
	}

	@Test
	void testConstructTwoEdges() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		G.addEdge(new Edge(1,2,4.5));
		G.addEdge(new Edge(1,3,4.5));
		assertEquals(G.E(), 2);		
	}

	@Test
	void testConstructTwoDuplicateEdges() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		G.addEdge(new Edge(1,2,4.5));
		G.addEdge(new Edge(1,2,4.5));
		assertEquals(G.E(), 2); // We don't check for duplicates
	}
	
	@Test
	void testConstructManyEdges() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		G.addEdge(new Edge(1,2,4.5));
		G.addEdge(new Edge(1,6,4.5));
		G.addEdge(new Edge(1,9,4.5));		
		G.addEdge(new Edge(1,4,4.5));
		G.addEdge(new Edge(1,7,4.5));
		G.addEdge(new Edge(7,9,4.5));		
		G.addEdge(new Edge(0,1,4.5));
		G.addEdge(new Edge(1,0,4.5));
		G.addEdge(new Edge(8,9,4.5));		
		G.addEdge(new Edge(9,9,4.5));
		assertEquals(G.E(), 10);
	}
	
	@Test
	void testToString() {
		EdgeWeightedGraph G = new EdgeWeightedGraph(10);
		assertEquals(G.V(), 10);
		G.addEdge(new Edge(1,2,4.5));
		G.addEdge(new Edge(1,6,4.5));
		G.addEdge(new Edge(1,9,4.5));		
		G.addEdge(new Edge(1,4,4.5));
		G.addEdge(new Edge(1,7,4.5));
		G.addEdge(new Edge(7,9,4.5));		
		G.addEdge(new Edge(0,1,4.5));
		G.addEdge(new Edge(1,0,4.5));
		G.addEdge(new Edge(8,9,4.5));		
		G.addEdge(new Edge(9,9,4.5));
		assertEquals(G.toString(),
					 "10\n" +
					 "10\n" +
					 "1 - 9 : 4.5\n" +
					 "7 - 9 : 4.5\n" +
					 "8 - 9 : 4.5\n" +
					 "9 - 9 : 4.5\n" +
					 "9 - 9 : 4.5\n" +
					 "8 - 9 : 4.5\n" +
					 "1 - 7 : 4.5\n" +
					 "7 - 9 : 4.5\n" +
					 "1 - 6 : 4.5\n" +
					 "1 - 4 : 4.5\n" +
					 "1 - 2 : 4.5\n" +
					 "1 - 2 : 4.5\n" +
					 "1 - 6 : 4.5\n" +
					 "1 - 9 : 4.5\n" +
					 "1 - 4 : 4.5\n" +
					 "1 - 7 : 4.5\n" +
					 "0 - 1 : 4.5\n" +
					 "1 - 0 : 4.5\n" +
					 "0 - 1 : 4.5\n" +
					 "1 - 0 : 4.5\n");
	}
	
	@Test
	void testContructFromScanner() {
		String s = "10\n" +
				 "10\n" +
				 "1 2 4.5\n" +
				 "1 6 4.5\n" +
				 "1 9 4.5\n" +
				 "1 4 4.5\n" +
				 "1 7 4.5\n" +
				 "7 9 4.5\n" +
				 "0 1 4.5\n" +
				 "1 0 4.5\n" +
				 "8 9 4.5\n" +
				 "9 9 4.5\n";

		Scanner sc = new Scanner(s);
		EdgeWeightedGraph G = new EdgeWeightedGraph(sc);
		assertEquals(G.toString(),
				 "10\n" +
				 "10\n" +
				 "1 - 9 : 4.5\n" +
				 "7 - 9 : 4.5\n" +
				 "8 - 9 : 4.5\n" +
				 "9 - 9 : 4.5\n" +
				 "9 - 9 : 4.5\n" +
				 "8 - 9 : 4.5\n" +
				 "1 - 7 : 4.5\n" +
				 "7 - 9 : 4.5\n" +
				 "1 - 6 : 4.5\n" +
				 "1 - 4 : 4.5\n" +
				 "1 - 2 : 4.5\n" +
				 "1 - 2 : 4.5\n" +
				 "1 - 6 : 4.5\n" +
				 "1 - 9 : 4.5\n" +
				 "1 - 4 : 4.5\n" +
				 "1 - 7 : 4.5\n" +
				 "0 - 1 : 4.5\n" +
				 "1 - 0 : 4.5\n" +
				 "0 - 1 : 4.5\n" +
				 "1 - 0 : 4.5\n");
		sc.close();
	}
}
