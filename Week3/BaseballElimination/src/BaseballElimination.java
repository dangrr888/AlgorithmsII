
public class BaseballElimination {

	/*
	 * @brief Create a baseball division from a specified
	 *   filename in a specified format.
	 * @param filename The specified filename.
	 */
	public BaseballElimination(String filename) {
		
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
		throw new UnsupportedOperationException();
	}
	
	/*
	 * @brief Return the number of losses of the specified
	 *   team.
	 * @param team The specified team.
	 * @return The number of losses of the specified team.
	 */
	public int losses(String team) {
		throw new UnsupportedOperationException();
	}
	
	/*
	 * @brief Return the number of remaining games of the specified
	 *   team.
	 * @param team The specified team.
	 * @return The number of remaining games of the specified team.
	 */
	public int remaining(String team) {
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
		throw new UnsupportedOperationException();		
	}
	
	/*
	 * @brief Return if the specified team is eliminated.
	 * @param team The specified team.
	 * @return If the specified team is eliminated.
	 */
	public boolean isEliminated(String team) {
		throw new UnsupportedOperationException();				
	}
	
	/*
	 * @brief Return the teams that eliminate the specified team.
	 * @param team The specified team.
	 * @retval The teams that eliminate the specified team.
	 * @retval null If the specified team is not eliminated.
	 */
	public Iterable<String> certificateOfElimination(String team) {
		throw new UnsupportedOperationException();		
	}
}
