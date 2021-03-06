package sea.nlp.pos;

import java.util.Iterator;
import java.util.TreeSet;

import sea.nlp.comparator.ContextComparator;

/**
 * @author Sagar
 *
 */
public class TagCount {
	private String tag;
	private int tagCounter;
	private TreeSet<Context> contexts;

	public TagCount() {
	}

	public TagCount(String tag) {
		super();
		this.tag = tag;
		this.setTagCounter(1);
		this.contexts = new TreeSet<Context>(new ContextComparator());
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getTagCounter() {
		return tagCounter;
	}

	public void setTagCounter(int tagCounter) {
		this.tagCounter = tagCounter;
	}

	public TreeSet<Context> getContexts() {
		return contexts;
	}

	public void setContexts(TreeSet<Context> contexts) {
		this.contexts = contexts;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((tag == null) ? 0 : tag.hashCode());
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
		TagCount other = (TagCount) obj;
		if (tag == null) {
			if (other.tag != null)
				return false;
		} else if (!tag.equals(other.tag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TagCount [tag=" + tag + ", tagCounter=" + tagCounter + "]";
	}

	public void addContext(String previousTag, String nextTag) {
		Context context = new Context(previousTag, nextTag);
		for (Iterator<Context> treeIterator = contexts.iterator(); treeIterator.hasNext();) {
			Context contextTags = (Context) treeIterator.next();
			if (contextTags.equals(context)) {
				context.setContextCounter(contextTags.getContextCounter() + 1);
				treeIterator.remove();
				break;
			}
		}
		contexts.add(context);
	}
}
