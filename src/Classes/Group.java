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
	
	// private List<Matchup> matchups = new ArrayList<Matchup>();
	private Map<Team, Map<Team, List<Matchup>>> matchups = new HashMap<Team, Map<Team, List<Matchup>>>();
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
	
	/**
	* Copy Constructor
	* @param g The group to copy
	*/
	public Group(Group g) {
		this.label = g.getLabel();
		this.capacity = g.getCapacity();
		this.gamesPerRoundRobin = g.getGamesPerRoundRobin();
		this.matchesAreBOX = g.getMatchesAreBOX();
		this.partOf = g.getPartOf();
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
	
	public void PlaceTeamsInPosition() {
		for (int i = 0; i < teams.size(); i++) {
			Team t = teams.get(i);
			postTBStandings.put(t, i + 1);
		}
	}
	
	// Simulates the group playing out by sorting the group in order of descending rating
	public void BinarySimulate() {
		Collections.sort(teams);
		for (int i = 1; i < teams.size() + 1; i++) {
			preTBStandings.put(teams.get(i - 1), teams.size() - i + 1);
		}
		preTBStandings = MapUtil.sortByIntegerValue(preTBStandings);
	}
	
	public void SetupMatches(String stageLabel, RegionalWLTracker tracker) throws Exception {
		// Make a copy of the initial Group
		Group copy = new Group("Copy", capacity, gamesPerRoundRobin, matchesAreBOX, partOf);
		for (Team t : teams) {
			t.setNewRecord(stageLabel);
			copy.Add(t);
		}
		
		// Add Matches
		while(copy.getSize() > 0) {
			Team t = copy.getGroup().get(0);
			for (int p = 0; p < copy.getGroup().size(); p++) {
				Team t2 = copy.getGroup().get(p);
				if (t != t2) {
					for (int q = getNumMatchupsBetweenTeams(t, t2); q < gamesPerRoundRobin; q++) {
						if (matchesAreBOX > 1) {
							Series S = new Series(stageLabel, 1, matchesAreBOX, t, t2, tracker);
							addMatchupToMatchups(t, t2, S);
						} else {
							Game M = new Game(stageLabel, 1, t, t2, tracker);
							addMatchupToMatchups(t, t2, M);
						}
					}	
				}
			}
			copy.Remove(t);
		}	
	}
	
	public void SimulatePresetMatches(String stageLabel, RegionalWLTracker tracker, boolean simulateTiebreakers) throws Exception {
		// Simulate Matches
		Set<Entry<Team, Map<Team, List<Matchup>>>> set = matchups.entrySet();
		for (Entry<Team, Map<Team, List<Matchup>>> entry : set) {
			Set<Entry<Team, List<Matchup>>> set2 = entry.getValue().entrySet();
			for (Entry<Team, List<Matchup>> entry2 : set2) {
				List<Matchup> innerMatchups = entry2.getValue();
				for (Matchup m : innerMatchups) {
					if (!m.resultDetermined()) {
						m.Simulate();	
					}
				}
			}
		}
		
		preTBStandings = SortStandingsPreTiebreakers();
		
		// Tiebreakers
		if (simulateTiebreakers && tiebreakersRequired()) {
			SimulateTiebreakers(stageLabel, tracker);
		} else {
			postTBStandings = preTBStandings;
		}
	}
	
	public void FullSimulate(String stageLabel, RegionalWLTracker tracker, boolean simulateTiebreakers) throws Exception {
		SetupMatches(stageLabel, tracker);
		SimulatePresetMatches(stageLabel, tracker, simulateTiebreakers);
	}
	
	private int getNumMatchupsBetweenTeams(Team t, Team t2) {
		Map<Team, List<Matchup>> nMap = matchups.get(t);
		if (nMap == null) {
			return 0;
		}
		List<Matchup> nList = nMap.get(t2);
		if (nList == null) {
			return 0;
		}
		return nList.size();
	}
	
	private void addMatchupToMatchups(Team t, Team t2, Matchup m) {
		Map<Team, List<Matchup>> nMap = matchups.get(t);
		if (nMap == null) {
			nMap = new HashMap<Team, List<Matchup>>();
		}
		List<Matchup> nList = nMap.get(t2);
		if (nList == null) {
			nList = new ArrayList<Matchup>();
		}
		nList.add(m);
		nMap.put(t2, nList);
		matchups.put(t, nMap);
	}
	
	public void addResultToGameMatchup(Team A, Team B, Team winner, Team loser, boolean allowLoop) {
		// Update map for the winner
		Map<Team, List<Matchup>> nMap = matchups.get(A);
		if (nMap == null) {
			nMap = new HashMap<Team, List<Matchup>>();
		}
		
		List<Matchup> nList = nMap.get(B);
		if (nList == null) {
			nList = new ArrayList<Matchup>();
		}
		
		boolean hasSet = false;
		for (int i = 0; i < nList.size(); i++) {
			Game g = (Game) nList.get(i);
			if (!g.resultDetermined()) {
				g.setResult(winner, loser);	
				hasSet = true;
				break;
			}
		}
		if (!hasSet && allowLoop) {
			addResultToGameMatchup(B, A, winner, loser, false);
		}
	}
	
	public void addResultToSeriesMatchup(Team winner, Team loser, int winnerGS, int loserGS) {
		Map<Team, List<Matchup>> nMap = matchups.get(winner);
		if (nMap == null) {
			nMap = new HashMap<Team, List<Matchup>>();
		}
		List<Matchup> nList = nMap.get(loser);
		if (nList == null) {
			nList = new ArrayList<Matchup>();
		}
		
		for (int i = 0; i < nList.size(); i++) {
			Series s = (Series) nList.get(i);
			if (s.getWinner() == null) {
				s.setResult(winner, loser, winnerGS, loserGS);	
				break;
			}
		}
	}
	
	private boolean tiebreakersRequired() {
		Object[] a = preTBStandings.keySet().toArray();
		for (int i = a.length - 1; i > 0; i--) {
			Team t1 = (Team)a[i];
			Team t2 = (Team)a[i - 1];
			
			if (t1.getRecord().getWins(false) == t2.getRecord().getWins(false)) {
				return true;
			}
		}
		return false;
	}
	
	private void SimulateTiebreakers(String stageLabel, RegionalWLTracker tracker) {
		Map<Team, Integer> fs = new HashMap<Team, Integer>();
		Object[] a = preTBStandings.keySet().toArray();
		for (int i = a.length - 1; i > 0; i--) {
			Team t1 = (Team)a[i];
			Team t2 = (Team)a[i - 1];
			
			Record t1r = t1.getRecord();
			Record t2r = t2.getRecord();
			
			if (t1r.getWins(false) == t2r.getWins(false)) {
				Game M = new Game(stageLabel, 1, t1, t2, tracker);
				
				M.TBSimulate();
				tiebreakers.add(M);
				
				Team winner = M.getWinner();
				
				if (winner == t1) {
					// Swap the two teams
					a[i] = t2;
					a[i - 1] = t1;
				} else {
					// Teams are already in correct order
				}
			}
		}
		
		// Place teams into the final standings
		for (int i = 0; i < a.length; i++) {
			fs.put((Team)a[i], i + 1);
		}
		
		postTBStandings = MapUtil.sortByIntegerValue(fs);
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
		String s = "Group " + label + " Games\n" + Strings.MediumLineBreak + "\n";
		int x; int y; int z;
		
		Set<Entry<Team, Map<Team, List<Matchup>>>> set = matchups.entrySet();
		x = 0;
		for (Entry<Team, Map<Team, List<Matchup>>> entry : set) {
			Set<Entry<Team, List<Matchup>>> set2 = entry.getValue().entrySet();
			y = 0;
			for (Entry<Team, List<Matchup>> entry2 : set2) {
				List<Matchup> innerMatchups = entry2.getValue();
				z = 0;
				for (Matchup m : innerMatchups) {
					s += "\n" + m.toString();
					
					if (x != set.size() - 1 || y != set2.size() - 1 || z != innerMatchups.size() - 1) {
						s += "\n" + Strings.SmallLineBreak + "\n";
					}
					z++;
				}
				y++;
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