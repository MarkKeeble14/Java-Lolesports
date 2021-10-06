package Classes;

import java.util.ArrayList;
import java.util.List;

import MSI.TournamentMSI;

public abstract class Bracket extends TournamentComponent {
	private List<Match> matches = new ArrayList<Match>();

	private Match championshipMatch;
	private Team champion;
	private Team runnerUp;
	
	private Tournament partOf;
	
	public Bracket(Tournament partOf) {
		super();
		this.partOf = partOf;
	}

	public abstract void Simulate(String label, List<Group> groups) throws Exception;
	
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

	public Tournament getPartOf() {
		return partOf;
	}
	
	@Override
	public String toString() {
		String s = "\n";
		int x = 0;
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			if (x == matches.size() - 1) {
				s += m.toString();
			} else {
				s += m.toString() + "\n";
			}
			x++;
		}
		return s.substring(0, s.length() - 1);
	}
}