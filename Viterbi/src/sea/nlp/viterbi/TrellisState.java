package sea.nlp.viterbi;

/**
 * This is the node of the Trellis generated from the HMM. It contains a
 * probability associated with it and the backpointer.
 * 
 * @author Sagar
 *
 */
public class TrellisState extends State {
	private float maxProbability;
	private TrellisState backpointer;

	public TrellisState(State state) {
		super(state.getStateName(), state.getPriorProbability(), state.getTransitions(), state.getObservations());
		this.setMaxProbability(0.0f);
	}

	public float getMaxProbability() {
		return maxProbability;
	}

	public void setMaxProbability(float maxProbability) {
		if (this.maxProbability < maxProbability)
			this.maxProbability = maxProbability;
	}

	public TrellisState getBackpointer() {
		return backpointer;
	}

	public void setBackpointer(TrellisState backpointer, float maxProbability) {
		if (maxProbability == this.getMaxProbability())
			this.backpointer = backpointer;
	}

	@Override
	public String toString() {
		return "TrellisState [maxProbability=" + maxProbability + ", backpointer=" + backpointer + "]";
	}

}
