import java.util.Random;

public class FindGraphDiameter {
	
	public static int diameter(Graph G) {
		Random r = new Random();
		BreadthFirstSearch bfs = new BreadthFirstSearch(G, r.nextInt(G.V()));
		
		// Find the vertex with the largest depth from random source
		int v =  bfs.getVertexWithLargestdepth();
		
		// Run BFS from v
		bfs = new BreadthFirstSearch(G, v);
		v = bfs.getVertexWithLargestdepth();
		
		return bfs.getDepth(v); 
	}
	
	public static void main(String[] args) {
		Graph G = new Graph(7);
		
		G.addEdge(0, 1);
		G.addEdge(0, 2);
		G.addEdge(2, 3);
		G.addEdge(0, 4);
		G.addEdge(0, 5);
		G.addEdge(0, 6);

		final int d = FindGraphDiameter.diameter(G);
		System.out.println("The diameter of the graph: ");
		System.out.println(G);
		System.out.println("is:\n" + d);		
	}
}
