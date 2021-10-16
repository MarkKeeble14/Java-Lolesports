package Misc;

import Drivers.DomesticDriver;
import Drivers.InternationalDriver;

public class GlobalVariables {
	private static DriverType CURRENT_DRIVER;
	
	// Variables / Tuning
	// Higher means rating matters more, i.e, Upsets are LESS likely
	// A scale of 1 makes most matchups 50/50
	
	// 300 means upsets are reasonably likely; Works well for international competitions
	// 75 is very rigid; Works well for domestic competitions
	public static int ELO_SCALING;
	private static int REASONABLE_ELO_SCALING = 75;
	private static int RIGID_ELO_SCALING = 300;
	private static int PURE_ELO_SCALING = 500;
	private static int WACKY_ELO_SCALING = 50;
	
	public static final boolean SHOW_REGIONAL_WL_WITH_0_GAMES = false;
	
	public static final boolean PRINT_DETAILED_SERIES_SUMMARY = true;
	
	public static final boolean PRINT_QUALIFICATION_REASONS = true;
	
	public static final boolean PRINT_GROUP_STAGE_SUMMARY = true;
	
	public static final boolean PRINT_OVERALL_WL = true;
	public static final boolean PRINT_INDIVIDUAL_WL = true;
	public static final boolean PRINT_MAJOR_REGIONAL_WL = true;
	public static final boolean PRINT_MINOR_REGIONAL_WL = true;

	public static final boolean PRINT_BRACKET_SUMMARY = true;
	
	public static void setEloScaling(ELO_SCALING_TYPE type) {
		switch(type) {
		case REASONABLE:
			ELO_SCALING = REASONABLE_ELO_SCALING;
			break;
		case WACKY:
			ELO_SCALING = WACKY_ELO_SCALING;
			break;
		case RIGID:
			ELO_SCALING = RIGID_ELO_SCALING;
			break;
		case PURE:
			ELO_SCALING = PURE_ELO_SCALING;
			break;
		}
	}

	public static int getEloScaling() {
		return ELO_SCALING;
	}

	public static void setEloScaling(int eLO_SCALING) {
		ELO_SCALING = eLO_SCALING;
	}
	
	public static DriverType getCurrentDriver() {
		return CURRENT_DRIVER;
	}

	public static void setCurrentDriver(DriverType currentDriver) {
		CURRENT_DRIVER = currentDriver;
		
		if (currentDriver == DriverType.International) {
			setEloScaling(InternationalDriver.ELO_SCALING);
		} else {
			setEloScaling(DomesticDriver.ELO_SCALING);
		}
	}
}
