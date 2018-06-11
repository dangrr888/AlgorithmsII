package interviewquestion3;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

class StringSortTest {

  @Test
  void StringSortTest1() {
    final String s = "algorithms";
    final String actual = StringSort.sort(s);
    final String expected = "aghilmorst";
    assertEquals(actual, expected);
  }

}
