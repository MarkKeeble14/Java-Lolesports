package GroupStageFormats;

import java.util.List;

import Classes.Group;

public abstract class GroupStageFormat {
	public abstract void Simulate(List<Group> groups) throws Exception;
}
