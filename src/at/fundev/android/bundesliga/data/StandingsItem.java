package at.fundev.android.bundesliga.data;

import android.os.Parcel;
import android.os.Parcelable;

public class StandingsItem implements Parcelable {
	
	private int position = -1;
	
	private String name = null;
	
	private int wins = -1;
	
	private int draws = -1;
	
	private int defeats = -1;
	
	private int goalsScored = -1;
	
	private int goalsAgainst = -1;
	
	private String teamLink = null;
	
	/**
	 * @return the position
	 */
	public int getPosition() {
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
	public int getWins() {
		return wins;
	}

	/**
	 * @return the draws
	 */
	public int getDraws() {
		return draws;
	}

	/**
	 * @return the defeats
	 */
	public int getDefeats() {
		return defeats;
	}

	/**
	 * @return the games
	 */
	public int getGames() {
		return wins + draws + defeats;
	}

	/**
	 * @return the goalsScored
	 */
	public int getGoalsScored() {
		return goalsScored;
	}

	/**
	 * @return the goalsAgainst
	 */
	public int getGoalsAgainst() {
		return goalsAgainst;
	}

	/**
	 * @return the goalsDiff
	 */
	public int getGoalsDiff() {
		return goalsScored - goalsAgainst;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
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
	public void setPosition(int position) {
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
	public void setWins(int wins) {
		this.wins = wins;
	}

	/**
	 * @param draws the draws to set
	 */
	public void setDraws(int draws) {
		this.draws = draws;
	}

	/**
	 * @param defeats the defeats to set
	 */
	public void setDefeats(int defeats) {
		this.defeats = defeats;
	}

	/**
	 * @param goalsScored the goalsScored to set
	 */
	public void setGoalsScored(int goalsScored) {
		this.goalsScored = goalsScored;
	}

	/**
	 * @param goalsAgainst the goalsAgainst to set
	 */
	public void setGoalsAgainst(int goalsAgainst) {
		this.goalsAgainst = goalsAgainst;
	}

	/**
	 * @param teamLink the teamLink to set
	 */
	public void setTeamLink(String teamLink) {
		this.teamLink = teamLink;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel out, int flags) {
		out.writeInt(position);
		out.writeString(name);
		out.writeInt(wins);
		out.writeInt(draws);
		out.writeInt(defeats);
		out.writeInt(goalsScored);
		out.writeInt(goalsAgainst);
		out.writeString(teamLink);
	}
	
	public static final Parcelable.Creator<StandingsItem> CREATOR = new Parcelable.Creator<StandingsItem>() {
		public StandingsItem createFromParcel(Parcel in) {
		    return new StandingsItem(in);
		}

		public StandingsItem[] newArray(int size) {
		    return new StandingsItem[size];
		}
	};

	private StandingsItem(Parcel in) {
		position = in.readInt();
		name = in.readString();
		wins = in.readInt();
		draws = in.readInt();
		defeats = in.readInt();
		goalsScored = in.readInt();
		goalsAgainst = in.readInt();
		teamLink = in.readString();
	}
	
	public StandingsItem() {
		
	}
}