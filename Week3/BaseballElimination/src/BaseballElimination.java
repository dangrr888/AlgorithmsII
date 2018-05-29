import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {

  private final HashMap<String, Integer> map;
  private final HashSet<Integer>[] R;
  private final int numTeams;
  private final String[] teams;
  private final int[] wins;
  private final int[] losses;
  private final int[] remaining;
  private final int[][] remainingInDivision;


  /*
   * @brief Create a baseball division from a specified
   *   filename in the following format:
   *   * Number of teams in the division, n
   *   * n lines, each containing:
   *    ** A team name (no internal whitespace characters)
   *    ** The number of wins
   *    ** The number of losses
   *    ** The number of remaining games
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

    // Check input.
    if (filename == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    // Initialize scanner
    final Scanner sc = new Scanner(filename);

    this.numTeams = sc.nextInt();
    if (this.numTeams < 1) {
      sc.close();
      throw new IllegalArgumentException("#teams must be >= 1");
    }

    // Initialize data members.
    this.map = new HashMap<String, Integer>();
    this.R = new HashSet[this.numTeams];
    this.teams = new String[this.numTeams];
    this.wins = new int[this.numTeams];
    this.losses = new int[this.numTeams];
    this.remaining = new int[this.numTeams];
    this.remainingInDivision = new int[this.numTeams][];
    for (int i = 0; i < this.numTeams; ++i) {
      this.remainingInDivision[i] = new int[this.numTeams];
      this.R[i] = new HashSet<Integer>();
    }

    // Read data for each team.
    for (int i = 0; i < this.numTeams; ++i) {
      this.teams[i] = sc.next();
      this.wins[i] = sc.nextInt();
      this.losses[i] = sc.nextInt();
      this.remaining[i] = sc.nextInt();
      for (int j = 0; j < this.numTeams; ++j) {
        this.remainingInDivision[i][j] = sc.nextInt();
      }
      this.map.put(this.teams[i], i);
    }

    // Close scanner.
    sc.close();

    // Determine eliminated teams.
    for (int i = 0; i < this.numTeams; ++i) {
      // Determine contract of elimination for team i:

      /*
       * Create the flow network.
       * Node 0: Source Node
       * Node 1 <= n <= N*(N-1)/2: Nodes representing games between teams 0-1, 0-2, 0-3, etc (NOT DOUBLE COUNTING)
       * Node N*(N-1)/2 + 1 <= n <= N*(N-1)/2 + N: Nodes representing each team from 0 to N-1
       * Node N*(N-1)/2+N+1: Target Node.
       * Note: N*(N-1)/2+N+2 nodes in total.
       */
      final FlowNetwork fn = new FlowNetwork(this.numTeams*(this.numTeams-1)/2+this.numTeams+2);

      int gameNodeIdx = 1;
      for (int j = 0; j < this.numTeams; ++j) {
        for (int k = j+1; k < this.numTeams; ++k) {

          /*
           * Add edges from source node to game nodes.
           */
          fn.addEdge(new FlowEdge(0, gameNodeIdx, this.remainingInDivision[j][k], 0.0));

          /*
           * Add edges from game nodes to team nodes
           */
          fn.addEdge(new FlowEdge(gameNodeIdx, this.numTeams*(this.numTeams-1)/2 +1+j, Double.POSITIVE_INFINITY, 0.0));
          fn.addEdge(new FlowEdge(gameNodeIdx, this.numTeams*(this.numTeams-1)/2 +1+k, Double.POSITIVE_INFINITY, 0.0));
          ++gameNodeIdx;
        }
      }

      /*
       * Add edges from team nodes to target node
       */
      for (int j = 0; j < this.numTeams; ++j) {
        if (i == j) {
          // Also set edge from team i to target to 0.
          fn.addEdge(new FlowEdge(this.numTeams*(this.numTeams-1)/2 + 1 + j,
              this.numTeams*(this.numTeams-1)/2 + this.numTeams + 1,
              0.0));
        } else {
          // Check team j doesn't trivially eliminate i. If so, then set capacity of edge from team i to target to 0
          final double capacity = this.wins[i] + this.remaining[i] - this.wins[j];
          fn.addEdge(new FlowEdge(this.numTeams*(this.numTeams-1)/2 + 1 + j,
                                  this.numTeams*(this.numTeams-1)/2 + this.numTeams + 1,
                                  capacity > 0.0 ? capacity : 0.0));
          }
      }

      final FordFulkerson ff = new FordFulkerson(fn, 0, this.numTeams*(this.numTeams-1)/2+this.numTeams+1);
      for (int j = 0; j < this.numTeams; ++j) {
        if (i == j) {
          continue;
        }
        if (ff.inCut(this.numTeams*(this.numTeams-1)/2 +1+j)) {
          this.R[i].add(j);
        }
      }

    }
  }

  /*
   * @brief Return the number of teams.
   * @return The number of teams.
   */
  public int numberOfTeams() {
    return this.numTeams;
  }

  /*
   * @brief Return an iterable object containing
   *   all teams.
   * @return An iterable object containing all
   *   teams.
   */
  public Iterable<String> teams() {
    final Stack<String> st = new Stack<String>();
    for (int i = 0; i < this.numTeams; ++i) {
      st.push(this.teams[i]);
    }

    return st;
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

    return this.wins[this.map.get(team)];
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

    return this.losses[this.map.get(team)];
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

    return this.remaining[this.map.get(team)];
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

    return this.remainingInDivision[this.map.get(team1)][this.map.get(team2)];
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

    return !this.R[this.map.get(team)].isEmpty();
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

    final Stack<String> st = new Stack<String>();
    for (int i : this.R[this.map.get(team)]) {
      st.push(this.teams[i]);
    }
    return st;
  }
}
