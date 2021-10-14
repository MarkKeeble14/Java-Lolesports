package Misc;

import Drivers.DomesticDriver;
import Drivers.InternationalDriver;

public class GlobalVariables {
	private static DriverType CURRENT_DRIVER;
	
	public static int ELO_SCALING;
	
	public static final boolean SHOW_REGIONAL_WL_WITH_0_GAMES = false;
	
	public static final boolean PRINT_DETAILED_SERIES_SUMMARY = true;
	
	public static final boolean PRINT_QUALIFICATION_REASONS = true;
	
	public static final boolean PRINT_GROUP_STAGE_SUMMARY = true;
	
	public static final boolean PRINT_OVERALL_WL = true;
	public static final boolean PRINT_INDIVIDUAL_WL = true;
	public static final boolean PRINT_MAJOR_REGIONAL_WL = true;
	public static final boolean PRINT_MINOR_REGIONAL_WL = false;

	public static final boolean PRINT_BRACKET_SUMMARY = true;
	
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

	public static int getEloScaling() {
		return ELO_SCALING;
	}

	public static void setEloScaling(int eLO_SCALING) {
		ELO_SCALING = eLO_SCALING;
	}
}
