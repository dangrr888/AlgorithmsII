package secondshortestpath;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class SecondShortestPathTest {
	
	private EdgeWeightedDigraph G;
	private String result;
	
	private void setup() {
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
		this.G = new EdgeWeightedDigraph(sc);
		sc.close();	
		this.result = null;
	}
	
	private void process(int source, int target) {
		
		this.setup();
		
		final DijkstraSP sp = new DijkstraSP(this.G, source); 
		final SecondShortestPath ssp = new SecondShortestPath(this.G, source, target);
		
		final StringBuilder sb = new StringBuilder();
		if (sp.hasPathTo(target)) {
			sb.append("Shortest path from " + source + " to " + target + ":\n");
			for (DirectedEdge e : sp.pathTo(target)) {
				sb.append(e+"\n");
			}
		} else {
			sb.append("No shortest path from " + source + " to " + target + ":\n");
		}
		
		if (ssp.hasNextBestPath()) {
			sb.append("Second shortest path from " + source + " to " + target + ":\n");
			for (DirectedEdge e : ssp.nextBestPath()) {
				sb.append(e+"\n");
			}
		} else {
			sb.append("No second shortest path from " + source + " to " + target + ":\n");		
		}
		this.result = sb.toString();
	}

	@Test
	void test0() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 0);
		});
		assertEquals("No second shortest path.", exc.getMessage());
	}

	@Test
	void test1() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 1);
		});
		assertEquals("No path to target: 1", exc.getMessage());
	}

	@Test
	void test2() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 2);
		});
		assertEquals("No second shortest path.", exc.getMessage());
	}

	@Test
	void test3() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 3);
		});
		assertEquals("No second shortest path.", exc.getMessage());
	}

	@Test
	void test4() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 4);
		});
		assertEquals("No second shortest path.", exc.getMessage());
	}

	@Test
	void test5() {
		IllegalArgumentException exc = assertThrows(IllegalArgumentException.class, () -> {
			this.process(0, 5);
		});
		assertEquals("No second shortest path.", exc.getMessage());
	}

	@Test
	void test6() {
		this.process(0, 6);
		assertEquals(this.result,
					 "Shortest path from 0 to 6:\n" + 
					 "0 - 6\n" + 
					 "Second shortest path from 0 to 6:\n" + 
					 "2 - 6\n" + 
					 "0 - 2\n");
	}
	
	@Test
	void test7() {
		this.process(0, 7);
		assertEquals(this.result,
					 "Shortest path from 0 to 7:\n" + 
					 "0 - 7\n" + 
					 "Second shortest path from 0 to 7:\n" + 
					 "2 - 7\n" + 
					 "0 - 2\n");
	}

}
