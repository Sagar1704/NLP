package sea.nlp.ngram;

import java.util.ArrayList;
import java.util.List;

/**
 * This is a generic NGram class with, list of words, The NGram count,
 * unsmoothed probability and smoothed probability
 * 
 * @author Sagar
 *
 */
public abstract class NGram {
	private List<String> words;
	private int count;
	private float unsmoothedProbability;
	private float smoothedProbability;

	public NGram(List<String> words) {
		super();
		this.words = new ArrayList<String>();
		for (String word : words) {
			this.words.add(word);
		}
		this.count = 1;
		this.unsmoothedProbability = 0.0f;
	}

	public List<String> getWords() {
		return words;
	}

	public void setWords(ArrayList<String> words) {
		this.words = words;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public float getUnsmoothedProbability() {
		return unsmoothedProbability;
	}

	public void setUnsmoothedProbability(float probability) {
		this.unsmoothedProbability = probability;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((words == null) ? 0 : words.hashCode());
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
		NGram other = (NGram) obj;
		if (words == null) {
			if (other.words != null)
				return false;
		} else if (!words.equals(other.words))
			return false;
		return true;
	}

	public float getSmoothedProbability() {
		return smoothedProbability;
	}

	public void setSmoothedProbability(float smoothedProbability) {
		this.smoothedProbability = smoothedProbability;
	}

}
