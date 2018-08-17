// Import statements
import java.util.LinkedList;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

  // Private data members.
  private final Digraph dg; /* Digraph to be analysed. */
  private final ArrayList<Synset> s; // Array of sysnets in this WordNet.
  private HashMap<String, LinkedList<Integer>> nouns; /* Map of nouns in this WordNet to their respective synset id. */
  private SAP sap; /* Utility SAP for finding shortest ancestral paths. */

  // Private classes.
  private class Synset {
    public int id;
    public String[] synonyms;

    public Synset(int id) {
      if (id < 0) {
        throw new IllegalArgumentException();
      }

      this.id = id;
      this.synonyms = null;
    }
  }

  /* Class to determine if a specified Digraph */
  /* is acyclic. */
  private class DetectCycles {

    // Private data members.
    private final Digraph G;
    private boolean[] marked;
    private boolean cycleFound = false;
    private final HashSet<Integer> path = new HashSet<Integer>();

    // Public constructors.
    public DetectCycles(Digraph G) {
      this.G = G;        
      this.marked = new boolean[this.G.V()];
      for (int v = 0; v < this.G.V(); ++v) {
        this.marked[v] = false;
      }
      for (int v = 0; v < this.G.V(); ++v) {
        if (!this.marked[v]) {
          // StdOut.println("Starting new path v: " + v);
          if (this.findCycles(v, v)) {
            // StdOut.println("Found cycle: " + this.path);
            break;
          }
        }
      }
    }   

    // Private helper methods.    

    private boolean findCycles(int w, int v) {
      // StdOut.println("w: " + w + ", v: " + v);
      this.marked[w] = true;
      this.path.add(w);
      // StdOut.println("path: " + path);
      for (int x : this.G.adj(w)) {
        if (!this.marked[x]) {
          if (this.findCycles(x, v)) {
            // StdOut.println("Cycle previously found, returning...");
            return true;
          }
        } else if (x == v || this.path.contains(x)) {
          // StdOut.println("Cycle Found at " + x + "!");
          this.cycleFound = true;
          return true;
        }
      }
      // StdOut.println("Finished w: " + w);
      this.path.remove(w);
      return false;
    }

    // Public methods.
    public boolean cycleFound() {
      return this.cycleFound;
    }
  } // ! class FindCycles

  /* Class to determine if there is a reachable */
  /* vertex in a specified DAG. */
  private class FindReachableVertex {

    // Private data members.
    private int reachableVertex;
    private final Digraph rdg; // reversed Digraph
    private boolean[] marked;
    private LinkedList<Integer> rpo; /* reverse post order */

    // Public structors     
    public FindReachableVertex(Digraph dg) {

      /* Check dg_ is a DAG. */
      final DetectCycles dc = new DetectCycles(dg);
      if (dc.cycleFound()) {
        throw new IllegalArgumentException();
      }

      /* Store the reverse of the specified Digraph. */
      this.rdg = dg.reverse();

      /* Topologically sort the Digraph vertices. */
      this.findRPO();

      /* Get the first entry, v, of the topologically sorted list. */
      final int v = this.rpo.getFirst();

      /* Conduct a DFS from v. */
      this.dfs(v);

      /* Check if all vertices are marked, if so */
      /* v is a reachable vertex of dg_. */
      this.reachableVertex = this.allMarked() ? v : -1;
    }

    // Private helper methods.
    private void checkIndex(int v) {
      if (v < 0 || v >= this.rdg.V()) {
        throw new IllegalArgumentException();
      }
    }
    private void reset() {
      this.marked = new boolean[this.rdg.V()];
      for (int i = 0; i < this.marked.length; ++i) {
        this.marked[i] = false;
      }
      this.rpo = new LinkedList<Integer>();
      this.reachableVertex = -1;
    }
    private boolean allMarked() {
      for (int i = 0; i < this.marked.length; ++i) {
        if (!this.marked[i]) {
          return false;
        }
      }
      return true;
    }
    private void dfsWithOptionalSort(int s, boolean sorting) {
      /* Standard DFS */
      for (int w : this.rdg.adj(s)) {
        if (!this.marked[w]) {
          this.marked[w] = true;
          this.dfsWithOptionalSort(w, sorting);
        }
      }

      /* Optionally record the reverse post order. */
      if (sorting) {
        rpo.addFirst(s);
      }
    }       
    /* Conduct DFS from s. */
    private void dfs(int s) {

      /* Verify input. */
      this.checkIndex(s);

      /* Reset marks. */
      this.reset();

      /* Mark entry point. */
      this.marked[s] = true;

      /* Conduct DFS (no sorting) */
      this.dfsWithOptionalSort(s, false);
    }
    /* Conduct DFS and store reverse post order. */
    private void findRPO() {

      /* Reset marks and rpo container. */
      this.reset();

      /* Iterate through vertices, conducting */
      /* DFS' from each, in order to store the */
      /* reverse post order of all vertices. */
      for (int s = 0; s < this.rdg.V(); ++s) {
        if (this.marked[s]) {
          continue;
        }
        this.marked[s] = true;
        this.dfsWithOptionalSort(s, true);
      }
    }

    // Public methods.
    public boolean hasReachableVertex() {
      return this.reachableVertex != -1;
    }
    public int getReachableVertex() {
      if (!this.hasReachableVertex()) {
        throw new IllegalStateException();
      }

      return this.reachableVertex;
    }
  } // ! class FindReachableVertex
  
  // Public structors.

  /*
   * @brief Constructor taking path to two input files.
   * @param synsets Path to synsets.txt
   * @param hypernyms Path to hypernyms.txt
   */
  public WordNet(String synsets, String hypernyms) {
    if (synsets == null) {
      throw new IllegalArgumentException("null path to sysnets data file.");
    }

    if (hypernyms == null) {
      throw new IllegalArgumentException("null path to hypernyms data file.");
    }

    /* Initialize Sysnet container. */
    this.s = new ArrayList<Synset>();

    /* Get the synsets */
    this.getSynsetsFromFile(synsets);

    /* Initialize Digraph to represent synsets. */
    this.dg = new Digraph(this.s.size());

    /* Get hypernyms to represent Graph edges. */
    this.getHypernymsFromFile(hypernyms);

    /* Check if constructed Digraph is a rooted DAG. */
    if (!this.isDAG(this.dg)) {
      throw new IllegalArgumentException("Digraph is not a DAG.");
    }

    if (!this.isRooted(this.dg)) {
      throw new IllegalArgumentException("Digraph is a DAG but is not rooted.");
    }
  }

  // Private methods.
  /* Parse data file. */
  private void parseFile(String filePath, boolean synsets) {
    final In fileIn = new In(filePath);
    while (fileIn.hasNextLine()) {
      final String line = fileIn.readLine();

      final String[] lineComponents = line.split(",");

      /* Get synset id */
      final int sid = Integer.parseInt(lineComponents[0]);

      if (synsets) {
        /* Get Synset nouns */
        Synset ss = new Synset(sid);                            
        final String snouns = lineComponents[1];
        ss.synonyms = snouns.split(" ");

        /* Cache new Synset. */
        this.s.add(ss);                         
      } else {
        /* Get hypernym ids */
        for (int i = 1; i < lineComponents.length; ++i) {
          final int hid = Integer.parseInt(lineComponents[i]);

          /* Add edge sid->hid to Digraph. */
          this.dg.addEdge(sid, hid);
        }
      }
    }
    fileIn.close();
  }
  /* Parse synsets file. */
  /* e.g., 36,AND_circuit AND_gate,a circuit in a computer that fires only when all of its inputs fire */
  private void getSynsetsFromFile(String synsetFilePath) {
    this.parseFile(synsetFilePath, true);
  }
  /* Parse hypernyms file. */
  /* e.g., 164,21012,56099 */
  private void getHypernymsFromFile(String hypernymsFilePath) {           
    this.parseFile(hypernymsFilePath, false);
  }
  private boolean isRooted(Digraph dg) {
    final FindReachableVertex rv = new FindReachableVertex(dg);

    if (rv.hasReachableVertex()) {
      final int rvIndex = rv.getReachableVertex();
      final boolean rooted = dg.outdegree(rvIndex) == 0;
      if (rooted) {
        StdOut.println("Rooted at: " + rvIndex);
      }
      return rooted;
    }

    return false;
  }
  private boolean isDAG(Digraph dg) {
    final DetectCycles fc = new DetectCycles(dg);
    return !fc.cycleFound();
  }

  private void initializeNouns() {
    if (this.nouns == null) {
      this.nouns = new HashMap<String, LinkedList<Integer>>();
      for (Synset ss : this.s) {
        for (String noun : ss.synonyms) {
          LinkedList<Integer> ll = this.nouns.get(noun);
          if (ll == null) {
            ll = new LinkedList<Integer>();
          }
          ll.add(ss.id);
          this.nouns.put(noun, ll);
        }
      }
    }
  }
  private void initializeSAP() {
    if (this.sap == null) {
      this.sap = new SAP(this.dg);
    }
  }

  /*
   * @brief Returns a collection of all nouns in this WordNet.
   * @return A collection of all nouns in this WordNet.
   */
  public Iterable<String> nouns() {
    this.initializeNouns();
    return this.nouns.keySet();
  }

  /*
   * @brief Returns if a specified word is a noun in this WordNet.
   * @param word The word to be checked.
   * @retval true If word is a noun in this WordNet.
   * @retval false If word is not a noun in this WordNet.
   */
  public boolean isNoun(String word) {
    if (word == null) {
      throw new IllegalArgumentException();
    }

    this.initializeNouns();
    return this.nouns.containsKey(word);
  }

  /*
   * @brief Returns the distance between two specified
   *    nouns in this WordNet.
   * @param nounA The first noun to be considered.
   * @param nounB The second noun to be considered.
   * @return The number of edges on the shortest path
   *    between the two specified nouns via their
   *    nearest common ancestor.
   */
  public int distance(String nounA, String nounB) {
    if (!this.isNoun(nounA)) {
      throw new IllegalArgumentException();
    }

    if (!this.isNoun(nounB)) {
      throw new IllegalArgumentException();
    }

    this.initializeSAP();

    return this.sap.length(this.nouns.get(nounA), this.nouns.get(nounB));
  }

  /*
   * @brief Return the synset that is the common ancestor of
   *    two specified nouns in a shortest ancestral path in
   *    this WordNet.
   * @param nounA The first noun to be considered.
   * @param nounB The second noun to be considered.
   * @return The synset that is the common ancestor of
   *    nounA and nounB in a shortest ancestral path in this
   *    WordNet.
   */
  public String sap(String nounA, String nounB) {
    if (!this.isNoun(nounA)) {
      throw new IllegalArgumentException();
    }

    if (!this.isNoun(nounB)) {
      throw new IllegalArgumentException();
    }

    this.initializeSAP();

    final int idx = this.sap.ancestor(this.nouns.get(nounA), this.nouns.get(nounB));

    String ret = null;
    final String[] sapSyns = this.s.get(idx).synonyms;

    if (sapSyns.length != 0) {
      final StringBuilder sb = new StringBuilder();
      for (int i = 0; i < sapSyns.length; ++i) {
        if (i > 0) {
          sb.append(" ");
        }
        sb.append(sapSyns[i]);
      }
      ret = sb.toString();
    }

    return ret;
  }

  /*
   *  @brief Optional unit testing.
   *  @param args Command line arguments.
   */
  public static void main(String[] args) {
    final WordNet wn = new WordNet(args[0], args[1]);
    System.out.println(wn.distance("a", "a"));
    System.out.println(wn.sap("a", "a"));
  }
} // ! class WordNet