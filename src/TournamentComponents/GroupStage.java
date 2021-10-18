package TournamentComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DefiningMatches.Game;
import DefiningTeams.Team;
import Drivers.DomesticDriver;
import StaticVariables.Settings;
import StaticVariables.Strings;
import Utility.Util;

public abstract class GroupStage extends TournamentComponent {
	private List<Group> groups;
	
	private Tournament partOf;
	
	public GroupStage(String label, Tournament partOf) {
		super();
		this.partOf = partOf;
		setLabel(label);
	}

	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public abstract void SetQualified(List<Group> groups, List<Team> teams);
	
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
