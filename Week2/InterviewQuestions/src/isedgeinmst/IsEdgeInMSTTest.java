package isedgeinmst;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Scanner;
import org.junit.jupiter.api.Test;

class IsEdgeInMSTTest {

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
		sc.close();
		
		IsEdgeInMST ieimst = new IsEdgeInMST(G, new Edge(0,2,2.5));
		assertEquals(ieimst.isEdgeInMST(), true);
		
		ieimst = new IsEdgeInMST(G, new Edge(0,4,3.75));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(0,6,5.5));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(0,7,1.5));
		assertEquals(ieimst.isEdgeInMST(), true);		

		ieimst = new IsEdgeInMST(G, new Edge(1,2,3.5));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(1,3,2.75));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(1,5,3.0));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(1,7,2.0));
		assertEquals(ieimst.isEdgeInMST(), true);		

		ieimst = new IsEdgeInMST(G, new Edge(2,3,1.75));
		assertEquals(ieimst.isEdgeInMST(), true);		

		ieimst = new IsEdgeInMST(G, new Edge(2,6,4.0));
		assertEquals(ieimst.isEdgeInMST(), true);		

		ieimst = new IsEdgeInMST(G, new Edge(2,7,3.25));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(3,6,5.0));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(4,5,3.25));
		assertEquals(ieimst.isEdgeInMST(), true);		

		ieimst = new IsEdgeInMST(G, new Edge(4,7,3.5));
		assertEquals(ieimst.isEdgeInMST(), false);		

		ieimst = new IsEdgeInMST(G, new Edge(5,7,2.5));
		assertEquals(ieimst.isEdgeInMST(), true);		
	}
}
