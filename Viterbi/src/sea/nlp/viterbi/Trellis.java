package sea.nlp.viterbi;

import java.util.ArrayList;

/**
 * This is the Trellis generated from the HMM.
 * 
 * @author Sagar
 *
 */
public class Trellis {
	private ArrayList<TrellisState> trellisStates;

	public Trellis() {
		super();
		this.trellisStates = new ArrayList<TrellisState>();
	}

	public ArrayList<TrellisState> getTrellisStates() {
		return trellisStates;
	}

	public void setTrellisStates(ArrayList<TrellisState> trellisStates) {
		this.trellisStates = trellisStates;
	}

	@Override
	public String toString() {
		return "Trellis [trellisStates=" + trellisStates + "]";
	}

	public void addToTrellis(TrellisState state, Boolean... start) {
		if(start.length > 0 && start[0])
			this.trellisStates.add(0, state);
		else
			this.trellisStates.add(state);
	}
}
