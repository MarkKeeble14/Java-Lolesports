package bracketgen;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

public class Group {
	private String label;
	private int capacity;
	
	private List<Team> teams;
	private Map<Team, Integer> standings = new HashMap<Team, Integer>();
	
	// private Map<Team, Record> teamRecords = new HashMap<Team, Record>();
	
	/**
	* Constructor
	* @param label A label for the group, i.e., A, B, C, D, etc
	* @param capacity The number of teams in the group
	* @param teams The teams in the group
	*/
	public Group(String label, int capacity, Team ...groupTeams) {
		this.label = label;
		this.capacity = capacity;
		teams = new ArrayList<Team>();
		for (Team t : groupTeams) {
			teams.add(t);
		}
	}
	
	/**
	* Constructor
	* @param label A label for the group, i.e., A, B, C, D, etc
	* @param capacity The number of teams in the group
	*/
	public Group(String label, int capacity) {
		this.label = label;
		this.capacity = capacity;
		teams = new ArrayList<Team>();
	}
	
	/**
	* Copy Constructor
	* @param g The group to copy
	*/
	public Group(Group g) {
		this.label = g.getLabel();
		this.capacity = g.getCapacity();
		
		teams = new ArrayList<Team>();
		for (Team t: g.getGroup()) {
			teams.add(t);
		}
	}
	
	public boolean Contains(Team team) {
		return teams.contains(team);
	}
	
	public List<Team> getGroup() {
		return teams;
	}
	
	public int getCapacity() {
		return capacity;
	}

	public int getSize() {
		return teams.size();
	}
	
	public String getLabel() {
		return label;
	}

	public boolean isFull() {
		return teams.size() == capacity;
	}
	
	public void Add(Team team) throws Exception {
		if (isFull()) {
			throw new GroupExceedingCapacityException(StringifyGroup(), team.toString(), capacity);
		}
		teams.add(team);
	}
	
	public void Remove(Team t) {
		teams.remove(t);
	}
	
	public Team GetTeamFromPlacement(Integer index) {
	    for (Entry<Team, Integer> entry : standings.entrySet()) {
	        if (index == entry.getValue()) {
	            return entry.getKey();
	        }
	    }
	    return null;
	}
	
	// Simulates the group playing out by sorting the group in order of descending rating
	public void BasicSimulate() {
		Collections.sort(teams);
		for (int i = 1; i < teams.size() + 1; i++) {
			standings.put(teams.get(i - 1), teams.size() - i + 1);
		}
		standings = MapUtil.sortByIntegerValue(standings);
	}
	
	public void FullSimulate(int matchesPerTwo, boolean simulateTiebreakers) throws Exception {
		// Make a copy of the initial Group
		Group copy = new Group("Copy", this.getCapacity());
		for (Team t : teams) {
			t.setRecord(new Record());
			copy.Add(t);
		}
		
		// Simulate the actual games
		while(copy.getSize() > 0) {
			Team t = copy.getGroup().get(0);
			for (int p = 0; p < copy.getGroup().size(); p++) {
				Team c = copy.getGroup().get(p);
				if (t != c) {
					for (int q = 0; q < matchesPerTwo; q++) {
						Match M = new Match("M", t, c);
						
						// Assuming groups are BO1
						M.Simulate(1);
						
						Team winner = M.getWinner();
						winner.getRecord().Win();
						
						Team loser = M.getLoser();
						loser.getRecord().Lose();
					}	
				}
			}
			copy.Remove(t);
		}
		
		// Tiebreakers
		if (simulateTiebreakers) {
			SimulateTiebreakers();
		}
		
		// Sort into standings
		SortMainStandings();
	}
	
	private void SimulateTiebreakers() {
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		// Create a Map consisting of teams sorted into groups based on their records
		Map<Record, List<Team>> teamsByRecordMap = new HashMap<Record, List<Team>>();
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		for (Entry<Team, Record> entry : set) {
			Set<Entry<Record, List<Team>>> alreadyAddedSet = teamsByRecordMap.entrySet();
			if (alreadyAddedSet.size() == 0) {
				List<Team> lst = new ArrayList<Team>();
				lst.add(entry.getKey());
				teamsByRecordMap.put(entry.getValue(), lst);
			} else {
				int i = 0;
				for (Entry<Record, List<Team>> entry2 : alreadyAddedSet) {
					i++;
					
					if (entry.getValue().getWins() == entry2.getKey().getWins()
							&& entry.getValue().getLosses() == entry2.getKey().getLosses()) {
						List<Team> lst = teamsByRecordMap.get(entry2.getKey());
						lst.add(entry.getKey());
						teamsByRecordMap.put(entry2.getKey(), lst);
						
						break;
					} else if (i == alreadyAddedSet.size()) {
						List<Team> lst = new ArrayList<Team>();
						lst.add(entry.getKey());
						teamsByRecordMap.put(entry.getValue(), lst);
					}
				}
			}
		}
		
		// Play out the tiebreakers
		Set<Entry<Record, List<Team>>> sortedTeams = teamsByRecordMap.entrySet();
		for (Entry<Record, List<Team>> entry : sortedTeams) {
			List<Team> lst = entry.getValue();
			if (lst.size() > 1) {
				// Sorts based on criteria you can see within the function
				lst = SortStandings(entry.getValue());
				
				Team prevTeam = lst.get(0);
				for (int i = 0; i < lst.size() - 1; i++) {
					Team teamA = prevTeam;
					Team teamB = lst.get(i + 1);
					
					System.out.println("\nTiebreaker between: " + teamA + ", and: " + teamB);
					System.out.print(teamA + " Record: " + teamA.getRecord() + " vs ");
					System.out.print(teamB + " Record: " + teamB.getRecord() + "\n");
					
					Match M = new Match("M", teamA, teamB);
					
					// Assuming groups are BO1
					M.Simulate(1);
					
					Team winner = M.getWinner();
					Team loser = M.getLoser();
					
					winner.getRecord().TiebreakerWin(loser);
					loser.getRecord().Lose(winner);
					
					prevTeam = winner;
				}
			}
		}
	}
	
