package sea.nlp.comparator;

import java.util.Comparator;
import sea.nlp.pos.WordTagsCount;

public class ErrorComparator implements Comparator<WordTagsCount> {

	@Override
	public int compare(WordTagsCount word1, WordTagsCount word2) {
		if (word1.getErrorCounter() < word2.getErrorCounter())
			return 1;
		else
			return -1;
	}

}
