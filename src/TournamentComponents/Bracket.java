package TournamentComponents;

import java.util.ArrayList;
import java.util.List;

import Classes.Group;
import Classes.Team;
import Classes.Tournament;
import Matches.Game;
import Matches.Series;
import Misc.Strings;
import QualificationDetails.QualificationDetails;
import TournamentSimulator.Driver;

public abstract class Bracket extends TournamentComponent {
	private List<Series> series = new ArrayList<Series>();

	private Series championshipMatch;
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
	
	public Series getSeries(int no) throws Exception {
		if (series.size() < no) {
			throw new Exception();
		}
		return series.get(no - 1);
	}
	
	public void addSeries(Series ...series) {
		for (Series m : series) {
			this.series.add(m);
		}
	}
	
	public List<Series> getSeries() {
		return series;
	}
	
	public void setChampionshipSeries(Series s) {
		championshipMatch = s;
		setChampion(s.getWinner());
		setRunnerUp(s.getLoser());
	}
	
	public Series getChampionshipSeries() {
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
		for (int i = 0; i < series.size(); i++) {
			Series m = series.get(i);
			
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
					} else {
						s += "\n";
					}
				}
				if (bQD != null && !seenTeams.contains(b)) {
					s += "Reason for Qualification for: " + b.getTag();
					
					s += bQD;
					
					s += "\n" + Strings.SmallLineBreak + "\n";
					
					seenTeams.add(b);
				}
			}
			
			if (x == series.size() - 1) {
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