package Misc;

import java.text.DecimalFormat;
import java.util.Map;
import java.util.Set;

import Classes.Team;
import TournamentSimulator.Driver;

import java.util.Map.Entry;

public class Util {
	public static void PrintLargeLineBreak() {
		if (Driver.PRINT_OUTPUT) {
			System.out.println(Strings.LargeLineBreak);			
		}
	}
	
	public static void PrintMediumLineBreak() {
		if (Driver.PRINT_OUTPUT) {
			System.out.println(Strings.MediumLineBreak);			
		}
	}
	
	public static void PrintSmallLineBreak() {
		if (Driver.PRINT_OUTPUT) {
			System.out.println(Strings.SmallLineBreak);			
		}
	}
	
	public static void PrintLargeLineBreak(boolean linebreak) {
		if (Driver.PRINT_OUTPUT) {
			if (linebreak) {
				System.out.println(Strings.LargeLineBreak);	
			} else {
				System.out.println(Strings.LargeLineBreakNoNL);	
			}
		}
	}
	
	public static void PrintMediumLineBreak(boolean linebreak) {
		if (Driver.PRINT_OUTPUT) {
			if (linebreak) {
				System.out.println(Strings.MediumLineBreak);	
			} else {
				System.out.println(Strings.MediumLineBreakNoNL);	
			}
		}
	}
	
	public static void PrintSmallLineBreak(boolean linebreak) {
		if (Driver.PRINT_OUTPUT) {
			if (linebreak) {
				System.out.println(Strings.SmallLineBreak);	
			} else {
				System.out.println(Strings.SmallLineBreakNoNL);	
			}
		}
	}
	
	public static void Print(String s) {
		if (Driver.PRINT_OUTPUT) {
			System.out.println(s);			
		}
	}
	
	public static void PrintChampionship(String s) {
		if (Driver.PRINT_WINNER) {
			System.out.println(s);			
		}
	}
	
	public static void StartSection(String sectionLabel) {
		if (Driver.PRINT_OUTPUT) {
			PrintMediumLineBreak();
			System.out.println("\nSimulating: " + sectionLabel);
			PrintMediumLineBreak();	
		}
	}

	public static void StartHeadline(String sectionLabel) {
		if (Driver.PRINT_OUTPUT) {
			PrintLargeLineBreak();
			System.out.println("\nSimulating: " + sectionLabel);
			PrintLargeLineBreak();
		}
	}
	
	public static <K, V> void NicePrintMap(Map<K, V> map) {
		Set<Entry<K, V>> set = map.entrySet();
		for (Entry<K, V> e : set) {
			System.out.println(e.getKey() + ", " + e.getValue() + "\n");
		}
	}
	
	public static void NicePrintResults(Map<Team, Integer> map, int timesRan) {
		DecimalFormat df = new DecimalFormat("#0.00###");
		Set<Entry<Team, Integer>> set = map.entrySet();
		for (Entry<Team, Integer> e : set) {
			float n = (float) e.getValue() / timesRan * 100;
			System.out.println(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%\n");
		}
	}
}
