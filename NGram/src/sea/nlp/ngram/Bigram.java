package sea.nlp.ngram;

import java.util.List;

/**
 * Bigram
 * 
 * @author Sagar
 *
 */
public class Bigram extends NGram implements Comparable<Bigram> {

	public Bigram(List<String> words) {
		super(words);
	}

	@Override
	public String toString() {
		return getWords().get(0) + "\t\t\t\t\t" + getWords().get(1) + "\t\t\t\t\t";
	}

	@Override
	public int compareTo(Bigram o) {
		String bigram1 = this.getWords().get(0) + this.getWords().get(1);
		String bigram2 = o.getWords().get(0) + o.getWords().get(1);

		return bigram1.compareTo(bigram2);
	}
}
