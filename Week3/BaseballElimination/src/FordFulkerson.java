import edu.princeton.cs.algs4.Queue;

/*
 * Class to determine the maxflow of a specified FlowNetwork by:
 * * All FlowEdge's in the network have zero flow.
 * * Find the next augmenting path by finding a path from
 * * the specified source and target in the residual flow
 *   network using BFS, say.
 * * Augment the path by adding the bottleneck residual flow
 *   to the flow of each edge along the path.
 * * Repeat the previous two steps until there are no more
 *   augmenting paths.
 */
public class FordFulkerson {

  // data members for finding augmenting path
  private FlowEdge[] edgeTo;
  private boolean[] marked;
  private double flow;

  public FordFulkerson(FlowNetwork G, int source, int target) {

    this.initializePathData(G);
    this.flow = 0.0;

    while (this.hasAugmentingPath(G, source, target)) {
      this.augmentPath(source, target);
    }
  }

  private void initializePathData(FlowNetwork G) {
    this.edgeTo = new FlowEdge[G.V()];
    this.marked = new boolean[G.V()];
    for (int v = 0; v < G.V(); ++v) {
      this.edgeTo[v] = null;
      this.marked[v] = false;
    }
  }

  private boolean hasAugmentingPath(FlowNetwork G, int source, int target) {

    this.initializePathData(G);

    // Perform graph search (BFS) across entire graph (not necessary for
    // just finding a path, but makes the results from inCut valid).

    final Queue<Integer> q = new Queue<Integer>();
    q.enqueue(source);
    this.marked[source] = true;
    while (!q.isEmpty()) {
      final int v = q.dequeue();
      for (FlowEdge edge : G.adj(v)) {
        // check other end is not marked.
        final int w = edge.other(v);
        if (!this.marked[w]) {
          // Check there is a residual capacity in this edge
          // from v to w.
          final double residualCapacity = edge.residualCapacityTo(w);
          if (residualCapacity > 0.0) {
            // We have a residual flow along this path.
            // Add dest vertex to the queue and mark it,
            // and update edgeTo.
            q.enqueue(w);
            this.marked[w] = true;
            this.edgeTo[w] = edge;
          }
        }
      }
    }

    // If target is inCut it is accessible from source in residual
    // network, hence we have an augmenting path.
    return this.inCut(target);
  }

  private void augmentPath(int source, int target) {

    // 1. Iterate through augmenting path and deduce bottleneck residual capacity
    double bottleneckCapacity = Double.POSITIVE_INFINITY;
    for (FlowEdge edge = this.edgeTo[target]; edge != null; edge = this.edgeTo[edge.from()]) {
      bottleneckCapacity = Math.min(bottleneckCapacity, edge.residualCapacityTo(edge.to()));
    }

    //2. Iterate throw path adding flow from 1 to each edge along the path.
    for (FlowEdge edge = this.edgeTo[target]; edge != null; edge = this.edgeTo[edge.from()]) {
      edge.addResidualFlowTo(edge.to(), bottleneckCapacity);
    }

    //3. Increment this.flow by that residual capacity.
    this.flow += bottleneckCapacity;
  }

  public double maxFlow() {
    return this.flow;
  }

  public boolean inCut(int v) {
    /*
     * Is v reachable from s in the residual network along the last augmenting path?
     */
    return this.marked[v];
  }
}
