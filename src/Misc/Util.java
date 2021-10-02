package Misc;

import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

public class Util {
	public static void PrintLargeLineBreak() {
		System.out.println(Strings.LargeLineBreak);
	}
	
	public static void PrintMediumLineBreak() {
		System.out.println(Strings.MediumLineBreak);
	}
	
	public static void PrintSmallLineBreak() {
		System.out.println(Strings.SmallLineBreak);
	}
	
	public static void StartSection(String sectionLabel) {
		PrintMediumLineBreak();
		System.out.println("\nSimulating: " + sectionLabel);
		PrintMediumLineBreak();
	}

	public static void StartHeadline(String sectionLabel) {
		PrintLargeLineBreak();
		System.out.println("\nSimulating: " + sectionLabel);
		PrintLargeLineBreak();
	}
	
	public static <K, V> void NicePrintMap(Map<K, V> map) {
		System.out.println();
		Set<Entry<K, V>> set = map.entrySet();
		for (Entry<K, V> e : set) {
			System.out.println(e.getKey() + ", " + e.getValue() + "\n");
		}
	}
}
