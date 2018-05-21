import java.util.Stack;

import edu.princeton.cs.algs4.In;

public class FlowNetwork {

  private int V;
  private int E;
  private Bag<FlowEdge>[] adj;

  @SuppressWarnings("unchecked")
  public FlowNetwork(int V) {
    this.V = V;
    this.E = 0;
    this.adj = new Bag[this.V];
    for (int v = 0; v < this.V; ++v) {
      this.adj[v] = new Bag<FlowEdge>();
    }
  }

  @SuppressWarnings("unchecked")
  public FlowNetwork(In in) {
    this.V = in.readInt();
    this.E = 0;
    final int numEdges = in.readInt();
    this.adj = new Bag[this.V];
    for (int v = 0; v < this.V; ++v) {
      this.adj[v] = new Bag<FlowEdge>();
    }

    for (int e = 0; e < numEdges; ++e) {
     final int v = in.readInt();
     final int w = in.readInt();
     final double capacity = in.readDouble();
     this.addEdge(new FlowEdge(v, w, capacity));
    }
  }

  public int V() {
    return this.V;
  }

  public int E() {
    return this.E;
  }

  public void addEdge(FlowEdge edge) {
    this.adj[edge.from()].add(edge);
    this.adj[edge.to()].add(edge);
    ++this.E;
  }

  public Iterable<FlowEdge> adj(int v) {
    return this.adj[v];
  }

  public Iterable<FlowEdge> edges() {
    final Stack<FlowEdge> st = new Stack<FlowEdge>();
    for (int v = 0; v < this.V; ++v) {
      for (FlowEdge edge : this.adj(v)) {
        st.push(edge);
      }
    }
    return st;
  }

  @Override
  public String toString() {
    final StringBuilder sb = new StringBuilder();
    for (int v = 0; v < this.V; ++v) {
      sb.append(v + ": ");
      for (FlowEdge e : this.adj(v)) {
        sb.append(e.toString() + " ");
      }
      sb.append("\n");
    }
    return sb.toString();
  }
}
