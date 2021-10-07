package Classes;

import java.util.List;

import Misc.Strings;
import Misc.Util;

public abstract class DrawStructure extends TournamentComponent {
	private List<Group> groups;
	
	private Tournament partOf;
	
	public DrawStructure(Tournament partOf) {
		super();
		this.partOf = partOf;
	}

	public abstract void Simulate(String label, List<Group> groups, List<Pool> pools) throws Exception;
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void PrintGroups() {
		for (Group g : groups) {
			System.out.println(g.toString());
		}
	}

	public Tournament getPartOf() {
		return partOf;
	}
	
	@Override 
	public String toString() {
		String s = "";
		int x = 0;
		for (int i = 0; i < groups.size(); i++) {
			Group g = groups.get(i);
			if (x == groups.size() - 1) {
				s += g.StringifyGroup();
			} else {
				s += g.StringifyGroup() + "\n";
				s += Strings.MediumLineBreak + "\n";
			}
			x++;
		}
		return s;
	}
}
