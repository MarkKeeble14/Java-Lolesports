package TournamentComponents;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import Classes.Group;
import Classes.Tournament;
import Matches.Game;
import Misc.Strings;
import Misc.Util;
import TournamentSimulator.DomesticDriver;

public abstract class GroupStage extends TournamentComponent {
	private List<Group> groups;
	
	private Tournament partOf;
	
	public GroupStage(Tournament partOf) {
		super();
		this.partOf = partOf;
	}

	public abstract void Simulate(String label, List<Group> groups) throws Exception;
	
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
				s += "\n" + g.StringifyMatches() + "\n";
				s += Strings.MediumLineBreak + "\n";
				s += "\n" + g.toStandings(super.getLabel(), false) + "\n";
				
				if (g.getNumTiebreakers() > 0) {
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.StringifyTiebreakerMatches() +"\n";	
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
				}
			} else {
				s += "\n" + g.StringifyGroupParticipants() + "\n";
				s += Strings.MediumLineBreak + "\n";
				s += "\n" + g.StringifyMatches() + "\n";
				s += Strings.MediumLineBreak + "\n";
				s += "\n" + g.toStandings(super.getLabel(), false) + "\n";
				
				if (g.getNumTiebreakers() > 0) {
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.StringifyTiebreakerMatches() +"\n";
					s += Strings.MediumLineBreak + "\n";
					s += "\n" + g.toStandings(super.getLabel(), true) + "\n";
				}
				s += Strings.LargeLineBreak + "\n";
			}
			x++;
		}
		
		if (DomesticDriver.PRINT_GROUP_STAGE_SUMMARY) {
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
