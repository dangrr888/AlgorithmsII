package interviewquestion3;

public class MSD {

  private static void sortHelper(String[] a, int[] indices, int[] aux, int lo, int hi, int d) {

    final int R = 26;
    final char offset = 'a';

    // Termination condition.
    if (lo >= hi) {
      return;
    }

    // Initialize count array (+1 for cumulative total).
    final int[] count = new int[R+1];
    for (int i = lo; i <= hi; ++i) {
      ++count[a[indices[i]].charAt(d)-offset+1];
    }

    // Calculate cumulative sums.
    for (int i = 0; i < R; ++i) {
      count[i+1] += count[i];
    }

    // Place string index in its sorted position according to
    //+the dth character of its associated string.
    for (int i = lo; i <= hi; ++i) {
      aux[lo+count[a[indices[i]].charAt(d)-offset]++] = indices[i];
    }

    // Update the subgroup indices according to their
    //+sorted order.
    for (int i = lo; i <= hi; ++i) {
      indices[i] = aux[i];
    }

    // Check if we have any more characters in the strings.
    if (d+1 < a[0].length()) {

      // Iterate over subgroups in the current subgroup
      //+recursively sorting each based upon the next
      //+character.
      for (int i = 0; i < R; ++i) {
        hi = lo + count[i] - (i > 0 ? count[i-1] : 0) - 1;
        MSD.sortHelper(a, indices, aux, lo, hi, d+1);
        lo = hi+1;
      }
    }
  }

  public static void sort(String[] a, int[] indices) {
    final int N = a.length;
    final int[] aux = new int[N];
    MSD.sortHelper(a, indices, aux, 0, N-1, 0);
  }
}
