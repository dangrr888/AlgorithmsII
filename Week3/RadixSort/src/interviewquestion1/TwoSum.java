package interviewquestion1;

class LSD {

  private static void sort(String[] a, String[] aux, int W) {
    final int R = 10;
    final int N = a.length;

    for (int d = W-1; d >= 0; --d) {
      final int[] count  = new int[R+1];
      for (int i = 0; i < count.length; ++i) {
        count[i] = 0;
      }

      // Calculate occurrences.
      for (int i = 0; i < N; ++i) {
        ++count[a[i].charAt(d) - '0' + 1];
      }

      // Calculate cumulates
      for (int i = 0; i < R; ++i) {
        count[i+1] += count[i];
      }

      // Place strings in order in the
      // auxiliary array.
      for (int i = 0; i < N; ++i) {
        aux[count[a[i].charAt(d)-'0']++] = a[i];
      }

      // Copy the aux array into a
      for (int i = 0; i < N; ++i) {
        a[i] = aux[i];
      }
    }
  }

  public static void sort(String[] a, int W) {
    final String[] aux = new String[a.length];
    LSD.sort(a, aux, W);
  }
}

class Converter {

  public static String PositiveLongToString(long l) {
    final StringBuilder sb = new StringBuilder();
    for (long b = (1L << 62); b >= 1; b >>= 1) {
      if ((b & l) != 0) {
        sb.append('1');
      } else {
        sb.append('0');
      }
    }
    return sb.toString();
  }

  public static long StringToPositiveLong(String s) {
    long ret = 0L;
    long l = 1L;
    for (int i = s.length()-1; i >= 0; --i) {
      if (s.charAt(i) == '1') {
        ret += l;
      }
      l <<= 1;
    }
    return ret;
  }
}

public class TwoSum {

  public static boolean ts(long[] a, long T) {

    final int n = a.length;

    // Separate the negative from the positive
    // integers.
    int numNeg = 0;
    for (int i = 0; i < n; ++i) {
      if (a[i] < 0) {
        ++numNeg;
      }
    }

    int posIdx = 0;
    int negIdx = 0;
    final String[] pos = new String[n-numNeg];
    final String[] neg = new String[numNeg];
    for (int i = 0; i < n; ++i) {
      if (a[i] < 0) {
        final Long l = new Long(-a[i]);
        neg[negIdx++] = Converter.PositiveLongToString(l);
      } else {
        final Long l = new Long(a[i]);
        pos[posIdx++] = Converter.PositiveLongToString(l);
      }
    }

    LSD.sort(pos, 63);
    LSD.sort(neg, 63);

    int j = 0;
    for (int i = neg.length-1; i >= 0; --i) {
      a[j++] = -1L*Converter.StringToPositiveLong(neg[i]);
    }
    for (int i = 0; i < pos.length; ++i) {
      a[j++] = Converter.StringToPositiveLong(pos[i]);
    }

    int lo = 0;
    int hi = n-1;
    long sum = 0;
    while (lo < hi) {
      sum = a[lo] + a[hi];
      if (sum == T) {
        return true;
      } else if (sum > T) {
        --hi;
      } else {
        ++lo;
      }
    }
    return false;
  }
}
