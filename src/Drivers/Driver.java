package Drivers;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import DefiningTeams.RatingDefinedTeam;
import DefiningTeams.Team;
import Enums.TOURNAMENT;
import Enums.DRIVER_TYPE;
import Enums.ELO_SCALING_TYPE;
import Enums.LEAGUE;
import LCS.LCSLockin;
import LCS.SpringLCS;
import LCS.SummerLCS;
import LEC.SpringLEC;
import LEC.SummerLEC;
import MSI.TournamentMSI;
import PrintStreams.TeePrintStream;
import StaticVariables.Settings;
import StaticVariables.Strings;
import StaticVariables.Teams;
import StaticVariables.TeamsWithPlayers;
import StaticVariables.TeamsWithPlayers;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;
import Utility.Util;
import Utility.UtilMaps;
import WorldChampionship.TournamentWorldChampionship;
import WorldChampionship2VS3.TournamentWorldChampionship2VS3;
import WorldChampionshipCurrentState.CurrentStateOfTournamentWorldChampionship;
import WorldChampionshipDoubleElim.TournamentWorldChampionshipDoubleElim;
import WorldChampionshipLong.TournamentWorldChampionshipLongFormat;

public class Driver {
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
		Settings.setEloScaling(ELO_SCALING_TYPE.PURE);
		
		FileOutputStream file = new FileOutputStream("output.txt");
	    TeePrintStream tee = new TeePrintStream(file, System.out);
	    System.setOut(tee);
		
		Scanner scan = new Scanner(System.in);
		LEAGUE league;
		TOURNAMENT tournament;
		
		String sentinal = "EXIT";
		String input = "";
		String exitPrompt = "\nDo You Want to Simulate another Tournament or Exit?: "
				+ "\nEntering anything other than the phrase 'exit' will continue the program.\n";
		
		// Program
		System.out.println("Welcome to my Tournament Simulator!");
		System.out.println("To Stop Input, type 'Exit' when prompted.");
		
		while (input.compareTo(sentinal) != 0) {
			league = ScanForWhichLeague(scan);
			
			if (league == null) {
				System.out.print(exitPrompt);
				input = scan.nextLine().toUpperCase();
				continue;
			}
			
			tournament = ScanForWhichTournament(league, scan);
			if (tournament == null) {
				System.out.print(exitPrompt);
				input = scan.nextLine().toUpperCase();
				continue;
			}
			
			LoopTournament(numberOfSims, tournament, scan);
			
			System.out.print(Strings.LargeLineBreak + "\n");
			
			System.out.print(exitPrompt);
			input = scan.nextLine().toUpperCase();
		}
		
		scan.close();
		
