
public class DirectedEdge implements Comparable<DirectedEdge> {

	private final int v;
	private final int w;
	private final double weight;
	
	public DirectedEdge(int v, int w, double weight) {
		this.v = v;
		this.w = w;
		this.weight = weight;
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
	
	@Override
	public int compareTo(DirectedEdge o) {
		if (this.weight() < o.weight()) {
			return -1;
		} else if (this.weight() > o.weight()) {
			return +1;
		}
		
		return 0;
	}
	
	@Override
	public String toString() {
		return this.from() + " -> " + this.to() + " : " + this.weight();
	}
}
