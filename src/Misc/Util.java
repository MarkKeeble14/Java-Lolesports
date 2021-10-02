package Misc;

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
}
