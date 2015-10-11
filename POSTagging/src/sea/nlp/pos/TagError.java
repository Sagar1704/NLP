package sea.nlp.pos;

/**
 * Has the error counts of the tags in the corpus
 * 
 * @author Sagar
 *
 */
public class TagError {
	private String tag;
	private int errorCount;

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public int getErrorCount() {
		return errorCount;
	}

	public void setErrorCount(int errorCount) {
		this.errorCount = errorCount;
	}

}
