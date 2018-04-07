import java.util.Scanner;
import java.util.Stack;

public class EdgeWeightedGraph {

	private Bag<Edge> e[];
	private int V;
	private int E;
	
	public EdgeWeightedGraph(int V) {
		this.V = V;
		this.e = (Bag<Edge>[]) new Bag[this.V];
		this.E = 0;
	}
	
	public EdgeWeightedGraph(Scanner sc) {
		this.V = sc.nextInt();
		this.E = sc.nextInt();
		this.e = (Bag<Edge>[]) new Bag[this.V];
		for (int v = 0; v < this.V; ++v) {
			this.e[v] = new Bag<Edge>();
		}
		for (int i = 0; i < this.E; ++i) {
			final int v = sc.nextInt();
			final int w = sc.nextInt();
			final double weight = sc.nextDouble();
			final Edge edge = new Edge(v, w, weight);
			this.e[v].add(edge);
			this.e[w].add(edge);
		}
	}
	
	public void addEdge(Edge edge) {
		this.e[edge.either()].add(edge);
		this.e[edge.other()].add(edge);
		++this.E;
	}
	
	public Iterable<Edge> adj(int v) {
		return this.e[v];
	}
	
	public Iterable<Edge> edges() {
		final Stack<Edge> st = new Stack<Edge>();
		for (int i = 0; i < this.V; ++i) {
			for (Edge edge : this.e[i]) {
				st.push(edge);
			}
		}
		return st;
	}
	
	public int V() {
		return this.V;
	}

	public int E() {
		return this.E;
	}

	@Override
	public String toString() {
		return "";
	}
	
	public static void main(String[] args) {
		
	}
}
