package sea.nlp.viterbi;

import java.util.ArrayList;

/**
 * This is the node of the HMM graph with its prior probability, transitions,
 * and observations.
 * 
 * @author Sagar
 *
 */
public class State {
	private String stateName;
	private float priorProbability;
	private ArrayList<Transition> transitions;
	private ArrayList<Observation> observations;

	public State() {
	}

	public State(String stateName) {
		super();
		this.stateName = stateName;
	}

	public State(String stateName, float priorProbability, ArrayList<Transition> transitions,
			ArrayList<Observation> observations) {
		super();
		this.stateName = stateName;
		this.priorProbability = priorProbability;
		this.transitions = transitions;
		this.observations = observations;
	}

	public String getStateName() {
		return stateName;
	}

	public void setStateName(String stateName) {
		this.stateName = stateName;
	}

	public float getPriorProbability() {
		return priorProbability;
	}

	public void setPriorProbability(float priorProbability) {
		this.priorProbability = priorProbability;
	}

	public ArrayList<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(ArrayList<Transition> transitions) {
		this.transitions = transitions;
	}

	public ArrayList<Observation> getObservations() {
		return observations;
	}

	public void setObservations(ArrayList<Observation> observations) {
		this.observations = observations;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((stateName == null) ? 0 : stateName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (stateName == null) {
			if (other.stateName != null)
				return false;
		} else if (!stateName.equals(other.stateName))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State [stateName=" + stateName + ", priorProbability=" + priorProbability + ", transitions="
				+ transitions + ", observations=" + observations + "]";
	}

}
