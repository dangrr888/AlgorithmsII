import java.util.Arrays;

public class LongestRepeatedSubstring {

  public static String lrs(String s) {

    // Construct the suffix array.
    final int N = s.length();
    final String[] suffixes = new String[N];
    for (int i = 0; i < N; ++i) {
      suffixes[i] = s.substring(i);
    }

    // Sort the suffixes.
    Arrays.sort(suffixes);

    // Check adjacent entries for a common prefix,
    //+and cache the largest.
    String prefix = "";
    for (int i = 1; i < N; ++i) {

      final int minLen = Math.min(suffixes[i].length(), suffixes[i-1].length());
      int len = 0;
      for (int j = 0; j < minLen; ++j) {
        if (suffixes[i-1].charAt(j) == suffixes[i].charAt(j)) {
          ++len;
        } else {
          break;
        }
      }

      // Update lrs
      if (len > prefix.length()) {
        prefix = suffixes[i].substring(0, len);
      }
    }

    // return lrs
    return prefix;
  }
}
