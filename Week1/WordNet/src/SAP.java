import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.HashMap;
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;

public class SAP {

  // Private data members.

  // The DiGraph.
  private final Digraph G;
  private final int numVertices;

  // Array of BreadFirstDirectedPaths.
  private final BreadthFirstDirectedPaths[] bfdps;

  // Map containing sorted results hitherto
  private final HashMap<Key, Value> results;

  // Public structors

  // constructor takes a digraph (not necessarily a DAG)
  public SAP(Digraph G) {

    // Validate arguments.
    if (G == null) {
      throw new IllegalArgumentException("SAP::SAP - Null argument.");
    }

    // Initialize private data members.
    this.numVertices = G.V();

    // Copy input Digraph so that SAP is immutable to changes within it.
    this.G = new Digraph(this.numVertices);
    for (int v = 0; v < this.numVertices; ++v) {
      for (int w : G.adj(v)) {
        this.G.addEdge(v, w);
      }
    }

    this.bfdps = new BreadthFirstDirectedPaths[this.numVertices];
    this.results = new HashMap<Key, Value>();

    // Populate diagonal results.
    for (int v = 0; v < this.numVertices; ++v) {
      this.results.put(new Key(v, v), new Value(v, 0));
    }
  }

  // Private classes.

  // Class to represent ordered information relating to a pair of vertices.
  private class Key
  {
    public final int v;
    public final int w;

    public Key(int v, int w) {
      this.v = v;
      this.w = w;
    }

    @Override
    public int hashCode() {
      int ret = 17;
      ret = 31 * ret + this.v;
      ret = 31 * ret + this.w;                        
      return ret;
    }

    @Override
    public boolean equals(Object obj) {
      if (obj != null && obj.getClass() == this.getClass()) {
        final Key otherKey = (Key) obj;
        if (this.v == otherKey.v && this.w == otherKey.w) {
          return true;
        }
      }

      return false;
    }
  }

  // Class to represent the ancestor and length of the sap associated with a Key,
  //   referred to it via the HashMap, this.results.
  private class Value
  {
    public final int anc;
    public final int len;

    public Value(int anc, int len) {
      this.anc = anc;
      this.len = len;
    }
  }

  // Private methods.
  private void checkIndex(int v) {
    if (v < 0 || v >= this.numVertices) {
      throw new IllegalArgumentException("SAP::checkIndex - Invalid index.");
    }
  }

  private boolean getValue(int v, int w) {

    // Validate arguments.
    this.checkIndex(v);
    this.checkIndex(w);

    // Check for cached result.
    final Key key = new Key(v, w);
    if (this.results.containsKey(key)) {
      return true;
    }

    // No cached result. Check if searches conducted already.
    BreadthFirstDirectedPaths vBfdp = this.bfdps[v];
    BreadthFirstDirectedPaths wBfdp = this.bfdps[w]; 

    if (vBfdp != null && wBfdp != null) {
      // searches conducted, no cached result, hence no path exists.
      return false;
    }

    if (vBfdp == null) {
      vBfdp = new BreadthFirstDirectedPaths(this.G, v);
    }

    if (wBfdp == null) {
      wBfdp = new BreadthFirstDirectedPaths(this.G, w);
    }

    // Both vertices have now been searched from. Calculate the
    //  ancestor candidates, and their lengths.
    int len;
    int lenBest = Integer.MAX_VALUE;
    int anc = -1; 
    for (int i = 0; i < this.numVertices; ++i) {
      if (!vBfdp.hasPathTo(i) || !wBfdp.hasPathTo(i)) {
        continue;
      }
      len = vBfdp.distTo(i) + wBfdp.distTo(i);
      if (len < lenBest) {
        lenBest = len;
        anc = i; 
      }
    }

    if (anc != -1) {
      this.results.put(new Key(v, w), new Value(anc, lenBest));
      return true;
    }       

    return false;
  }

  // Public methods.

  // length of shortest ancestral path between v and w; -1 if no such path
  public int length(int v, int w) {

    // Get the cached value, or calculate it then cache it.
    if (this.getValue(v, w)) {
      // Common ancestor exists. Return the SAP.
      return this.results.get(new Key(v, w)).len;
    }

    // No common ancestor exists.
    return -1;
  }

  // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
  public int ancestor(int v, int w) {

    // Get the cached value, or calculate it then cache it.
    if (this.getValue(v, w)) {
      // Common ancestor exists. Return the SAP.
      return this.results.get(new Key(v, w)).anc;
    }

    // No common ancestor exists.
    return -1;

  }

  // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
  public int length(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException("length: null argument.");
    }

    int bestLen = Integer.MAX_VALUE;
    int bestAnc = -1;

    for (int x : v) {
      for (int y : w) {
        if (this.getValue(x, y)) {
          final Value val = this.results.get(new Key(x, y));
          final int len = val.len;
          if (len < bestLen) {
            bestLen = len;
            bestAnc = val.anc;
          }
        }
      }
    }

    return bestAnc == -1 ? -1 : bestLen;
  }

  // a common ancestor that participates in shortest ancestral path; -1 if no such path
  public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
    if (v == null || w == null) {
      throw new IllegalArgumentException("ancestor: null argument.");
    }

    int bestLen = Integer.MAX_VALUE;
    int bestAnc = -1;

    for (int x : v) {
      for (int y : w) {
        if (this.getValue(x, y)) {
          final Value val = this.results.get(new Key(x, y));
          final int len = val.len;
          if (len < bestLen) {
            bestLen = len;
            bestAnc = val.anc;
          }
        }
      }
    }

    return bestAnc;
  }

  // do unit testing of this class
  public static void main(String[] args) {
    In in = new In(args[0]);
    Digraph G = new Digraph(in);
    StdOut.println(G);
    SAP sap = new SAP(G);
    while (!StdIn.isEmpty()) {
      int v = StdIn.readInt();
      int w = StdIn.readInt();
      int length   = sap.length(v, w);
      int ancestor = sap.ancestor(v, w);
      StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
  }
}