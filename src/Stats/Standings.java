package Stats;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import DefiningTeams.Team;

import java.util.Set;

import StaticVariables.Strings;
import Utility.Util;
import Utility.UtilMaps;

public class Standings {
	// private Map<Team, Integer> standings = new HashMap<Team, Integer>();
	private Map<Integer, List<Team>> standings = new HashMap<Integer, List<Team>>();
	private int totalTeams;
	private int teamsRemaining;
	private int lastPlaced;
	
	public void subtractTeams(int n) {
		teamsRemaining = totalTeams - n;
	}
	
	public Standings(int totalTeams) {
		super();
		this.totalTeams = totalTeams;
		teamsRemaining = totalTeams;
	}
	
	public void PlaceTeamDuringBacketStage(Team t, boolean newPlacing) {
		if (newPlacing) {
			lastPlaced -= standings.get(lastPlaced).size();
		}
		List<Team> l = standings.get(lastPlaced);
		if (l == null) {
			l = new ArrayList<Team>();
		}
		l.add(t);
		standings.put(lastPlaced, l);
		teamsRemaining--;
	}
	
	public void PlaceTeamDuringGroupStage(Team t, Integer placing) {
		int index = teamsRemaining - placing;
		List<Team> l = standings.get(index);
		if (l == null) {
			l = new ArrayList<Team>();
		}
		l.add(t);
		standings.put(index, l);
		lastPlaced = index;
	}

	public int getTotalTeams() {
		return totalTeams;
	}

	public void Print() {
		System.out.println(this);
	}

	@Override
	public String toString() {
		String s = "";
		
		// Sort
		standings = UtilMaps.sortByIntegerKey(standings);
		
		// Print
		Set<Integer> keys = standings.keySet();
		
		for (Integer i : keys) {
			List<Team> teams = standings.get(i);
			
			for (Team t : teams) {
	        	Record top = t.getTopRecord();
        		s += "\n" + t.getTag() + ": Finished - " + i + " | Run Ended During: " 
            	        + top.getLabel() + "\n";
			}
		}
		return s.substring(0, s.length() - 1);
	}
	
	/*
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
	*/
	/*
	public boolean SameResultAs(Standings other) {
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
	*/
	
	
}
