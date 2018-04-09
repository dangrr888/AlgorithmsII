import java.util.Scanner;
import edu.princeton.cs.algs4.Stack;

public class EdgeWeightedGraph {

	private Bag<Edge>[] adj;
	private int V;
	private int E;
	
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
		
		sc.close();
	}
	
	@SuppressWarnings("unchecked")
	public EdgeWeightedGraph(int V) {
		this.V = V;
		this.E = 0;
		this.adj = (Bag<Edge>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.adj[v] = new Bag<Edge>();
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
	
	public Iterable<Edge> adj(int v) {
		return this.adj[v];
	}
	
	public Iterable<Edge> edges() {
		Stack<Edge> s = new Stack<Edge>();
		for (int v = 0; v < this.V; ++v) {
			for (Edge e : this.adj(v)) {
				s.push(e);
			}
		}
		return s;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(this.V() + "\n");
		sb.append(this.E() + "\n");
		for (Edge e : this.edges()) {
			sb.append(e + "\n");
		}
		return sb.toString();
	}
}
