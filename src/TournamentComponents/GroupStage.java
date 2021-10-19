package TournamentComponents;

import java.util.List;

import StaticVariables.Settings;
import StaticVariables.Strings;
import Stats.Standings;

public abstract class GroupStage extends TournamentComponent {
	private List<Group> groups;
	
	private Tournament partOf;
	
	public GroupStage(String label, Tournament partOf) {
		super();
		this.partOf = partOf;
		setLabel(label);
	}

	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public void SetQualified(List<Group> groups, Standings standings) {
		for (Group g : groups) {
			g.SetQualified(getLabel(), standings, groups.size());
		}
		
		// Set the remaining number of teams for the standings
		int teamsThisStage = groups.size() * (groups.get(0).getCapacity() - groups.get(0).getTopXEscape());
		standings.subtractTeams(teamsThisStage);
	}
	
	public List<Group> getGroups() {
		return groups;
	}

	public void setGroups(List<Group> groups) {
		this.groups = groups;
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
				s += "\n" + g.StringifyGroupParticipants() + "\n";
				s += Strings.MediumLineBreak + "\n";
				if (Settings.PRINT_GROUP_STAGE_GAMES) {
					s += "\n" + g.StringifyMatches() + "\n";
					s += Strings.MediumLineBreak + "\n";	
				}
				s += "\n" + g.toStandings(super.getLabel(), false) + "\n";
				
				if (g.getNumTiebreakers() > 0) {
					if (Settings.PRINT_GROUP_STAGE_GAMES) {
						s += Strings.MediumLineBreak + "\n";
						s += "\n" + g.StringifyTiebreakerMatches() +"\n";	
					}
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
				}
			} else {
				s += "\n" + g.StringifyGroupParticipants() + "\n";
				s += Strings.MediumLineBreak + "\n";
				if (Settings.PRINT_GROUP_STAGE_GAMES) {
					s += "\n" + g.StringifyMatches() + "\n";
					s += Strings.MediumLineBreak + "\n";	
				}
				s += "\n" + g.toStandings(super.getLabel(), false) + "\n";
				
				if (g.getNumTiebreakers() > 0) {
					if (Settings.PRINT_GROUP_STAGE_GAMES) {
						s += Strings.MediumLineBreak + "\n";
						s += "\n" + g.StringifyTiebreakerMatches() +"\n";
					}
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
				}
				s += Strings.LargeLineBreak + "\n";
			}
			x++;
		}
		
		if (Settings.PRINT_GROUP_STAGE_SUMMARY) {
			s += Strings.MediumLineBreak + "\n\n";
			s += "Summary\n";
			s += Strings.MediumLineBreak + "\n";
			
			x = 0;
			for (int i = 0; i < groups.size(); i++) {
				Group g = groups.get(i);
				if (x == groups.size() - 1) {
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
				} else {
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
					s += Strings.MediumLineBreak + "\n";
				}
				x++;
			}	
		}
		
		return s.substring(0, s.length() - 1);
	}
}
