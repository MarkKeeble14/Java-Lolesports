package Matches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import StatsTracking.RegionalWLTracker;
import Teams.Team;
import Misc.GlobalVariables;

public class Series extends Matchup  {
	private String stageLabel;
	private int matchLabel;
	private int numGames;
	
	private Map<Team, Integer> gamescore = new HashMap<Team, Integer>();
	private List<Game> matches = new ArrayList<Game>();
	
	private Team A;
	private Team B;
	
	private Team winner;
	private Team loser;
	
	private RegionalWLTracker WLT;
	
	public Series(String stageLabel, int matchLabel, int numGames, Team a, Team b, RegionalWLTracker t) {
		super();
		this.stageLabel = stageLabel;
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		setTeamA(a);
		setTeamB(b);
		WLT = t;
	}
	
	public Series(int matchLabel, int numGames, Team a, Team b, RegionalWLTracker t) {
		super();
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		setTeamA(a);
		setTeamB(b);
		WLT = t;
	}
	
	public Series(String stageLabel, int matchLabel, int numGames, RegionalWLTracker t) {
		super();
		this.stageLabel = stageLabel;
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		WLT = t;
	}
	
	public Series(int matchLabel, int numGames, RegionalWLTracker t) {
		super();
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		WLT = t;
	}
	
	public void Simulate() {
		// Set Variables
		A.setNewRecord(stageLabel);
		B.setNewRecord(stageLabel);
		
		int goal = (int) Math.ceil((double) numGames / 2);
		
		// Simulate the games
		for (int i = 1; i < numGames + 1; i++) {
			
			Game m = new Game(stageLabel, matchLabel, A, B, WLT);
			m.Simulate();
			matches.add(m);
			
			// Team A Wins the game
			if (m.getWinner() == A) {
				TeamWin(A, B);
				
				if (gamescore.get(A) == goal) {
					winner = A;
					loser = B;
					
					break;
				}
			} else { // Team B Wins the game
				TeamWin(B, A);
				
				if (gamescore.get(B) == goal) {
					winner = B;
					loser = A;
					
					break;
				}
			}
		}	
	}
	
	private void TeamWin(Team win, Team loss) {
		UpdateGamescore(win);
	}
	
	private void UpdateGamescore(Team t) {
		if (gamescore.containsKey(t)) {
			gamescore.put(t, gamescore.get(t) + 1);
		} else {
			gamescore.put(t, 1);
		}
	}
	
	public void setResult(Team t1, Team t2, int t1GS, int t2GS) {
		// Set Variables
		t1.setNewRecord(stageLabel);
		t2.setNewRecord(stageLabel);
		
		int goal = (int) Math.ceil((double) numGames / 2);
		
		// Simulate the games
		for (int i = 1; i < t1GS; i++) {
			Game m = new Game(stageLabel, matchLabel, A, B, WLT);
			m.setResult(t1, t2);
			matches.add(m);
		}	
		// Simulate the games
		for (int i = 1; i < t2GS; i++) {
			Game m = new Game(stageLabel, matchLabel, A, B, WLT);
			m.setResult(t2, t1);
			matches.add(m);
		}
		if (t1GS == goal) {
			winner = t1;
			loser = t2;
		} else {
			winner = t2;
			loser = t1;
		}
	}
	
	public Team getWinner() {
		return winner;
	}

	public Team getLoser() {
		return loser;
	}

	public int getLabel() {
		return matchLabel;
	}

	public int getNumGames() {
		return numGames;
	}

	public Map<Team, Integer> getGamescore() {
		return gamescore;
	}

	public List<Game> getMatches() {
		return matches;
	}

	public Team getTeamA() {
		return A;
	}

	public Team getTeamB() {
		return B;
	}

	public RegionalWLTracker getWLT() {
		return WLT;
	}

	public String getStageLabel() {
		return stageLabel;
	}

	public void setStageLabel(String stageLabel) {
		this.stageLabel = stageLabel;
	}

	public void setLabel(int label) {
		this.matchLabel = label;
	}

	public void setNumGames(int numGames) {
		this.numGames = numGames;
	}

	public void setGamescore(Map<Team, Integer> gamescore) {
		this.gamescore = gamescore;
	}

	public void setMatches(List<Game> matches) {
		this.matches = matches;
	}

	public void setTeamA(Team a) {
		A = a;
		gamescore.put(a, 0);
	}

	public void setTeamB(Team b) {
		B = b;
		gamescore.put(b, 0);
	}

	public void setWinner(Team winner) {
		winner = winner;
	}

	public void setLoser(Team loser) {
		loser = loser;
	}

	public void setWLT(RegionalWLTracker wLT) {
		WLT = wLT;
	}

	@Override
	public String toString() {
		if (winner == null) {
			return matchLabel + ": " + A.getTag() + " VS " + B.getTag() + " - Bo" + numGames;
		} else {
			if (GlobalVariables.PRINT_DETAILED_SERIES_SUMMARY && gamescore.size() > 1) {
				
				String s = matches.get(0).getMatchDetails();
				int aWins = 0, bWins = 0;
				
				for (int i = 0; i < matches.size(); i++) {
					Game m = matches.get(i);
					Team won = m.getWinner();
					
					if (won == A) {
						aWins++;
					} else if (won == B) {
						bWins++;
					}
					
					s += "Game #" + i + ": " + won.getTag() + " Win \t";
					
					if (aWins > bWins) {
						s += aWins + "-" + bWins + " for: " + A.getTag();
					} else if (aWins < bWins) {
						s += bWins + "-" + aWins + " for: " + B.getTag();
					} else {
						s += "Tied at: " + aWins + "-" + bWins;
					}
					
					s += "\n";	
				}
				
				s += "\n";
				
				if (aWins > bWins) {
					s += A.getTag() + " Beats " + B.getTag() + ": " + aWins + "-" + bWins;
				} else {
					s += B.getTag() + " Beats " + A.getTag() + ": " + bWins + "-" + aWins;
				}
				
				return s;
			} else {
				String s = matchLabel + ": " + A + " VS " + B + "\n";
				if (winner == A) {
					s += gamescore.get(A) + ":" + gamescore.get(B) + "; " + A.getTag() + " > " + B.getTag() + "\n";
				} else {
					s += gamescore.get(A) + ":" + gamescore.get(B) + "; " + A.getTag() + " < " + B.getTag() + "\n";
				}
				return s;	
			}
		}
	}	
	
	@Override
	public boolean resultDetermined() {
		return winner != null;
	}
}
