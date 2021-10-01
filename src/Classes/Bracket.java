package Classes;

import java.util.ArrayList;
import java.util.List;

public abstract class Bracket {
	private List<Match> matches = new ArrayList<Match>();

	public abstract void Simulate(List<Group> groups) throws Exception;
	
	public Match GetMatch(int no) throws Exception {
		if (matches.size() < no) {
			throw new Exception();
		}
		return matches.get(no - 1);
	}
	
	public void AddMatches(Match ...matches) {
		for (Match m : matches) {
			this.matches.add(m);
		}
	}
	
	public void PrintWinnerStats() {
		Team winner = matches.get(matches.size() - 1).getWinner();
		System.out.println("\n" + winner + " has Won the Tournament!");
		System.out.println("\n" + winner + " Records: " + winner.recordLog());
		System.out.println("\n" + winner + " has Won the Tournament!");
	}
}