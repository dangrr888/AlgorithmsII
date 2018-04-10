package isedgeinmst;
import java.util.Iterator;
import java.util.Scanner;

import edu.princeton.cs.algs4.Queue;

class Bag<T> implements Iterable<T>
{
	private class Node {
		public T val;
		public Node next;
		
		public Node(T val, Node next) {
			this.val = val;
			this.next = next;
		}
	}
	
	private class ForwardIterator implements Iterator<T> {
		Node n;
		
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
				throw new IllegalStateException("Empty Iterator.");
			}
			final T ret = this.n.val;
			this.n = this.n.next;
			return ret;
		}
	}
	
	private int sz;
	private Node head;

	public Bag() {
		this.sz = 0;
		this.head = null;
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
	public int compareTo(Edge o) {
		if (this.weight() < o.weight()) {
			return -1;
		} else if (this.weight() > o.weight()) {
			return +1;
		}
		return 0;
	}

	@Override
	public String toString() {
		return this.either() + " - " + this.other() + " : " + this.weight(); 
	}
}

class EdgeWeightedGraph
{
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
	    final int numEdges = sc.nextInt();
	    this.adj = (Bag<Edge>[]) new Bag[this.V()];
	    for (int v = 0; v < this.V(); ++v) {
	    		this.adj[v] = new Bag<Edge>();
	    }
	    
	    for (int e = 0; e < numEdges; ++e) {
	    		final int v = sc.nextInt();
	    		final int w = sc.nextInt();
	    		final double weight = sc.nextDouble();
	    		
	    		this.addEdge(new Edge(v, w, weight));
	    }
	}
	
	public int V() {
		return this.V;
	}
	
	public int E() {
		return this.E;
	}
	
	public void addEdge(Edge e) {
		this.adj[e.either()].add(e);
		this.adj[e.other()].add(e);
		++this.E;
	}
	
	public Iterable<Edge> edges() {
		Queue<Edge> q = new Queue<Edge>();
		for (int v = 0; v < this.V(); ++v) {
			for (Edge e : this.adj(v)) {
				q.enqueue(e);
			}
		}
		return q;
	}
	
	public Iterable<Edge> adj(int v) {
		return this.adj[v];
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.V()+"\n");
		sb.append(this.E()+"\n");
		for (Edge e : this.edges()) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}

/*
 * If one considers the subgraph H of G containing the edges
 * less than e, if one can get from e.either() to e.other() in
 * H, e.g., by DFS, then e is not in the MST of G. H can be
 * cyclic, its irrelevant since the cycles constitute a redundant
 * part of the required path.
 */
public class IsEdgeInMST {

	private EdgeWeightedGraph H;
	private boolean isEdgeInMST;
	boolean marked[];
	
	private boolean DFS(int sourceVertex, int targetVertex) {
		this.marked = new boolean[this.H.V()];
		for (int v = 0; v < this.H.V(); ++v) {
			this.marked[v] = false;
		}
		
		// Mark source vertex
		this.marked[sourceVertex] = true;
		
		// DFS from source vertex.
		return this.DFSHelper(sourceVertex, targetVertex);
	}
	
	private boolean DFSHelper(int sourceVertex, int targetVertex) {
		for (Edge e : this.H.adj(sourceVertex)) {
			final int newSourceVertex = e.either() != sourceVertex ? e.either() : e.other();
			
			if (newSourceVertex == targetVertex) {
				return true;
			} else if (!this.marked[newSourceVertex]) {
				this.marked[newSourceVertex] = true;
				if (DFSHelper(newSourceVertex, targetVertex)) {
					return true;
				}
			}
		}
		
		return false;
	}
	
	public IsEdgeInMST(EdgeWeightedGraph G, Edge edge) {
		this.H = new EdgeWeightedGraph(G.V());
		this.isEdgeInMST = false;
		for (Edge e : G.edges()) {
			if (e.weight() < edge.weight()) {
				this.H.addEdge(e);
			}
		}
		
		this.isEdgeInMST = !this.DFS(edge.either(), edge.other());
	}

	public boolean isEdgeInMST() {
		return this.isEdgeInMST;
	}
}
