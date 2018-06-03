public class MSD {

  private static final int cutoff = 3;
  private static final int R = 256;
  private static int charAt(String s, int idx) {
    return idx < s.length() ? s.charAt(idx) : -1;
  }

  private static void exch(String[] a, int i, int j) {
    final String tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }

  private static boolean less(String s, String t, int d) {
    return s.substring(d).compareTo(t.substring(d)) < 0;
  }

  private static void sort(String[] a, int lo, int hi, int d) {
    for (int i = lo; i <= hi; ++i) {
      for (int j = i; j > lo && MSD.less(a[j], a[i], d); --j) {
        MSD.exch(a, j, j-1);
      }
    }
  }

  public static void sort(String[] a) {
    final String[] aux = new String[a.length];
    sort(a, aux, 0, a.length-1, 0);
  }

  private static void sort(String[] a, String[] aux, int lo, int hi, int d) {

    // Check for positive range
    if (lo >= hi) {
      return;
    }

    if (hi-lo+1 < cutoff) {
      MSD.sort(a, lo, hi, d);
    }

    // Initialize count array (additional space for -1).
    final int count[] = new int[R+2];
    for(int i = 0; i < R+2; ++i) {
      count[i] = 0;
    }

    // Single pass through d'th character of each String
    // in this subsection
    // incrementing count array accordingly.
    for (int i = lo; i <= hi; ++i) {
      ++count[2+MSD.charAt(a[i], d)];
    }

    // Calculate cumulates
    for (int i = 0; i < R+1; ++i) {
      count[i+1] += count[i];
    }

    // Place strings into order in aux array
    for (int i = lo; i <= hi; ++i) {
      aux[count[MSD.charAt(a[i], d)+1]++] = a[i];
    }

    // Copy content of aux back to a
    for (int i = lo; i <= hi; ++i) {
      a[i] = aux[i-lo];
    }

    // recurse for each subarray
    for (int i = 0; i < R; ++i) {
      MSD.sort(a, aux, lo+count[i], lo+count[i+1]-1, d+1);
    }


  }


}
