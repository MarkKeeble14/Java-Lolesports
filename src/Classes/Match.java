package Classes;

import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import Misc.Strings;
import Misc.Util;
import TournamentSimulator.Driver;

public class Match {
	private String label;
	private Team teamA;
	private Team teamB;
	
	private Team winner;
	private Team loser;
	
	Random rand = new Random();
	
	private int teamAWins;
	private int teamBWins;
	private Map<Integer, Team> winnerMap = new HashMap<Integer, Team>();
	
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
	public void Simulate(String stageLabel, RegionalWLTracker t, int bestOf) {
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * 100;
		// double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * scale;
		
		// Best of is equal to the number of games to play
		if (bestOf > 1) {
			
			// Set Variables
			teamA.setNewRecord(stageLabel);
			Record teamARecord = teamA.getRecord();
			
			teamB.setNewRecord(stageLabel);
			Record teamBRecord = teamB.getRecord();
			
			int goal = (int) Math.ceil((double) bestOf / 2);
			
			// Simulate the games
			for (int i = 1; i < bestOf + 1; i++) {
				double random = rand.nextDouble() * 100;
				// Team A Wins the game
				if (random > 0 && random < oddsTeamAWins) {
					teamAWins++;
					teamARecord.MatchWin(teamB);
					teamBRecord.MatchLoss(teamA);
					
					winnerMap.put(i, teamA);
					
					// Team A Wins the match
					if (teamARecord.getWins() == goal) { // Team A Wins
						winner = teamA;
						loser = teamB;
						t.Update(winner, loser);
						
						break;
					}
				} else {
					// Team B Wins the game
					teamBWins++;
					teamBRecord.MatchWin(teamA);
					teamARecord.MatchLoss(teamB);
					
					winnerMap.put(i, teamB);
					
					// Team B Wins the match
					if (teamBRecord.getWins() == goal) { // Team B wins
						winner = teamB;
						loser = teamA;
						t.Update(winner, loser);
						
						break;
					}
				}
			}	
		} else {
			double random = rand.nextDouble() * 100;
			if (random > 0 && random < oddsTeamAWins) {
				teamAWins++;
				winner = teamA;
				loser = teamB;
				
				winnerMap.put(1, teamA);
			} else {
				teamBWins++;
				winner = teamB;
				loser = teamA;
				
				winnerMap.put(1, teamB);
			}
			t.Update(winner, loser);
		}
	}
	
	// Simulates the match playing out, the team with the higher rating wins
	public void SimulateGroupStageMatch(String stageLabel, RegionalWLTracker t, int bestOf, Group g, GroupStage gs) {
		int scale = 100;
		
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * scale;
		// double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * scale;
		
		// Best of is equal to the number of games to play
		if (bestOf > 1) {
			// Set Variables
			teamA.setNewRecord(stageLabel);
			Record teamARecord = teamA.getRecord();
			
			teamB.setNewRecord(stageLabel);
			Record teamBRecord = teamB.getRecord();
			
			int goal = (int) Math.ceil((double) bestOf / 2);
			
			// Simulate the games
			for (int i = 1; i < bestOf + 1; i++) {
				double random = rand.nextDouble() * scale;
				// Team A Wins the game
				if (random > 0 && random < oddsTeamAWins) {
					teamAWins++;
					teamARecord.MatchWin(teamB);
					teamBRecord.MatchLoss(teamA);
					
					winnerMap.put(i, teamA);
					
					// Team A Wins the match
					if (teamARecord.getWins() == goal) { // Team A Wins
						winner = teamA;
						loser = teamB;
						t.Update(winner, loser);
						
						break;
					}
				} else {
					// Team B Wins the game
					teamBWins++;
					teamBRecord.MatchWin(teamA);
					teamARecord.MatchLoss(teamB);
				
					winnerMap.put(i, teamB);
					
					// Team B Wins the match
					if (teamBRecord.getWins() == goal) { // Team B wins
						winner = teamB;
						loser = teamA;
						t.Update(winner, loser);
						
						break;
					}
				}
			}	
		} else {
			double random = rand.nextDouble() * scale;
			if (random > 0 && random < oddsTeamAWins) {
				teamAWins++;
				winner = teamA;
				loser = teamB;
				
				winnerMap.put(1, teamA);
			} else {
				teamBWins++;
				winner = teamB;
				loser = teamA;
				
				winnerMap.put(1, teamB);
			}
			t.Update(winner, loser);
		}
		gs.AddMatches(g, this);
	}
	
