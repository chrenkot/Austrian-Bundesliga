package at.fundev.android.bundesliga.data;

public class StandingsItem {
	
	private Integer position = null;
	
	private String name = null;
	
	private Integer wins = null;
	
	private Integer draws = null;
	
	private Integer defeats = null;
	
	private Integer goalsScored = null;
	
	private Integer goalsAgainst = null;
	
	private String teamLink = null;

	/**
	 * @return the position
	 */
	public Integer getPosition() {
		return position;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @return the wins
	 */
	public Integer getWins() {
		return wins;
	}

	/**
	 * @return the draws
	 */
	public Integer getDraws() {
		return draws;
	}

	/**
	 * @return the defeats
	 */
	public Integer getDefeats() {
		return defeats;
	}

	/**
	 * @return the games
	 */
	public Integer getGames() {
		return wins + draws + defeats;
	}

	/**
	 * @return the goalsScored
	 */
	public Integer getGoalsScored() {
		return goalsScored;
	}

	/**
	 * @return the goalsAgainst
	 */
	public Integer getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @return the goalsDiff
	 */
	public Integer getGoalsDiff() {
		return goalsScored - goalsAgainst;
	}

	/**
	 * @return the points
	 */
	public Integer getPoints() {
		return wins * 3 + draws;
	}

	/**
	 * @return the teamLink
	 */
	public String getTeamLink() {
		return teamLink;
	}

	/**
	 * @param position the position to set
	 */
	public void setPosition(Integer position) {
		this.position = position;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param wins the wins to set
	 */
	public void setWins(Integer wins) {
		this.wins = wins;
	}

	/**
	 * @param draws the draws to set
	 */
	public void setDraws(Integer draws) {
		this.draws = draws;
	}

	/**
	 * @param defeats the defeats to set
	 */
	public void setDefeats(Integer defeats) {
		this.defeats = defeats;
	}

	/**
	 * @param goalsScored the goalsScored to set
	 */
	public void setGoalsScored(Integer goalsScored) {
		this.goalsScored = goalsScored;
	}

	/**
	 * @param goalsAgainst the goalsAgainst to set
	 */
	public void setGoalsAgainst(Integer goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @param teamLink the teamLink to set
	 */
	public void setTeamLink(String teamLink) {
		this.teamLink = teamLink;
	}
}