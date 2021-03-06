package sea.nlp.viterbi;

/**
 * This is the Observation that is observed at each state in the HMM along with
 * its probability of observation.
 * 
 * @author Sagar
 *
 */
public class Observation {
	private String observation;
	private float observationProbability;

	public Observation() {
	}

	public Observation(String observation) {
		super();
		this.observation = observation;
	}

	public Observation(String observation, float observationProbability) {
		super();
		this.observation = observation;
		this.observationProbability = observationProbability;
	}

	public String getObservation() {
		return observation;
	}

	public void setObservation(String observation) {
		this.observation = observation;
	}

	public float getObservationProbability() {
		return observationProbability;
	}

	public void setObservationProbability(float observationProbability) {
		this.observationProbability = observationProbability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((observation == null) ? 0 : observation.hashCode());
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
		Observation other = (Observation) obj;
		if (observation == null) {
			if (other.observation != null)
				return false;
		} else if (!observation.equals(other.observation))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Observation [observation=" + observation + ", observationProbability=" + observationProbability + "]";
	}

}
