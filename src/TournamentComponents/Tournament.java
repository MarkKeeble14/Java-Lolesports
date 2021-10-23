package TournamentComponents;

import java.util.ArrayList;
import java.util.List;

import DefiningMatches.Series;
import DefiningTeams.Team;
import StaticVariables.Settings;
import StaticVariables.Strings;
import Stats.Standings;
import Stats.TournamentStats;
import Stats.MatchStats;
import Utility.Util;

public abstract class Tournament {
	private String label;
	private Bracket championshipBracket;
	
	public TournamentStats tStats;
	
	private List<TournamentComponent> tComps = new ArrayList<TournamentComponent>();
	
	private List<Bracket> brackets = new ArrayList<Bracket>();
	private List<DrawStructure> draws = new ArrayList<DrawStructure>();
	private List<GroupStage> grpStages = new ArrayList<GroupStage>();
	
	public Tournament(String label, int numTeams) {
		this.label = label;
		tStats = new TournamentStats(numTeams);
	}
	
	public String getLabel() {
		return label;
	}

	public void PrintHeadline() {
		Util.StartHeadline(label);
	}
	
	public abstract void Simulate(List<Pool> pools) throws Exception;
	
	public abstract void Setup();
	
	public void ConcludeTournament() {
		championshipBracket = brackets.get(brackets.size() - 1);
	}
	
	public void PrintTournamentComponents() {
		Util.PrintSectionBreak("Printing Results of Tournement");
		for (TournamentComponent t : tComps) {
			Util.PrintSectionBreak(t.getLabel());
			System.out.println(t.toString());
		}
	}
	
	public void PrintWinnerStats() {
		Util.PrintSectionBreak("Champion Stats/Records");
		PrintChampionshipStats();	
	}
	
	public void PrintWinLossStats() {
		MatchStats mStats = tStats.getMatchStats();
		
		Util.PrintSectionBreak("Win/Loss Records");
		if (Settings.PRINT_MAJOR_REGIONAL_WL) {
			 mStats.PrintMajorRegionWL();		
		}
		if (Settings.PRINT_MINOR_REGIONAL_WL) {
			 mStats.PrintMinorRegionWL();	
		}
	}
	
	public void PrintStandings() {
		Standings standings = tStats.getStandings();
		
		Util.PrintSectionBreak("Tournament Standings");
		standings.Print();	
	}
	
	private void PrintTournamentStats() throws Exception {
		Util.PrintSectionBreak("Tournament Stats");
		tStats.Print();
	}
	
	public void PrintInfo(
			boolean printTComps, boolean printWinner, 
			boolean printFinalStandings, boolean printWL, 
			boolean printTournamentStats) throws Exception {
		
		if (printTComps) {
			PrintTournamentComponents();
		}
		
		if (printWinner) {
			PrintWinnerStats();
		}
		
		if (printWL) {
			PrintWinLossStats();
		}
		
		if (printFinalStandings) {
			PrintStandings();
		}
		
		if (printTournamentStats) {
			PrintTournamentStats();
		}
	}

	public void PrintChampionshipStats() {
		String s = "";
		for (Series match : championshipBracket.getChampionshipMatches()) {
			Team winner = match.getWinner();
			Team runnerUp = match.getLoser();
			if (winner.hasQDs()) {
				s += "\n" + winner.qdLog() + "\n" + Strings.MediumLineBreak;
				if (winner.getCurrentRecordIndex() > 0) {
					s += "\n";
				}
			}
			if (winner.hasRecords()) {
				s += "\n" + winner.recordLog() + Strings.MediumLineBreak + "\n";
			}
			s += "\n" + winner.getTag() + " has Won " + label + "; Runner Up: " + runnerUp.getTag();
		}
		System.out.println(s);
	}

	public Team getWinner() {
		return 	championshipBracket.getChampionshipMatches()
				.get(championshipBracket.getChampionshipMatches().size() - 1)
				.getWinner();
	}

	public Team getRunnerUp() {
		return 	championshipBracket.getChampionshipMatches()
				.get(championshipBracket.getChampionshipMatches().size() - 1)
				.getLoser();
	}
	
	public MatchStats getT() {
		return tStats.getMatchStats();
	}

	public Standings getEots() {
		return tStats.getStandings();
	}
	
	public void addBracket(Bracket b) {
		brackets.add(b);
		tComps.add(b);
	}
	
	public void addGroupStage(GroupStage gs) {
		grpStages.add(gs);
		tComps.add(gs);
	}
	
	public void addDrawStructure(DrawStructure ds) {
		draws.add(ds);
		tComps.add(ds);
	}
	
	public static void PrintQualified(String sectionQualifiedThrough, List<Team> qualified) {
		Util.PrintMediumLineBreak(false);
		System.out.println("\nQualified Teams Through " + sectionQualifiedThrough + ": ");
		for (Team t : qualified) {
			Util.PrintSmallLineBreak(false);
			System.out.println(t.getQD().toString());
		}
	}
}
