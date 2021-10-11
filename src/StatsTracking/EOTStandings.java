package StatsTracking;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Misc.MapUtil;
import Misc.Strings;
import Misc.Util;
import Teams.Team;

public class EOTStandings {
	private Map<Team, Integer> standings = new HashMap<Team, Integer>();
	
	public void PlaceTeam(Team t, Integer placing) {
		standings.put(t, placing);
	}

	public void Print() {
		// Sort
		standings = MapUtil.sortByIntegerValue(standings);
		
		// Print
		Util.NicePrintStandings(standings);
	}
	
	public Map<Team, Integer> getStandings() {
		return standings;
	}

	public Integer GetTeamPlacing(Team t) {
		return standings.get(t);
	}
	
	public Team GetTeamByPlacing(Integer i) {
		Set<Entry<Team, Integer>> set = standings.entrySet();
		int index = 0;
		for (Entry<Team, Integer> e : set) {
			if (index == i) {
				return e.getKey();
			}
			index++;
		}
		return null;
	}
	
	public boolean SameResultAs(EOTStandings other) {
		ArrayList<Team> keys = new ArrayList<Team>(standings.keySet());
		Map<Team, Integer> otherStandings = other.getStandings();
		ArrayList<Team> otherKeys = new ArrayList<Team>(otherStandings.keySet());
        for(int i = 0; i < keys.size(); i++){
        	Team t = keys.get(i);
        	Team t2 = otherKeys.get(i);
        	
        	// Having this means that teams must place in the exact same spot, which would mean ending on
        	// the same side as the bracket, which might not be wanted behaviour. Unsure.
        	if (t != t2) {
        		return false;
        	}
        	if (standings.get(t) != otherStandings.get(t2)) {
        		return false;
        	}
        	
        }
        return true;
	}
}
