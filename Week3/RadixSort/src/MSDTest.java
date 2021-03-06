import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MSDTest {

  @Test
  void test1() {
    final String[] s = {"dab",
                        "add",
                        "cab",
                        "fad",
                        "fee",
                        "bad",
                        "dad",
                        "bee",
                        "fed",
                        "bed",
                        "ebb",
                        "ace"};
   MSD.sort(s);
   final String[] expected = { "ace",
                               "add",
                               "bad",
                               "bed",
                               "bee",
                               "cab",
                               "dab",
                               "dad",
                               "ebb",
                               "fad",
                               "fed",
                               "fee"};

   for (int i = 0; i < s.length; ++i) {
     assertEquals(s[i], expected[i]);
   }
  }
}
