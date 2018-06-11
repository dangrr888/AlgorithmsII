package interviewquestion3;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class MSDTest {

  @Test
  void test() {
    final String[] a = {"algorithms",//0
                        "polynomial",//1
                        "sortsuffix",//2
                        "boyermoore",//3
                        "structures",//4
                        "minimumcut",//5
                        "suffixsort",//6
                        "stackstack",//7
                        "binaryheap",//8
                        "digraphdfs",//9
                        "stringsort",//10
                        "digraphbfs"};//11

    final int[] indices = {0,1,2,3,4,5,6,7,8,9,10,11};

    MSD.sort(a, indices);

    final int[] expected = {0,8,3,11,9,5,1,2,7,10,4,6};

    for (int i = 0; i < 12; ++i) {
      assertEquals(indices[i], expected[i]);
    }
  }

}
