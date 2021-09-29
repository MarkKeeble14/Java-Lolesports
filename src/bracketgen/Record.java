package bracketgen;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Record implements Comparable<Record> {
	private int wins = 0;
	private int losses = 0;
	
	private Map<Team, Integer> timesBeatTeamMap = new HashMap<Team, Integer>();
	private Map<Team, Integer> timesBeatInTiebreaker = new HashMap<Team, Integer>();
	private Map<Team, Integer> timesLostToTeamMap = new HashMap<Team, Integer>();
	
	public Record Win(Team teamBeat) {
		wins++;
		if (timesBeatTeamMap.containsKey(teamBeat)) {
			timesBeatTeamMap.put(teamBeat, timesBeatTeamMap.get(teamBeat) + 1);			
		} else {			
			timesBeatTeamMap.put(teamBeat, 1);
		}
		return this;
	}
	
	public Record Lose(Team teamLostTo) {
		losses++;
		if (timesLostToTeamMap.containsKey(teamLostTo)) {
			timesLostToTeamMap.put(teamLostTo, timesLostToTeamMap.get(teamLostTo) + 1);			
		} else {			
			timesLostToTeamMap.put(teamLostTo, 1);
		}
		return this;
	}
	
	public Record TiebreakerWin(Team teamBeat) {
		wins++;
		if (timesBeatInTiebreaker.containsKey(teamBeat)) {
			timesBeatInTiebreaker.put(teamBeat, timesBeatInTiebreaker.get(teamBeat) + 1);			
		} else {			
			timesBeatInTiebreaker.put(teamBeat, 1);
		}
		return this;
	}
	
	public boolean hasBeatenInTiebreaker(Team team) {
		return timesBeatInTiebreaker.containsKey(team);
	}
	
	public Record Win() {
		wins++;
		return this;
	}
	
	public Record Lose() {
		losses++;
		return this;
	}

	public int getWins() {
		return wins;
	}

	public int getLosses() {
		return losses;
	}
	
	public boolean getHasBeaten(Team t) {
		return timesBeatTeamMap.containsKey(t);
	}
	
	public int getTimesBeat(Team t) {
		return timesBeatTeamMap.get(t);
	}

	public Map<Team, Integer> getTimesBeatTeamMap() {
		return timesBeatTeamMap;
	}

	@Override
	public int compareTo(Record r) {
		if (this.getWins() > r.getWins()) {
			return 1;
		} else if (this.getWins() < r.getWins()) {
			return -1;
		} else {
			return 0;			
		}
	}
	
	

	@Override
	public String toString() {
		return "Record [wins=" + wins + ", losses=" + losses + "]";
	}
}
