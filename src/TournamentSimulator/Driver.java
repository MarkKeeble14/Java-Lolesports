package TournamentSimulator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Classes.Pool;
import Classes.Team;
import Classes.Tournament;
import MSI.TournamentMSI;
import Misc.MapUtil;
import Misc.Strings;
import Misc.Teams;
import Misc.Util;
import WorldChampionship.CurrentStateOfTournamentWorldChampionship;
import WorldChampionship.TournamentWorldChampionship;
import WorldChampionship.TournamentWorldChampionshipDoubleElim;

public class Driver {
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 75;
	
	// 100,000 Too High
	// 10,000 ~10 Seconds
	private static final int numberOfSims = 10000;
	
	public static final boolean SHOW_REGIONAL_WL_WITH_0_GAMES = false;
	
	public static final boolean PRINT_DETAILED_SERIES_SUMMARY = true;
	
	public static final boolean PRINT_QUALIFICATION_REASONS = true;
	
	public static final boolean PRINT_GROUP_STAGE_SUMMARY = true;
	
	public static final boolean PRINT_OVERALL_WL = true;
	public static final boolean PRINT_INDIVIDUAL_WL = true;
	public static final boolean PRINT_MAJOR_REGIONAL_WL = true;
	public static final boolean PRINT_MINOR_REGIONAL_WL = false;
	
	// Main
	public static void main(String[] args) throws Exception {
		// SimulateCurrentWorldsState().PrintInfo(true, false, false, true);
		// SimulateWorldsFormatFromScratch().PrintInfo(true, true, true, true);
		
		// SimulateMSIFormatFromScratch().PrintInfo(true, false, false, true);
		
		LoopTournament(numberOfSims);
	}
	
	// Simulates an Entire Tournament
	public static Tournament SimulateWorldsFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool PIPool1 = new Pool(Strings.LPIPoolOne, new Team(Teams.LNG), new Team(Teams.HLE), new Team(Teams.BYG), new Team(Teams.C9)); 
		Pool PIPool2 = new Pool(Strings.LPIPoolTwo, new Team(Teams.INF), new Team(Teams.GS), new Team(Teams.UOL), new Team(Teams.PCE), new Team(Teams.RED), new Team(Teams.DFM));
		Pool P1 = new Pool(Strings.LPoolOne, new Team(Teams.DK), new Team(Teams.EDG), new Team(Teams.MAD), new Team(Teams.PSG)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new Team(Teams.O100T), new Team(Teams.FNC), new Team(Teams.GEN), new Team(Teams.FPX));
		Pool P3 = new Pool(Strings.LPoolThree, new Team(Teams.TL), new Team(Teams.T1), new Team(Teams.RGE), new Team(Teams.RNG));
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(PIPool1, PIPool2, P1, P2, P3));
		
		Tournament WC = new TournamentWorldChampionshipDoubleElim(Strings.LWC);
		WC.Simulate(pools);
		return WC;
	}
	
	// Simulates the current World Championship, from it's current state (Updated Manually).
	public static Tournament SimulateCurrentWorldsState() throws Exception {
		Tournament WC = new CurrentStateOfTournamentWorldChampionship(Strings.LWC);
		WC.Simulate(null);
		return WC;
	}
	
	public static Tournament SimulateMSIFormatFromScratch() throws Exception {
		// Setting up Pools
		Pool P1 = new Pool(Strings.LPoolOne, new Team(Teams.RNG), new Team(Teams.DK), new Team(Teams.PSG), new Team(Teams.C9), new Team(Teams.MAD), new Team(Teams.GAM)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new Team(Teams.PGG), new Team(Teams.UOL), new Team(Teams.PNG), new Team(Teams.IW), new Team(Teams.DFM), new Team(Teams.INF));
		List<Pool> pools = new ArrayList<Pool>(Arrays.asList(P1, P2));
		
		Tournament MSI = new TournamentMSI(Strings.LMSI);
		MSI.Simulate(pools);
		return MSI;
	}
	
	// Doesn't allow for SUPER large numbers; 10,000 Works: Takes like 10ish Seconds to run for me
	// You can probably go higher if you want to wait.
	private static void LoopTournament(int x) throws Exception {
		Map<Integer, Tournament> tournamentMap = new HashMap<Integer, Tournament>();
		Map<String, Integer> timesTeamWonMap = new HashMap<String, Integer>();
		Map<String, List<Integer>> indexOfTeamWins = new HashMap<String, List<Integer>>();
		for (int i = 0; i < x; i++) {
			Tournament T = SimulateWorldsFormatFromScratch();
			tournamentMap.put(tournamentMap.size(), T);
			Team champion = T.getWinner();
			if (timesTeamWonMap.containsKey(champion.getTag())) {
				timesTeamWonMap.put(champion.getTag(), timesTeamWonMap.get(champion.getTag()) + 1);
				List<Integer> prevWins =  indexOfTeamWins.get(champion.getTag());
				prevWins.add(i);
				indexOfTeamWins.put(champion.getTag(), prevWins);
			} else {
				timesTeamWonMap.put(champion.getTag(), 1);
				indexOfTeamWins.put(champion.getTag(), new ArrayList<Integer>(Arrays.asList(i)));
			}
		}
		
		Util.PrintSectionBreak("World's Simulations");
		
		// Program
		Scanner scan = new Scanner(System.in);
		String sentinal = "NO";
		String input = "";
		while (true) {
			// Print Results 
			// Currently can count duplicate tournament results (Same exact outcomes) as a seperate tournament win,
			// Which might skew the results a little?
			timesTeamWonMap = MapUtil.sortByIntegerValue(timesTeamWonMap);
			Util.NicePrintResults(timesTeamWonMap, x);
			
			System.out.print("\nShow me a World where X Wins: ");
			input = scan.nextLine().toUpperCase();
			
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nOk");
				break;
			}
			
			// Print out possible numbers
			if (indexOfTeamWins.containsKey(input)) {
				List<Integer> options = indexOfTeamWins.get(input);
				
				if (options.size() > 0) {
					int index = options.get(0);
					options.remove(0);
					indexOfTeamWins.put(input, options);
					
					System.out.println("Print Tournament Progression?: ");
					boolean printTProg = GetYN(scan);
					
					System.out.println("Print Tournament Champion Records?: ");
					boolean printCRec = GetYN(scan);
					
					System.out.println("Print W/L?: ");
					boolean printRWL = GetYN(scan);
					
					System.out.println("Print Tournament Standings?: ");
					boolean printTStand = GetYN(scan);
					
					Util.PrintSectionBreak("Printing out Results of: Simulation #" + index + " -");
					tournamentMap.get(index).PrintInfo(printCRec, printRWL, printTStand, printTProg);
				} else {
					System.out.println("No more saved simulations where " + input + " Wins; Run again if you'd like");
				}
			} else {
				System.out.println("No saved simulations where " + input + " Wins; Run again if you'd like");
				continue;
			}
		}
		
		scan.close();
	}
	
	private static boolean GetYN(Scanner scan) {
		System.out.print("Y or N?: ");
		
		String input = scan.nextLine();
		while (input.toUpperCase().compareTo("Y") != 0 && input.toUpperCase().compareTo("N") != 0) {
			input = scan.nextLine();
		}
		
		return input.toUpperCase().compareTo("Y") == 0 ? true : false;
	}
}
