package TournamentComponents;

import java.util.ArrayList;
import java.util.Collection;
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
		
		Set<Entry<String, BracketSection>> set = bracketSections.entrySet();
		List<Series> listOfSeries = new ArrayList<Series>();
		for (Entry<String, BracketSection> entry : set) {
			List<Series> seriesList = entry.getValue().getSeriesList();
			for (int i = 0; i < seriesList.size(); i++) {
				listOfSeries.add(seriesList.get(i));
			}
		}
		sortSLListByLabel(listOfSeries);
		
		int x = 0;
		for (int i = 0; i < listOfSeries.size(); i++) {
			Series m = listOfSeries.get(i);
			
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
			if (x == listOfSeries.size() - 1) {
				s += "\n" + m.toString();
			} else {
				s += "\n" + m.toString() + "\n";
				s += Strings.SmallLineBreak + "\n";
			}
			x++;
		}
		
		// PRINTING BRACKET SUMMARY
		if (GlobalVariables.PRINT_BRACKET_SUMMARY) {
			s += "\n" + Strings.LargeLineBreak + "\n\nBracket Summary";
			s += "\n" + Strings.SmallLineBreak + "\n" + "\n";
			
			int mostEntriesVertical = 0;
			int horizontalEntries = 0;
			Object[] toConv = bracketSections.values().toArray();
			List<BracketSection> convSections = convertObjectArrayToBracketSectionList(toConv);			
			sortBSListByLabel(convSections);
			
			for (int i = 0; i < convSections.size(); i++) {
				List<Series> seriesList = convSections.get(i).getSeriesList();
				if (seriesList.size() > mostEntriesVertical) {
					mostEntriesVertical = seriesList.size();
				}
				horizontalEntries++;
			}
			
			Series[][] bracketSummary = new Series[horizontalEntries][mostEntriesVertical];
			int y = 0;
			for (int i = 0; i < convSections.size(); i++) {
				BracketSection bs = convSections.get(i);
				List<Series> seriesList = bs.getSeriesList();
				for (int p = 0; p < seriesList.size(); p++) {
					int xIndex = y;
					int yIndex = p;
					bracketSummary[xIndex][yIndex] = seriesList.get(p);
				}
				y++;
			}
			
			
			for (int i = 0; i < convSections.size(); i++) {
				BracketSection bs = convSections.get(i);
				s += String.format(Strings.BracketSeriesFormatSingle, bs.getLabel()); 
			}
			s += "\n";
			
			int q = 0;
			while (q < mostEntriesVertical) {
				for (int p = 0; p < horizontalEntries; p++) {
					Series[] column = bracketSummary[p];
					Series series = column[q];
					if (series != null) {
						s += String.format(Strings.BracketSeriesFormatSingle, 
								series.getTeamA().getTag() + " : " + series.getTeamB().getTag());
					} else {
						s += String.format(Strings.BracketSeriesFormatSingle, ""); 
					}
					if (p == horizontalEntries - 1) {
						q++;
						s += "\n";
					}
				}	
			}
			s += Strings.MediumLineBreak + "\n";
		}
		// PRINTING BRACKET SUMMARY
		
		return s;
	}
	
	private void sortSLListByLabel(List<Series> seriesList) {
		seriesList.sort(new Comparator<Series>() {
			@Override
			public int compare(Series lhs, Series rhs) {
				return lhs.getLabel() < rhs.getLabel() ? -1 : (lhs.getLabel() < rhs.getLabel()) ? 1 : 0;
			}
		});
	}
	
	private List<BracketSection> convertObjectArrayToBracketSectionList(Object[] toConv) {
		List<BracketSection> res = new ArrayList<BracketSection>();
		for (int i = 0; i < toConv.length; i++) {
			res.add((BracketSection)toConv[i]);
		}
		return res;
	}
	
	private void sortBSListByLabel(List<BracketSection> sections) {
		sections.sort(new Comparator<BracketSection>() {
			@Override
			public int compare(BracketSection lhs, BracketSection rhs) {
				return lhs.getOrder() < rhs.getOrder() ? -1 : (lhs.getOrder() < rhs.getOrder()) ? 1 : 0;
			}
		});
	}
}