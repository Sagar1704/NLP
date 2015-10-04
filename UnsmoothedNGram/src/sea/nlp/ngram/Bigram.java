package sea.nlp.ngram;

import java.util.List;

public class Bigram extends NGram {

	public Bigram(List<String> words) {
		super(words);
	}

	@Override
	public String toString() {
		return getWords() + "\t\t\t\t\t" + getUnsmoothedProbability();
	}
}
