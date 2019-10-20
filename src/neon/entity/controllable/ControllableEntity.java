package neon.entity.controllable;

import org.newdawn.slick.Input;

import neon.controller.Controller;
import neon.entity.PhysicalEntity;

public abstract class ControllableEntity extends PhysicalEntity {
	
	protected Controller controller;
	
	public void control(Input input, int delta) {
		controller.control(input, delta);
	}
}
