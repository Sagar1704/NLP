package sea.nlp.comparator;

import java.util.Comparator;

import sea.nlp.pos.WordError;

public class ErrorCounter implements Comparator<WordError> {

	@Override
	public int compare(WordError word1, WordError word2) {
		if (word1.getErrorCount() < word2.getErrorCount())
			return 1;
		else
			return -1;
	}

}
