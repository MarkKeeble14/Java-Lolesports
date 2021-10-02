package Classes;

import java.util.Random;

import Misc.Util;
import TournamentSimulator.Driver;

public class Match {
	private String label;
	private Team teamA;
	private Team teamB;
	
	private Team winner;
	private Team loser;
	
	Random rand = new Random();
	
	/**
	* Constructor
	* @param label The match id/number of the match, i.e., M1, M2, M3, etc
	* @param A Combatant A 
	* @param B Combatant B
	*/
	public Match(String label, Team A, Team B) {
		this.label = label;
		teamA = A;
		teamB = B;
	}
	
	/**
	* Constructor
	* @param label The match id/number of the match, i.e., M1, M2, M3, etc
	*/
	public Match(String label) {
		this.label = label;
	}

	// Simulates the match playing out, the team with the higher rating wins
	public void Simulate(String stageLabel, int bestOf, boolean printLineBreak) {
		int scale = 100;
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * scale;
		double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * scale;
		
		Util.Print("\nMatch Between: " + teamA + ", and: " + teamB);
		Util.Print(teamA + " Odds - " + oddsTeamAWins + ", " + teamB + " Odds - " + oddsTeamBWins);
		
		// Best of is equal to the number of games to play
		if (bestOf > 1) {
			Util.Print("\nBest of " + bestOf + ": " + teamA + " vs " + teamB + "\n");
			
			// Set Variables
			teamA.setNewRecord(stageLabel);
			Record teamARecord = teamA.getRecord();
			
			teamB.setNewRecord(stageLabel);
			Record teamBRecord = teamB.getRecord();
			
			int goal = (int) Math.ceil((double) bestOf / 2);
			
			// Simulate the games
			for (int i = 1; i < bestOf + 1; i++) {
				Util.Print("Game #" + i);
				double random = rand.nextDouble() * scale;
				// Team A Wins the game
				if (random > 0 && random < oddsTeamAWins) {
					teamARecord.MatchWin(teamB);
					teamBRecord.MatchLoss(teamA);
					Util.Print(teamA.getTag() + " Win\n");
					
					// Team A Wins the match
					if (teamARecord.getWins() == goal) { // Team A Wins
						winner = teamA;
						loser = teamB;
						
						Util.Print(label + ": " + winner.getTag() + " has beaten " + loser.getTag() 
						+ ": Gamescore: " + teamARecord.getWins() + "-" + teamBRecord.getWins());
						
						if (printLineBreak) {
							Util.PrintSmallLineBreak();	
						}
						break;
					}
				} else {
					// Team B Wins the game
					teamBRecord.MatchWin(teamA);
					teamARecord.MatchLoss(teamB);
					Util.Print(teamB.getTag() + " Win\n");
				
					// Team B Wins the match
					if (teamBRecord.getWins() == goal) { // Team B wins
						winner = teamB;
						loser = teamA;
						
						Util.Print(label + ": " + winner.getTag() + " has beaten " + loser.getTag() 
						+ ": Gamescore: " + teamBRecord.getWins() + "-" + teamARecord.getWins());
						
						if (printLineBreak) {
							Util.PrintSmallLineBreak();	
						}
						break;
					}
				}
			}	
		} else {
			double random = rand.nextDouble() * scale;
			if (random > 0 && random < oddsTeamAWins) {
				winner = teamA;
				loser = teamB;
			} else {
				winner = teamB;
				loser = teamA;
			}
			Util.Print(label + ": " + winner.getTag() + " has beaten " + loser.getTag());
		}
	}

	public Team getWinner() {
		return winner;
	}

	public Team getLoser() {
		return loser;
	}

	public Team getTeamA() {
		return teamA;
	}

	public Team getTeamB() {
		return teamB;
	}

	public void setTeamA(Team teamA) {
		this.teamA = teamA;
	}

	public void setTeamB(Team teamB) {
		this.teamB = teamB;
	}

	@Override
	public String toString() {
		if (winner == null) {
			return label + ": " + teamA.getTag() + " VS " + teamB.getTag();
		} else {
			String s = label + teamA + " VS " + teamB + "\n";
			if (winner == teamA) {
				s += teamA.getTag() + " > " + teamB.getTag();
			} else {
				s += teamB.getTag() + " > " + teamA.getTag();
			}
			return s;
		}
	}
	
	// Calculates the chance of a team with rating RA beating a team with rating RB
	private double CalculateChance(double RA, double RB) {
		// Scale is used to bump up the ratings to a larger number so that the ELO formula would work better
		// It essentially just exaggerates the odds/makes an actual discernable difference between teams 
		// with conventionally small elo ratings
		
		// Higher means rating matters more
		// A scale of 1 makes most matchups 50/50
		int scale = Driver.ELO_SCALING;
		RA = RA * scale;
		RB = RB * scale;
		
		// Formula to determine chance
		double exp = (RB-RA)/400;
		double x = Math.pow(10, exp);
		double y = 1 + x;
		double z = 1 / y;
		return z;
	}
}
