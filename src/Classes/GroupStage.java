package Classes;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Misc.Strings;
import Misc.Util;

public abstract class GroupStage extends TournamentComponent {
	private List<Group> groups;
	
	private Map<Group, List<Match>> groupMatchMap = new HashMap<Group, List<Match>>();
	
	private Map<Group, List<Match>> groupTiebreakerMatchMap = new HashMap<Group, List<Match>>();
	
	private Tournament partOf;
	
	public GroupStage(Tournament partOf) {
		super();
		this.partOf = partOf;
	}

	public abstract void Simulate(String label, List<Group> groups) throws Exception;
	
	public void AddMatches(Group g, Match ...matches) {
		if (!groupMatchMap.containsKey(g)) {
			groupMatchMap.put(g, new ArrayList<Match>());
		}
		List<Match> cMatches = groupMatchMap.get(g);
		for (Match m : matches) {
			cMatches.add(m);
		}
		groupMatchMap.put(g, cMatches);
	}
	
	public void AddTiebreakerMatches(Group g, Match ...matches) {
		if (!groupTiebreakerMatchMap.containsKey(g)) {
			groupTiebreakerMatchMap.put(g, new ArrayList<Match>());
		}
		List<Match> cMatches = groupTiebreakerMatchMap.get(g);
		for (Match m : matches) {
			cMatches.add(m);
		}
		groupTiebreakerMatchMap.put(g, cMatches);
	}
	
	public List<Match> GetMatches(Group g) {
		return groupMatchMap.get(g);
	}
	
	public List<Match> GetTiebreakerMatches(Group g) {
		return groupTiebreakerMatchMap.get(g);
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void PrintGroups() {
		for (Group g : groups) {
			g.PrintStandings();
		}
	}

	public Tournament getPartOf() {
		return partOf;
	}
	
	public String StringifyMatches(Group g) {
		String s = Strings.MediumLineBreak + "\n\nGroup Stage Games\n" + Strings.MediumLineBreak + "\n\n";
		List<Match> matches = GetMatches(g);
		int x = 0;
		
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			if (x == matches.size() - 1) {
				s += m.toString();
			} else {
				s += m.toString() + "\n";
			}
			x++;
		}
		
		return s;
	}
	
	public String StringifyTiebreakerMatches(Group g) {
		List<Match> matches = GetTiebreakerMatches(g);
		if (matches == null)
			return "";
		
		String s = Strings.MediumLineBreak + "\n\nTiebreakers\n" + Strings.MediumLineBreak + "\n\n";
		int x = 0;
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			if (x == matches.size() - 1) {
				s += m.toString();
			} else {
				s += m.toString() + "\n";
			}
			x++;
		}
		return s;
	}
	
	@Override
	public String toString() {
		String s = "";
		int x = 0;
		for (int i = 0; i < groups.size(); i++) {
			Group g = groups.get(i);
			if (x == groups.size() - 1) {
				s += g.toStandings(super.getLabel()) + "\n";
				s += StringifyMatches(g);
				s += StringifyTiebreakerMatches(g);
			} else {
				s += g.toStandings(super.getLabel()) + "\n";
				s += StringifyMatches(g);
				s += StringifyTiebreakerMatches(g);
				s += Strings.MediumLineBreak + "\n";
			}
			x++;
		}
		
		return s.substring(0, s.length() - 1);
	}
}
