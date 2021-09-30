package bracketgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
	// Stages
	public static String PIGS = "Play-ins Group Stage";
	public static String PIKS = "Play-ins Knockout Stage";
	public static String MSGS = "Main Stage Group Stage";
	public static String MSKS = "Main Stage Knockout Stage";
	
	// Pools
	public static String PoolOne = "Pool One";
	public static String PoolTwo = "Pool Two";
	public static String PoolThree = "Pool Three";
	public static String PIPoolOne = "PI - Pool One";
	public static String PIPoolTwo = "PI - Pool Two";
	public static String QualifiedPI = "Qualified Through PI";
	
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are less likely
	// A scale of 1 makes most matchups 50/50
	public static int ELO_SCALING = 75;
	
	// Main
	public static void main(String[] args) throws Exception {
		FullSimulation();
		// SimulateCurrentWorldsState();
	}
	
	public static void ForceSimulate() {
		ClearScreen();
		try {
			FullSimulation();		
		} catch (Exception e) {
			ForceSimulate();
		}
	}
	
	// Simulates an entire tournament
	public static void FullSimulation() throws Exception {
		// Simulating Play-ins Group Draw
		System.out.println("Simulating Play-ins Group Draw\n"); 
		
		// Setting up groups
		Group PA = new Group("A", 5); 
		Group PB = new Group("B", 5);
	  
		// Setting up pools
		Pool playinsPoolOne = new Pool(PIPoolOne, Teams.LNG, Teams.HLE, Teams.BYG, Teams.C9); 
		Pool playinsPoolTwo = new Pool(PIPoolTwo, Teams.INF, Teams.GS, Teams.UOL, Teams.PCE, Teams.RED, Teams.DFM);
	  
		// Draw Teams
		PA.Add(playinsPoolOne.Draw()); 
		PB.Add(playinsPoolOne.Draw());
		PA.Add(playinsPoolOne.Draw()); 
		PB.Add(playinsPoolOne.Draw());
		PA.Add(playinsPoolTwo.Draw()); 
		PB.Add(playinsPoolTwo.Draw());
		PA.Add(playinsPoolTwo.Draw()); 
		PB.Add(playinsPoolTwo.Draw());
		PA.Add(playinsPoolTwo.Draw()); 
		PB.Add(playinsPoolTwo.Draw());
	  
		// Print out Group A
		System.out.println(PA + "\n");
		// Print out Group B
		System.out.println(PB + "\n");
		
		System.out.println("------------------------------------------------");
		
		// Play Out Groups
		PA.FullSimulate(PIGS, 1, true); 
	  	PA.PrintResults(); 
	  	PB.FullSimulate(PIGS, 1, true);
	  	PB.PrintResults();
	  	
	  	// Summary of Play-ins groups
		System.out.println("\nSummary of Play-ins Group Stage");
		PA.PrintResults();
		PB.PrintResults();
		
		// Simulate Play-ins Knockout Stage
		System.out.println("\nSimulating Play-ins Knockout Stage");
		
		Team[] qualified = SimulatePlayinsKO(PA, PB);
		
		// Printing
		System.out.print("\nQualified Play-ins Teams: ");
		for (int i = 0; i < qualified.length; i++) {
			if (i == qualified.length - 1) {
				System.out.println(qualified[i] + "\n");	
			} else {
				System.out.print(qualified[i] + " | ");	
			}
		}
		
		Pool PI = new Pool(QualifiedPI, qualified[0], qualified[1], qualified[2], qualified[3]);
		
		System.out.println("------------------------------------------------");
		
		// Simulate Main Group Draw
		System.out.println("\nSimulating Main Group Draw\n");
		
		// Setting up pools
		Pool P1 = new Pool(PoolOne, Teams.DK, Teams.EDG, Teams.MAD, Teams.PSG); 
		Pool P2 = new Pool(PoolTwo, Teams.O100T, Teams.FNC, Teams.GEN, Teams.FPX);
		Pool P3 = new Pool(PoolThree, Teams.TL, Teams.T1, Teams.RGE, Teams.RNG);
		
		// Setting up groups
		Group A = new Group("A", 4); 
		Group B = new Group("B", 4);
		Group C = new Group("C", 4); 
		Group D = new Group("D", 4);
		
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		ArrayList<Pool> pools = new ArrayList<Pool>(Arrays.asList(P2, P3, PI));
		
		// Draw Teams
		A.Add(P1.Draw());
		B.Add(P1.Draw());
		C.Add(P1.Draw());
		D.Add(P1.Draw());
		
		A.Add(P2.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(P2.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		C.Add(P2.DrawWithSameRegionRule(pools, 0, groups, 2, new ArrayList<Team>()));
		D.Add(P2.DrawWithSameRegionRule(pools, 0, groups, 3, new ArrayList<Team>()));
		pools.remove(0);
		
		A.Add(P3.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(P3.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		C.Add(P3.DrawWithSameRegionRule(pools, 0, groups, 2, new ArrayList<Team>()));
		D.Add(P3.DrawWithSameRegionRule(pools, 0, groups, 3, new ArrayList<Team>()));
		pools.remove(0);
		
		A.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		C.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 2, new ArrayList<Team>()));
		D.Add(PI.DrawWithSameRegionRule(pools, 0, groups, 3, new ArrayList<Team>()));
		pools.remove(0);
		
		// Printout Groups
		System.out.println(A + "\n");
		System.out.println(B + "\n");
		System.out.println(C + "\n");
		System.out.println(D + "\n");
		
		System.out.println("------------------------------------------------");
		
		// Simulate Main Group Stage
		System.out.println("\nSimulating Main Group Stage");
		
		// Play out games
		A.FullSimulate(MSGS, 2, true); 
		A.PrintResults();
		B.FullSimulate(MSGS, 2, true); 
		B.PrintResults();
		C.FullSimulate(MSGS, 2, true); 
		C.PrintResults();
		D.FullSimulate(MSGS, 2, true); 
		D.PrintResults();
		
		// Summary of Main Groups
		System.out.println("\nSummary of Main Group Stage");
		A.PrintResults();
		B.PrintResults();
		C.PrintResults();
		D.PrintResults();
		
		// Simulate Main Knockout Stage
		System.out.println("\nSimulating Main Knockout Stage");
		
		// Simulate Knockout Stage
		SimulateCurrentDrawKO(A, B, C, D);
		// SimulateCurrentPlusSecondThirdMatchesKO(A, B, C, D);
		// SimulateDoubleElimKO(A, B, C, D);
	}
	
	public static void SimulateCurrentWorldsState() throws Exception {
		// Play-ins Group Stage
		System.out.println("Simulating Play-ins Group Stage");
		Group PA = new Group("A (Play-ins)", 5, Teams.LNG, Teams.HLE, Teams.INF, Teams.PCE, Teams.RED);
		Group PB = new Group("B (Play-ins)", 5, Teams.C9, Teams.BYG, Teams.UOL, Teams.GS, Teams.DFM);
		
		PA.FullSimulate(PIGS, 1, true);
		PA.PrintResults();
		PB.FullSimulate(PIGS, 1, true);
		PB.PrintResults();
		
		// Play-ins Knockout Stage
		System.out.println("\nSimulating Play-ins Knockout Stage");
		Team[] qualified = SimulatePlayinsKO(PA, PB);
		Pool PI = new Pool(QualifiedPI, qualified[0], qualified[1], qualified[2], qualified[3]);
		
		// Main Group Stage
		System.out.println("\nSimulating Main Group Stage");
		Group A = new Group("A (Main)", 4, Teams.DK, Teams.FPX, Teams.RGE); 
		Group B = new Group("B (Main)", 4, Teams.EDG, Teams.O100T, Teams.T1);
		Group C = new Group("C (Main)", 4, Teams.PSG, Teams.FNC, Teams.RNG); 
		Group D = new Group("D (Main)", 4, Teams.MAD, Teams.GEN, Teams.TL);
		
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		ArrayList<Pool> pools = new ArrayList<Pool>(Arrays.asList(PI));
		
		// Draw Play-ins teams into groups
		
		/*
		A.Add(PI.DrawWithSameRegionRule(A, groups, 4, new ArrayList<Team>()));
		B.Add(PI.DrawWithSameRegionRule(B, groups, 4, new ArrayList<Team>()));
		C.Add(PI.DrawWithSameRegionRule(C, groups, 4, new ArrayList<Team>()));
		D.Add(PI.DrawWithSameRegionRule(D, groups, 4, new ArrayList<Team>()));
		*/
		
		A.FullSimulate(MSGS, 2, true); 
		A.PrintResults();
		B.FullSimulate(MSGS, 2, true); 
		B.PrintResults();
		C.FullSimulate(MSGS, 2, true); 
		C.PrintResults();
		D.FullSimulate(MSGS, 2, true); 
		D.PrintResults();
		
		// Main Knockout Stage
		System.out.println("Simulating Main Knockout Stage");
		SimulateCurrentDrawKO(A, B, C, D);
	}

	public static Team[] SimulatePlayinsKO(Group A, Group B) {
		Team t1 = A.GetTeamFromPlacement(3);
		Team t2 = B.GetTeamFromPlacement(3);
		Team t3 = A.GetTeamFromPlacement(4);
		Team t4 = B.GetTeamFromPlacement(4);
		Match M1 = new Match("M1", t1, t3);
		Match M2 = new Match("M2", t2, t4);
		M1.Simulate(PIKS, 5);
		M2.Simulate(PIKS, 5);
		
		Match M3 = new Match("M3", B.GetTeamFromPlacement(2), M1.getWinner());
		Match M4 = new Match("M4", A.GetTeamFromPlacement(2), M2.getWinner());
		M3.Simulate(PIKS, 5);
		M4.Simulate(PIKS, 5);
		
		Team[] qualified = new Team[] { A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), M3.getWinner(), M4.getWinner() };
		return qualified;
	}
	
	public static void SimulateCurrentDrawKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(PoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(PoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		Match M4 = new Match("M4");
		Match M5 = new Match("M5");
		Match M6 = new Match("M6");
		Match M7 = new Match("M7");
	
		ArrayList<Match> matches = new ArrayList<Match>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		M1.setTeamA(poolOne.Draw());
		M2.setTeamA(poolOne.Draw());
		M3.setTeamA(poolOne.Draw());
		M4.setTeamA(poolOne.Draw());
		
		M1.setTeamB(poolTwo.DrawWithSameSideRule(M1, M2, poolTwo, new ArrayList<Team>(), matches, groups));
		M2.setTeamB(poolTwo.DrawWithSameSideRule(M2, M1, poolTwo, new ArrayList<Team>(), matches, groups));
		M3.setTeamB(poolTwo.DrawWithSameSideRule(M3, M4, poolTwo, new ArrayList<Team>(), matches, groups));
		M4.setTeamB(poolTwo.DrawWithSameSideRule(M4, M3, poolTwo, new ArrayList<Team>(), matches, groups));
				
		M1.Simulate(MSKS, 5);
		M2.Simulate(MSKS, 5);
		M3.Simulate(MSKS, 5);
		M4.Simulate(MSKS, 5);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate(MSKS, 5);
		M6.Simulate(MSKS, 5);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate(MSKS, 5);
		
		Team winner = M7.getWinner();
		System.out.println("\n" + winner + " Records: " + winner.recordLog());
		System.out.println("\n" + winner + " has Won the World Championship");
		
		// Eventually can print out some stats
	}
	
	public static void SimulateCurrentPlusSecondThirdMatchesKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(PoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(PoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(PoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		Match M4 = new Match("M4");
		Match M5 = new Match("M5");
		Match M6 = new Match("M6");
		Match M7 = new Match("M7");
		Match M8 = new Match("M8");
		Match M9 = new Match("M9");
		Match M10 = new Match("M10");
		Match M11 = new Match("M11");
		
		ArrayList<Match> upperMatchups = new ArrayList<Match>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Match> lowerMatchups = new ArrayList<Match>(Arrays.asList(M5, M6, M7, M8));
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		M1.setTeamA(poolTwo.Draw());
		M2.setTeamA(poolTwo.Draw());
		M3.setTeamA(poolTwo.Draw());
		M4.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolThree.DrawWithSameSideRule(M1, M2, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M2.setTeamB(poolThree.DrawWithSameSideRule(M2, M1, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M3.setTeamB(poolThree.DrawWithSameSideRule(M3, M4, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M4.setTeamB(poolThree.DrawWithSameSideRule(M4, M3, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M1.Simulate(MSKS, 3);
		M2.Simulate(MSKS, 3);
		M3.Simulate(MSKS, 3);
		M4.Simulate(MSKS, 3);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate(MSKS, 5);
		M6.Simulate(MSKS, 5);
		M7.Simulate(MSKS, 5);
		M8.Simulate(MSKS, 5);
		
		M9.setTeamA(M5.getWinner());
		M9.setTeamB(M6.getWinner());
		M10.setTeamA(M7.getWinner());
		M10.setTeamB(M8.getWinner());
		M9.Simulate(MSKS, 5);
		M10.Simulate(MSKS, 5);
		
		M11.setTeamA(M9.getWinner());
		M11.setTeamB(M10.getWinner());
		M11.Simulate(MSKS, 5);
		System.out.println("\n" + M11.getWinner() + " has Won the World Championship");
	}
	
	
	public static void SimulateDoubleElimKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(PoolOne, A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(PoolTwo, A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(PoolThree, A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
		Match M1 = new Match("M1");
		Match M2 = new Match("M2");
		Match M3 = new Match("M3");
		Match M4 = new Match("M4");
		Match M5 = new Match("M5");
		Match M6 = new Match("M6");
		Match M7 = new Match("M7");
		Match M8 = new Match("M8");
		Match M9 = new Match("M9");
		Match M10 = new Match("M10");
		Match M11 = new Match("M11");
		Match M12 = new Match("M12");
		Match M13 = new Match("M13");
		Match M14 = new Match("M14");
		Match M15 = new Match("M15");
		Match M16 = new Match("M16");
		Match M17 = new Match("M17");
		Match M18 = new Match("M18");
		
		ArrayList<Match> upperMatchups = new ArrayList<Match>(Arrays.asList(M1, M2, M3, M4));
		ArrayList<Match> lowerMatchups = new ArrayList<Match>(Arrays.asList(M5, M6, M7, M8));
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		
		M1.setTeamA(poolTwo.Draw());
		M2.setTeamA(poolTwo.Draw());
		M3.setTeamA(poolTwo.Draw());
		M4.setTeamA(poolTwo.Draw());
		M1.setTeamB(poolThree.DrawWithSameSideRule(M1, M2, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M2.setTeamB(poolThree.DrawWithSameSideRule(M2, M1, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M3.setTeamB(poolThree.DrawWithSameSideRule(M3, M4, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M4.setTeamB(poolThree.DrawWithSameSideRule(M4, M3, poolThree, new ArrayList<Team>(), upperMatchups, groups));
		M1.Simulate(MSKS, 3);
		M2.Simulate(MSKS, 3);
		M3.Simulate(MSKS, 3);
		M4.Simulate(MSKS, 3);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate(MSKS, 5);
		M6.Simulate(MSKS, 5);
		M7.Simulate(MSKS, 5);
		M8.Simulate(MSKS, 5);
		
		M9.setTeamA(M5.getLoser());
		M9.setTeamB(M7.getLoser());
		M10.setTeamA(M6.getLoser());
		M10.setTeamB(M8.getLoser());
		M11.setTeamA(M5.getWinner());
		M11.setTeamB(M6.getWinner());
		M12.setTeamA(M7.getWinner());
		M12.setTeamB(M8.getWinner());
		M9.Simulate(MSKS, 5);
		M10.Simulate(MSKS, 5);
		M11.Simulate(MSKS, 5);
		M12.Simulate(MSKS, 5);
		
		M13.setTeamA(M11.getLoser());
		M13.setTeamB(M9.getWinner());
		M14.setTeamA(M12.getLoser());
		M14.setTeamB(M10.getWinner());
		M15.setTeamA(M11.getWinner());
		M15.setTeamB(M12.getWinner());
		M13.Simulate(MSKS, 5);
		M14.Simulate(MSKS, 5);
		M15.Simulate(MSKS, 5);
		
		M16.setTeamA(M13.getWinner());
		M16.setTeamB(M14.getWinner());
		M16.Simulate(MSKS, 5);
		
		M17.setTeamA(M15.getLoser());
		M17.setTeamB(M16.getWinner());
		M17.Simulate(MSKS, 5);
		
		M18.setTeamA(M15.getWinner());
		M18.setTeamB(M17.getWinner());
		M18.Simulate(MSKS, 5);
		
		System.out.println("\n" + M18.getWinner() + " has Won the World Championship");
	}
	
	public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
	}  
}
