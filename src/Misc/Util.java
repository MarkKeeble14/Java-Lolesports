package Misc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

import Classes.Record;
import Classes.Team;
import TournamentSimulator.Driver;

import java.util.Map.Entry;

public class Util {
	public static void PrintLargeLineBreak(boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			System.out.println(Strings.LargeLineBreak);			
		}
	}
	
	public static void PrintMediumLineBreak(boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			System.out.println(Strings.MediumLineBreak);			
		}
	}
	
	public static void PrintSmallLineBreak(boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			System.out.println(Strings.SmallLineBreak);			
		}
	}
	
	public static void PrintLargeLineBreak(boolean linebreak, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			if (linebreak) {
				System.out.println(Strings.LargeLineBreak);	
			} else {
				System.out.println(Strings.LargeLineBreakNoNL);	
			}
		}
	}
	
	public static void PrintMediumLineBreak(boolean linebreak, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			if (linebreak) {
				System.out.println(Strings.MediumLineBreak);	
			} else {
				System.out.println(Strings.MediumLineBreakNoNL);	
			}
		}
	}
	
	public static void PrintSmallLineBreak(boolean linebreak, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			if (linebreak) {
				System.out.println(Strings.SmallLineBreak);	
			} else {
				System.out.println(Strings.SmallLineBreakNoNL);	
			}
		}
	}
	
	public static void Print(String s, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			System.out.println(s);			
		}
	}
	
	public static void StartSection(String sectionLabel, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			PrintMediumLineBreak(forcePrint);
			System.out.println("\nSimulating: " + sectionLabel);
			PrintMediumLineBreak(forcePrint);	
		}
	}

	public static void StartHeadline(String sectionLabel, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			PrintLargeLineBreak(forcePrint);
			System.out.println("\nSimulating: " + sectionLabel);
			PrintLargeLineBreak(forcePrint);
		}
	}
	
	public static void PrintSectionBreak(String sectionLabel, boolean forcePrint) {
		if (Driver.PRINT_OUTPUT || forcePrint) {
			PrintLargeLineBreak(forcePrint);
			System.out.println("\n" + sectionLabel);
			PrintLargeLineBreak(forcePrint);
		}
	}
	
	public static <K, V> void NicePrintMap(Map<K, V> map) {
		if (!Driver.PRINT_OUTPUT) {
			return;
		}
		
		Set<Entry<K, V>> set = map.entrySet();
		for (Entry<K, V> e : set) {
			System.out.println(e.getKey() + ", " + e.getValue() + "\n");
		}
	}
	
	public static <K> void NicePrintResults(Map<K, Integer> map, int timesRan) {
		PrintMediumLineBreak(false, true);
		System.out.println("\nResults of Simulating the tournament: " + timesRan + " times");
		PrintMediumLineBreak(true, true);
		System.out.println();
		
		DecimalFormat df = new DecimalFormat("#0.00###");
		Set<Entry<K, Integer>> set = map.entrySet();
		int i = 0;
		for (Entry<K, Integer> e : set) {
			float n = (float) e.getValue() / timesRan * 100;
			
			if (i == set.size() - 1) {
				Util.Print(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%", true);
			} else {
				Util.Print(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%\n", true);	
			}
			i++;
		}
	}
	
	public static void NicePrintStandings(Map<Team, Integer> map) {
		PrintMediumLineBreak(true, true);
		Util.Print("\nResults:", true);
		PrintMediumLineBreak(true, true);
		System.out.println();
		
		ArrayList<Team> keys = new ArrayList<Team>(map.keySet());
        for(int i=keys.size()-1; i>=0;i--){
        	Team t = keys.get(i);
        	Record top = t.getTopRecord();
        	
        	if (i == 0) {
            	Util.Print(t.getTag() + ": Finished - " + map.get(t) + " | Run Ended During: " 
            	        + top.getLabel(), true);
        	} else {
            	Util.Print(t.getTag() + ": Finished - " + map.get(t) + " | Run Ended During: " 
            	        + top.getLabel() + "\n", true);	
        	}
        }
	}
}