	private List<Team> SortStandings(List<Team> teamsToSort) {
		Map<Team, Integer> tmpStandings = new HashMap<Team, Integer>();
		
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teamsToSort) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (tmpStandings.size() < teamRecords.size()) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				if (tmpStandings.containsKey(entry.getKey())) {
					continue;
				}
	            if (topRecord == null) {
	            	topRecord = entry;
	            } else if (entry.getValue().hasBeatenInTiebreaker(topRecord.getKey())) {
	            	topRecord = entry;
	            } else if (entry.getValue().getWins() > topRecord.getValue().getWins()) {
	            	topRecord = entry;
	            } else if (entry.getValue().getWins() == topRecord.getValue().getWins()
	            		&& entry.getValue().getLosses() < topRecord.getValue().getLosses()) {
	            	topRecord = entry;
	            } else if (entry.getValue().getWins() == topRecord.getValue().getWins() 
	            	&& entry.getValue().getLosses() == topRecord.getValue().getLosses()
	            	&& entry.getValue().getHasBeaten(topRecord.getKey())	
	            	&& entry.getValue().getTimesBeat(topRecord.getKey()) > topRecord.getValue().getTimesBeat(entry.getKey())) {
	            		topRecord = entry;
	            } else if (entry.getValue().getWins() == topRecord.getValue().getWins() 
	            	&& entry.getValue().getLosses() == topRecord.getValue().getLosses()
        			&& entry.getValue().getHasBeaten(topRecord.getKey())
        			&& topRecord.getValue().getHasBeaten(entry.getKey())
            		&& entry.getValue().getTimesBeat(topRecord.getKey()) == topRecord.getValue().getTimesBeat(entry.getKey())) {
	            		
	            		Set<Entry<Team, Integer>> eTeamsBeaten = entry.getValue().getTimesBeatTeamMap().entrySet();
	            		int eWinsOfBeatenTeams = 0;
	            		for (Entry<Team, Integer> ee : eTeamsBeaten) {
	            			eWinsOfBeatenTeams += teamRecords.get(ee.getKey()).getWins();
	            		}
	            		
	            		Set<Entry<Team, Integer>> trTeamsBeaten = topRecord.getValue().getTimesBeatTeamMap().entrySet();
	            		int trWinsOfBeatenTeams = 0;
	            		for (Entry<Team, Integer> ee : trTeamsBeaten) {
	            			trWinsOfBeatenTeams += teamRecords.get(ee.getKey()).getWins();
	            		}
	            		
	            		if (eWinsOfBeatenTeams > trWinsOfBeatenTeams) {
	            			topRecord = entry;
	            		} else if (eWinsOfBeatenTeams == trWinsOfBeatenTeams) {
	            			// Sucks to suck
	            		}
	            }
	        }
			tmpStandings.put(topRecord.getKey(), ++place);
		}
		tmpStandings = MapUtil.sortByIntegerValue(tmpStandings);
		List<Team> sortedList = new ArrayList<Team>();
		for (Entry<Team, Record> entry : set) {
			sortedList.add(entry.getKey());
		}
		return sortedList;
	}
	
	
	private void SortMainStandings() {
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (standings.size() < teamRecords.size()) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				if (standings.containsKey(entry.getKey())) {
					continue;
				}
	            if (topRecord == null) {
	            	topRecord = entry;
	            } else if (entry.getValue().hasBeatenInTiebreaker(topRecord.getKey())) {
	            	topRecord = entry;
	            } else if (entry.getValue().getWins() > topRecord.getValue().getWins()) {
	            	topRecord = entry;
	            } else if (entry.getValue().getWins() == topRecord.getValue().getWins() 
	            	&& entry.getValue().getLosses() == topRecord.getValue().getLosses()
	            	&& entry.getValue().getHasBeaten(topRecord.getKey())	
	            	&& entry.getValue().getTimesBeat(topRecord.getKey()) > topRecord.getValue().getTimesBeat(entry.getKey())) {
	            		topRecord = entry;
	            } else if (entry.getValue().getWins() == topRecord.getValue().getWins() 
	            	&& entry.getValue().getLosses() == topRecord.getValue().getLosses()
        			&& entry.getValue().getHasBeaten(topRecord.getKey())
        			&& topRecord.getValue().getHasBeaten(entry.getKey())
            		&& entry.getValue().getTimesBeat(topRecord.getKey()) == topRecord.getValue().getTimesBeat(entry.getKey())) {
	            		
	            		Set<Entry<Team, Integer>> eTeamsBeaten = entry.getValue().getTimesBeatTeamMap().entrySet();
	            		int eWinsOfBeatenTeams = 0;
	            		for (Entry<Team, Integer> ee : eTeamsBeaten) {
	            			eWinsOfBeatenTeams += teamRecords.get(ee.getKey()).getWins();
	            		}
	            		
	            		Set<Entry<Team, Integer>> trTeamsBeaten = topRecord.getValue().getTimesBeatTeamMap().entrySet();
	            		int trWinsOfBeatenTeams = 0;
	            		for (Entry<Team, Integer> ee : trTeamsBeaten) {
	            			trWinsOfBeatenTeams += teamRecords.get(ee.getKey()).getWins();
	            		}
	            		
	            		if (eWinsOfBeatenTeams > trWinsOfBeatenTeams) {
	            			topRecord = entry;
	            		} else if (eWinsOfBeatenTeams == trWinsOfBeatenTeams) {
	            			// Sucks to suck
	            		}
	            }
	        }
			standings.put(topRecord.getKey(), ++place);
		}
		standings = MapUtil.sortByIntegerValue(standings);
	}
	
	public void PrintResults() {
		System.out.println("\nGroup " + label);
		standings.forEach((k, v) -> System.out.println((v + " : " + k + " | Record: " 
				+ k.getRecord().getWins() + "-" + k.getRecord().getLosses())));
		System.out.println("\n------------------------------------------------");
	}
	
	public String StringifyGroup() {
		String s = "\nGroup " + label + "\n";
		for (int i = 0; i < teams.size(); i++) {
			Team t = teams.get(i);
			if (i == teams.size() - 1) {
				s += t.getTag() + ": " + t.getRegion() + "\n";
			} else {
				s += t.getTag() + ": " + t.getRegion() + ", ";
			}
		}
		return s;
	}
	
	// Returns true if there is a team from the Pool p which can be drawn into this group, given the rule that 
	// no group may have more than a single team from any given region
	public boolean CanDrawBasedOnRegion(Pool p) {
		for (Team t : p.getPool()) {
			for (Team ct : teams) {
				if (t.getRegion() != ct.getRegion()) {
					return true;
				}
			}
		}
		return false;
	}
	
	// Returns a list of the potential draws for this group, given the Pool p and the rule that 
	// no group may have more than a single team from any given region
	public List<Team> FilterRegion(Pool p) {
		List<Team> potentialDraws = new ArrayList<Team>();
		for (Team t : p.getPool()) {
			boolean legal = true;
			for (Team ct : teams) {
				if (t.getRegion() == ct.getRegion()) {
					legal = false;
					break;
				}
			}
			if (legal) {
				potentialDraws.add(t);
			}
		}
		return potentialDraws;
	}
	
	// Returns a list of the potential draws for this group, given the Pool p and the rule that 
	// no group may have more than a single team from any given region
	public List<Team> FilterGroup(Pool p, Group[] groups) {
		List<Team> potentialDraws = new ArrayList<Team>();
		for (Team t : p.getPool()) {
			boolean legal = true;
			for (Team ct : teams) {
				if (Group.FindGroup(t, groups) == Group.FindGroup(ct, groups)) {
					legal = false;
					break;
				}
			}
			if (legal) {
				potentialDraws.add(t);
			}
		}
		return potentialDraws;
	}
	

	
	@Override
	public String toString() {
		if (standings.isEmpty()) {
			return "Group [label=" + label + ", capacity=" + capacity + ", group=" + teams + "]";
		} else {
			return "Group [label=" + label + ", capacity=" + capacity + ", group=" + teams 
					+ ", standings=" + standings + "]";
		}
	}

	// Finds the group of Team t
	public static Group FindGroup(Team t, Group[] groups) {
		for (Group g : groups) {
			if (g.Contains(t)) {
				return g;
			}
		}
		return null;
	}

	// Finds the group of Team t
	public static Group FindGroup(Team t, ArrayList<Group> groups) {
		for (Group g : groups) {
			if (g.Contains(t)) {
				return g;
			}
		}
		return null;
	}
}
