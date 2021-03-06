package sea.nlp.pos;

import java.util.Iterator;
import java.util.TreeSet;

import sea.nlp.comparator.TagComparator;

/**
 * @author Sagar
 *
 */
public class WordTagsCount {
	private String word;
	private TreeSet<TagCount> tags;
	private int wordCounter;
	private int errorCounter;

	public WordTagsCount() {
	}

	public WordTagsCount(String word, String tag) {
		super();
		this.word = word;
		this.tags = new TreeSet<TagCount>(new TagComparator());
		this.tags.add(new TagCount(tag));
		this.setWordCounter(1);
		this.setErrorCounter(0);
	}

	public WordTagsCount(String word) {
		this.setWord(word);
		this.tags = new TreeSet<TagCount>(new TagComparator());
		this.setWordCounter(1);
		this.setErrorCounter(0);
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public TreeSet<TagCount> getTags() {
		return tags;
	}

	public void setTags(TreeSet<TagCount> tags) {
			this.tags = tags;
	}

	public int getWordCounter() {
		return wordCounter;
	}

	public void setWordCounter(int wordCounter) {
		this.wordCounter = wordCounter;
	}

	public int getErrorCounter() {
		return errorCounter;
	}

	public void setErrorCounter(int errorCounter) {
		this.errorCounter = errorCounter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((word == null) ? 0 : word.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WordTagsCount other = (WordTagsCount) obj;
		if (word == null) {
			if (other.word != null)
				return false;
		} else if (!word.equals(other.word))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "WordTagsCount [word=" + word + ", tags=" + tags + ", wordCounter=" + wordCounter + ", errorCounter="
				+ errorCounter + "]";
	}

	public void addTag(String tagStr) {
		TagCount tag = new TagCount(tagStr);
		for (Iterator<TagCount> treeIterator = tags.iterator(); treeIterator.hasNext();) {
			TagCount tagCount = (TagCount) treeIterator.next();
			if (tagCount.equals(tag)) {
				tag.setTagCounter(tagCount.getTagCounter() + 1);
				treeIterator.remove();
				break;
			}
		}
		tags.add(tag);
	}
	
	public void updateTagContext(String tagStr, String previousTag, String nextTag) {
		TagCount tag = new TagCount(tagStr);
		for (Iterator<TagCount> treeIterator = tags.iterator(); treeIterator.hasNext();) {
			TagCount tagCount = (TagCount) treeIterator.next();
			if (tagCount.equals(tag)) {
				tagCount.addContext(previousTag, nextTag);
				break;
			}
		}
	}

	public String getCorrectTag() {
		return tags.first().getTag();
	}

	public void incrementErrorCounter() {
		errorCounter++;
	}
}
