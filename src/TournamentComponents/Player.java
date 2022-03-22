package TournamentComponents;

import Enums.REGION;
import Enums.ROLE;

public class Player {
	private String name;
	private float ratingOutOf1; // 1.00 is a perfect score; Think of it as a scale out of 100, just divided by 100
	private REGION region;
	private boolean isImport;
	private ROLE role;
	
	public String getName() {
		return name;
	}
	
	public float getRatingOutOf1() {
		return ratingOutOf1;
	}
	
	public REGION getRegion() {
		return region;
	}

	public boolean isImport() {
		return isImport;
	}

	public Player(String name, float ratingOutOf1, ROLE role, REGION region, boolean isImport) {
		super();
		this.name = name;
		this.ratingOutOf1 = ratingOutOf1;
		this.region = region;
		this.isImport = isImport;
		this.role = role;
	}
}