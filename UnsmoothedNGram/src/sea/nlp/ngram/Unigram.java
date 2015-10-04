package sea.nlp.ngram;

import java.util.List;

public class Unigram extends NGram{
	static int totalUnigramCount;
	
	public Unigram() {
		super(null);
	}
	
	public Unigram(List<String> words) {
		super(words);
	}

	public List<NGram> calculateSmoothedProbability(List<NGram> unigrams) {
		
		return unigrams;
	}

	public int getTotalUnigramCount() {
		return totalUnigramCount;
	}

	@Override
	public String toString() {
		return getWords().toString() + "\t\t\t\t\t" + getUnsmoothedProbability();
	}

	
}
