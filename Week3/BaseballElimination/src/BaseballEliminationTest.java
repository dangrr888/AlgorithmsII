import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.FileNotFoundException;

import org.junit.jupiter.api.Test;

class BaseballEliminationTest {

  /*
   * @brief Test client that reads in a sports division from an
   *   input file specified in the first CL argument, and prints
   *   whether each team is mathematically eliminated as well as a
   *   certificate of elimination for each team that is eliminated.
   */
  String testClient(String filename) throws FileNotFoundException {
    final String filePath = "/Users/danielcumberbatch/eclipse-workspace/Coursera/AlgorithmsII/Week3/BaseballElimination/test/baseball/" + filename;

    final File f = new File(filePath);
    if (!f.exists()) {
      throw new FileNotFoundException("Specified file does not exist.");
    }

    final BaseballElimination division = new BaseballElimination(filePath);
    StringBuilder sb = new StringBuilder();
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        sb.append(team + " is eliminated by the subset R = {");
        for (String r : division.certificateOfElimination(team)) {
          sb.append(r + " ");
        }
        sb.append("}\n");
      } else {
        sb.append(team + " is not eliminated.\n");
      }
    }
    return sb.toString();
  }

  @Test
  public void checkFileImportThrowsException() {
      assertThrows(FileNotFoundException.class,
              ()->{
                this.testClient("foo.txt");
              });
  }

  @Test
  void test4() throws FileNotFoundException {
    final String actual = this.testClient("teams4.txt");
    final String expected =
        "Atlanta is not eliminated\n" +
        "Philadelphia is eliminated by the subset R = { Atlanta New_York }\n" +
        "New_York is not eliminated\n" +
        "Montreal is eliminated by the subset R = { Atlanta }\n";
    assertEquals(actual, expected);
  }

  @Test
  void test5() throws FileNotFoundException {
    final String actual = this.testClient("teams5.txt");
    final String expected =
      "New_York is not eliminated\n" +
      "Baltimore is not eliminated\n" +
      "Boston is not eliminated\n" +
      "Toronto is not eliminated\n" +
      "Detroit is eliminated by the subset R = { New_York Baltimore Boston Toronto }\n";
    assertEquals(actual, expected);
  }

  @Test
  void test7() throws FileNotFoundException {
    final String actual = this.testClient("teams7.txt");
    final String expected =
      "U.S.A is not eliminated\n" +
      "England is not eliminated\n" +
      "France is not eliminated\n" +
      "Germany is not eliminated\n" +
      "Ireland is eliminated by the subset R = {  }\n" +
      "Belgium is not eliminated\n" +
      "China is not eliminated\n";

    assertEquals(actual, expected);
  }

  @Test
  void test24() throws FileNotFoundException {
    final String actual = this.testClient("teams7.txt");
    final String expected =
      "Team0 is not eliminated\n" +
      "Team1 is not eliminated\n" +
      "Team2 is not eliminated\n" +
      "Team3 is not eliminated\n" +
      "Team4 is not eliminated\n" +
      "Team5 is not eliminated\n" +
      "Team6 is not eliminated\n" +
      "Team7 is not eliminated\n" +
      "Team8 is not eliminated\n" +
      "Team9 is not eliminated\n" +
      "Team10 is not eliminated\n" +
      "Team11 is not eliminated\n" +
      "Team12 is not eliminated\n" +
      "Team13 is eliminated by the subset R = {  }\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n";

    assertEquals(actual, expected);
  }

}
