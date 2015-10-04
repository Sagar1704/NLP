package sea.nlp.ngram;

import java.util.List;

public class Unigram extends NGram {
	static int totalUnigramCount = 0;

	public Unigram() {
		super(null);
	}

	public Unigram(List<String> words) {
		super(words);
	}

	@Override
	public String toString() {
		return getWords().toString() + "\t\t\t\t\t" + getUnsmoothedProbability();
	}

}
