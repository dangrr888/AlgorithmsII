package interviewquestion3;

import java.util.Stack;

class Pair {
  public String first;
  public String second;

  public Pair(String first, String second)
  {
    this.first = first;
    this.second = second;
  }

  @Override
  public String toString() {
    return this.first + " - " + this.second;
  }
}

public class CyclicRotations {

  private static boolean isPermutationPair(String first, String second) {
    final int L = first.length();
    if (L == second.length()) {
      for (int i = 1; i < L; ++i) {
        if (first.equals(second.substring(i) + second.substring(0, i))) {
          return true;
        }
      }
    }

    return false;
  }

  public static Stack<Pair> findCyclicPairs(String[] a) {

    final int N = a.length;

    // Copy the strings and sort the characters within them in ascending order.
    final String[] aSorted = new String[N];
    for (int i = 0; i < N; ++i) {
      aSorted[i] = StringSort.sort(a[i]);
    }

    // Setup unmapped indices array.
    final int[] indices = new int[N];
    for (int i = 0; i < N; ++i) {
      indices[i] = i;
    }

    // Sort the indices array according the lexicographical order of the elements of aSorted.
    MSD.sort(aSorted, indices);

    // Check 'adjacent' entries of aSorted according to indices
    //+for identical entries, which are candidates for cyclic
    //+permutations.
    final Stack<Pair> pairs = new Stack<Pair>();
    int i = 0;
    while (i < N-1) {
      String first = aSorted[indices[i]];
      int j = i+1;
      while (first.equals(aSorted[indices[j]])) {
        if (CyclicRotations.isPermutationPair(a[indices[i]], a[indices[j]])) {
          pairs.push(new Pair(a[indices[i]], a[indices[j]]));
        }
        ++j;
      }
      i = j;
    }

    return pairs;
  }
}
