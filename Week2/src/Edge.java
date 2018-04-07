import edu.princeton.cs.algs4.StdOut;

public class Edge implements Comparable<Edge> {

	private final int v;
	private final int w;
	private final double weight;
	
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
		if (this.weight < o.weight) {
			return -1;
		}
		if (this.weight > o.weight) {
			return 1;
		}
		return 0;
	}
	
	@Override
	public String toString() {
		return "Edge: " + this.v + " - " + this.w + ", W = " + this.weight;
	}
}
