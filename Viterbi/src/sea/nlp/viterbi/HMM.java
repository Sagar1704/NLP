package sea.nlp.viterbi;

import java.util.ArrayList;

/**
 * HMM is the graph consisting of states.
 * 
 * @author Sagar
 *
 */
public class HMM {
	private ArrayList<State> states;

	public HMM() {
		this.states = new ArrayList<State>();
	}

	public ArrayList<State> getStates() {
		return states;
	}

	public void setStates(ArrayList<State> states) {
		this.states = states;
	}

	@Override
	public String toString() {
		return "HMM [states=" + states + "]";
	}

	public void addState(State state) {
		this.states.add(state);
	}
	
	
}
