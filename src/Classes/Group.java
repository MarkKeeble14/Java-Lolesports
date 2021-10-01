package Classes;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import CustomExceptions.GroupExceedingCapacityException;
import Misc.MapUtil;

import java.util.Set;

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
	* Constructor
	* @param label A label for the group, i.e., A, B, C, D, etc
	* @param capacity The number of teams in the group
	*/
	public Group(String label, List<Team> finalListOfTeams) {
		this.label = label;
		this.capacity = finalListOfTeams.size();
		teams = new ArrayList<Team>();
		for (Team t : finalListOfTeams) {
			teams.add(t);
		}
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
	
	public void FullSimulate(String stageLabel, int matchesPerTwo, boolean simulateTiebreakers) throws Exception {
		// Make a copy of the initial Group
		Group copy = new Group("Copy", this.getCapacity());
		for (Team t : teams) {
			t.setNewRecord(stageLabel);
			copy.Add(t);
		}
		
		System.out.println("Group: " + label);
		
		// Simulate the actual games
		while(copy.getSize() > 0) {
			Team t = copy.getGroup().get(0);
			for (int p = 0; p < copy.getGroup().size(); p++) {
				Team c = copy.getGroup().get(p);
				if (t != c) {
					for (int q = 0; q < matchesPerTwo; q++) {
						Match M = new Match("M", t, c);
						
						// Assuming groups are BO1
						M.Simulate(stageLabel, 1);
						
						Team winner = M.getWinner();
						Team loser = M.getLoser();
						
						winner.getRecord().MatchWin(loser);
						loser.getRecord().MatchLoss(winner);
					}	
				}
			}
			copy.Remove(t);
		}
		
		// Tiebreakers
		if (simulateTiebreakers && tiebreakersRequired()) {
			SimulateTiebreakers(stageLabel);
		}
		
		// Sort into standings
		SortStandingsPostTiebreakers();
		
		System.out.println("\nFinal Standings\n");
		PrintStandings();
		
		System.out.println("\n------------------------------------------------\n");
	}
	
	private boolean tiebreakersRequired() {
		// Create a Map consisting of teams sorted into groups based on their records
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
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
		
		for (Entry<Record, List<Team>> entry : teamsByRecordMap.entrySet()) {
			if (entry.getValue().size() > 1) {
				return true;
			}
		}
		return false;
	}
	
	private void SimulateTiebreakers(String stageLabel) {
		System.out.println("\nPre-Tiebreakers Standings\n");
		SortStandingsPreTiebreakers();
		PrintStandings();
		
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
		
		System.out.println("\n------------------------------------------------");
		
		// Play out the tiebreakers
		Set<Entry<Record, List<Team>>> sortedTeams = teamsByRecordMap.entrySet();
		for (Entry<Record, List<Team>> entry : sortedTeams) {
			List<Team> lst = entry.getValue();
			if (lst.size() > 1) {
				// Sorts based on criteria you can see within the function
				lst = SortSameRecords(entry.getValue());
				
				Team prevTeam = lst.get(0);
				for (int i = 0; i < lst.size() - 1; i++) {
					Team teamA = prevTeam;
					Team teamB = lst.get(i + 1);
					
					System.out.println("\nTiebreaker between: " + teamA + ", and: " + teamB);
					System.out.print(teamA + " Record: " + teamA.getRecord() + " vs ");
					System.out.print(teamB + " Record: " + teamB.getRecord() + "\n");
					
					Match M = new Match("M", teamA, teamB);
					
					// Assuming groups are BO1
					M.Simulate(stageLabel + ": Tiebreakers", 1);
					
					Team winner = M.getWinner();
					Team loser = M.getLoser();
					
					winner.getRecord().TiebreakerWin(loser);
					loser.getRecord().TiebreakerLoss(winner);
					
					prevTeam = winner;
				}
			}
		}
	}
		
	private List<Team> SortSameRecords(List<Team> teamsToSort) {
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
				// Set Variables
				Team eTeam = entry.getKey();
				Record eRecord = entry.getValue();
				
				if (tmpStandings.containsKey(eTeam)) {
					continue;
				}
				
				if (topRecord == null) {
	            	topRecord = entry;
	            	continue;
	            }
				Record trRecord = topRecord.getValue();
				
				// Sorting
				if (eRecord.getWins() > trRecord.getWins()) {
	            	topRecord = entry;
	            } else if (eRecord.getWinsOfTeamsBeat() == trRecord.getWinsOfTeamsBeat()) {
	            	if (eRecord.getWinsOfTeamsBeat() < trRecord.getWinsOfTeamsBeat()) {
		            	topRecord = entry;
		            }
	            }
	        }
			tmpStandings.put(topRecord.getKey(), ++place);
		}
		tmpStandings = MapUtil.sortByIntegerValue(tmpStandings);
		List<Team> sortedList = new ArrayList<Team>();
		for (Entry<Team, Record> entry : set) {
			sortedList.add(0, entry.getKey());
		}
		return sortedList;
	}
	
	private void SortStandingsPreTiebreakers() {
		standings = new HashMap<Team, Integer>();
		
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (standings.size() < teamRecords.size()) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				
				// Set Variables
				Team eTeam = entry.getKey();
				Record eRecord = entry.getValue();
				
				if (standings.containsKey(eTeam)) {
					continue;
				}
				
				if (topRecord == null) {
	            	topRecord = entry;
	            	continue;
	            } 
				
				Record trRecord = topRecord.getValue();
				
				// Sorting
            	if (eRecord.getWins() > trRecord.getWins()) {
	            	topRecord = entry;
	            } else if (eRecord.getWinsOfTeamsBeat() == trRecord.getWinsOfTeamsBeat()) {
	            	if (eRecord.getWinsOfTeamsBeat() < trRecord.getWinsOfTeamsBeat()) {
		            	topRecord = entry;
		            }
	            }
	        }
			standings.put(topRecord.getKey(), ++place);
		}
		standings = MapUtil.sortByIntegerValue(standings);
	}
	
	private void SortStandingsPostTiebreakers() {
		standings = new HashMap<Team, Integer>();
		
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (standings.size() < teamRecords.size()) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				// Set Variables
				Team eTeam = entry.getKey();
				Record eRecord = entry.getValue();
				
				if (standings.containsKey(eTeam)) {
					continue;
				}
				
				if (topRecord == null) {
	            	topRecord = entry;
	            	continue;
	            } 
				
				Team trTeam = topRecord.getKey();
				Record trRecord = topRecord.getValue();
				
				// Sorting
				if (eRecord.getWins() >= trRecord.getWins()) {
					if (eRecord.getNumberOfTiebreakers() < trRecord.getNumberOfTiebreakers()) {
            			topRecord = entry;
            		} else if (eRecord.getNumberOfTiebreakers() >= trRecord.getNumberOfTiebreakers()) {
            			if (eRecord.hasBeatenInTiebreaker(trTeam)) {
            				topRecord = entry;
            			} else {
            				if (eRecord.getTotalWinsOfTeamsLostToInTiebreakers() 
            						> trRecord.getTotalWinsOfTeamsLostToInTiebreakers()) {
            					topRecord = entry;
            				} else if (trRecord.getLosses() > eRecord.getLosses()) {
            					topRecord = entry;
            				} else if (eRecord.getWins() > trRecord.getWins()) {
            					topRecord = entry;
            				}
            			}
            		}
	            }
	        }
			standings.put(topRecord.getKey(), ++place);
		}
		standings = MapUtil.sortByIntegerValue(standings);
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

	public void PrintStandings() {
		System.out.println("Group " + label);
		standings.forEach((k, v) -> System.out.println((v + " : " + k + " | Record: " 
				+ k.getRecord().getWins() + "-" + k.getRecord().getLosses())));
	}
	
	public void PrintStandingsWithRecordLogs() {
		System.out.println("Group " + label);
		standings.forEach((k, v) -> System.out.println((v + " : " + k + " | Record: " 
				+ k.getRecord().detailedPrint())));
	}
	
	public String StringifyGroup() {
		String s = "Group " + label + "\n";
		for (int i = 0; i < teams.size(); i++) {
			Team t = teams.get(i);
			if (i == teams.size() - 1) {
				s += t;
			} else {
				s += t + "\n";
			}
		}
		return s;
	}
	
	@Override
	public String toString() {
		return StringifyGroup();
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
	public static Group FindGroup(Team t, List<Group> groups) {
		for (Group g : groups) {
			if (g.Contains(t)) {
				return g;
			}
		}
		return null;
	}
}
