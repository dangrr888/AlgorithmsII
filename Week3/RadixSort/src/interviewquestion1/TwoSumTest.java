package interviewquestion1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.Test;

class TwoSumTest {

  @Test
  void convertIntToStringTest1() {
    final String s = Converter.PositiveLongToString(1);
    assertEquals(s, "000000000000000000000000000000000000000000000000000000000000001");
  }

  @Test
  void convertStringToLongTest1() {
    final long l = Converter.StringToPositiveLong("000000000000000000000000000000000000000000000000000000000000001");
    assertEquals(1L, l);
  }


  @Test
  void LSDSortTest1() {
    final String[] s = {"100",
                        "099",
                        "098",
                        "097",
                        "096",
                        "095",
                        "094",
                        "093",
                        "092",
                        "091",
                        "090",
                        "089"};
   LSD.sort(s, s[0].length());
   final String[] expected = { "089",
                               "090",
                               "091",
                               "092",
                               "093",
                               "094",
                               "095",
                               "096",
                               "097",
                               "098",
                               "099",
                               "100"};

   for (int i = 0; i < s.length; ++i) {
     assertEquals(s[i], expected[i]);
   }
  }

  @Test
  void TwoSumTest1() {
    final long[] l = {1,4,5,6,1,2,3,1,10};
    assertTrue(TwoSum.ts(l, 9));
  }

  @Test
  void TwoSumTest2() {
    final long[] l = {1,4,-5,6,1,2,3,1,10};
    assertTrue(TwoSum.ts(l, -2));
  }

  @Test
  void TwoSumTest3() {
    final long[] l = {1,4,-5,6,1,-8,66,1,-100};
    assertTrue(TwoSum.ts(l, 61));
  }

}
