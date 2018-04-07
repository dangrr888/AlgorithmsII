import java.util.Random;

public class FindCentre {

	public static int centre(Graph G) {
		Random r = new Random();
		BreadthFirstSearch bfs = new BreadthFirstSearch(G, r.nextInt(G.V()));
		
		// Find the vertex with the largest depth from random source
		final int v0 =  bfs.getVertexWithLargestdepth();
		
		// Run BFS from v0
		bfs = new BreadthFirstSearch(G, v0);

		// Get index of vertex at one end of the diameter.
		final int v1 = bfs.getVertexWithLargestdepth();
		
		// Run BFS from v1
		bfs = new BreadthFirstSearch(G, v1);

		// Get index of vertex at the opposite end of the diameter from v1.
		final int v2 = bfs.getVertexWithLargestdepth();

		// Get length of diameter.
		final int diameter = bfs.getDepth(v2);
		
		// Iterate along diameter/2 edges along the path from v1 to v2 to get the centre of G.
		int centre = -1;
		int edgecount = diameter/2;
		for (int w : bfs.pathTo(v2))  {
			if (edgecount-- == 0) {
				centre = w;
				break;
			}
		}
		
		return centre;
	}
	
	public static void main(String[] args) {
		Graph G = new Graph(7);
		
		G.addEdge(0, 1);
		G.addEdge(0, 2);
		G.addEdge(2, 3);
		G.addEdge(3, 4);
		G.addEdge(0, 5);
		G.addEdge(0, 6);

		final int d = FindGraphDiameter.diameter(G);
		System.out.println("The diameter of the graph: ");
		System.out.println(G);
		System.out.println("is:\n" + d);
		
		final int centre = FindCentre.centre(G);
		System.out.println("and the centre is: ");
		System.out.println(centre);
	}
}
