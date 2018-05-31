import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Stack;

import edu.princeton.cs.algs4.FlowEdge;
import edu.princeton.cs.algs4.FlowNetwork;
import edu.princeton.cs.algs4.FordFulkerson;

public class BaseballElimination {

  // Map of team names to an index
  private final HashMap<String, Integer> map;

  // Array of sets of teams constituting the
  // certificate of elimination for each team.
  private final HashSet<String>[] R;

  private final int numTeams;
  private final String[] teams;
  private final int[] wins;
  private final int[] losses;

  // Overall number of remaining games for each team.
  private final int[] remaining;

  // The number of games remaining between two teams
  // in the target division.
  private final int[][] remainingInDivision;

  private int sourceNodeIdx;
  private int gameNodeOffset;
  private int numGameNodes;
  private int teamNodeOffset;
  private int numTeamNodes;
  private int targetNodeIdx;
  private int numNodes;

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
  @SuppressWarnings("unchecked")
  public BaseballElimination(String filename) {

    // Check input.
    if (filename == null) {
      throw new IllegalArgumentException("Null argument.");
    }

    // Initialize scanner
    final Scanner sc = new Scanner(filename);

    // Number of teams
    this.numTeams = sc.nextInt();
    if (this.numTeams < 1) {
      sc.close();
      throw new IllegalArgumentException("#teams must be >= 1");
    }

    // Initialize remaining data members.

    this.map = new HashMap<String, Integer>();

    this.R = (HashSet<String>[]) new HashSet[this.numTeams];
    for (int i = 0; i < this.numTeams; ++i) {
      this.R[i] = new HashSet<String>();
    }

    this.teams = new String[this.numTeams];
    this.wins = new int[this.numTeams];
    this.losses = new int[this.numTeams];
    this.remaining = new int[this.numTeams];

    this.remainingInDivision = new int[this.numTeams][];
    for (int i = 0; i < this.numTeams; ++i) {
      this.remainingInDivision[i] = new int[this.numTeams];
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


    // Team nodes: Nodes representing each team, hence N nodes.
    this.numTeamNodes = this.numTeams;

    // Game nodes: Nodes representing games between teams 0-1, 0-2, 0-3, ... not double counting,
    // hence N*(N-1) nodes
    this.numGameNodes = this.numTeamNodes * (this.numTeamNodes-1) / 2;

    // Source node index is always 0.
    this.sourceNodeIdx = 0;

    // Game nodes: Nodes representing games between each unique pair of team nodes (not
    // double counting and omitting diagonal nodes).
    this.gameNodeOffset = this.sourceNodeIdx + 1;

    // Team nodes: Nodes representing each team in the flow network.
    this.teamNodeOffset = this.gameNodeOffset + this.numGameNodes;

    // Target node is always the last node.
    this.targetNodeIdx = this.teamNodeOffset + this.numTeamNodes;

    // Hence we have 2+N*(N-1)/2+N nodes starting from 0, i.e., 0 <= x <= 1+N(N-1)/2+N,
    // where N is the number of team nodes.
    this.numNodes = this.targetNodeIdx - this.sourceNodeIdx + 1;

    // Determine the eliminated teams.
    for (int i = 0; i < this.numTeams; ++i) {

      // Determine contract of elimination for team i:

      // 1. Check if team is trivially eliminated.
      this.findTrivialCertificateOfElimination(i);

      // 2. Calculate team i's certificate elimination
      // using a max flow calculation of a graph
      // consisting of the remaining games.
      this.findMaxFlowCertificateOfElimination(i);
    }
  }

  private void findMaxFlowCertificateOfElimination(int i) {

    // 1. Create flow network for determining if team i
    // is eliminated.
    final FlowNetwork fn = this.createFlowNetwork(i);

    // 2. Calculate max flow of division targeted at team i,
    // collate the teams in the associated min cut and add
    // to team i's certificate elimination.
    final FordFulkerson ff = new FordFulkerson(fn,this.sourceNodeIdx,this.targetNodeIdx);
    for (int j = 0; j < this.numTeams; ++j) {
      if (ff.inCut(this.teamNodeOffset+j)) {
        this.R[i].add(this.teams[j]);
      }
    }
  }

  private void findTrivialCertificateOfElimination(int i) {
    for (int j = 0; j < this.numTeams; ++j) {
      if (this.isTriviallyEliminatedBy(i, j)) {
        this.R[i].add(this.teams[j]);
      }
    }
  }

  private boolean isTriviallyEliminatedBy(int i, int j) {
    return this.wins[i] + this.remaining[i] < this.wins[j];
  }

  /*
   * @brief Create the flow network for calculating
   * the max flow targeting the team of the specified
   * index.
   * @param i The index of the specified team.
   */
  private FlowNetwork createFlowNetwork(int i) {

    final FlowNetwork fn = new FlowNetwork(this.numNodes);

    int nodeIdx = this.gameNodeOffset;
    for (int j = 0; j < this.numTeamNodes; ++j) {
      for (int k = j+1; k < this.numTeamNodes; ++k) {

        // Add edges from source node to game nodes.
        fn.addEdge(new FlowEdge(this.sourceNodeIdx, nodeIdx, this.remainingInDivision[j][k], 0.0));

        // Add edges from game nodes to team nodes.
        fn.addEdge(new FlowEdge(nodeIdx, this.teamNodeOffset+j, Double.POSITIVE_INFINITY, 0.0));
        fn.addEdge(new FlowEdge(nodeIdx, this.teamNodeOffset+k, Double.POSITIVE_INFINITY, 0.0));
        ++nodeIdx;
      }
    }


    // Add edges from team nodes to target node.
    for (int j = 0; j < this.numTeamNodes; ++j) {
      if (i == j) {
        // Set capacity of FlowEdge from team i to target to 0.0.
        fn.addEdge(new FlowEdge(this.teamNodeOffset + i, this.targetNodeIdx, 0.0));
      } else {
        // Check team j doesn't trivially eliminate i. If so, then set capacity of edge from team i to target to 0
        final double capacity = this.wins[i] + this.remaining[i] - this.wins[j];
        fn.addEdge(new FlowEdge(this.teamNodeOffset + j, this.targetNodeIdx, capacity > 0.0 ? capacity : 0.0));
      }
    }

    return fn;
  }

  /*
   * @brief Return the index of the specified team.
   * @param team The name of the specified team.
   * @return The index of the specified team.
   */
  private int teamIndex(String team) {
    return this.map.get(team);
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

    return this.wins[this.teamIndex(team)];
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

    return this.losses[this.teamIndex(team)];
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

    return this.remaining[this.teamIndex(team)];
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

    return this.remainingInDivision[this.teamIndex(team1)][this.teamIndex(team2)];
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

    return !this.R[this.teamIndex(team)].isEmpty();
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

    return this.R[this.teamIndex(team)];
  }
}
