package shortestpathwithoneskippableedge;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class ShortestPathWithOneSkippableEdgeTest {

	private EdgeWeightedDigraph G;
	private String result;
	
	private void setup() {
		final String s =  "8\n" +
			    "15\n" +
				"0 2 2.5\n" +
				"0 4 3.75\n" +
				"0 6 5.5\n" +
				"0 7 100.0\n" +
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
		this.G = new EdgeWeightedDigraph(sc);
		sc.close();	
		this.result = null;
	}
	
	private void process(int source, int target,
			             int skippedStart, int skippedEnd) {
		
		this.setup();
		
		final ShortestPathWithOneSkippableEdge p
			= new ShortestPathWithOneSkippableEdge(this.G,
												  source, target,
												  skippedStart, skippedEnd);
		
		final StringBuilder sb = new StringBuilder();
		sb.append("Shortest path from " + source + " to " + target +
				  " skipping edge " + skippedStart + " -> " + skippedEnd +
				  ":\n");
		for (DirectedEdge e : p.shortestPath()) {
			sb.append(e +"\n");
		}
		this.result = sb.toString();
	}
	
	@Test
	void test1() {
		this.process(0, 7, 0, 7);
		assertEquals(this.result,
					 "Shortest path from 0 to 7 skipping edge 0 -> 7:\n" + 
					 "0 - 7\n");
	}
	
	@Test
	void test2() {
		this.process(0, 7, 2, 7);
		assertEquals(this.result,
					 "Shortest path from 0 to 7 skipping edge 2 -> 7:\n" + 
					 "2 - 7\n0 - 2\n");
	}
	
}
