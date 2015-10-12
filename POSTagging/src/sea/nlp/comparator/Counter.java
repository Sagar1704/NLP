package sea.nlp.comparator;

import java.util.Comparator;

import sea.nlp.pos.TagCounter;

public class Counter implements Comparator<TagCounter> {

	@Override
	public int compare(TagCounter tag1, TagCounter tag2) {
		if (tag1.getCounter() < tag2.getCounter())
			return 1;
		else
			return -1;
	}

}