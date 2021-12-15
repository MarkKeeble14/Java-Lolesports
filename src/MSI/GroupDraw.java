package MSI;

import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import CustomExceptions.MismatchedNumberOfPoolsException;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class GroupDraw extends DrawStructure {
	public GroupDraw(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 3;
	int requiredNumberOfPools = 2;
	
	@Override
	public void Simulate(List<Group> groups, List<Pool> pools) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfPoolsException(requiredNumberOfPools, pools.size());
		}
		
		// Set Pools
		Pool P1 = pools.get(0);
		Pool P2 = pools.get(1);
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		Group C = groups.get(2);
		
		// Draw Teams From Pool One
		A.Add(P1.Draw());
		B.Add(P1.Draw());
		C.Add(P1.Draw());
		
		// Draw Teams From Pool Two
		A.Add(P1.Draw());
		B.Add(P1.Draw());
		C.Add(P1.Draw());
		pools.remove(0);
		
		// Draw Teams From Pool Three
		A.Add(P2.Draw());
		B.Add(P2.Draw());
		C.Add(P2.Draw());
		
		// Draw Teams From Pool PI
		A.Add(P2.Draw());
		B.Add(P2.Draw());
		C.Add(P2.Draw());
		pools.remove(0);
		
		super.setGroups(groups);
	}
}
