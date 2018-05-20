import edu.princeton.cs.algs4.StdOut;

public class BaseballElimination {

  /*
   * @brief Create a baseball division from a specified
   *   filename in the following format:
   *   * Number of teams in the division, n
   *   * n lines, each containing:
   *    ** A team name (no internal whitespace characters)
   *    ** The number of wins
   *    ** The number of losses
   *    ** The number of remaining games against each team
   *         in the division.
   *
   *  * e.g.,
   *    ** 4
   *    ** Atlanta       83 71  8  0 1 6 1
   *    ** Philadelphia  80 79  3  1 0 0 2
   *    ** New_York      78 78  6  6 0 0 0
   *    ** Montreal      77 82  3  1 2 0 0
   *
   *  * n must be >= 1
   *  * Note that a team may play teams outside the
   *    division, hence the number of remaining games
   *    of a particular team may exceed the sum of the
   *    specified remaining games against teams within
   *    its division.
   *
   * @param filename The specified filename.
   */
  public BaseballElimination(String filename) {
    if (filename == null) {
      throw new IllegalArgumentException("Null argument.");
    }
  }

  /*
   * @brief Return the number of teams.
   * @return The number of teams.
   */
  public int numberOfTeams() {
    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return an iterable object containing
   *   all teams.
   * @return An iterable object containing all
   *   teams.
   */
  public Iterable<String> teams() {
    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return the number of wins of the specified
   *   team.
   * @param team The specified team.
   * @return The number of wins of the specified team.
   */
  public int wins(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return the number of losses of the specified
   *   team.
   * @param team The specified team.
   * @return The number of losses of the specified team.
   */
  public int losses(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return the number of remaining games of the specified
   *   team.
   * @param team The specified team.
   * @return The number of remaining games of the specified team.
   */
  public int remaining(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return the number of remaining games between
   *   the specified teams.
   * @param team1 The first team.
   * @param team2 The second team.
   * @return The number of remaining games between
   *   the specified teams.
   */
  public int against(String team1, String team2) {
    if (team1 == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    if (team2 == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return if the specified team is eliminated.
   * @param team The specified team.
   * @return If the specified team is eliminated.
   */
  public boolean isEliminated(String team) {
    if (team == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Return the teams that eliminate the specified team.
   * @param team The specified team.
   * @retval The teams that eliminate the specified team.
   * @retval null If the specified team is not eliminated.
   */
  public Iterable<String> certificateOfElimination(String team) {

    if (team == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    throw new UnsupportedOperationException();
  }

  /*
   * @brief Test client that reads in a sports division from an
   *   input file specified in the first CL argument, and prints
   *   whether each team is mathematically eliminated as well as a
   *   certificate of elimination for each team that is eliminated.
   */
  public static void main(String[] args) {
    final BaseballElimination division = new BaseballElimination(args[0]);
    for (String team : division.teams()) {
      if (division.isEliminated(team)) {
        StdOut.print(team + " is eliminated by the subset R = {");
        for (String r : division.certificateOfElimination(team)) {
          StdOut.print(r + " ");
        }
        StdOut.println("}");
      } else {
        StdOut.println(team + " is not eliminated.");
      }
    }
  }
}
