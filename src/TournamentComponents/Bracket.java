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

import DefiningMatches.Game;
import DefiningMatches.Series;
import DefiningQualificationDetails.QualificationDetails;
import DefiningTeams.Team;
import Stats.Standings;
import StaticVariables.Settings;
import StaticVariables.Strings;

public abstract class Bracket extends TournamentComponent {
	private Map<String, BracketSlice> bracketSections = new HashMap<String, BracketSlice>();
	
	private List<Series> championshipMatches = new ArrayList<Series>();
	
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
	
	public abstract void Simulate(Bracket b, List<Group> groups) throws Exception;
	
	public void SetQualified(Standings standings) {
		for (Series s : championshipMatches) {
			s.SetQualified(getLabel(), standings);
		}
	}
	
	public BracketSlice getBracketSection(String label) {
		return bracketSections.get(label);
	}
	
	public Series getSeries(int no) throws Exception {
		Set<Entry<String, BracketSlice>> set = bracketSections.entrySet();
		for (Entry<String, BracketSlice> entry : set) {
			BracketSlice section = entry.getValue();
			Series s;
			if ((s = section.getSeries(no)) != null) {
				return s;
			}
		}
		return null;
	}
	
	public void addBracketSection(BracketSlice bracketSection) {
		bracketSections.put(bracketSection.getStageLabel(), bracketSection);
	}
	
	public void addBracketSections(BracketSlice... bracketsToAdd) {
		for (BracketSlice s : bracketsToAdd) {
			bracketSections.put(s.getStageLabel(), s);	
		}
	}
	
	public void setChampionshipSeries(Series... series) {
		for (Series s : series) {
			championshipMatches.add(s);	
		}
	}

	public Tournament getPartOf() {
		return partOf;
	}
	
	@SuppressWarnings("unused")
	@Override
	public String toString() {
		String s = "";
		
		Set<Entry<String, BracketSlice>> set = bracketSections.entrySet();
		List<Series> listOfSeries = new ArrayList<Series>();
		for (Entry<String, BracketSlice> entry : set) {
			List<Series> seriesList = entry.getValue().getSeriesList();
			for (int i = 0; i < seriesList.size(); i++) {
				listOfSeries.add(seriesList.get(i));
			}
		}
		sortSLListByLabel(listOfSeries);
		
		// PRINTING BRACKET SUMMARY
		if (Settings.PRINT_PRE_BRACKET_SUMMARY) {
			s += Strings.LargeLineBreak + "\n\nPre-Bracket Summary: " + super.getLabel();
			s += "\n" + Strings.SmallLineBreak + "\n" + "\n";
			
			int mostEntriesVertical = 0;
			int horizontalEntries = 1;
			Object[] toConv = bracketSections.values().toArray();
			List<BracketSlice> convSections = convertObjectArrayToBracketSectionList(toConv);			
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
				BracketSlice bs = convSections.get(i);
				List<Series> seriesList = bs.getSeriesList();
				for (int p = 0; p < seriesList.size(); p++) {
					int xIndex = y;
					int yIndex = p;
					bracketSummary[xIndex][yIndex] = seriesList.get(p);
				}
				y++;
			}
			
			
			for (int i = 0; i < convSections.size(); i++) {
				BracketSlice bs = convSections.get(i);
				s += String.format(Strings.BracketSeriesFormatSingle, bs.getStageLabel()); 
				s += String.format(Strings.BracketSeriesFormatGamescore, "");
			}
			s += "\n";
			
			int q = 0;
			BracketSlice lastSlice = convSections.get(convSections.size() - 1);
			List<Series> lastSliceSeries = lastSlice.getSeriesList();
			while (q < mostEntriesVertical) {
				for (int p = 0; p < horizontalEntries; p++) {
					Series[] column = bracketSummary[p];
					Series series = column[q];
					if (series != null) {
						s += String.format(Strings.BracketSeriesFormatSingle, 
								series.getTeamA().getTag() + " : " + series.getTeamB().getTag());
						s += String.format(Strings.BracketSeriesFormatGamescore, "");
					} else if (p == horizontalEntries - 1) {
						if (q < lastSliceSeries.size()) {
							boolean contained = false;
							for (Series cMatch : championshipMatches) {
								if (cMatch.getWinner().getTag().compareTo(lastSliceSeries.get(q).getWinner().getTag()) == 0) {
									contained = true;
								}
							}
							s += String.format(Strings.BracketSeriesFormatSingle, ""); 
							s += String.format(Strings.BracketSeriesFormatGamescore, "");
						} else {
							s += String.format(Strings.BracketSeriesFormatSingle, ""); 
							s += String.format(Strings.BracketSeriesFormatGamescore, "");
						}
					} else {
						s += String.format(Strings.BracketSeriesFormatSingle, ""); 
						s += String.format(Strings.BracketSeriesFormatGamescore, "");
					}
					if (p == horizontalEntries - 1) {
						q++;
						if (q != mostEntriesVertical) {
							s += "\n";	
						}
					}
				}	
			}
		}
		// PRINTING BRACKET SUMMARY
		s += "\n" + Strings.LargeLineBreak + "\n";
		
