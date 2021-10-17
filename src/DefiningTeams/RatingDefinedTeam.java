package DefiningTeams;

import Enums.REGION;

public class RatingDefinedTeam extends Team {
	public RatingDefinedTeam(String tag, REGION region, double rating) {
		super(tag, region, rating);
	}
	
	public RatingDefinedTeam(Team team) {
		super(team);
	}
}
