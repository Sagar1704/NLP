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
	private State backpointer;

	public float getMaxProbability() {
		return maxProbability;
	}

	public void setMaxProbability(float maxProbability) {
		this.maxProbability = maxProbability;
	}

	public State getBackpointer() {
		return backpointer;
	}

	public void setBackpointer(State backpointer) {
		this.backpointer = backpointer;
	}

	@Override
	public String toString() {
		return "TrellisState [maxProbability=" + maxProbability + ", backpointer=" + backpointer + "]";
	}

}