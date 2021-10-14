package Drivers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import Classes.Pool;
import Classes.Tournament;
import LCS.LCSLockin;
import LCS.SpringLCS;
import LCS.SummerLCS;
import LEC.SpringLEC;
import LEC.SummerLEC;
import MSI.TournamentMSI;
import Misc.DriverType;
import Misc.GlobalVariables;
import Misc.MapUtil;
import Misc.Strings;
import Misc.Teams;
import Misc.TeamsWithPlayers;
import Misc.Util;
import Teams.RatingDefinedTeam;
import Teams.Team;
import WorldChampionship.CurrentStateOfTournamentWorldChampionship;
import WorldChampionship.TournamentWorldChampionship;
import WorldChampionship.TournamentWorldChampionshipDoubleElim;

public class DomesticDriver {
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 300;
	
	// LCS
	// Lockin - 	100,000 	~8-10 Seconds
	// Spring - 	10,000		~2 Seconds
	// Summer - 	10,000		~2 Seconds
	
	// LEC
	// Spring - 	10,000		~2 Seconds
	// Summer - 	10,000		~2 Seconds
	private static final int numberOfSims = 10000;
	
	// Main
	public static void main(String[] args) throws Exception {
		GlobalVariables.setCurrentDriver(DriverType.Domestic);
		
		// LoopTournament(numberOfSims);
		
		SimulateLockinLCS().PrintInfo(true, false, true, true);
		// SimulateSpringLCS().PrintInfo(true, false, true, true);
		// SimulateSummerLCS().PrintInfo(true, false, true, true);
		
		// SimulateSpringLEC().PrintInfo(true, false, true, true);
		// SimulateSummerLEC().PrintInfo(true, false, true, true);
	}
	
	public static Tournament SimulateSpringLCS() throws Exception {
		Tournament WC = new SpringLCS();
		Pool p = getPoolOfLCSTeams();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateSummerLCS() throws Exception {
		Tournament WC = new SummerLCS();
		Pool p = getPoolOfLCSTeams();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateLockinLCS() throws Exception {
		Tournament WC = new LCSLockin();
		Pool p = getPoolOfLCSTeams();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateSpringLEC() throws Exception {
		Tournament WC = new SpringLEC();
		Pool p = getPoolOfLECTeams();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateSummerLEC() throws Exception {
		Tournament WC = new SummerLEC();
		Pool p = getPoolOfLECTeams();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Pool getPoolOfLCSTeams() {
		List<Team> teams = new ArrayList<Team>(Arrays.asList(
				new RatingDefinedTeam(TeamsWithPlayers.TSM), new RatingDefinedTeam(TeamsWithPlayers.C9), 
				new RatingDefinedTeam(TeamsWithPlayers.TL), new RatingDefinedTeam(TeamsWithPlayers.EG), 
				new RatingDefinedTeam(TeamsWithPlayers.FLY), new RatingDefinedTeam(TeamsWithPlayers.GG), 
				new RatingDefinedTeam(TeamsWithPlayers.CLG), new RatingDefinedTeam(TeamsWithPlayers.DIG), 
				new RatingDefinedTeam(TeamsWithPlayers.O100), new RatingDefinedTeam(TeamsWithPlayers.IMT)));
		Pool p = new Pool(Strings.LCSTeams, teams);
		return p;
	}
	
	public static Pool getPoolOfLECTeams() {
		List<Team> teams = new ArrayList<Team>(Arrays.asList(
				new RatingDefinedTeam(TeamsWithPlayers.G2), new RatingDefinedTeam(TeamsWithPlayers.XL), 
				new RatingDefinedTeam(TeamsWithPlayers.MAD), new RatingDefinedTeam(TeamsWithPlayers.FNC), 
				new RatingDefinedTeam(TeamsWithPlayers.AST), new RatingDefinedTeam(TeamsWithPlayers.VIT), 
				new RatingDefinedTeam(TeamsWithPlayers.RGE), new RatingDefinedTeam(TeamsWithPlayers.SK), 
				new RatingDefinedTeam(TeamsWithPlayers.BDS), new RatingDefinedTeam(TeamsWithPlayers.MSF)));
		Pool p = new Pool(Strings.LCSTeams, teams);
		return p;
	}
	
	// Doesn't allow for SUPER large numbers; 10,000 Works: Takes like 10ish Seconds to run for me
	// You can probably go higher if you want to wait.
	private static void LoopTournament(int x) throws Exception {
		Map<Integer, Tournament> tournamentMap = new HashMap<Integer, Tournament>();
		Map<String, Integer> timesTeamWonMap = new HashMap<String, Integer>();
		Map<String, List<Integer>> indexOfTeamWins = new HashMap<String, List<Integer>>();
		for (int i = 0; i < x; i++) {
			
			// Change this
			Tournament T = SimulateSummerLCS();
			//
			
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
					
					System.out.println("Print Tournament Standings?: ");
					boolean printTStand = GetYN(scan);
					
					Util.PrintSectionBreak("Printing out Results of: Simulation #" + index + " -");
					tournamentMap.get(index).PrintInfo(printCRec, false, printTStand, printTProg);
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
