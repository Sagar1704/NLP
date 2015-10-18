package sea.nlp.comparator;

import java.util.Comparator;

import sea.nlp.pos.TagCount;

public class TagCounter implements Comparator<TagCount> {

	@Override
	public int compare(TagCount tag1, TagCount tag2) {
		if (tag1.getCounter() < tag2.getCounter())
			return 1;
		else
			return -1;
	}

}
