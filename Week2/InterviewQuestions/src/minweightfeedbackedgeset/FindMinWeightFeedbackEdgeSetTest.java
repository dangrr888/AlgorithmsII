package minweightfeedbackedgeset;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.jupiter.api.Test;

class FindMinWeightFeedbackEdgeSetTest {
	@Test
	void test1() {
		String s =  "8\n" +
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
		Scanner sc = new Scanner(s);
		EdgeWeightedGraph G = new EdgeWeightedGraph(sc);
		FindMinWeightFeedbackEdgeSet f = new FindMinWeightFeedbackEdgeSet(G);
		assertEquals(f.edges().toString(),
				"0 - 7 : 1.5 " +
				"0 - 2 : 2.5 " +
				"1 - 7 : 2.0 " +
				"1 - 5 : 3.0 " +
				"1 - 3 : 2.75 " +
				"2 - 3 : 1.75 " +
				"0 - 2 : 2.5 " +
				"2 - 3 : 1.75 " +
				"1 - 3 : 2.75 " +
				"5 - 7 : 2.5 " +
				"1 - 5 : 3.0 " +
				"5 - 7 : 2.5 " +
				"1 - 7 : 2.0 " +
				"0 - 7 : 1.5 "
				);
	}
}
