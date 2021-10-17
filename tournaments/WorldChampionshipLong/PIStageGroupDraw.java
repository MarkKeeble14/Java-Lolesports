package WorldChampionshipLong;

import java.util.ArrayList;
import java.util.List;

import CustomExceptions.MismatchedNumberOfGroupsException;
import CustomExceptions.MismatchedNumberOfPoolsException;
import CustomExceptions.MismatchedSizeOfPoolException;
import DefiningTeams.Team;
import TournamentComponents.DrawStructure;
import TournamentComponents.Group;
import TournamentComponents.Pool;
import TournamentComponents.Tournament;

public class PIStageGroupDraw extends DrawStructure {
	public PIStageGroupDraw(String label, Tournament partOf) {
		super(label, partOf);
	}

	int requiredNumberOfGroups = 2;
	int requiredNumberOfPools = 2;
	int requiredSizeOfFirstPool = 8;
	int requiredSizeOfSecondPool = 6;
	
	@Override
	public void Simulate(List<Group> groups, List<Pool> pools) throws Exception {
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
		
		// Set Pools
		Pool PoolOne = pools.get(0);
		Pool PoolTwo = pools.get(1);
		
		// Set Groups
		Group A = groups.get(0);
		Group B = groups.get(1);
		
		// Draw Teams
		A.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		A.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		A.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		A.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolOne.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		pools.remove(0);
		
		A.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		A.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		A.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 0, new ArrayList<Team>()));
		B.Add(PoolTwo.DrawWithSameRegionRule(pools, 0, groups, 1, new ArrayList<Team>()));
		pools.remove(0);
		
		super.setGroups(groups);
	}
}
