package LEC;

import java.util.List;

import Classes.Group;
import Classes.Tournament;
import TournamentComponents.Bracket;

public class SpringLECPlayoffs extends Bracket {
	
	public SpringLECPlayoffs(Tournament partOf) {
		super(partOf);
		// TODO Auto-generated constructor stub
	}

	public SpringLECPlayoffs(Tournament partOf, String fedTeamsThrough) {
		super(partOf, fedTeamsThrough);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void Simulate(String label, List<Group> groups) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
