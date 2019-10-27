package neon.state;

import java.util.ArrayList;

public class State {
	
	private String name;
	private ArrayList<String> toStates;
	private boolean interuptable;
	
	public State(String name, boolean interuptable) {
		this.name = name;
		toStates = new ArrayList<String>();
		this.interuptable = interuptable;
	}
	
	public ArrayList<String> getToStates() {
		return this.toStates;
	}
	
	public String getName() {
		return this.name;
	}
	
	public boolean isInteruptable() {
		return interuptable;
	}
}
