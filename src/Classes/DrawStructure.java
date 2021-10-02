package Classes;

import java.util.List;

public abstract class DrawStructure {
	private List<Group> groups;
	
	public abstract void Simulate(List<Group> groups, List<Pool> pools) throws Exception;
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
	}

	public void PrintGroups() {
		for (Group g : groups) {
			System.out.println(g);
		}
	}
}
