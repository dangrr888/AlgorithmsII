package interviewquestion3;

import static org.junit.Assert.assertEquals;

import java.util.Stack;

import org.junit.jupiter.api.Test;

class CyclicRotationsTest {

  @Test
  void test1() {
    final String[] a = {"algorithms",
                        "polynomial",
                        "sortsuffix",
                        "boyermoore",
                        "structures",
                        "minimumcut",
                        "suffixsort",
                        "stackstack",
                        "binaryheap",
                        "digraphdfs",
                        "stringsort",
                        "digraphbfs"};

    Stack<Pair> expected = new Stack<Pair>();
    expected.push(new Pair(a[2], a[6]));
    Stack<Pair> actual = CyclicRotations.findCyclicPairs(a);

    assertEquals(actual.size(), expected.size());

    for (Pair actualPair : actual) {
      final Pair expectedPair = expected.pop();
      assertEquals(expectedPair.first, actualPair.first);
      assertEquals(expectedPair.second, actualPair.second);
    }
  }
}
