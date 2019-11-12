package neon.entity.event;

import neon.entity.Entity;

public interface Event extends Entity {
	
	public boolean meetsCondition();
	public void fireEvent();
}
