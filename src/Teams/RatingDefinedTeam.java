package Teams;

import Misc.Region;

public class RatingDefinedTeam extends Team {
	public RatingDefinedTeam(String tag, Region region, double rating) {
		super(tag, region, rating);
	}
	
	public RatingDefinedTeam(Team team) {
		super(team);
	}
}
