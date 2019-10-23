package neon.state;

import java.util.ArrayList;

public class State {
	
	private String name;
	private ArrayList<String> toStates;
	
	public State(String name) {
		this.name = name;
		toStates = new ArrayList<String>();
	}
	
	public ArrayList<String> getToStates() {
		return this.toStates;
	}
	
	public String getName() {
		return this.name;
	}
}
