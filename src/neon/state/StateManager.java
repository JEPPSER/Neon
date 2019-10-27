package neon.state;

import java.util.ArrayList;
import java.util.HashMap;

public class StateManager {
	
	private State currentState;
	private HashMap<String, State> states;
	
	public StateManager() {
		states = new HashMap<String, State>();
	}
	
	public void addState(State state) {
		states.put(state.getName(), state);
	}
	
	public boolean canActivateState(String state) {
		if (!currentState.isInteruptable()) {
			return false;
		}
		State toState = states.get(state);
		ArrayList<String> to = currentState.getToStates();
		for (int i = 0; i < to.size(); i++) {
			if (toState.getName().equals(to.get(i))) {
				return true;
			}
		}
		return false;
	}
	
	public void activateState(String state) {
		currentState = states.get(state);
	}
	
	public String getCurrentState() {
		return currentState.getName();
	}
}
