package bracketgen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Driver {
	// LPL
	public static Team EDG = new Team("EDG", Region.CN, 7.154);
	public static Team FPX = new Team("FPX", Region.CN, 7.939);
	public static Team RNG = new Team("RNG", Region.CN, 6.827);
	public static Team LNG = new Team("LNG", Region.CN, 5.497);
	
	// PCS
	public static Team PSG = new Team("PSG", Region.SEA, 5.131);
	public static Team BYG = new Team("BYG", Region.SEA, 3.385);
	
	// LCS
	public static Team O100T = new Team("100", Region.NA, 4.732);
	public static Team TL = new Team("TL", Region.NA, 4.456);
	public static Team C9 = new Team("C9", Region.NA, 4.482);
	
	// LCK
	public static Team DK = new Team("DK", Region.KR, 7.775);
	public static Team GEN = new Team("GEN", Region.KR, 5.779);
	public static Team T1 = new Team("T1", Region.KR, 6.720);
	public static Team HLE = new Team("HLE", Region.KR, 5.282);
	
	// LEC
	public static Team MAD = new Team("MAD", Region.EU, 6.818);
	public static Team FNC = new Team("FNC", Region.EU, 6.153);
	public static Team RGE = new Team("RGE", Region.EU, 4.912);
	
	// LLA
	public static Team INF = new Team("INF", Region.LAN, 2.425);
	
	// TCL
	public static Team GS = new Team("GS", Region.TR, 2.675);
	
	// LCL
	public static Team UOL = new Team("UOL", Region.RUS, 3.568);
	
	// LCO
	public static Team PCE = new Team("PCE", Region.OCE, 2.368);
	
	// CBLOL
	public static Team RED = new Team("RED", Region.BR, 2.297);
	
	// LJL
	public static Team DFM = new Team("DFM", Region.JP, 2.425);
	
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
		Group PA = new Group("A (Play-ins)", 5); 
		Group PB = new Group("B (Play-ins)", 5);
	  
		// Setting up pools
		Pool playinsPoolOne = new Pool(LNG, HLE, BYG, C9); 
		Pool playinsPoolTwo = new Pool(INF, GS, UOL, PCE, RED, DFM);
	  
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
		System.out.println("Group A: " + PA + "\n");
		// Print out Group B
		System.out.println("Group B: " + PB + "\n");
		
		System.out.println("------------------------------------------------");
		
		// Play Out Groups
		PA.FullSimulate(1, true); 
	  	PA.PrintResults(); 
	  	PB.FullSimulate(1, true);
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
		
		Pool PI = new Pool(qualified[0], qualified[1], qualified[2], qualified[3]);
		
		System.out.println("------------------------------------------------");
		
		// Simulate Main Group Draw
		System.out.println("\nSimulating Main Group Draw\n");
		
		// Setting up pools
		Pool P1 = new Pool(DK, EDG, MAD, PSG); 
		Pool P2 = new Pool(O100T, FNC, GEN, FPX);
		Pool P3 = new Pool(TL, T1, RGE, RNG);
		
		// Setting up groups
		Group A = new Group("A (Main)", 4); 
		Group B = new Group("B (Main)", 4);
		Group C = new Group("C (Main)", 4); 
		Group D = new Group("D (Main)", 4);
		
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		ArrayList<Pool> pools = new ArrayList<Pool>(Arrays.asList(P2, P3, PI));
		
		// Draw Teams
		A.Add(P1.Draw());
		B.Add(P1.Draw());
		C.Add(P1.Draw());
		D.Add(P1.Draw());
		
		A.Add(P2.DrawWithSameRegionRule(A, groups, 2, new ArrayList<Team>()));
		B.Add(P2.DrawWithSameRegionRule(B, groups, 2, new ArrayList<Team>()));
		C.Add(P2.DrawWithSameRegionRule(C, groups, 2, new ArrayList<Team>()));
		D.Add(P2.DrawWithSameRegionRule(D, groups, 2, new ArrayList<Team>()));
		
		A.Add(P3.DrawWithSameRegionRule(A, groups, 3, new ArrayList<Team>()));
		B.Add(P3.DrawWithSameRegionRule(B, groups, 3, new ArrayList<Team>()));
		C.Add(P3.DrawWithSameRegionRule(C, groups, 3, new ArrayList<Team>()));
		D.Add(P3.DrawWithSameRegionRule(D, groups, 3, new ArrayList<Team>()));
		
		A.Add(PI.DrawWithSameRegionRule(A, groups, 4, new ArrayList<Team>()));
		B.Add(PI.DrawWithSameRegionRule(B, groups, 4, new ArrayList<Team>()));
		C.Add(PI.DrawWithSameRegionRule(C, groups, 4, new ArrayList<Team>()));
		D.Add(PI.DrawWithSameRegionRule(D, groups, 4, new ArrayList<Team>()));
		
		// Printout Groups
		System.out.println("Group A: " + A + "\n");
		System.out.println("Group B: " + B + "\n");
		System.out.println("Group C: " + C + "\n");
		System.out.println("Group D: " + D + "\n");
		
		System.out.println("------------------------------------------------");
		
		// Simulate Main Group Stage
		System.out.println("\nSimulating Main Group Stage");
		
		// Play out games
		A.FullSimulate(2, true); 
		A.PrintResults();
		B.FullSimulate(2, true); 
		B.PrintResults();
		C.FullSimulate(2, true); 
		C.PrintResults();
		D.FullSimulate(2, true); 
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
		Group PA = new Group("A (Play-ins)", 5, LNG, HLE, INF, PCE, RED);
		Group PB = new Group("B (Play-ins)", 5, C9, BYG, UOL, GS, DFM);
		
		PA.FullSimulate(2, true);
		PA.PrintResults();
		PB.FullSimulate(2, true);
		PB.PrintResults();
		
		// Play-ins Knockout Stage
		System.out.println("\nSimulating Play-ins Knockout Stage");
		Team[] qualified = SimulatePlayinsKO(PA, PB);
		Pool qualifiedThroughPlayins = new Pool(qualified[0], qualified[1], qualified[2], qualified[3]);
		
		// Main Group Stage
		System.out.println("\nSimulating Main Group Stage");
		Group A = new Group("A (Main)", 4, DK, FPX, RGE); 
		Group B = new Group("B (Main)", 4, EDG, O100T, T1);
		Group C = new Group("C (Main)", 4, PSG, FNC, RNG); 
		Group D = new Group("D (Main)", 4, MAD, GEN, TL);
		
		ArrayList<Group> groups = new ArrayList<Group>(Arrays.asList(A, B, C, D));
		ArrayList<Pool> pools = new ArrayList<Pool>(Arrays.asList(qualifiedThroughPlayins));
		
		// Draw Play-ins teams into groups
		/*
		A.Add(qualifiedThroughPlayins.DrawWithSameRegionRule(new ArrayList<Team>(), A, groups, 4));
		B.Add(qualifiedThroughPlayins.DrawWithSameRegionRule(new ArrayList<Team>(), B, groups, 4));
		C.Add(qualifiedThroughPlayins.DrawWithSameRegionRule(new ArrayList<Team>(), C, groups, 4));
		D.Add(qualifiedThroughPlayins.DrawWithSameRegionRule(new ArrayList<Team>(), D, groups, 4));
		*/
		
		A.FullSimulate(2, true); 
		A.PrintResults();
		B.FullSimulate(2, true); 
		B.PrintResults();
		C.FullSimulate(2, true); 
		C.PrintResults();
		D.FullSimulate(2, true); 
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
		M1.Simulate(5);
		M2.Simulate(5);
		
		Match M3 = new Match("M3", B.GetTeamFromPlacement(2), M1.getWinner());
		Match M4 = new Match("M4", A.GetTeamFromPlacement(2), M2.getWinner());
		M3.Simulate(5);
		M4.Simulate(5);
		
		Team[] qualified = new Team[] { A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), M3.getWinner(), M4.getWinner() };
		return qualified;
	}
	
	public static void SimulateCurrentDrawKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		
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
				
		M1.Simulate(5);
		M2.Simulate(5);
		M3.Simulate(5);
		M4.Simulate(5);
		
		M5.setTeamA(M1.getWinner());
		M5.setTeamB(M2.getWinner());
		M6.setTeamA(M3.getWinner());
		M6.setTeamB(M4.getWinner());
		M5.Simulate(5);
		M6.Simulate(5);
		
		M7.setTeamA(M5.getWinner());
		M7.setTeamB(M6.getWinner());
		M7.Simulate(5);
		System.out.println("\n" + M7.getWinner() + " has Won the World Championship");
	}
	
	public static void SimulateCurrentPlusSecondThirdMatchesKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
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
		M1.Simulate(3);
		M2.Simulate(3);
		M3.Simulate(3);
		M4.Simulate(3);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate(5);
		M6.Simulate(5);
		M7.Simulate(5);
		M8.Simulate(5);
		
		M9.setTeamA(M5.getWinner());
		M9.setTeamB(M6.getWinner());
		M10.setTeamA(M7.getWinner());
		M10.setTeamB(M8.getWinner());
		M9.Simulate(5);
		M10.Simulate(5);
		
		M11.setTeamA(M9.getWinner());
		M11.setTeamB(M10.getWinner());
		M11.Simulate(5);
		System.out.println("\n" + M11.getWinner() + " has Won the World Championship");
	}
	
	
	public static void SimulateDoubleElimKO(Group A, Group B, Group C, Group D) throws Exception {
		Pool poolOne = new Pool(A.GetTeamFromPlacement(1), B.GetTeamFromPlacement(1), C.GetTeamFromPlacement(1), D.GetTeamFromPlacement(1));
		Pool poolTwo = new Pool(A.GetTeamFromPlacement(2), B.GetTeamFromPlacement(2), C.GetTeamFromPlacement(2), D.GetTeamFromPlacement(2));
		Pool poolThree = new Pool(A.GetTeamFromPlacement(3), B.GetTeamFromPlacement(3), C.GetTeamFromPlacement(3), D.GetTeamFromPlacement(3));
		
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
		M1.Simulate(3);
		M2.Simulate(3);
		M3.Simulate(3);
		M4.Simulate(3);
		
		M5.setTeamB(M1.getWinner());
		M6.setTeamB(M2.getWinner());
		M7.setTeamB(M3.getWinner());
		M8.setTeamB(M4.getWinner());
		M5.setTeamA(poolOne.DrawWithSameMatchRule(M5, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M6.setTeamA(poolOne.DrawWithSameMatchRule(M6, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M7.setTeamA(poolOne.DrawWithSameMatchRule(M7, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M8.setTeamA(poolOne.DrawWithSameMatchRule(M8, poolOne, new ArrayList<Team>(), lowerMatchups, groups));
		M5.Simulate(5);
		M6.Simulate(5);
		M7.Simulate(5);
		M8.Simulate(5);
		
		M9.setTeamA(M5.getLoser());
		M9.setTeamB(M7.getLoser());
		M10.setTeamA(M6.getLoser());
		M10.setTeamB(M8.getLoser());
		M11.setTeamA(M5.getWinner());
		M11.setTeamB(M6.getWinner());
		M12.setTeamA(M7.getWinner());
		M12.setTeamB(M8.getWinner());
		M9.Simulate(5);
		M10.Simulate(5);
		M11.Simulate(5);
		M12.Simulate(5);
		
		M13.setTeamA(M11.getLoser());
		M13.setTeamB(M9.getWinner());
		M14.setTeamA(M12.getLoser());
		M14.setTeamB(M10.getWinner());
		M15.setTeamA(M11.getWinner());
		M15.setTeamB(M12.getWinner());
		M13.Simulate(5);
		M14.Simulate(5);
		M15.Simulate(5);
		
		M16.setTeamA(M13.getWinner());
		M16.setTeamB(M14.getWinner());
		M16.Simulate(5);
		
		M17.setTeamA(M15.getLoser());
		M17.setTeamB(M16.getWinner());
		M17.Simulate(5);
		
		M18.setTeamA(M15.getWinner());
		M18.setTeamB(M17.getWinner());
		M18.Simulate(5);
		
		System.out.println("\n" + M18.getWinner() + " has Won the World Championship");
	}
	
	public static void ClearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
	}  
}