	public void SimulateGroupStageTiebreakerMatch(String stageLabel, RegionalWLTracker t, int bestOf, Group g, GroupStage gs) {
		int scale = 100;
		
		double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * scale;
		// double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * scale;
		
		// Best of is equal to the number of games to play
		if (bestOf > 1) {
			// Set Variables
			teamA.setNewRecord(stageLabel);
			Record teamARecord = teamA.getRecord();
			
			teamB.setNewRecord(stageLabel);
			Record teamBRecord = teamB.getRecord();
			
			int goal = (int) Math.ceil((double) bestOf / 2);
			
			// Simulate the games
			for (int i = 1; i < bestOf + 1; i++) {
				double random = rand.nextDouble() * scale;
				// Team A Wins the game
				if (random > 0 && random < oddsTeamAWins) {
					teamAWins++;
					teamARecord.MatchWin(teamB);
					teamBRecord.MatchLoss(teamA);
					
					winnerMap.put(i, teamA);
					
					// Team A Wins the match
					if (teamARecord.getWins() == goal) { // Team A Wins
						winner = teamA;
						loser = teamB;
						t.Update(winner, loser);
						
						break;
					}
				} else {
					// Team B Wins the game
					teamBWins++;
					teamBRecord.MatchWin(teamA);
					teamARecord.MatchLoss(teamB);
				
					winnerMap.put(i, teamB);
					
					// Team B Wins the match
					if (teamBRecord.getWins() == goal) { // Team B wins
						winner = teamB;
						loser = teamA;
						t.Update(winner, loser);
						
						break;
					}
				}
			}	
		} else {
			double random = rand.nextDouble() * scale;
			if (random > 0 && random < oddsTeamAWins) {
				teamAWins++;
				winner = teamA;
				loser = teamB;
				
				winnerMap.put(1, teamA);
			} else {
				teamBWins++;
				winner = teamB;
				loser = teamA;
				
				winnerMap.put(1, teamB);
			}
			t.Update(winner, loser);
		}
		gs.AddTiebreakerMatches(g, this);
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
			return label + ": " + teamA.getTag() + " VS " + teamB.getTag() + " - Bo" + winnerMap.size();
		} else {
			if (Driver.PRINT_DETAILED_SERIES_SUMMARY && winnerMap.size() > 1) {
				DecimalFormat df = new DecimalFormat("00.00");
				
				String s = label + ": " + teamA.getTag() + " VS " + teamB.getTag() + "\n";
				
				double oddsTeamAWins = CalculateChance(teamA.getRating(), teamB.getRating()) * 100;
				double oddsTeamBWins = CalculateChance(teamB.getRating(), teamA.getRating()) * 100;
				
				s += teamA.getTag() + ": " + df.format(oddsTeamAWins) + "% Chance to Win\n";
				s += teamB.getTag() + ": " + df.format(oddsTeamBWins) + "% Chance to Win\n\n";
				
				int aWins = 0, bWins = 0;
				for (int i = 1; i < winnerMap.size() + 1; i++) {
					Team win = winnerMap.get(i);
					if (win == teamA) {
						aWins++;
					} else if (win == teamB) {
						bWins++;
					}
					
					s += "Game #" + i + ": " + win.getTag() + " Win \t";
					
					if (aWins > bWins) {
						s += aWins + "-" + bWins + " for: " + teamA.getTag();
					} else if (aWins < bWins) {
						s += bWins + "-" + aWins + " for: " + teamB.getTag();
					} else {
						s += "Tied at: " + aWins + "-" + bWins;
					}
					
					s += "\n";	
				}
				
				s += "\n";
				
				if (aWins > bWins) {
					s += teamA.getTag() + " Beats " + teamB.getTag() + ": " + aWins + "-" + bWins;
				} else {
					s += teamB.getTag() + " Beats " + teamA.getTag() + ": " + bWins + "-" + aWins;
				}
				
				return s;
			} else {
				String s = label + ": " + teamA + " VS " + teamB + "\n";
				if (winner == teamA) {
					s += teamAWins + ":" + teamBWins + "; " + teamA.getTag() + " > " + teamB.getTag() + "\n";
				} else {
					s += teamAWins + ":" + teamBWins + "; " + teamA.getTag() + " < " + teamB.getTag() + "\n";
				}
				return s;	
			}
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
