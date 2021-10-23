package DefiningMatches;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import DefiningQualificationDetails.QualifiedThroughGroupPlacement;
import DefiningQualificationDetails.QualifiedThroughSeriesWin;
import DefiningTeams.Team;
import StaticVariables.Settings;
import StaticVariables.Strings;
import Stats.MatchStats;
import Stats.Standings;

public class Series extends Matchup  {
	private String stageLabel;
	private int matchLabel;
	private int numGames;
	
	private Map<Team, Integer> gamescore = new HashMap<Team, Integer>();
	private List<Game> matches = new ArrayList<Game>();
	
	private Team A;
	private Team B;
	
	private MatchStats WLT;
	
	public Series(String stageLabel, int matchLabel, int numGames, Team a, Team b, MatchStats t) {
		super();
		this.stageLabel = stageLabel;
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		setTeamA(a);
		setTeamB(b);
		WLT = t;
	}
	
	public Series(int matchLabel, int numGames, Team a, Team b, MatchStats t) {
		super();
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		setTeamA(a);
		setTeamB(b);
		WLT = t;
	}
	
	public Series(String stageLabel, int matchLabel, int numGames, MatchStats t) {
		super();
		this.stageLabel = stageLabel;
		this.matchLabel = matchLabel;
		this.numGames = numGames;
		WLT = t;
	}
	
	public Series(int matchLabel, int numGames, MatchStats t) {
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
					super.setWinner(A);
					super.setLoser(B);
					break;
				}
			} else { // Team B Wins the game
				TeamWin(B, A);
				
				if (gamescore.get(B) == goal) {
					super.setWinner(B);
					super.setLoser(A);
					
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
		for (int i = 1; i < t1GS + 1; i++) {
			Game m = new Game(stageLabel, matchLabel, A, B, WLT);
			m.setResult(t1, t2);
			matches.add(m);
			UpdateGamescore(t1);
		}	
		// Simulate the games
		for (int i = 1; i < t2GS + 1; i++) {
			Game m = new Game(stageLabel, matchLabel, A, B, WLT);
			m.setResult(t2, t1);
			matches.add(m);
			UpdateGamescore(t2);
		}
		if (t1GS == goal) {
			super.setWinner(t1);
			super.setLoser(t2);
		} else {
			super.setWinner(t2);
			super.setLoser(t1);
		}
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

	public MatchStats getWLT() {
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

	public void setWLT(MatchStats wLT) {
		WLT = wLT;
	}

	@Override
	public String toString() {
		if (super.getWinner() == null) {
			return matchLabel + ": " + A.getTag() + " VS " + B.getTag() + " - Bo" + numGames;
		} else {
			if (Settings.PRINT_DETAILED_SERIES_SUMMARRIES && gamescore.size() > 1) {
				if (matches.get(0).getSetManually()) {
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
					}
					
					if (aWins > bWins) {
						s += A.getTag() + " Beats " + B.getTag() + ": " + aWins + "-" + bWins;
					} else {
						s += B.getTag() + " Beats " + A.getTag() + ": " + bWins + "-" + aWins;
					}
					
					s += "\n\nSet Manually";
					
					return s;
				} else {
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
				}
			} else {
				String s = matchLabel + ": " + A + " VS " + B + "\n";
				if (super.getWinner() == A) {
					s += gamescore.get(A) + ":" + gamescore.get(B) + "; " + A.getTag() + " > " + B.getTag();
				} else {
					s += gamescore.get(A) + ":" + gamescore.get(B) + "; " + A.getTag() + " < " + B.getTag();
				}
				return s;	
			}
		}
	}	
	
	@Override
	public boolean resultDetermined() {
		return super.getWinner() != null;
	}

	public void SetQualified(String label, Standings standings) {
		Team t = getWinner();
		t.setNewQD(new QualifiedThroughSeriesWin(label, this));
	}
	
	public String getFullLabel() {
		return matchLabel + ": " + stageLabel;
	}
}
