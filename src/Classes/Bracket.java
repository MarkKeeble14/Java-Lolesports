package Classes;

import java.util.ArrayList;
import java.util.List;

import MSI.TournamentMSI;

public abstract class Bracket {
	private List<Match> matches = new ArrayList<Match>();

	private Match championshipMatch;
	private Team champion;
	private Team runnerUp;
	
	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public Match getMatch(int no) throws Exception {
		if (matches.size() < no) {
			throw new Exception();
		}
		return matches.get(no - 1);
	}
	
	public void addMatches(Match ...matches) {
		for (Match m : matches) {
			this.matches.add(m);
		}
	}
	
	public List<Match> getMatches() {
		return matches;
	}
	
	public void setChampionshipMatch(Match m) {
		championshipMatch = m;
		setChampion(m.getWinner());
		setRunnerUp(m.getLoser());
	}
	
	public Match getChampionshipMatch() {
		return championshipMatch;
	}

	public void setChampion(Team champion) {
		this.champion = champion;
	}
	
	public Team getChampion() {
		return champion;
	}

	public Team getRunnerUp() {
		return runnerUp;
	}

	public void setRunnerUp(Team runnerUp) {
		this.runnerUp = runnerUp;
	}
	
}