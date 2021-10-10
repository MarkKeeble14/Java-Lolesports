package Misc;

public class Strings {
	// Stages
	public static final String PIGD = "PI Group Draw";
	public static final String PIGS = "PI Group Stage";
	public static final String PIKS = "PI Knockout Stage";
	public static final String MSGD = "Main Stage Group Draw";
	public static final String MSGS = "Main Stage Group Stage";
	public static final String MSKS = "Main Stage Knockout Stage";
	
	public static final String GSGD = "Group Stage Group Draw";
	public static final String RSGS = "Rumble Stage Group Stage";
	public static final String RSKS = "Rumble Stage Knockout Stage";
	public static final String GS = "Group Stage";

	public static final String QFS = "Quaterfinals";
	public static final String SFS = "Semifinals";
	public static final String FS = "Finals";
	
	public static final String WB = "Winners Bracket";
	public static final String LB = "Losers Bracket";
	
	public static final String S1 = "Stage 1";
	public static final String S2 = "Stage 2";
	public static final String S3 = "Stage 3";
	public static final String S4 = "Stage 4";
	public static final String S5 = "Stage 5";
	
	// Pools
	public static final String LPoolOne = "Pool One";
	public static final String LPoolTwo = "Pool Two";
	public static final String LPoolThree = "Pool Three";
	public static final String LPIPoolOne = "PI - Pool One";
	public static final String LPIPoolTwo = "PI - Pool Two";
	public static final String LQualifiedPI = "Qualified Through PI";

	// Labels
	public static final String LFirstGroup = "A";
	public static final String LSecondGroup = "B";
	public static final String LThirdGroup = "C";
	public static final String LFourthGroup = "D";
	
	public static final String LRumble = "Rumble";
	public static final String LMSI = "MSI";
	public static final String LWC = "the World Championship";
	
	public static final String SmallLineBreak = "\n----------------------------------------";
	public static final String MediumLineBreak = "\n---------------------------------------"
			+ "----------------------------------------";
	public static final String LargeLineBreak = "\n----------------------------------------"
			+ "--------------------------------------------------------------------------------";
	
	public static final String SmallLineBreakNoNL = "----------------------------------------";
	public static final String MediumLineBreakNoNL = "---------------------------------------"
			+ "----------------------------------------";
	public static final String LargeLineBreakNoNL = "----------------------------------------"
			+ "--------------------------------------------------------------------------------";
	public static final String EOTStandings = "Results";
	
	public static final String BasicBridge = "-";
	public static final String BasicBridgeWSpace = " - ";
	
	public static final String R34 = "Round of Two VS Three";
	public static final String WQFS = "Winners Quarterfinals";
	public static final String WSFS = "Winners Semifinals";
	public static final String WFS = "Winners Finals";
	
	public static final String LR1 = "Losers Round 1";
	public static final String LR2 = "Losers Round 2";
	public static final String LR3 = "Losers Round 3";
	public static final String LFS = "Losers Finals";
	
	public static final String GFS = "Grand Finals";
	
	public static final String TableFormat = "%-15s %-7s %-12s %-20s%n";
	
	public static final String StandingFormat = "%-50s %-20s";
	
	public static String Concat(String bridge, String... strings) {
		String s = strings[0];
		
		for (int i = 1; i < strings.length; i++) {
			s += bridge + strings[i];
		}
		
		return s;
	}
}
