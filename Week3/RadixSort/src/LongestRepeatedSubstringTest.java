import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class LongestRepeatedSubstringTest {

  @Test
  void test() {
    final String s = "abcdefgkkkkkkkkabcdefg";
    final String lrs = LongestRepeatedSubstring.lrs(s);

    assertEquals(lrs, "abcdefg");
  }
}
