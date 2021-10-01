package Tournaments;

import java.util.List;

import Classes.Pool;

public abstract class TournamentFormat {
	public abstract void Simulate(List<Pool> pools) throws Exception;
}
