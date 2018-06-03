import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

class KeyIndexedCountingTest {

  private static void compareSorts(String s) {
    final char[] c1 = s.toCharArray();
    Arrays.sort(c1);

    final char[] c2 = s.toCharArray();
    KeyIndexedCounting.sort(c2, 26);

    assertEquals(c1.length, c2.length);

    for (int i = 0; i < c1.length; ++i) {
      assertEquals(c1[i], c2[i]);
    }
  }

  private static void compareSortsNoAux(String s) {
    final char[] c1 = s.toCharArray();
    Arrays.sort(c1);

    final char[] c2 = s.toCharArray();
    KeyIndexedCounting.sortNoAux(c2, 26);

    assertEquals(c1.length, c2.length);

    for (int i = 0; i < c1.length; ++i) {
      assertEquals(c1[i], c2[i]);
    }
  }


  @Test
  void test1() {
    final String s = "djahksdhakshdkahskd";
    KeyIndexedCountingTest.compareSorts(s);
    KeyIndexedCountingTest.compareSortsNoAux(s);
  }


  @Test
  void test2() {
    final String s = "a";
    KeyIndexedCountingTest.compareSorts(s);
    KeyIndexedCountingTest.compareSortsNoAux(s);
  }

  @Test
  void test3() {
    final String s = "aaaaaaaaaaaaaaaa";
    KeyIndexedCountingTest.compareSorts(s);
    KeyIndexedCountingTest.compareSortsNoAux(s);
  }
}
