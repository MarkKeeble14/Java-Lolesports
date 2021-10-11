package Teams;

import Classes.Player;
import Misc.Region;

public class RosterDefinedTeam extends Team {
	private Player top;
	private Player jungle;
	private Player mid;
	private Player bottom;
	private Player support;
	
	public RosterDefinedTeam(String tag, Region region, Player top, Player jungle, Player mid, Player bottom, Player support) {
		super(tag, region, RosterDefinedTeam.getRatingFromPlayers(
				top, jungle, mid, bottom, support));
	}
	
	public RosterDefinedTeam(Team team) {
		super(team);
		super.setRating(getRatingFromPlayers(top, jungle, mid, bottom, support));
	}
	
	private static float getRatingFromPlayers(Player top, Player jungle, Player mid, Player bottom, Player support) {
		float rating = top.getRatingOutOf1() + jungle.getRatingOutOf1() + mid.getRatingOutOf1()
			+ bottom.getRatingOutOf1() + support.getRatingOutOf1();
		return rating;
	}
}