		int x = 0;
		for (int i = 0; i < listOfSeries.size(); i++) {
			Series m = listOfSeries.get(i);
			
			if (Settings.PRINT_QUALIFICATION_REASONS && teamsQThroughLabel != "") {
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
			if (Settings.PRINT_SERIES_SUMMARRIES) {
				if (x == listOfSeries.size() - 1) {
					s += "\n" + m.toString();
				} else {
					s += "\n" + m.toString() + "\n";
					s += Strings.SmallLineBreak + "\n";
				}	
			}
			x++;
		}
		
		if (!Settings.PRINT_SERIES_SUMMARRIES) {
			s += "\nPrint Bracket Summary set to false";
		}
		
		// PRINTING BRACKET SUMMARY
		if (Settings.PRINT_END_OF_BRACKET_SUMMARY) {
			s += "\n" + Strings.LargeLineBreak + "\n\nEnd of Bracket Summary: " + super.getLabel();
			s += "\n" + Strings.SmallLineBreak + "\n" + "\n";
			
			int mostEntriesVertical = 0;
			int horizontalEntries = 1;
			Object[] toConv = bracketSections.values().toArray();
			List<BracketSlice> convSections = convertObjectArrayToBracketSectionList(toConv);			
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
				BracketSlice bs = convSections.get(i);
				List<Series> seriesList = bs.getSeriesList();
				for (int p = 0; p < seriesList.size(); p++) {
					int xIndex = y;
					int yIndex = p;
					bracketSummary[xIndex][yIndex] = seriesList.get(p);
				}
				y++;
			}
			
			
			for (int i = 0; i < convSections.size(); i++) {
				BracketSlice bs = convSections.get(i);
				s += String.format(Strings.BracketSeriesFormatSingle, bs.getStageLabel()); 
				s += String.format(Strings.BracketSeriesFormatGamescore, "");
			}
			s += String.format(Strings.BracketSeriesFormatSingle, Strings.WNNRS);
			s += "\n";
			
			int q = 0;
			BracketSlice lastSlice = convSections.get(convSections.size() - 1);
			List<Series> lastSliceSeries = lastSlice.getSeriesList();
			while (q < mostEntriesVertical) {
				for (int p = 0; p < horizontalEntries; p++) {
					Series[] column = bracketSummary[p];
					Series series = column[q];
					if (series != null) {
						Map<Team, Integer> gamescore = series.getGamescore();
						int teamAScore = gamescore.get(series.getTeamA());
						int teamBScore = gamescore.get(series.getTeamB());
						s += String.format(Strings.BracketSeriesFormatSingle, 
								series.getTeamA().getTag() + " : " + series.getTeamB().getTag());
						s += String.format(Strings.BracketSeriesFormatGamescore, " | " + teamAScore + "-" + teamBScore);
					} else if (p == horizontalEntries - 1) {
						if (q < lastSliceSeries.size()) {
							boolean contained = false;
							for (Series cMatch : championshipMatches) {
								if (cMatch.getWinner().getTag().compareTo(lastSliceSeries.get(q).getWinner().getTag()) == 0) {
									contained = true;
								}
							}
							s += String.format(Strings.BracketSeriesFormatSingle, 
									contained ? lastSliceSeries.get(q).getWinner().getTag() : ""); 
						} else {
							s += String.format(Strings.BracketSeriesFormatSingle, ""); 	
						}
					} else {
						s += String.format(Strings.BracketSeriesFormatSingle, ""); 
						s += String.format(Strings.BracketSeriesFormatGamescore, "");
					}
					if (p == horizontalEntries - 1) {
						q++;
						if (q != mostEntriesVertical) {
							s += "\n";	
						}
					}
				}	
			}
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
	
	private List<BracketSlice> convertObjectArrayToBracketSectionList(Object[] toConv) {
		List<BracketSlice> res = new ArrayList<BracketSlice>();
		for (int i = 0; i < toConv.length; i++) {
			res.add((BracketSlice)toConv[i]);
		}
		return res;
	}
	
	private void sortBSListByLabel(List<BracketSlice> sections) {
		sections.sort(new Comparator<BracketSlice>() {
			@Override
			public int compare(BracketSlice lhs, BracketSlice rhs) {
				return lhs.getOrder() < rhs.getOrder() ? -1 : (lhs.getOrder() < rhs.getOrder()) ? 1 : 0;
			}
		});
	}

	public List<Series> getChampionshipMatches() {
		return championshipMatches;
	}
}