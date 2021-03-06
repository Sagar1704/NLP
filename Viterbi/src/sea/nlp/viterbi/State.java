package sea.nlp.viterbi;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

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
	private Set<Transition> transitions;
	private ArrayList<Observation> observations;

	public State() {
	}

	public State(String stateName) {
		super();
		this.stateName = stateName;
	}

	public State(String stateName, float priorProbability, Set<Transition> transitions,
			ArrayList<Observation> observations) {
		super();
		this.stateName = stateName;
		this.priorProbability = priorProbability;

		this.transitions = new HashSet<Transition>();
		if (transitions != null) {
			for (Iterator<Transition> iterator = transitions.iterator(); iterator.hasNext();) {
				Transition transition = (Transition) iterator.next();

				this.transitions.add(transition);
			}
		}

		this.observations = new ArrayList<Observation>();
		if (observations != null) {
			for (Observation observation : observations) {
				this.observations.add(observation);
			}
		}
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

	public Set<Transition> getTransitions() {
		return transitions;
	}

	public void setTransitions(Set<Transition> transitions) {
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

	public void addTransition(Transition transition) {
		if(!this.transitions.add(transition)) {
			this.transitions.remove(transition);
			this.transitions.add(transition);
		}
		
	}

	public float getTransitinProbability(State toState) {
		for (Transition transition : transitions) {
			if (transition.getToState().equals(toState))
				return transition.getTransitionProbability();
		}
		return 0.0f;
	}
}
