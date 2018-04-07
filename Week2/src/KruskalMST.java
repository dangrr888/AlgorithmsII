// import statements
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


// A class to represent an MST.
public class KruskalMST {

	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;
	
	// Construct an MST for a specified Graph.
	public KruskalMST(EdgeWeightedGraph G) {
						
		final MinPQ<Edge> pq = new MinPQ<Edge>();
		for (Edge e : G.edges()) {
			pq.insert(e);
		}
		
		// Construct a union-find to track connected
		//   components of the current MST forest.
		final UF uf = new UF(G.V());
		
		// Iterate through sorted list of edges
		//   adding those that do not generate cycles
		//   in the MST forest to the current forest,
		//   until we reach V-1 edges.
		while (!pq.isEmpty() && mst.size() < G.V()-1) {
			final Edge e = pq.delMin();
			if (!uf.connected(e.either(), e.other())) {
				this.mst.enqueue(e);
				this.mstWeight += e.weight();
				
				// Add vertices to same connected component.
				uf.union(e.either(), e.other());
			}
		}
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}
	
	public double weight() {
		return this.mstWeight;
	}

	 public static void main(String[] args)
	 {
		 try {
			 final Scanner in = new Scanner(new File(args[0]));
			 EdgeWeightedGraph G = new EdgeWeightedGraph(in);
			 KruskalMST mst = new KruskalMST(G);
			 for (Edge e : mst.edges())
			 StdOut.println(e);
			 StdOut.printf("%.2f\n", mst.weight());
			 in.close();
		 } catch (FileNotFoundException exc) {
			 StdOut.println("File " + args[0] + " not found: " + exc.getMessage());
		 }
	 }
}
