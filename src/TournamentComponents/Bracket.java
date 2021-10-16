package TournamentComponents;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import Classes.BracketSection;
import Classes.Group;
import Classes.Tournament;
import Matches.Game;
import Matches.Series;
import Misc.Strings;
import QualificationDetails.QualificationDetails;
import StatsTracking.EOTStandings;
import Teams.Team;
import Misc.GlobalVariables;

public abstract class Bracket extends TournamentComponent {
	private Map<String, BracketSection> bracketSections = new HashMap<String, BracketSection>();
	
	private Series championshipMatch;
	private Team champion;
	private Team runnerUp;
	
	private Tournament partOf;
	
	private String teamsQThroughLabel;
	
	private List<Team> seenTeams = new ArrayList<Team>();
	
	public Bracket(String label, Tournament partOf) {
		super();
		this.partOf = partOf;
		
		setLabel(label);
	}
	
	public Bracket(String label, Tournament partOf, String fedTeamsThrough) {
		super();
		this.partOf = partOf;
		
		setLabel(label);
		teamsQThroughLabel = fedTeamsThrough;
	}

	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public BracketSection getBracketSection(String label) {
		return bracketSections.get(label);
	}
	
	public Series getSeries(int no) throws Exception {
		Set<Entry<String, BracketSection>> set = bracketSections.entrySet();
		for (Entry<String, BracketSection> entry : set) {
			BracketSection section = entry.getValue();
			Series s;
			if ((s = section.getSeries(no)) != null) {
				return s;
			}
		}
		return null;
	}
	
	public void addBracketSection(BracketSection bracketSection) {
		bracketSections.put(bracketSection.getLabel(), bracketSection);
	}
	
	public void addBracketSections(BracketSection... bracketsToAdd) {
		for (BracketSection s : bracketsToAdd) {
			bracketSections.put(s.getLabel(), s);	
		}
	}
	
	public void setChampionshipSeries(Series s) {
		championshipMatch = s;
		setChampion(s.getWinner());
		setRunnerUp(s.getLoser());
	}
	
	public Series getChampionshipSeries() {
		return championshipMatch;
	}

	public void setChampion(Team champion) {
		this.champion = champion;
	}
	
	public Team getChampion() {
		return champion;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}

	public void setRunnerUp(Team runnerUp) {
		this.runnerUp = runnerUp;
	}

	public Tournament getPartOf() {
		return partOf;
	}
	
	@Override
	public String toString() {
		String s = "";
		
		if (GlobalVariables.PRINT_BRACKET_SUMMARY) {
			s += "\nBracket Summary";
			s += "\n" + Strings.SmallLineBreak + "\n";
			
			Set<Entry<String, BracketSection>> set = bracketSections.entrySet();
			Map<Integer, List<Series>> map = new HashMap<Integer, List<Series>>();
			for (Entry<String, BracketSection> entry : set) {
				List<Series> seriesList = entry.getValue().getSeriesList();
				for (int i = 0; i < seriesList.size(); i++) {
					if (map.get(i) == null) {
						List<Series> nList = new ArrayList<Series>();
						nList.add(seriesList.get(i));
						map.put(i, nList);
					} else {
						List<Series> nList = map.get(i);
						nList.add(seriesList.get(i));
						map.put(i, nList);
					}
				}
			}
			Set<Entry<Integer, List<Series>>> set2 = map.entrySet();
			for (Entry<Integer, List<Series>> entry : set2) {
				String bracketLineS = "\n";
				List<Series> seriesList = entry.getValue();
				sortListByLabel(seriesList);
				for (int i = 0; i < seriesList.size(); i++) {
					Series m = seriesList.get(i);
					bracketLineS += m.getLabel() + ": " + m.getTeamA().getTag() + " VS " + m.getTeamB().getTag() + "\t\t";
				}
				bracketLineS += "\n";
				s += bracketLineS;
			}
			s += Strings.MediumLineBreak + "\n";
		}
		
		Set<Entry<String, BracketSection>> set = bracketSections.entrySet();
		List<Series> series = new ArrayList<Series>();
		for (Entry<String, BracketSection> entry : set) {
			List<Series> seriesList = entry.getValue().getSeriesList();
			for (int i = 0; i < seriesList.size(); i++) {
				series.add(seriesList.get(i));
			}
		}
		sortListByLabel(series);
		
		int x = 0;
		for (int i = 0; i < series.size(); i++) {
			Series m = series.get(i);
			
			if (GlobalVariables.PRINT_QUALIFICATION_REASONS && teamsQThroughLabel != "") {
				Team a = m.getTeamA();
				Team b = m.getTeamB();
				
				QualificationDetails aQD = a.getQD(teamsQThroughLabel);
				QualificationDetails bQD = b.getQD(teamsQThroughLabel);
				
				if (!seenTeams.contains(a) && aQD != null || !seenTeams.contains(b) && bQD != null) {
					s += "\n";
				}
				
				if (aQD != null && !seenTeams.contains(a)) {
					s += aQD;
					
					s += "\n" + Strings.SmallLineBreak;
					
					seenTeams.add(a);
					
					if (!seenTeams.contains(b) && bQD != null) {
						s += "\n\n";
					} else {
						s += "\n";
					}
				}
				if (bQD != null && !seenTeams.contains(b)) {
					s += bQD;
					
					s += "\n" + Strings.SmallLineBreak + "\n";
					
					seenTeams.add(b);
				}
			}
			if (x == bracketSections.size() - 1) {
				s += "\n" + m.toString();
			} else {
				s += "\n" + m.toString() + "\n";
				s += Strings.SmallLineBreak + "\n";
			}
			x++;
		}
		
		return s;
	}
	
	private void sortListByLabel(List<Series> seriesList) {
		seriesList.sort(new Comparator<Series>() {
			@Override
			public int compare(Series lhs, Series rhs) {
				return lhs.getLabel() < rhs.getLabel() ? -1 : (lhs.getLabel() < rhs.getLabel()) ? 1 : 0;
			}
		});
	}
}