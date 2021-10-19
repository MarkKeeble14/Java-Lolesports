package Utility;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import DefiningTeams.Team;
import Drivers.DomesticDriver;
import StaticVariables.Strings;
import Stats.Record;

public class Util {
	public static void PrintLargeLineBreak(boolean linebreak) {
		if (linebreak) {
			System.out.println(Strings.LargeLineBreak);	
		} else {
			System.out.println(Strings.LargeLineBreakNoNL);	
		}
	}
	
	public static void PrintMediumLineBreak(boolean linebreak) {
		if (linebreak) {
			System.out.println(Strings.MediumLineBreak);	
		} else {
			System.out.println(Strings.MediumLineBreakNoNL);	
		}
	}
	
	public static void PrintSmallLineBreak(boolean linebreak) {
		if (linebreak) {
			System.out.println(Strings.SmallLineBreak);	
		} else {
			System.out.println(Strings.SmallLineBreakNoNL);	
		}
	}
	
	public static void StartSection(String sectionLabel) {
		PrintMediumLineBreak(true);
		System.out.println("\nSimulating: " + sectionLabel);
		PrintMediumLineBreak(true);	
	}

	public static void StartHeadline(String sectionLabel) {
		PrintLargeLineBreak(true);
		System.out.println("\nSimulating: " + sectionLabel);
		PrintLargeLineBreak(true);
	}
	
	public static void PrintSectionBreak(String sectionLabel) {
		PrintLargeLineBreak(true);
		System.out.println("\n" + sectionLabel);
		PrintLargeLineBreak(true);
	}
	
	public static <K, V> void NicePrintMap(Map<K, V> map) {
		Set<Entry<K, V>> set = map.entrySet();
		for (Entry<K, V> e : set) {
			System.out.println(e.getKey() + ", " + e.getValue() + "\n");
		}
	}
	
	public static <K> void NicePrintResults(Map<K, Integer> map, int timesRan) {
		PrintMediumLineBreak(true);
		System.out.println("\n# of Times X Team Won; Results from " + timesRan + " Simulations");
		PrintMediumLineBreak(true);
		System.out.println();
		
		DecimalFormat df = new DecimalFormat("#0.00###");
		Set<Entry<K, Integer>> set = map.entrySet();
		int i = 0;
		for (Entry<K, Integer> e : set) {
			float n = (float) e.getValue() / timesRan * 100;
			
			if (i == set.size() - 1) {
				System.out.println(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%");
			} else {
				System.out.println(e.getKey() + ", " + e.getValue() + ": Percent - " + df.format(n) + "%\n");	
			}
			i++;
		}
	}
}
