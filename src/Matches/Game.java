package Matches;

import java.text.DecimalFormat;
import java.util.Random;

import Drivers.DomesticDriver;
import Drivers.InternationalDriver;
import Misc.GlobalVariables;
import StatsTracking.RegionalWLTracker;
import Teams.Team;

public class Game extends Matchup {
	private String stageLabel;
	private int matchLabel;
	private Team teamA;
	private Team teamB;
	
	private Team winner;
	private Team loser;
	
	private Random rand = new Random();
	private RegionalWLTracker WLT;
	
	private boolean setManually = false;
	
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
		winner.getRecord(stageLabel).MatchWin(loser);
		loser.getRecord(stageLabel).MatchLoss(winner);
		
		WLT.Update(winner, loser);
	}
	
	public void TBSimulate() {
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
		winner.getRecord(stageLabel).TiebreakerWin(loser);
		loser.getRecord(stageLabel).TiebreakerLoss(winner);
		
		WLT.Update(winner, loser);
	}
	
	/**
	* Constructor
	* @param label The match id/number of the match, i.e., M1, M2, M3, etc
	* @param A Combatant A 
	* @param B Combatant B
	*/
	public Game(String sl, int l, Team A, Team B, RegionalWLTracker t) {
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
	public Game(String sl, int l, RegionalWLTracker t) {
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
		int scale = GlobalVariables.getEloScaling();
		RA = RA * scale;
		RB = RB * scale;
		
		// Formula to determine chance
		double exp = (RB-RA)/400;
		double x = Math.pow(10, exp);
		double y = 1 + x;
		double z = 1 / y;
		return z;
	}
	
	public void setResult(Team t1, Team t2) {
		winner = t1;
		loser = t2;
		
		// Update Teams Records
		winner.getRecord(stageLabel).MatchWin(loser);
		loser.getRecord(stageLabel).MatchLoss(winner);
		
		WLT.Update(winner, loser);
		
		setManually = true;
	}
	
	public void setTBResult(Team t1, Team t2) {
		winner = t1;
		loser = t2;
		
		// Update Teams Records
		winner.getRecord(stageLabel).TiebreakerWin(loser);
		loser.getRecord(stageLabel).TiebreakerLoss(winner);
		
		WLT.Update(winner, loser);
		
		setManually = true;
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
	
	public int getMatchLabel() {
		return matchLabel;
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
			if (setManually) {
				s += "\n\nSet Manually";
			}
			return s;	
		}
	}
	
	public String getMatchDetails() {
		String s = "";
		
		s += matchLabel + ": " + stageLabel + " - " + teamA + " VS " + teamB + "\n";
		
		DecimalFormat df = new DecimalFormat("00.00");
		
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * 100;
		double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * 100;
		
		s += teamA.getTag() + ": " + df.format(oddsTeamAWins) + "% Chance to Win\n";
		s += teamB.getTag() + ": " + df.format(oddsTeamBWins) + "% Chance to Win\n\n";
		
		return s;
	}

	@Override
	public boolean resultDetermined() {
		return winner != null;
	}
}
