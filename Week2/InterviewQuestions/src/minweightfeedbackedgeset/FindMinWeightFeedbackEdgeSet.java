package minweightfeedbackedgeset;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;

import edu.princeton.cs.algs4.IndexMaxPQ;
import edu.princeton.cs.algs4.Queue;

class Bag<T> implements Iterable<T> {

	private class Node {
		public T val;
		public Node next;
		
		public Node(T val, Node next) {
			this.val = val;
			this.next = next;
		}
	}
	
	private class ForwardIterator implements Iterator<T> {

		private Node n;
		
		public ForwardIterator() {
			this.n = head;
		}
		
		@Override
		public boolean hasNext() {
			return this.n != null;
		}

		@Override
		public T next() {
			if (!this.hasNext()) {
				throw new IllegalStateException("No next Node.");
			}
			
			final T val = this.n.val;
			this.n = this.n.next;
			return val;
		}
	}
	
	private Node head;
	private int sz;
	
	public Bag() {
		this.head = null;
		this.sz = 0;
	}
	
	public void add(T val) {
		this.head = new Node(val, this.head);
		++this.sz;
	}
	
	public int size() {
		return this.sz;
	}
	
	@Override
	public Iterator<T> iterator() {
		return new ForwardIterator();
	}
}

class Edge implements Comparable<Edge> {
	
	private int v;
	private int w;
	private double weight;
	
	public Edge(int v, int w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}
	
	public int either() {
		return this.v;
	}
	
	public int other() {
		return this.w;
	}
	
	public double weight() {
		return this.weight;
	}
	
	@Override
	public String toString() {
		return this.either() + " - " + this.other() + " : " + this.weight();
	}

	@Override
	public int compareTo(Edge o) {

		if (this.weight() < o.weight()) {
			return -1;
		} else if (this.weight() > o.weight()) {
			return +1;
		}
		return 0;
	}
}

class EdgeWeightedGraph {
	
	private Bag<Edge>[] adj;
	private int V;
	private int E;
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V) {
		this.V = V;
		this.E = 0;
		this.adj = (Bag<Edge>[]) new Bag[this.V()];
		for (int v = 0; v < this.V(); ++v) {
			this.adj[v] = new Bag<Edge>();
		}
	}
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(Scanner sc) {
		this.V = sc.nextInt();
		this.E = 0;
		this.adj = (Bag<Edge>[]) new Bag[this.V()];
		for (int v = 0; v < this.V(); ++v) {
			this.adj[v] = new Bag<Edge>();
		}
		final int numEdges = sc.nextInt();
		for (int e = 0; e < numEdges; ++e) {
			final int v = sc.nextInt();
			final int w = sc.nextInt();
			final double weight = sc.nextDouble();
			this.addEdge(new Edge(v, w, weight));
		}
		sc.close();
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public void addEdge(Edge e) {
		++this.E;
		this.adj[e.either()].add(e);
		this.adj[e.other()].add(e);
	}
	
	public Iterable<Edge> adj(int v) {
		return this.adj[v];
	}
	
	public Iterable<Edge> edges() {
		final Queue<Edge> q = new Queue<Edge>();
		for (int v = 0; v < this.V(); ++v) {
			for (Edge e : this.adj(v)) {
				q.enqueue(e);
			}
		}
		
		return q;
	}
	
	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder();
		sb.append(this.V() + "\n");
		sb.append(this.E() + "\n");
		for (Edge e : this.edges()) {
			sb.append(e + "\n");
		}
		
		return sb.toString();
	}
}

/*
 * find the Maximum Spanning Tree
 */
class EagerPrimMaxST
{
	IndexMaxPQ<Edge> pq = null;
	Queue<Edge> mst = new Queue<Edge>();
	boolean marked[] = null;
	double mstWeight = 0.0;
	
	private void visit(int v, EdgeWeightedGraph G) {
		// Mark vertex
		this.marked[v] = true;
		
		// Iterate through edges incident to vertex v
		for (Edge e : G.adj(v)) {
			
			// Deduce endpoint, w, of edge that isn't v
			final int w = e.either() != v ? e.either() : e.other();
			
			// Check if w is marked
			if (!this.marked[w]) {
				
				// Check if w has an entry in the queue
				if (this.pq.contains(w)) {
					
					// w has entry in queue. Check if key of entry is less than current edge to w.
					if (this.pq.keyOf(w).weight() < e.weight()) {
						
						// Update key of w's entry with heavier edge.
						this.pq.increaseKey(w, e);
					}
				} else {
					// w has no current entry, insert one.
					this.pq.insert(w, e);
				}
			}
		}
	}
	
	public EagerPrimMaxST(EdgeWeightedGraph G) {
		
		// Initialize data members
		this.pq = new IndexMaxPQ<Edge>(G.V());
		this.marked = new boolean[G.V()];
		
		// Visit vertex 0
		this.visit(0, G);
		
		// Process Queue until we connect mst to all vertices in G.
		while (!this.pq.isEmpty() && this.mst.size() < G.V()-1) {
			
			// Get heaviest edge.
			final Edge e = this.pq.maxKey();
			
			// Check which vertex is unmarked (at least one must be),
			// and visit it.
			if (!this.marked[e.either()]) {
				this.visit(e.either(), G);
			} else if (!this.marked[e.other()]) {
				this.visit(e.other(), G);
			} else {
				// Both ends of e are marked, edge is not in mst.
				continue;
			}
			
			// Add e to mst and update mst weight.
			this.mst.enqueue(e);
			this.mstWeight += e.weight();
			
			// Pop the queue.
			this.pq.delMax();
		}
	}
	
	public double weight() {
		return this.mstWeight;
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}
}

public class FindMinWeightFeedbackEdgeSet {
	
	private Queue<Edge> mwfes = new Queue<Edge>();
	
	private boolean contains(LinkedList<Edge> ll, Edge edge) {
		for (Edge e : ll) {
			if (edge.compareTo(e) == 0) {
				return true;
			}
		}
		return false;
	}
	
	public FindMinWeightFeedbackEdgeSet(EdgeWeightedGraph G) {
		
		// Get Queue containing max spanning tree edges.
		final EagerPrimMaxST epmst = new EagerPrimMaxST(G);
		LinkedList<Edge> mst = new LinkedList<Edge>();
		for (Edge e : epmst.edges()) {
			mst.add(e);
		}
		
		// Get complementary set of edges
		for (Edge e : G.edges()) {
			if (!this.contains(mst, e)) {
				mwfes.enqueue(e);
			}
		}
	}
	
	public Iterable<Edge> edges() {
		return this.mwfes;
	}
}
