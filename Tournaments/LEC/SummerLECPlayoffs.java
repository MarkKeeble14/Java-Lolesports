package LEC;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import TournamentComponents.Bracket;

public class SummerLECPlayoffs extends Bracket {
	
	public SummerLECPlayoffs(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}
	
	public SummerLECPlayoffs(Tournament partOf, String fedTeamsThrough) {
		super(partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
