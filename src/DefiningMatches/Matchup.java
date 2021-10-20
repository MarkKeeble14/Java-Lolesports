package DefiningMatches;

import DefiningTeams.Team;

public abstract class Matchup {
	private Team winner;
	private Team loser;
	
	public abstract String toString();
	
	public abstract void Simulate();
	
	public abstract boolean resultDetermined();
	
	public void setWinner(Team winner) {
		this.winner = winner;
	}

	public void setLoser(Team loser) {
		this.loser = loser;
	}

	public Team getWinner() {
		return winner;
	}

	public Team getLoser() {
		return loser;
	}
}
