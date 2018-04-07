import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import edu.princeton.cs.algs4.IndexMinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;

public class EagerPrimMST {

	private IndexMinPQ<Edge> pq;
	private Queue<Edge> mst = new Queue<Edge>();
	private int weight = 0;
	boolean marked[];
	
	private void visit(int v, EdgeWeightedGraph G) {
		this.marked[v] = true;
		int minWeight = Integer.MAX_VALUE;
		Edge minEdge = null;
		for (Edge e : G.adj(v)) {
			final int w = e.either() == v ? e.other() : e.either();
			if (this.marked[w]) {
				// Min edge to w already found.
				continue;
			}
			if (e.weight() < minWeight) {
				minWeight = (int)e.weight();
				minEdge = e;
			}
			
			// Update the priority queue entry for w, if
			// such an entry exists and the associated
			// key is greater than that associated with
			// the current edge v-w
			if (this.pq.contains(w)) {
				if (minWeight < this.pq.keyOf(w).weight()) {
					this.pq.decreaseKey(w, minEdge);
				}
			} else {
				// w is not on the q, add it, keyed by its current minimum edge to the MST, v-w
				this.pq.insert(w, minEdge);
			}
		}
	}
	
	public EagerPrimMST(EdgeWeightedGraph G) {
		
		// Initialize data members
		this.pq = new IndexMinPQ<Edge>(G.V());
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
		}
		
		// Add vertex 0 to the MST
		this.visit(0, G);
		
		while (!this.pq.isEmpty() && this.mst.size() < G.V()-1) {
			
			final int v = this.pq.minIndex();
			final Edge e = this.pq.minKey();
			this.mst.enqueue(e);
			this.weight += e.weight();
			this.visit(v, G);
		}
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}
	
	public double weight() {
		return this.weight;
	}

	 public static void main(String[] args)
	 {
		 try {
			 final Scanner in = new Scanner(new File(args[0]));
			 EdgeWeightedGraph G = new EdgeWeightedGraph(in);
			 PrimMST mst = new PrimMST(G);
			 for (Edge e : mst.edges()) {
				 StdOut.println(e);
			 }
			 StdOut.printf("%.2f\n", mst.weight());
			 in.close();
		 } catch (FileNotFoundException exc) {
			 StdOut.println("File " + args[0] + " not found: " + exc.getMessage());
		 }
	 }
}
