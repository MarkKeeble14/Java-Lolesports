package DrawStructures;

import java.util.List;

import Classes.Group;
import Classes.Pool;

public abstract class DrawStructure {
	public abstract void Simulate(List<Group> groups, List<Pool> pools) throws Exception;
}
