// Import statements.
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/* Class to determine the outcast of a collection
 * of specified nouns in a specified WordNet.
 */
public class Outcast {

  // Private data members.
  private final WordNet wordnet;

  // Public structors.

  /*
   * @brief constructor taking a WordNet object.
   * @param wordnet The Wordnet to be analysed.
   */
  public Outcast(WordNet wordnet) {
    this.wordnet = wordnet;
  }

  /*
   * @brief Return the outcast from a collection of nouns.
   * @pre The nouns must be from the WordNet used to
   *    initialize this Outcast Object, and there must
   *    be at least two nouns in the input array.
   * @param nouns An array of nouns to be analyzed.
   * @return An outcast of the supplied nouns.
   */
  public String outcast(String[] nouns) {
    final int n = nouns.length;

    if (n < 2) {
      throw new IllegalArgumentException("Usage: <synsets.txt> <hypernyms.txt> <outcast.txt>");
    }

    for (String noun : nouns) {
      if (!this.wordnet.isNoun(noun)) {
        throw new IllegalArgumentException(noun + " is not a noun from the WordNet!");
      }
    }

    int[][] distances = new int[n][n];
    for (int i = 0; i < n; ++i) {
      for (int j = i+1; j < n; ++j) { // top right half
        distances[i][j] = this.wordnet.distance(nouns[i], nouns[j]);
      }
    }

    int outcasti = -1;
    int outcastd = 0;
    for (int i = 0; i < n; ++i) {
      int d = 0;

      for (int j = i+1; j < n; ++j) {
        d += distances[i][j];
      }

      for (int j = 0; j < i; ++j) {
        d += distances[j][i];
      }

      if (d > outcastd) {
        outcasti = i;
        outcastd = d;
      }
    }

    return nouns[outcasti];
  }
  /*
   * Optional test client.
   * Usage: <synsets.txt> <hypernyms.txt> <outcast.txt>
   */
  public static void main(String[] args) {
    WordNet wordnet = new WordNet(args[0], args[1]);
    final Outcast outcast = new Outcast(wordnet);
    for (int t = 2; t < args.length; ++t) {
      final In in = new In(args[t]);
      final String[] nouns = in.readAllStrings();
      StdOut.println(args[t] + ": " + outcast.outcast(nouns));
    }
  }
}