package Matches;

import java.text.DecimalFormat;
import java.util.Random;

import Classes.Team;
import StatsTracking.RegionalWLTracker;
import TournamentSimulator.Driver;

public class Game extends Matchup {
	private String stageLabel;
	private String matchLabel;
	private Team teamA;
	private Team teamB;
	
	private Team winner;
	private Team loser;
	
	private Random rand = new Random();
	private RegionalWLTracker WLT;
	
	public void Simulate() {
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * 100;
		
		double random = rand.nextDouble() * 100;
		if (random > 0 && random < oddsTeamAWins) {
			winner = teamA;
			loser = teamB;
		} else {
			winner = teamB;
			loser = teamA;
		}
		
		// Update Teams Records
		winner.getRecord().MatchWin(loser);
		loser.getRecord().MatchLoss(winner);
		
		WLT.Update(winner, loser);
	}
	
	/**
	* Constructor
	* @param label The match id/number of the match, i.e., M1, M2, M3, etc
	* @param A Combatant A 
	* @param B Combatant B
	*/
	public Game(String sl, String l, Team A, Team B, RegionalWLTracker t) {
		stageLabel = sl;
		matchLabel = l;
		teamA = A;
		teamB = B;
		WLT = t;
	}
	
	/**
	* Constructor
	* @param label The match id/number of the match, i.e., M1, M2, M3, etc
	*/
	public Game(String sl, String l, RegionalWLTracker t) {
		stageLabel = sl;
		matchLabel = l;
		WLT = t;
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
			return getMatchDetails();
		} else {
			String s = getMatchDetails();
			if (winner == teamA) {
				s += teamA.getTag() + " > " + teamB.getTag();
			} else {
				s += teamA.getTag() + " < " + teamB.getTag();
			}
			return s;	
		}
	}
	
	public String getMatchDetails() {
		String s = stageLabel + ": " + matchLabel + " - " + teamA + " VS " + teamB + "\n";
		
		DecimalFormat df = new DecimalFormat("00.00");
		
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * 100;
		double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * 100;
		
		s += teamA.getTag() + ": " + df.format(oddsTeamAWins) + "% Chance to Win\n";
		s += teamB.getTag() + ": " + df.format(oddsTeamBWins) + "% Chance to Win\n\n";
		
		return s;
	}
}
