
public class KeyIndexedCounting {

  static void sort(char[] s, int R) {

    // Count the amount of each alphabet character
    //+occurring in the string.
    final int[] count = new int[R+1];
    for (int i = 0; i < count.length; ++i) {
      count[i] = 0;
    }

    for (int i = 0; i < s.length; ++i) {
      ++count[s[i]-'a'+1];
    }

    // Calculate the cumulative sums
    for (int i = 1; i < R+1; ++i) {
      count[i] += count[i-1];
    }

    // Iterate through the original string,
    //+placing the values in an auxiliary array.
    final char[] aux = new char[s.length];
    for (int i = 0; i < s.length; ++i) {
      aux[count[s[i]-'a']++] = s[i];
    }

    // Copy contents of aux to s
    for (int i = 0; i < aux.length; ++i) {
      s[i] = aux[i];
    }
  }

  static void sortNoAux(char[] s, int R) {

    // Count the amount of each alphabet character
    //+occurring in the string.
    final int[] count = new int[R];
    for (int i = 0; i < count.length; ++i) {
      count[i] = 0;
    }

    for (int i = 0; i < s.length; ++i) {
      ++count[s[i]-'a'];
    }

    // Construct return string (fine if no
    //+associated data).
    int k = 0;
    for (int i = 0; i < count.length; ++i) {
      for (int j = 0; j < count[i]; ++j) {
        s[k++] = (char) ('a'+i);
      }
    }
  }

}
