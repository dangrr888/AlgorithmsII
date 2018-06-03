
public class LSD {

  public static void sort(String[] a, int W) {

    final int R = 256;
    final int N = a.length;
    String[] aux = new String[N];

    for (int d = W-1; d >= 0; --d) {

      final int count[] = new int[R+1];
      for (int i = 0; i < count.length; ++i) {
        count [i]= 0;
      }

      for (int i = 0; i < N; ++i) {
        ++count[a[i].charAt(d)+1];
      }

      for (int i = 0; i < R; ++i) {
        count[i+1] += count[i];
      }

      for (int i = 0; i < N; ++i) {
        aux[count[a[i].charAt(d)]++] = a[i];
      }

      for (int i = 0; i < N; ++i) {
        a[i] = aux[i];
      }
    }
  }
}
