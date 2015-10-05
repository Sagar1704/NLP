package sea.nlp.ngram;

import java.util.List;

/**
 * Unigram
 * 
 * @author Sagar
 *
 */
public class Unigram extends NGram implements Comparable<Unigram> {
	static int totalUnigramCount = 0;

	public Unigram() {
		super(null);
	}

	public Unigram(List<String> words) {
		super(words);
	}

	@Override
	public String toString() {
		return getWords().get(0) + "\t\t\t\t\t";
	}

	@Override
	public int compareTo(Unigram o) {
		String unigram1 = this.getWords().get(0);
		String unigram2 = o.getWords().get(0);

		return unigram1.compareTo(unigram2);
	}

}
