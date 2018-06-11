package interviewquestion3;

public class StringSort {

  private static int R = 26;
  private static char offset = 'a';

  private static String sortHelper(String a, char[] aux) {

    // Initialize count array (+1 for cumulative sum).
    final int[] count = new int[R+1];
    for (int i = 0; i < a.length(); ++i) {
      ++count[a.charAt(i) - offset + 1];
    }

    // Calculate cumulative sums.
    for (int i = 0; i < R; ++i) {
      count[i+1] += count[i];
    }

    // Populate auxiliary array with characters
    //+placed in their sorted positions, incrementing
    //+count as we go for each subgroup.
    for (int i = 0; i < a.length(); ++i) {
      final char ch = a.charAt(i);
      aux[count[ch-offset]++] = ch;
    }

    // Return sorted string.
    return new String(aux);
  }

  public static String sort(String a) {
    final int N = a.length();
    final char[] aux = new char[N];
    return StringSort.sortHelper(a, aux);
  }
}
