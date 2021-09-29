package bracketgen;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Pool {
	private List<Team> teams;
	
	/**
	* Constructor
	* @param teamsToAdd The teams in the pool as a dynamic parameter
	*/
	public Pool( Team ...teamsToAdd) {
		teams = new ArrayList<Team>();
		for (Team t : teamsToAdd) {
			teams.add(t);
		}
	}
	
	/**
	* Constructor
	* @param teamsToAdd The teams in the pool as a list
	*/
	public Pool(List<Team> teamsToAdd) {
		teams = teamsToAdd;
	}
	
	/**
	* Copy Constructor
	* @param p The pool to copy
	*/
	public Pool(Pool p) {
		teams = new ArrayList<Team>();
		for (Team t : p.getPool()) {
			teams.add(t);
		}
	}
	
	// Draw a team at complete random
	public Team Draw() {
		Team t = teams.get((int) (Math.random() * teams.size()));
		teams.remove(t);
		return t;
	}
	
	public int size() {
		return teams.size();
	}
	
	public String StringifyPool() {
		String s = "";
		for (Team t : teams) {
			if (t == teams.get(teams.size() - 1)) {
				s += t.getTag() + ": " +  t.getRegion();
			} else {
				s += t.getTag() + ": " +  t.getRegion() + ", ";	
			}
		}
		return s;
	}
	
	/**
	*  Used for group stage 
	*  Draws a team ensuring that there are no two teams in the same group from the same region
	*  
	*  Only simulates through one pool, so there can still be incorrect draws with combonation of teams that can have a legal draw
	* 
	*  @param p The pool of teams to draw from
	*  @param A list of teams already attempted to be drawn; pass in an empty ArrayList<Team> if you are calling the method from outside itself
	*  @param groups A list of the groups
	 * @throws ImpossibleDrawException 
	 * @throws Exception 
	*/
	public Team DrawWithSameRegionRule(Group startingGroup, ArrayList<Group> groups, int sizeThisDraw, ArrayList<Team> attempted) throws ImpossibleDrawException {
		// Make a copy of the initial Pool
		Pool copy = new Pool();
		for (Team t : teams) {
			copy.Add(t);
		}
		
		// Compare the initial pool with the list of teams that have already been attempted;
		// Create a new pool consisting of the teams which have not been attempted.
		Pool availableOptions = new Pool(GetElementsNotIn(copy.getPool(), attempted));
		
		// If you've attempted every team in the pool and nothing works, the situation is impossible to resolve according to the rule
		if (availableOptions.size() == 0) {
			String teamsString = StringifyPool();
			String groupsString = "";
			for (Group g : groups) {
				groupsString += g.StringifyGroup();
			}
			throw new ImpossibleDrawException(teamsString, groupsString, "Same Group, Same Region");
		}
		
		// Choose a random team from the available options
		int sizeOfPool = availableOptions.size();
		int rand = (int) (Math.random() * sizeOfPool);
		Team t = availableOptions.GetAt(rand);
	
		// Rules to determine legality of the draw
		// First Rule, can't have two teams from the same region in the same group
		for (Team gt : startingGroup.getGroup()) {
			if (t.getRegion() == gt.getRegion()) {
				attempted.add(t);
				t = DrawWithSameRegionRule(startingGroup, groups, sizeThisDraw, attempted);
				break;
			}
		}
		
		// Second Rule, the first rule must not leave the bracket in an impossible situation
		for (int i = 0; i < groups.size(); i++) {
			Group testingGroup = groups.get(i);
			// if the group is not already full (if it is, no need to verify that it can still draw a legal team
			if (!(testingGroup.getSize() == sizeThisDraw)) {
				// Create a list of potential legal draws based on the rule
				List<Team> potentialDraws = testingGroup.FilterRegion(copy);
				if (potentialDraws.size() == 0) {
					attempted.add(t);
					t = DrawWithSameRegionRule(startingGroup, groups, sizeThisDraw, attempted);
					break;
				} else if (potentialDraws.contains(t)) {
					copy.Remove(t);
				}
			}
		}
		
		// Drawn team is legal, remove it from the true pool and return it
		teams.remove(t);
		return t;	
	}
	
	/**
	* Used for knockout stage
	* Ensures that the match is not populated with two teams from the same group
	* 
	* @param match The match you're drawing into
	* @param p The pool of teams to draw from
	* @param A list of teams already attempted to be drawn; pass in an empty ArrayList<Team> if you are calling the method from outside itself
	* @param matches A list of the matches at this stage in the bracket
	* @param groups A list of the groups
	*/
	public Team DrawWithSameMatchRule(Match match, Pool p, List<Team> attempted, 
			ArrayList<Match> matches, ArrayList<Group> groups) throws Exception {
		// If you've attempted every team in the pool and nothing works, the situation is impossible to resolve according to the rule
		if (attempted.size() == p.getPool().size()) {
			throw new ImpossibleDrawException(p.toString(), groups.toString(), "Same Match, Same Group");
		}
		
		// Make a copy of the initial Pool
		Pool copy = new Pool();
		for (Team t : p.getPool()) {
			copy.Add(t);
		}
		
		// Compare the initial pool with the list of teams that have already been attempted;
		// Create a new pool consisting of the teams which have not been attempted.
		Pool availableOptions = new Pool(GetElementsNotIn(copy.getPool(), attempted));
		
		// Choose a random team from the available options
		int sizeOfPool = availableOptions.size();
		int rand = (int) (Math.random() * sizeOfPool);
		Team t = availableOptions.GetAt(rand);
		
		// Rules to determine legality of the draw
		// First Rule, can't have two teams from the same group facing off
		boolean bA = Group.FindGroup(t, groups) == Group.FindGroup(match.getTeamA(), groups);
		boolean bB = Group.FindGroup(t, groups) == Group.FindGroup(match.getTeamB(), groups);
		if (bA || bB) {
			attempted.add(t);
			t = DrawWithSameMatchRule(match, p, attempted, matches, groups);
		}
		
		// Second Rule, the first rule must not leave the bracket in an impossible situation
		for (int i = 1; i < matches.size(); i++) {
			Match currentMatch = matches.get(i);
			// If the match isn't already filled
			if (currentMatch.getTeamA() != null && currentMatch.getTeamB() != null) {
				continue;
			}

			// Create a list of all the legal draws into the current match
			List<Team> potentialDraws = new ArrayList<Team>();
			for (Team team : copy.getPool()) {
				boolean bC = Group.FindGroup(team, groups) != Group.FindGroup(currentMatch.getTeamA(), groups);
				boolean bD = Group.FindGroup(team, groups) != Group.FindGroup(currentMatch.getTeamB(), groups);
				if (bC && bD) {
					potentialDraws.add(team);
				}
			}
			// If there are no legal draws, attempt a different team
			if (potentialDraws.size() == 0) {
				attempted.add(t);
				t = DrawWithSameMatchRule(match, p, attempted, matches, groups);
			} else if (potentialDraws.contains(t)) {
				copy.Remove(t);
			}
		}
		
		teams.remove(t);
		return t;
	}
	
	/**
	* Used for knockout stage
	* Ensures that the side if the bracket made up of match1 and match2 are not from teams from the same group
	* 
	* @param match1 One match on the side of the bracket
	* @param match2 The other match on the side of the bracket
	* @param p The pool of teams to draw from
	* @param A list of teams already attempted to be drawn; pass in an empty ArrayList<Team> if you are calling the method from outside itself
	* @param matches A list of the matches at this stage in the bracket
	* @param groups A list of the groups
	*/
	public Team DrawWithSameSideRule(Match match1, Match match2, Pool p, 
			List<Team> attempted, ArrayList<Match> matches, ArrayList<Group> groups) throws Exception {
		// If you've attempted every team in the pool and nothing works, the situation is impossible to resolve according to the rule
		if (attempted.size() == p.getPool().size()) {
			throw new ImpossibleDrawException(p.toString(), groups.toString(), "Same Side, Same Group");
		}
		
		// Make a copy of the initial Pool
		Pool copy = new Pool();
		for (Team t : p.getPool()) {
			copy.Add(t);
		}
		
		// Compare the initial pool with the list of teams that have already been attempted;
		// Create a new pool consisting of the teams which have not been attempted.
		Pool availableOptions = new Pool(GetElementsNotIn(copy.getPool(), attempted));
		
		// Choose a random team from the available options
		int sizeOfPool = availableOptions.size();
		int rand = (int) (Math.random() * sizeOfPool);
		Team t = availableOptions.GetAt(rand);
		
		// Rules to determine legality of the draw
		// First Rule, teams from the same group must be on opposite sides of the bracket
		boolean bA = Group.FindGroup(t, groups) == Group.FindGroup(match1.getTeamA(), groups);
		boolean bB = Group.FindGroup(t, groups) == Group.FindGroup(match1.getTeamB(), groups);
		boolean bC = Group.FindGroup(t, groups) == Group.FindGroup(match2.getTeamA(), groups);
		boolean bD = Group.FindGroup(t, groups) == Group.FindGroup(match2.getTeamB(), groups);
		if (bA || bB || bC || bD) {
			attempted.add(t);
			t = DrawWithSameSideRule(match1, match2, p, attempted, matches, groups);
		}
		
		// Second Rule, the first rule must not leave the bracket in an impossible situation
		for (int i = 1; i < matches.size(); i++) {
			Match currentMatch = matches.get(i);
			// If the match isn't already filled
			if (currentMatch.getTeamA() != null && currentMatch.getTeamB() != null) {
				continue;
			}
			// Create a list of all the legal draws into the current match
			List<Team> potentialDraws = new ArrayList<Team>();
			for (Team team : copy.getPool()) {
				if (Group.FindGroup(team, groups) != Group.FindGroup(currentMatch.getTeamA(), groups)) {
					potentialDraws.add(team);
				}
			}
			// If there are no legal draws, attempt a different team
			if (potentialDraws.size() == 0) {
				attempted.add(t);
				t = DrawWithSameSideRule(match1, match2, p, attempted, matches, groups);
			} else if (potentialDraws.contains(t)) {
				copy.Remove(t);
			}
		}
		
		teams.remove(t);
		return t;
	}
	
	public void Add(Team t) {
		teams.add(t);
	}
	
	public void Remove(Team t) {
		teams.remove(t);
	}

	public List<Team> getPool() {
		return teams;
	}

	public Team GetAt(int i) {
		return teams.get(i);
	}
	
	// Compares two lists and returns the items in List A that are not present in List B
	private List<Team> GetElementsNotIn(List<Team> A, List<Team> B) {
		List<Team> result = new ArrayList<Team>();
		for (Team t : A) {
			if (!B.contains(t)) {
				result.add(t);
			}
		}
		return result;
	}
	
	@Override
	public String toString() {
		return "Pool [pool=" + teams + "]";
	}
}
