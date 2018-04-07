// import statements
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.UF;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


// A class to represent an MST.
public class PrimMST {

	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;
	private boolean marked[];
	private MinPQ<Edge> pq;
	
	private void visit(int v, EdgeWeightedGraph G) {
		this.marked[v] = true;
		for (Edge e : G.adj(v)) {
			this.pq.insert(e);
		}
	}
	
	// Construct an MST for a specified Graph.
	public PrimMST(EdgeWeightedGraph G) {
		
		// Initialise data members.
		pq = new MinPQ<Edge>();
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
		}
		
		this.visit(0, G);
		
		while (!this.pq.isEmpty() && this.mst.size() < G.V()-1) {

			final Edge e = pq.delMin();
			if (this.marked[e.either()] && this.marked[e.other()]) {
				continue;
			}
			
			this.mst.enqueue(e);
			this.mstWeight += e.weight();
			if (!this.marked[e.either()]) {
				this.visit(e.either(), G);
			} else {
				this.visit(e.other(), G);
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
