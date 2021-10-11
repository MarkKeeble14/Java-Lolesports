package Classes;

import java.util.Collections;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import CustomExceptions.GroupExceedingCapacityException;
import Matches.Game;
import Matches.Matchup;
import Matches.Series;
import Misc.MapUtil;
import Misc.Strings;
import Misc.Util;
import StatsTracking.Record;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.GroupStage;

import java.util.Set;

public class Group {
	private String label;
	private int capacity;
	
	private List<Team> teams;
	
	private Map<Team, Integer> preTBStandings = new HashMap<Team, Integer>();
	
	private Map<Team, Integer> postTBStandings = new HashMap<Team, Integer>();
	
	private GroupStage partOf;
	private int gamesPerRoundRobin;
	private int matchesAreBOX;
	
	private List<Matchup> matchups = new ArrayList<Matchup>();
	private List<Matchup> tiebreakers = new ArrayList<Matchup>();
	
	public int getNumTiebreakers() { 
		return tiebreakers.size();
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
	
	/**
	* Constructor
	* @param label A label for the group, i.e., A, B, C, D, etc
	* @param capacity The number of teams in the group
	* @param teams The teams in the group
	*/
	public Group(String label, int capacity, int gamesPerRoundRobin, int matchesAreBOX, GroupStage partOf, Team ...groupTeams) {
		this.label = label;
		this.capacity = capacity;
		this.gamesPerRoundRobin = gamesPerRoundRobin;
		this.matchesAreBOX = matchesAreBOX;
		this.partOf = partOf;
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
	public Group(String label, int capacity, int gamesPerRoundRobin, int matchesAreBOX, int numGamesPerMatch, GroupStage partOf) {
		this.label = label;
		this.capacity = capacity;
		this.gamesPerRoundRobin = gamesPerRoundRobin;
		this.matchesAreBOX = matchesAreBOX;
		this.partOf = partOf;
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
	
	public Group(String label, int capacity, int gamesPerRoundRobin, int matchesAreBOX, GroupStage partOf, List<Team> teams) {
		this.label = label;
		this.capacity = capacity;
		this.gamesPerRoundRobin = gamesPerRoundRobin;
		this.matchesAreBOX = matchesAreBOX;
		this.partOf = partOf;
		this.teams = new ArrayList<Team>();
		for (Team t: teams) {
			this.teams.add(t);
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
			throw new GroupExceedingCapacityException(StringifyGroupParticipants(), team.toString(), capacity);
		}
		teams.add(team);
	}
	
	public void Remove(Team t) {
		teams.remove(t);
	}
	
	public Team GetTeamFromPlacement(Integer index) {
	    for (Entry<Team, Integer> entry : postTBStandings.entrySet()) {
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
			preTBStandings.put(teams.get(i - 1), teams.size() - i + 1);
		}
		preTBStandings = MapUtil.sortByIntegerValue(preTBStandings);
	}
	
	public void FullSimulate(String stageLabel, RegionalWLTracker tracker, boolean simulateTiebreakers) throws Exception {
		// Make a copy of the initial Group
		Group copy = new Group("Copy", capacity, gamesPerRoundRobin, matchesAreBOX, partOf);
		for (Team t : teams) {
			t.setNewRecord(stageLabel);
			copy.Add(t);
		}
		
		// Simulate the actual games
		while(copy.getSize() > 0) {
			Team t = copy.getGroup().get(0);
			for (int p = 0; p < copy.getGroup().size(); p++) {
				Team c = copy.getGroup().get(p);
				if (t != c) {
					for (int q = 0; q < gamesPerRoundRobin; q++) {
						if (matchesAreBOX > 1) {
							Series S = new Series(stageLabel, "S", matchesAreBOX, t, c, tracker);
							
							S.Simulate();
							matchups.add(S);
						} else {
							Game M = new Game(stageLabel, "M", t, c, tracker);
							
							M.Simulate();
							matchups.add(M);
						}
					}	
				}
			}
			copy.Remove(t);
		}
		
		preTBStandings = SortStandingsPreTiebreakers();
		
		// Tiebreakers
		if (simulateTiebreakers && tiebreakersRequired()) {
			SimulateTiebreakers(stageLabel, tracker);
		}
		
		postTBStandings = SortStandingsPostTiebreakers();
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
					
					if (entry.getValue().getWins(true) == entry2.getKey().getWins(true)
							&& entry.getValue().getLosses(true) == entry2.getKey().getLosses(true)) {
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
	
	private void SimulateTiebreakers(String stageLabel, RegionalWLTracker tracker) {
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
					
					if (entry.getValue().getWins(false) == entry2.getKey().getWins(false)
							&& entry.getValue().getLosses(false) == entry2.getKey().getLosses(false)) {
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
		
		teamsByRecordMap = MapUtil.sortSectionsByRecordValue(teamsByRecordMap);
		Map<Team, Integer> fs = new HashMap<Team, Integer>();
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
					
					Game M = new Game(stageLabel, "M", teamA, teamB, tracker);
					
					// Assuming groups are BO1
					M.TBSimulate();
					tiebreakers.add(M);
					
					Team winner = M.getWinner();
					Team loser = M.getLoser();
					
					prevTeam = winner;
					
					fs.put(loser, fs.size());
					
					if (fs.size() == teams.size() - 1) {
						fs.put(winner, fs.size());
					}
				}
			} else {
				fs.put(lst.get(0), fs.size());
			}
		}
		
		postTBStandings = MapUtil.sortByIntegerValue(fs);
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
				if (eRecord.getWins(true) > trRecord.getWins(true)) {
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
	
	private Map<Team, Integer> SortStandingsPreTiebreakers() {
		Map<Team, Integer> fs = new HashMap<Team, Integer>();
		
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		int numTeams = teamRecords.size();
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (fs.size() < numTeams) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				
				// Set Variables
				Team eTeam = entry.getKey();
				Record eRecord = entry.getValue();
				
				if (topRecord == null) {
	            	topRecord = entry;
	            	continue;
	            } 
				
				Record trRecord = topRecord.getValue();
				
				// Sorting
				if (eRecord.getWins(false) > trRecord.getWins(false)) {
	            	topRecord = entry;
	            }
	        }
			fs.put(topRecord.getKey(), ++place);
			teamRecords.remove(topRecord.getKey());
		}
		return MapUtil.sortByIntegerValue(fs);
	}
	
	private Map<Team, Integer> SortStandingsPostTiebreakers() {
		Map<Team, Integer> fs = new HashMap<Team, Integer>();
		
		Map<Team, Record> teamRecords = new HashMap<Team, Record>();
		for (Team t : teams) {
			teamRecords.put(t, t.getRecord());
		}
		
		int place = 0;
		int numTeams = teamRecords.size();
		Set<Entry<Team, Record>> set = teamRecords.entrySet();
		while (fs.size() < numTeams) {
			Map.Entry<Team, Record> topRecord = null;
			for (Entry<Team, Record> entry : set) {
				// Set Variables
				Team eTeam = entry.getKey();
				Record eRecord = entry.getValue();
				
				if (topRecord == null) {
	            	topRecord = entry;
	            	continue;
	            } 
				
				Team trTeam = topRecord.getKey();
				Record trRecord = topRecord.getValue();
				
				// Sorting
				if (eRecord.getWins(true) >= trRecord.getWins(true)) {
					if (eRecord.getNumberOfTiebreakers() < trRecord.getNumberOfTiebreakers()) {
            			topRecord = entry;
            		} else if (eRecord.getNumberOfTiebreakers() >= trRecord.getNumberOfTiebreakers()) {
            			if (eRecord.hasBeatenInTiebreaker(trTeam)) {
            				topRecord = entry;
            			} else {
            				if (eRecord.getTotalWinsOfTeamsLostToInTiebreakers() 
            						> trRecord.getTotalWinsOfTeamsLostToInTiebreakers()) {
            					topRecord = entry;
            				} else if (trRecord.getLosses(true) > eRecord.getLosses(true)) {
            					topRecord = entry;
            				} else if (eRecord.getWins(true) > trRecord.getWins(true)) {
            					topRecord = entry;
            				}
            			}
            		}
	            }
	        }
			fs.put(topRecord.getKey(), ++place);
			teamRecords.remove(topRecord.getKey());
		}
		return MapUtil.sortByIntegerValue(fs);
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

	public void setPostTBStandings(Map<Team, Integer> m) {
		postTBStandings = m;
		postTBStandings = MapUtil.sortByIntegerValue(postTBStandings);
	}
	
	public List<Team> getTeams() {
		return teams;
	}

	public Map<Team, Integer> getPostTBStandings() {
		return postTBStandings;
	}

	public GroupStage getPartOf() {
		return partOf;
	}
	
	public int getGamesPerRoundRobin() {
		return gamesPerRoundRobin;
	}

	public int getMatchesAreBOX() {
		return matchesAreBOX;
	}

	@Override
	public String toString() {
		return StringifyGroupParticipants();
	}
	
	public String StringifyGroupParticipants() {
		String s = "Group " + label + "\n\n";
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
	
	public String toStandings(String stageLabel, boolean includeTiebreakers) {
		String s = "";
		if (includeTiebreakers) {
			s += "Group " + label + ": Post-Tiebreakers\n\n";
			Set<Entry<Team, Integer>> teamStandings = postTBStandings.entrySet();
			int x = 0;
			for (Entry<Team, Integer> entry : teamStandings) {
				Team k = entry.getKey();
				Integer v = entry.getValue();
				Record r = k.getRecord(stageLabel);
				
				int wins = r.getWins(includeTiebreakers);
				int losses = r.getLosses(includeTiebreakers);
				
				if (x == teamStandings.size() - 1) {
					String s1 = v + " : " + k;
					String s2 = " | Record: " + wins + "-" + losses;
					
					s += String.format(Strings.StandingFormat, s1, s2);
				} else {
					String s1 = v + " : " + k;
					String s2 = " | Record: " + wins + "-" + losses;
					
					s += String.format(Strings.StandingFormat, s1, s2) + "\n";
				}
				
				x++;
			}
		} else {
			s += "Group " + label + ": Pre-Tiebreakers\n\n";
			Set<Entry<Team, Integer>> teamStandings = preTBStandings.entrySet();
			int x = 0;
			for (Entry<Team, Integer> entry : teamStandings) {
				Team k = entry.getKey();
				Integer v = entry.getValue();
				Record r = k.getRecord(stageLabel);
				
				int wins = r.getWins(includeTiebreakers);
				int losses = r.getLosses(includeTiebreakers);
				
				if (x == teamStandings.size() - 1) {
					String s1 = v + " : " + k;
					String s2 = " | Record: " + wins + "-" + losses;
					
					s += String.format(Strings.StandingFormat, s1, s2);
				} else {
					String s1 = v + " : " + k;
					String s2 = " | Record: " + wins + "-" + losses;
					
					s += String.format(Strings.StandingFormat, s1, s2) + "\n";
				}
				
				x++;
			}
		}
		
		return s;
	}
	
	public String StringifyMatches() {
		String s = "Group " + label + " Games\n";
		int x = 0;
		
		for (int i = 0; i < matchups.size(); i++) {
			Matchup m = matchups.get(i);
			if (x == matchups.size() - 1) {
				s += "\n" + m.toString();
			} else {
				s += "\n" + m.toString() + "\n";
				s += Strings.SmallLineBreak + "\n";
			}
			x++;
		}
		
		return s;
	}
	
	public String StringifyTiebreakerMatches() {
		if (tiebreakers.size() == 0)
			return "";
		
		String s = "Group " + label + " Tiebreakers\n";
		int x = 0;
		for (int i = 0; i < tiebreakers.size(); i++) {
			Matchup m = tiebreakers.get(i);
			if (x == tiebreakers.size() - 1) {
				s += "\n" + m.toString();
			} else {
				s += "\n" + m.toString() + "\n";
				s += Strings.SmallLineBreak + "\n";
			}
			x++;
		}
		return s;
	}
}