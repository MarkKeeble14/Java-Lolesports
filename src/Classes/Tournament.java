package Classes;

import java.util.ArrayList;
import java.util.List;

import Misc.Util;
import StatsTracking.EOTStandings;
import StatsTracking.RegionalWLTracker;
import Teams.Team;
import TournamentComponents.Bracket;
import TournamentComponents.DrawStructure;
import TournamentComponents.GroupStage;
import TournamentComponents.TournamentComponent;
import TournamentSimulator.DomesticDriver;

public abstract class Tournament {
	private String label;
	private Team winner;
	private Team runnerUp;
	
	public RegionalWLTracker t = new RegionalWLTracker();
	public EOTStandings eots = new EOTStandings();
	
	private List<TournamentComponent> tComps = new ArrayList<TournamentComponent>();
	
	private List<Bracket> brackets = new ArrayList<Bracket>();
	private List<DrawStructure> draws = new ArrayList<DrawStructure>();
	private List<GroupStage> grpStages = new ArrayList<GroupStage>();
	
	public Tournament(String label) {
		this.label = label;
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
		Bracket b = brackets.get(brackets.size() - 1);
		
		winner = b.getChampion();
		runnerUp = b.getRunnerUp();
	}
	
	public void PrintLists() {
		Util.PrintSectionBreak("Printing Results of Tournement");
		for (TournamentComponent t : tComps) {
			Util.PrintSectionBreak(t.getLabel());
			System.out.println(t.toString());
		}
	}
	
	public void PrintInfo(
			boolean printWinner, boolean printRegionalWL, 
			boolean printFinalStandings, boolean printTComps) {
		
		if (printTComps) {
			PrintLists();
		}
		
		if (printWinner) {
			Util.PrintSectionBreak("Championship Stats");
			PrintChampionshipStats();	
		}
		
		if (printRegionalWL) {
			Util.PrintSectionBreak("Win/Loss Records");
			if (DomesticDriver.PRINT_MAJOR_REGIONAL_WL) {
				t.NicePrintMajor();		
			}
			if (DomesticDriver.PRINT_MINOR_REGIONAL_WL) {
				t.NicePrintMinor();	
			}
		}
		
		if (printFinalStandings) {
			Util.PrintSectionBreak("Tournament Standings");
			eots.Print();	
		}
	}
	
	public void PrintChampionshipStats() {
		if (winner.getCurrentQDIndex() > 0) {
			System.out.println("\n" + winner + " QDS: \n" + winner.qdLog());	
		}
		if (winner.getCurrentRecordIndex() > 0) {
			System.out.println("\n" + winner + " Records: \n\n" + winner.recordLog());	
		}
		System.out.println(winner.getTag() + " has Won " + label + "; Runner Up: " + runnerUp.getTag());	
	}

	public Team getWinner() {
		return winner;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}
	
	public RegionalWLTracker getT() {
		return t;
	}

	public EOTStandings getEots() {
		return eots;
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