		System.out.print(Strings.LargeLineBreak + "\n\nThanks for Simulating!");
	}
	
	private static LEAGUE ScanForWhichLeague(Scanner scan) {
		// Program
		String sentinal = "EXIT";
		String input = "";
		
		LEAGUE returnVal = null;
		
		while (returnVal == null) {
			System.out.print("\nWhich League do you want to Simulate?: ");
			System.out.print("\nOptions: ");
			System.out.print("\n1 - LCS");
			System.out.print("\n2 - LEC");
			System.out.print("\n3 - International");
			System.out.println(); // To force input to a new line
			
			input = scan.nextLine().toUpperCase();
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nExit");
				break;
			}
			
			switch (input) {
				case "LCS":
					returnVal = LEAGUE.LCS;
					break;
				case "1":
					returnVal = LEAGUE.LCS;
					break;
				case "LEC":
					returnVal = LEAGUE.LEC;
					break;
				case "2":
					returnVal = LEAGUE.LEC;
					break;
				case "INTERNATIONAL":
					returnVal = LEAGUE.INTERNATIONAL;
					break;
				case "3":
					returnVal = LEAGUE.INTERNATIONAL;
					break;
				default:
					System.out.print("\nPlease Enter one of the Options Given.\n");
			}
		}
		
		return returnVal;
	}
	
	private static TOURNAMENT ScanForWhichTournament(LEAGUE whichLeague, Scanner scan) {
		switch (whichLeague) {
		case LCS:
			return ScanForWhichLCSTournament(scan);
		case LEC:
			return ScanForWhichLECTournament(scan);
		case INTERNATIONAL:
			return ScanForWhichInternationalTournament(scan);
		default:
			return null;
		}
	}
	
	private static TOURNAMENT ScanForWhichLCSTournament(Scanner scan) {
		String sentinal = "EXIT";
		String input = "";
		
		TOURNAMENT returnVal = null;
		
		while (returnVal == null) {
			System.out.print("\nWhich LCS Tournament do you want to Simulate?: ");
			System.out.print("\nOptions: ");
			System.out.print("\n1 - Lockin");
			System.out.print("\n2 - Spring");
			System.out.print("\n3 - Summer");
			System.out.println(); // To force input to a new line
			
			input = scan.nextLine().toUpperCase();
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nExit");
				break;
			}
			
			switch (input) {
				case "Lockin":
					returnVal = TOURNAMENT.LCS_Lockin;
					break;
				case "1":
					returnVal = TOURNAMENT.LCS_Lockin;
					break;
				case "SPRING":
					returnVal = TOURNAMENT.LCS_Spring;
					break;
				case "2":
					returnVal = TOURNAMENT.LCS_Spring;
					break;
				case "SUMMER":
					returnVal = TOURNAMENT.LCS_Summer;
					break;
				case "3":
					returnVal = TOURNAMENT.LCS_Summer;
					break;
				default:
					System.out.print("\nPlease Enter one of the Options Given.\n");
			}
		}
		
		return returnVal;
	}
	
	private static TOURNAMENT ScanForWhichLECTournament(Scanner scan) {
		// Program
		String sentinal = "EXIT";
		String input = "";
		
		TOURNAMENT returnVal = null;
		
		while (returnVal == null) {
			System.out.print("\nWhich LEC Tournament do you want to Simulate?: ");
			System.out.print("\nOptions");
			System.out.print("\n1 - Spring");
			System.out.print("\n2 - Summer");
			System.out.println(); // To force input to a new line
			
			input = scan.nextLine().toUpperCase();
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nExit");
				break;
			}
			
			switch (input) {
				case "SPRING":
					returnVal = TOURNAMENT.LEC_Spring;
					break;
				case "1":
					returnVal = TOURNAMENT.LEC_Spring;
					break;
				case "SUMMER":
					returnVal = TOURNAMENT.LEC_Summer;
					break;
				case "2":
					returnVal = TOURNAMENT.LEC_Summer;
					break;
				default:
					System.out.print("\nPlease Enter one of the Options Given\n");
			}
		}
		
		return returnVal;
	}
	
	private static TOURNAMENT ScanForWhichInternationalTournament(Scanner scan) {
		// Program
		String sentinal = "EXIT";
		String input = "";
		
		TOURNAMENT returnVal = null;
		
		while (returnVal == null) {
			System.out.print("\nWhich International Tournament do you want to Simulate?: ");
			System.out.print("\nOptions");
			System.out.print("\n1 - [Standard] World Championship");
			System.out.print("\n2 - [Long World] Championship");
			System.out.print("\n3 - [Double Elim] World Championship");
			System.out.print("\n4 - World Championship with Round of [2 VS 3]");
			System.out.print("\n5 - [Current State] of the World Championship");
			System.out.print("\n6 - MSI");
			System.out.println(); // To force input to a new line
			
			input = scan.nextLine().toUpperCase();
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nExit");
				break;
			}
			
			switch (input) {
				case "1":
					returnVal = TOURNAMENT.WorldChampionshipCurrentFormat;
					break;
				case "STANDARD":
					returnVal = TOURNAMENT.WorldChampionshipCurrentFormat;
					break;
				case "2":
					returnVal = TOURNAMENT.WorldChampionshipLong;
					break;
				case "LONG":
					returnVal = TOURNAMENT.WorldChampionshipLong;
					break;
				case "3":
					returnVal = TOURNAMENT.WorldChampionshipDoubleElim;
					break;
				case "DOUBLE ELIM":
					returnVal = TOURNAMENT.WorldChampionshipDoubleElim;
					break;
				case "4":
					returnVal = TOURNAMENT.WorldChampionship2VS3;
					break;
				case "2 VS 3":
					returnVal = TOURNAMENT.WorldChampionship2VS3;
					break;
				case "5":
					returnVal = TOURNAMENT.WorldChampionshipCurrentState;
					break;
				case "CURRENT STATE":
					returnVal = TOURNAMENT.WorldChampionshipCurrentState;
					break;
				case "6":
					returnVal = TOURNAMENT.MSI;
					break;
				case "MSI":
					returnVal = TOURNAMENT.MSI;
					break;
				default:
					System.out.print("\nPlease Enter one of the Options Given\n");
			}
		}
		
		return returnVal;
	}
	
	// Doesn't allow for SUPER large numbers; 10,000 Works: Takes like 10ish Seconds to run for me
	// You can probably go higher if you want to wait.
	private static void LoopTournament(int x, TOURNAMENT t, Scanner scan) throws Exception {
		Map<Integer, Tournament> tournamentMap = new HashMap<Integer, Tournament>();
		Map<String, Integer> timesTeamWonMap = new HashMap<String, Integer>();
		Map<String, List<Integer>> indexOfTeamWins = new HashMap<String, List<Integer>>();
		
		for (int i = 0; i < x; i++) {
			Tournament T = null;
			switch (t) {
			case LCS_Lockin:
				T = SimulateLockinLCS();
				break;
			case LCS_Spring:
				T = SimulateSpringLCS();
				break;
			case LCS_Summer:
				 T = SimulateSummerLCS();
				break;
			case LEC_Spring:
				T = SimulateSpringLEC();
				break;
			case LEC_Summer:
				T = SimulateSummerLEC();
				break;
			case WorldChampionshipCurrentFormat:
				T = SimulateStandardWC();
				break;
			case WorldChampionshipCurrentState:
				T = SimulateCurrentWorldsState();
				break;
			case WorldChampionship2VS3:
				T = Simulate2VS3WC();
				break;
			case WorldChampionshipLong:
				T = SimulateLongWC();
				break;
			case WorldChampionshipDoubleElim:
				T = SimulateDoubleElimWC();
				break;
			case MSI:
				T = SimulateStandardMSI();
				break;
			}
			
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
		
		Util.PrintSectionBreak("Result's of Simulating: " + t);
		
		// Program
		String sentinal = "EXIT";
		String input = "";
		while (true) {
			// Print Results 
			// Currently can count duplicate tournament results (Same exact outcomes) as a seperate tournament win,
			// Which might skew the results a little?
			timesTeamWonMap = UtilMaps.sortByIntegerValue(timesTeamWonMap);
			Util.NicePrintResults(timesTeamWonMap, x);
			
			System.out.print("\nShow me a World where X Wins: ");
			input = scan.nextLine().toUpperCase();
			
			if (input.compareTo(sentinal) == 0) {
				System.out.println("\nExit");
				break;
			}

			// Print out possible numbers
			if (input.compareTo("ANY") == 0) {
				Random rand = new Random();
				int rNum = rand.nextInt(indexOfTeamWins.size());
				input = (String) indexOfTeamWins.keySet().toArray()[rNum]; 
			}
			
			if (indexOfTeamWins.containsKey(input)) {
				List<Integer> options = indexOfTeamWins.get(input);
				
				if (options.size() > 0) {
					int index = options.get(0);
					options.remove(0);
					indexOfTeamWins.put(input, options);
					
					System.out.println("Print Tournament Progression?: ");
					boolean printTProg = GetYN(scan);
					System.out.println();
					System.out.println("Print Champions Records?: ");
					boolean printCRec = GetYN(scan);
					System.out.println();
					System.out.println("Print Tournament Standings?: ");
					boolean printTStand = GetYN(scan);
					System.out.println();
					System.out.println("Print Tournament Stats?: ");
					boolean printTStats = GetYN(scan);
					System.out.println();
					
					Util.PrintSectionBreak("Printing out Results of: Simulation #" + index + " -");
					tournamentMap.get(index).PrintInfo(printTProg, printCRec, printTStand, false, printTStats);
				} else {
					System.out.println("No more saved simulations where " + input + " Wins; Run again if you'd like");
				}
			} else {
				System.out.println("No saved simulations where " + input + " Wins; Run again if you'd like");
				continue;
			}
		}
	}
	
	private static boolean GetYN(Scanner scan) {
		System.out.print("Y or N?: ");
		
		String input = scan.nextLine();
		while (input.toUpperCase().compareTo("Y") != 0 && input.toUpperCase().compareTo("N") != 0) {
			input = scan.nextLine();
		}
		
		return input.toUpperCase().compareTo("Y") == 0 ? true : false;
	}
	
	public static Tournament SimulateSpringLCS() throws Exception {
		Tournament WC = new SpringLCS();
		Pool p = getPoolOfLCSTeamsOffseason();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateSummerLCS() throws Exception {
		Tournament WC = new SummerLCS();
		Pool p = getPoolOfLCSTeamsOffseason();
		WC.Simulate(new ArrayList<Pool>(Arrays.asList(p)));
		return WC;
	}
	
	public static Tournament SimulateLockinLCS() throws Exception {
		Tournament WC = new LCSLockin();
		Pool p = getPoolOfLCSTeamsOffseason();
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
	
	public static Pool getPoolOfLCSTeamsOffseason() {
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
	
	private static List<Pool> getLongWCPools() {
		Pool P1 = new Pool(Strings.LPoolOne, new RatingDefinedTeam(Teams.EDG), new RatingDefinedTeam(Teams.DK),
				new RatingDefinedTeam(Teams.MAD), new RatingDefinedTeam(Teams.O100T)); 
		
		Pool P2 = new Pool(Strings.LPoolTwo, new RatingDefinedTeam(Teams.FPX), new RatingDefinedTeam(Teams.GEN), 
				new RatingDefinedTeam(Teams.FNC), new RatingDefinedTeam(Teams.PSG));
		
		Pool P3 = new Pool(Strings.LPoolThree, new RatingDefinedTeam(Teams.RNG), new RatingDefinedTeam(Teams.T1), 
				new RatingDefinedTeam(Teams.TL), new RatingDefinedTeam(Teams.GAM)); 
		
		Pool P4 = new Pool(Strings.LPoolFour, new RatingDefinedTeam(Teams.RGE), new RatingDefinedTeam(Teams.C9));
		
		Pool PI1 = new Pool(Strings.LPIPoolOne, new RatingDefinedTeam(Teams.LNG), new RatingDefinedTeam(Teams.HLE), 
				new RatingDefinedTeam(Teams.G2), new RatingDefinedTeam(Teams.TSM), 
				new RatingDefinedTeam(Teams.BYG), new RatingDefinedTeam(Teams.JT), 
				new RatingDefinedTeam(Teams.SGB), new RatingDefinedTeam(Teams.TS));
		
		Pool PI2 = new Pool(Strings.LPIPoolTwo, new RatingDefinedTeam(Teams.INF), new RatingDefinedTeam(Teams.GS), 
				new RatingDefinedTeam(Teams.UOL), new RatingDefinedTeam(Teams.PCE),
				new RatingDefinedTeam(Teams.RED), new RatingDefinedTeam(Teams.DFM));
		
		return new ArrayList<Pool>(Arrays.asList(PI1, PI2, P1, P2, P3, P4));
	}
	
	private static List<Pool> get2021WCPools() {
		Pool PIPool1 = new Pool(Strings.LPIPoolOne, new RatingDefinedTeam(Teams.LNG), new RatingDefinedTeam(Teams.HLE), new RatingDefinedTeam(Teams.BYG), new RatingDefinedTeam(Teams.C9)); 
		Pool PIPool2 = new Pool(Strings.LPIPoolTwo, new RatingDefinedTeam(Teams.INF), new RatingDefinedTeam(Teams.GS), new RatingDefinedTeam(Teams.UOL), new RatingDefinedTeam(Teams.PCE), new RatingDefinedTeam(Teams.RED), new RatingDefinedTeam(Teams.DFM));
		Pool P1 = new Pool(Strings.LPoolOne, new RatingDefinedTeam(Teams.DK), new RatingDefinedTeam(Teams.EDG), new RatingDefinedTeam(Teams.MAD), new RatingDefinedTeam(Teams.PSG)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new RatingDefinedTeam(Teams.O100T), new RatingDefinedTeam(Teams.FNC), new RatingDefinedTeam(Teams.GEN), new RatingDefinedTeam(Teams.FPX));
		Pool P3 = new Pool(Strings.LPoolThree, new RatingDefinedTeam(Teams.TL), new RatingDefinedTeam(Teams.T1), new RatingDefinedTeam(Teams.RGE), new RatingDefinedTeam(Teams.RNG));
		return new ArrayList<Pool>(Arrays.asList(PIPool1, PIPool2, P1, P2, P3));
	}
	
	private static List<Pool> get2021MSIPools() {
		Pool P1 = new Pool(Strings.LPoolOne, new RatingDefinedTeam(Teams.RNG), new RatingDefinedTeam(Teams.DK), new RatingDefinedTeam(Teams.PSG), new RatingDefinedTeam(Teams.C9), new RatingDefinedTeam(Teams.MAD), new RatingDefinedTeam(Teams.GAM)); 
		Pool P2 = new Pool(Strings.LPoolTwo, new RatingDefinedTeam(Teams.PGG), new RatingDefinedTeam(Teams.UOL), new RatingDefinedTeam(Teams.PNG), new RatingDefinedTeam(Teams.IW), new RatingDefinedTeam(Teams.DFM), new RatingDefinedTeam(Teams.INF));
		return new ArrayList<Pool>(Arrays.asList(P1, P2));
	}
	
	private static Tournament SimulateLongWC() throws Exception {
		Tournament WC = new TournamentWorldChampionshipLongFormat(Strings.LWC);
		List<Pool> pools = getLongWCPools();
		WC.Simulate(pools);
		return WC;
	}
	
	public static Tournament SimulateStandardWC() throws Exception {
		Tournament WC = new TournamentWorldChampionship(Strings.LWC);
		List<Pool> pools = get2021WCPools();
		WC.Simulate(pools);
		return WC;
	}
	
	public static Tournament SimulateDoubleElimWC() throws Exception {
		Tournament WC = new TournamentWorldChampionshipDoubleElim(Strings.LWC);
		List<Pool> pools = get2021WCPools();
		WC.Simulate(pools);
		return WC;
	}
	
	public static Tournament Simulate2VS3WC() throws Exception {
		Tournament WC = new TournamentWorldChampionship2VS3(Strings.LWC);
		List<Pool> pools = get2021WCPools();
		WC.Simulate(pools);
		return WC;
	}
	
	// Simulates the current World Championship, from it's current state (Updated Manually).
	public static Tournament SimulateCurrentWorldsState() throws Exception {
		Tournament WC = new CurrentStateOfTournamentWorldChampionship(Strings.LWC);
		WC.Simulate(null);
		return WC;
	}
	
	public static Tournament SimulateStandardMSI() throws Exception {
		Tournament MSI = new TournamentMSI();
		List<Pool> pools = get2021MSIPools();
		MSI.Simulate(pools);
		return MSI;
	}
}
