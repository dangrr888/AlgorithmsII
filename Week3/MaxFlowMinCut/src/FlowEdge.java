
public class FlowEdge {

	private final int v;
	private final int w;
	private final double capacity;
	private double flow;

	public FlowEdge(int v, int w, double capacity) {
		this.v = v;
		this.w = w;
		this.capacity = capacity;
		this.flow = 0.0;
	}

	public int from() {
		return this.v;
	}

	public int to() {
		return this.w;
	}

	public int other(int vertex) {
		if (this.v == vertex) {
			return this.w;
		}

		if (this.w == vertex) {
			return this.v;
		}

		throw new IllegalArgumentException(vertex + " is neither start nor end of FlowEdge.");
	}

	public double capacity() {
		return this.capacity;
	}

	public double flow() {
		return this.flow;
	}

	public double residualCapacityTo(int vertex) {
		if (this.w == vertex) {
			// forwards
			return this.capacity - this.flow;
		}

		if (this.v == vertex) {
			// backwards
			return -this.flow;
		}

		throw new IllegalArgumentException(vertex + " is neither start nor end of FlowEdge.");
	}

	void addResidualFlowTo(int vertex, double delta) {
		if (delta < 0.0) {
			throw new IllegalArgumentException("Negative delta.");
		}


		if (vertex == this.w) {
			// forwards
			this.flow += delta;
		} else if (vertex == this.v) {
			// backwards
			final double newFlow = this.flow - delta;
			if (newFlow < 0.0) {
				throw new IllegalArgumentException("Invalid delta - generates negative flow.");
			}

			this.flow = newFlow;
		} else {
			throw new IllegalArgumentException(vertex + " is neither start nor end of FlowEdge.");
		}
	}

	@Override
	public String toString() {
		return this.v + "->" + this.w + ": " + this.capacity + ": " + this.flow;
	}
}
