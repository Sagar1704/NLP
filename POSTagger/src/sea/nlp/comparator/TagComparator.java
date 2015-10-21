package sea.nlp.comparator;

import java.util.Comparator;

import sea.nlp.pos.TagCount;

public class TagComparator implements Comparator<TagCount> {

	@Override
	public int compare(TagCount tag1, TagCount tag2) {
		if (tag1.getTagCounter() < tag2.getTagCounter())
			return 1;
		else
			return -1;
	}

}
