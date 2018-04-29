package shortestpathwithoneskippableedge;

public class DirectedEdge implements Comparable<DirectedEdge> {

	private int v;
	private int w;
	private double weight;
	
	public DirectedEdge(int v, int w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
	}

	@Override
	public int compareTo(DirectedEdge o) {
		if (this.weight() < o.weight()) {
			return -1;
		}
		
		if (this.weight() > o.weight()) {
			return +1;
		}
		
		return 0;
	}
	
	public int from() {
		return this.v;
	}
	
	public int to() {
		return this.w;
	}
	
	public double weight() {
		return this.weight;
	}
	
	public DirectedEdge reverse() {
		return new DirectedEdge(this.to(), this.from(), this.weight());
	}
	
	@Override
	public String toString() {
		return this.from() + " - " + this.to();
	}
}
