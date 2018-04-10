package findbottleneckmst;
import java.util.Iterator;
import java.util.Scanner;

import edu.princeton.cs.algs4.IndexMinPQ;
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

class EagerPrimMST
{
	private boolean marked[];
	private IndexMinPQ<Edge> pq;
	private Queue<Edge> mst = new Queue<Edge>();
	private double mstWeight = 0.0;
	
	private void visit(int v, EdgeWeightedGraph G) {
		this.marked[v] = true;
		for (Edge e : G.adj(v)) {
			final int w = e.either() != v ? e.either() : e.other();
			if (!this.marked[w]) {
				if (this.pq.contains(w)) {
					if (this.pq.keyOf(w).weight() > e.weight()) {
						this.pq.decreaseKey(w, e);
					}
				} else {
					this.pq.insert(w, e);
				}
			}
		}
	}
	
	public EagerPrimMST(EdgeWeightedGraph G) {
		this.marked = new boolean[G.V()];
		for (int v = 0; v < G.V(); ++v) {
			this.marked[v] = false;
		}
		
		this.pq = new IndexMinPQ<Edge>(G.V());
		
		this.visit(0, G);
		
		while (!pq.isEmpty() && this.mst.size() < G.V() - 1) {
			
			final Edge e = this.pq.minKey();
			this.mst.enqueue(e);
			this.mstWeight += e.weight();
			final int v = this.pq.delMin();
			this.visit(v, G);			
		}
	}
	
	public Iterable<Edge> edges() {
		return this.mst;
	}
	
	public double weight() {
		return this.mstWeight;
	}
}

public class FindBottleneckMST {

	final EagerPrimMST epmst;

	public FindBottleneckMST(EdgeWeightedGraph G) {
		this.epmst = new EagerPrimMST(G);
	}
	public Iterable<Edge> edges() {
		return this.epmst.edges();
	}
	
	public double weight() {
		return this.epmst.weight();
	}	
}
