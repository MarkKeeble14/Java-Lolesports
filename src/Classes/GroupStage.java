package Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class GroupStage {
	private List<Match> matches = new ArrayList<Match>();
	
	private List<Group> groups;
	
	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public void AddMatches(Match ...matches) {
		for (Match m : matches) {
			this.matches.add(m);
		}
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
}
