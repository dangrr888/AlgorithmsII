package interviewquestion2;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import interviewquestion2.AmericanFlagSort;

class AmericanFlagSortTest {

  @Test
  void test() {
    final int[] a = {4,5,6,3,2,1,3,2,7};
    final int[] e = {1,2,2,3,3,4,5,6,7};
    AmericanFlagSort.sort(a);

    assertEquals(a.length, e.length);
    for (int i = 0; i < a.length; ++i) {
      assertEquals(a[i], e[i]);
    }
  }

}
