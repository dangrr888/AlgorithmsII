
public class FlowEdge {

  private int v;
  private int w;
  private double capacity;
  private double flow;

  public FlowEdge(int v, int w, double capacity) {
    this.v = v;
    this.w = w;
    this.capacity = capacity;
    this.flow = 0.0;
  }

  public int to() {
    return this.w;
  }

  public int from() {
    return this.v;
  }

  public int other(int vertex) {
    if (vertex != this.v)
    {
      if (vertex != this.w) {
        throw new IllegalArgumentException(vertex + " is niether end of this FlowEdge.");
      }
      return this.w;
    }
    return this.v;
  }

  public double capacity() {
    return this.capacity;
  }

  public double flow() {
    return this.flow;
  }

  public double residualCapacityTo(int vertex) {
    if (vertex == this.w) {
      return this.capacity-this.flow;
    }

    if (vertex == this.v) {
      return this.flow;
    }

    throw new IllegalArgumentException(vertex + " is niether end of this FlowEdge.");
  }

  public void addResidualFlowTo(int vertex, double delta) {
    if (delta < 0.0) {
      throw new IllegalArgumentException("Negative delta");
    }

    if (this.residualCapacityTo(vertex) < delta) {
      throw new IllegalArgumentException(delta + " exceeds capacity.");
    } else {
      if (vertex == this.w) {
        this.flow += delta;
      } else if (vertex == this.v) {
        this.flow -= delta;
      } else {
        throw new IllegalArgumentException(vertex + " is niether end of this FlowEdge.");
      }
    }
  }

  @Override
  public String toString() {
    return this.v + "->" + this.w +
           ", c = " + this.capacity +
           ", f = " + this.flow;
  }
}
