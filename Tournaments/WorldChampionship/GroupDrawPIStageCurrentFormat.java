package WorldChampionship;

import java.util.List;

import Classes.DrawStructure;
import Classes.Group;
import Classes.Pool;
import Classes.Tournament;
import CustomExceptions.MismatchedNumberOfGroupsException;
import CustomExceptions.MismatchedNumberOfPoolsException;
import CustomExceptions.MismatchedSizeOfPoolException;

public class GroupDrawPIStageCurrentFormat extends DrawStructure {
	public GroupDrawPIStageCurrentFormat(Tournament partOf) {
		super(partOf);
	}

	int requiredNumberOfGroups = 2;
	int requiredNumberOfPools = 2;
	int requiredSizeOfFirstPool = 4;
	int requiredSizeOfSecondPool = 6;
	
	@Override
	public void Simulate(String label, List<Group> groups, List<Pool> pools) throws Exception {
		if (groups.size() != requiredNumberOfGroups) {
			throw new MismatchedNumberOfGroupsException(requiredNumberOfGroups, groups.size());
		}
		if (pools.size() != requiredNumberOfPools) {
			throw new MismatchedNumberOfPoolsException(requiredNumberOfPools, pools.size());
		}
		if (pools.get(0).size() != requiredSizeOfFirstPool) {
			throw new MismatchedSizeOfPoolException(requiredSizeOfFirstPool, pools.get(0).size());			
		}
		if (pools.get(1).size() != requiredSizeOfSecondPool) {
			throw new MismatchedSizeOfPoolException(requiredSizeOfSecondPool, pools.get(1).size());
		}
		super.setLabel(label);
		// Set Pools
		Pool PoolOne = pools.get(0);
		Pool PoolTwo = pools.get(1);
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Draw Teams
		A.Add(PoolOne.Draw()); 
		B.Add(PoolOne.Draw());
		A.Add(PoolOne.Draw()); 
		B.Add(PoolOne.Draw());
		A.Add(PoolTwo.Draw()); 
		B.Add(PoolTwo.Draw());
		A.Add(PoolTwo.Draw()); 
		B.Add(PoolTwo.Draw());
		A.Add(PoolTwo.Draw()); 
		B.Add(PoolTwo.Draw());
		
		super.setGroups(groups);
		super.PrintGroups();
	}
}
