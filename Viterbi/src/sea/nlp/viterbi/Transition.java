package sea.nlp.viterbi;

/**
 * This is the Transition of the HMM graph consisting of 'from node', 'to node',
 * and the probability of the transition.
 * 
 * @author Sagar
 *
 */
public class Transition {
	private State toState;
	private float transitionProbability;

	public Transition() {
	}

	public Transition(State toState, float transitionProbability) {
		super();
		this.toState = toState;
		this.transitionProbability = transitionProbability;
	}

	public State getToState() {
		return toState;
	}

	public void setToState(State toState) {
		this.toState = toState;
	}

	public float getTransitionProbability() {
		return transitionProbability;
	}

	public void setTransitionProbability(float transitionProbability) {
		this.transitionProbability = transitionProbability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((toState == null) ? 0 : toState.hashCode());
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
		Transition other = (Transition) obj;
		if (toState == null) {
			if (other.toState != null)
				return false;
		} else if (!toState.equals(other.toState))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transition [toState=" + toState + ", transitionProbability=" + transitionProbability + "]";
	}

}
