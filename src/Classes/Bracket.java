package Classes;

import java.util.ArrayList;
import java.util.List;

import MSI.TournamentMSI;
import Misc.Strings;
import TournamentSimulator.Driver;

public abstract class Bracket extends TournamentComponent {
	private List<Match> matches = new ArrayList<Match>();

	private Match championshipMatch;
	private Team champion;
	private Team runnerUp;
	
	private Tournament partOf;
	
	private String teamsQThroughLabel;
	
	private List<Team> seenTeams = new ArrayList<Team>();
	
	public Bracket(Tournament partOf) {
		super();
		this.partOf = partOf;
	}
	
	public Bracket(Tournament partOf, String fedTeamsThrough) {
		super();
		this.partOf = partOf;
		teamsQThroughLabel = fedTeamsThrough;
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
		String s = "";
		int x = 0;
		for (int i = 0; i < matches.size(); i++) {
			Match m = matches.get(i);
			
			if (Driver.PRINT_QUALIFICATION_REASONS && teamsQThroughLabel != "") {
				Team a = m.getTeamA();
				Team b = m.getTeamB();
				
				QualificationDetails aQD = a.getQD(teamsQThroughLabel);
				QualificationDetails bQD = b.getQD(teamsQThroughLabel);
				
				if (!seenTeams.contains(a) && aQD != null || !seenTeams.contains(b) && bQD != null) {
					s += "\n";
				}
				
				if (aQD != null && !seenTeams.contains(a)) {
					s += "Reason for Qualification for: " + a.getTag();
					
					s += aQD;
					
					s += "\n" + Strings.SmallLineBreak;
					
					seenTeams.add(a);
					
					if (!seenTeams.contains(b) && bQD != null) {
						s += "\n\n";
					}
				}
				if (bQD != null && !seenTeams.contains(b)) {
					s += "Reason for Qualification for: " + b.getTag();
					
					s += bQD;
					
					s += "\n" + Strings.SmallLineBreak + "\n";
					
					seenTeams.add(b);
				}
			}
			
			if (x == matches.size() - 1) {
				s += "\n" + m.toString();
			} else {
				s += "\n" + m.toString() + "\n";
				s += Strings.SmallLineBreak + "\n";
			}
			x++;
		}
		return s;
	}
}