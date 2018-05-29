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
  void test4b() throws FileNotFoundException {
    final String actual = this.testClient("teams4b.txt");
    final String expected =
        "Gryffindor is not eliminated\n" +
        "Hufflepuff is not eliminated\n" +
        "Ravenclaw is not eliminated\n" +
        "Slytherin is not eliminated\n";
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
  void test5a() throws FileNotFoundException {
    final String actual = this.testClient("teams5a.txt");
    final String expected =
      "New_York is not eliminated\n" +
      "Baltimore is not eliminated\n" +
      "Boston is not eliminated\n" +
      "Toronto is not eliminated\n" +
      "Detroit is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test5b() throws FileNotFoundException {
    final String actual = this.testClient("teams5b.txt");
    final String expected =
      "New_York is not eliminated\n" +
      "Baltimore is not eliminated\n" +
      "Boston is not eliminated\n" +
      "Toronto is not eliminated\n" +
      "Detroit is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test5c() throws FileNotFoundException {
    final String actual = this.testClient("teams5c.txt");
    final String expected =
      "New_York is not eliminated\n" +
      "Philadelphia is not eliminated\n" +
      "Atlanta is not eliminated\n" +
      "Florida is not eliminated\n" +
      "Washington is not eliminated\n";
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
      "Ireland is not eliminated\n" +
      "Belgium is not eliminated\n" +
      "China is not eliminated\n";

    assertEquals(actual, expected);
  }

  @Test
  void test8() throws FileNotFoundException {
    final String actual = this.testClient("teams8.txt");
    final String expected =
        "Brown is not eliminated\n" +
        "Columbia is not eliminated\n" +
        "Cornell is not eliminated\n" +
        "Dartmouth is not eliminated\n" +
        "Penn is not eliminated\n" +
        "Harvard is not eliminated\n" +
        "Yale is not eliminated\n" +
        "Princeton is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test10() throws FileNotFoundException {
    final String actual = this.testClient("teams10.txt");
    final String expected =
        "Atlanta is not eliminated\n" +
        "Boston is not eliminated\n" +
       "Chicago is not eliminated\n" +
     "Cleveland is not eliminated\n" +
        "Dallas is not eliminated\n" +
        "Denver is not eliminated\n" +
       "Detroit is not eliminated\n" +
  "Golden_State is not eliminated\n" +
       "Houston is not eliminated\n" +
       "Indiana is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test12() throws FileNotFoundException {
    final String actual = this.testClient("teams12.txt");
    final String expected =
        "Poland is not eliminated\n" +
        "Russia is not eliminated\n" +
        "Brazil is not eliminated\n" +
        "Iran is not eliminated\n" +
        "Italy is not eliminated\n" +
        "Cuba is not eliminated\n" +
        "Argentina is not eliminated\n" +
        "USA is not eliminated\n" +
        "Japan is not eliminated\n" +
        "Serbia is not eliminated\n" +
        "Egypt is not eliminated\n" +
        "China is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test12_allgames() throws FileNotFoundException {
    final String actual = this.testClient("teams12-allgames.txt");
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
      "Team11 is not eliminated\n";
      assertEquals(actual, expected);
    }

  @Test
  void test24() throws FileNotFoundException {
    final String actual = this.testClient("teams24.txt");
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
      "Team13 is not eliminated\n" +
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

  @Test
  void test29() throws FileNotFoundException {
    final String actual = this.testClient("teams29.txt");
    final String expected =
        "Atlanta is not eliminated\n" +
        "Boston is not eliminated\n" +
       "Chicago is not eliminated\n" +
     "Cleveland is not eliminated\n" +
        "Dallas is not eliminated\n" +
        "Denver is not eliminated\n" +
       "Detroit is not eliminated\n" +
  "Golden_State is not eliminated\n" +
       "Houston is not eliminated\n" +
       "Indiana is not eliminated\n" +
   "LA_Clippers is not eliminated\n" +
     "LA_Lakers is not eliminated\n" +
       "Memphis is not eliminated\n" +
         "Miami is not eliminated\n" +
     "Milwaukee is not eliminated\n" +
     "Minnesota is not eliminated\n" +
    "New_Jersey is not eliminated\n" +
   "New_Orleans is not eliminated\n" +
      "New_York is not eliminated\n" +
       "Orlando is not eliminated\n" +
  "Philadelphia is not eliminated\n" +
       "Phoenix is not eliminated\n" +
       "Portand is not eliminated\n" +
    "Sacramento is not eliminated\n" +
   "San_Antonio is not eliminated\n" +
       "Seattle is not eliminated\n" +
       "Toronto is not eliminated\n" +
          "Utah is not eliminated\n" +
    "Washington is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test30() throws FileNotFoundException {
    final String actual = this.testClient("teams30.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n";
    assertEquals(actual, expected);
  }

  @Test
  void test32() throws FileNotFoundException {
    final String actual = this.testClient("teams32.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n";
      assertEquals(actual, expected);
  }

  @Test
  void test36() throws FileNotFoundException {
    final String actual = this.testClient("teams36.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n" +
      "Team32 is not eliminated\n" +
      "Team33 is not eliminated\n" +
      "Team34 is not eliminated\n" +
      "Team35 is not eliminated\n";
      assertEquals(actual, expected);
  }

  @Test
  void test42() throws FileNotFoundException {
    final String actual = this.testClient("teams42.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n" +
      "Team32 is not eliminated\n" +
      "Team33 is not eliminated\n" +
      "Team34 is not eliminated\n" +
      "Team35 is not eliminated\n" +
      "Team36 is not eliminated\n" +
      "Team37 is not eliminated\n" +
      "Team38 is not eliminated\n" +
      "Team39 is not eliminated\n" +
      "Team40 is not eliminated\n" +
      "Team41 is not eliminated\n";
      assertEquals(actual, expected);
  }

  @Test
  void test48() throws FileNotFoundException {
    final String actual = this.testClient("teams48.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n" +
      "Team32 is not eliminated\n" +
      "Team33 is not eliminated\n" +
      "Team34 is not eliminated\n" +
      "Team35 is not eliminated\n" +
      "Team36 is not eliminated\n" +
      "Team37 is not eliminated\n" +
      "Team38 is not eliminated\n" +
      "Team39 is not eliminated\n" +
      "Team40 is not eliminated\n" +
      "Team41 is not eliminated\n" +
      "Team42 is not eliminated\n" +
      "Team43 is not eliminated\n" +
      "Team44 is not eliminated\n" +
      "Team45 is not eliminated\n" +
      "Team46 is not eliminated\n" +
      "Team47 is not eliminated\n";
      assertEquals(actual, expected);
  }

  @Test
  void test50() throws FileNotFoundException {
    final String actual = this.testClient("teams50.txt");
    final String expected =
        "turkey is not eliminated\n" +
            "guineafowl is not eliminated\n" +
            "quail is not eliminated\n" +
            "grouse is not eliminated\n" +
            "ptarmigan is not eliminated\n" +
            "snowcock is not eliminated\n" +
            "partridge is not eliminated\n" +
            "pheasant is not eliminated\n" +
            "chicken is not eliminated\n" +
            "peacock is not eliminated\n" +
            "dove is not eliminated\n" +
            "goose is not eliminated\n" +
            "duck is not eliminated\n" +
            "swan is not eliminated\n" +
            "mallard is not eliminated\n" +
            "pintail is not eliminated\n" +
            "eider is not eliminated\n" +
            "penguin is not eliminated\n" +
            "loon is not eliminated\n" +
            "albatross is not eliminated\n" +
            "flamingo is not eliminated\n" +
            "stork is not eliminated\n" +
            "ibis is not eliminated\n" +
            "spoonbill is not eliminated\n" +
            "heronegret is not eliminated\n" +
            "booby is not eliminated\n" +
            "cormorant is not eliminated\n" +
            "vulture is not eliminated\n" +
            "falcon is not eliminated\n" +
            "condor is not eliminated\n" +
            "osprey is not eliminated\n" +
            "buzzard is not eliminated\n" +
            "kite is not eliminated\n" +
            "eagle is not eliminated\n" +
            "hawk is not eliminated\n" +
            "harrier is not eliminated\n" +
            "sparrow is not eliminated\n" +
            "rail is not eliminated\n" +
            "coot is not eliminated\n" +
            "crane is not eliminated\n" +
            "sandpiper is not eliminated\n" +
            "gull is not eliminated\n" +
            "tern is not eliminated\n" +
            "razorbill is not eliminated\n" +
            "parrot is not eliminated\n" +
            "cockatoo is not eliminated\n" +
            "macaw is not eliminated\n" +
            "cuckoo is not eliminated\n" +
            "roadrunner is not eliminated\n" +
            "owl is not eliminated\n";
        assertEquals(actual, expected);
  }

  @Test
  void test54() throws FileNotFoundException {
    final String actual = this.testClient("teams54.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n" +
      "Team32 is not eliminated\n" +
      "Team33 is not eliminated\n" +
      "Team34 is not eliminated\n" +
      "Team35 is not eliminated\n" +
      "Team36 is not eliminated\n" +
      "Team37 is not eliminated\n" +
      "Team38 is not eliminated\n" +
      "Team39 is not eliminated\n" +
      "Team40 is not eliminated\n" +
      "Team41 is not eliminated\n" +
      "Team42 is not eliminated\n" +
      "Team43 is not eliminated\n" +
      "Team44 is not eliminated\n" +
      "Team45 is not eliminated\n" +
      "Team46 is not eliminated\n" +
      "Team47 is not eliminated\n" +
      "Team48 is not eliminated\n" +
      "Team49 is not eliminated\n" +
      "Team50 is not eliminated\n" +
      "Team51 is not eliminated\n" +
      "Team52 is not eliminated\n" +
      "Team53 is not eliminated\n";
      assertEquals(actual, expected);
  }

  @Test
  void test60() throws FileNotFoundException {
    final String actual = this.testClient("teams60.txt");
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
      "Team13 is not eliminated\n" +
      "Team14 is not eliminated\n" +
      "Team15 is not eliminated\n" +
      "Team16 is not eliminated\n" +
      "Team17 is not eliminated\n" +
      "Team18 is not eliminated\n" +
      "Team19 is not eliminated\n" +
      "Team20 is not eliminated\n" +
      "Team21 is not eliminated\n" +
      "Team22 is not eliminated\n" +
      "Team23 is not eliminated\n" +
      "Team24 is not eliminated\n" +
      "Team25 is not eliminated\n" +
      "Team26 is not eliminated\n" +
      "Team27 is not eliminated\n" +
      "Team28 is not eliminated\n" +
      "Team29 is not eliminated\n" +
      "Team30 is not eliminated\n" +
      "Team31 is not eliminated\n" +
      "Team32 is not eliminated\n" +
      "Team33 is not eliminated\n" +
      "Team34 is not eliminated\n" +
      "Team35 is not eliminated\n" +
      "Team36 is not eliminated\n" +
      "Team37 is not eliminated\n" +
      "Team38 is not eliminated\n" +
      "Team39 is not eliminated\n" +
      "Team40 is not eliminated\n" +
      "Team41 is not eliminated\n" +
      "Team42 is not eliminated\n" +
      "Team43 is not eliminated\n" +
      "Team44 is not eliminated\n" +
      "Team45 is not eliminated\n" +
      "Team46 is not eliminated\n" +
      "Team47 is not eliminated\n" +
      "Team48 is not eliminated\n" +
      "Team49 is not eliminated\n" +
      "Team50 is not eliminated\n" +
      "Team51 is not eliminated\n" +
      "Team52 is not eliminated\n" +
      "Team53 is not eliminated\n" +
      "Team54 is not eliminated\n" +
      "Team55 is not eliminated\n" +
      "Team56 is not eliminated\n" +
      "Team57 is not eliminated\n" +
      "Team58 is not eliminated\n" +
      "Team59 is not eliminated\n";
      assertEquals(actual, expected);
  }

}
