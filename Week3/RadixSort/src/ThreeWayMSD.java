
public class ThreeWayMSD {

  public static void sort(String[] a) {
    ThreeWayMSD.sort(a, 0, a.length-1, 0);
  }

  private static int charAt(String s, int d) {
    return d < s.length() ? s.charAt(d) : -1;
  }

  private static void exch(String[] a, int i, int j) {
    final String tmp = a[i];
    a[i] = a[j];
    a[j] = tmp;
  }

  private static void sort(String[] a, int lo, int hi, int d) {
    if (lo >= hi) {
      return;
    }

    int lt = lo;
    int gt = hi;
    int v = ThreeWayMSD.charAt(a[lo], d);
    int i = lo + 1;
    while (i <= gt) {
      int t = ThreeWayMSD.charAt(a[i], d);
      if (t < v) {
        exch(a, lt++, i++);
      } else if (t > v) {
        exch(a, i, gt--);
      } else {
        ++i;
      }
    }

    ThreeWayMSD.sort(a, lo, lt-1, d);
    if (v != -1) {
      ThreeWayMSD.sort(a, lt, gt, d+1);
    }
    ThreeWayMSD.sort(a, gt+1, hi, d);
  }
}
