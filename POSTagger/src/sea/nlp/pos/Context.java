package sea.nlp.pos;

public class Context {
	private String previousTag;
	private String nextTag;
	private int contextCounter;

	public Context() {
	}
	
	public Context(String previousTag, String nextTag) {
		super();
		this.previousTag = previousTag;
		this.nextTag = nextTag;
		this.setContextCounter(1);
	}

	public String getPreviousTag() {
		return previousTag;
	}

	public void setPreviousTag(String previousTag) {
		this.previousTag = previousTag;
	}

	public String getNextTag() {
		return nextTag;
	}

	public void setNextTag(String nextTag) {
		this.nextTag = nextTag;
	}

	public int getContextCounter() {
		return contextCounter;
	}

	public void setContextCounter(int contextCounter) {
		this.contextCounter = contextCounter;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nextTag == null) ? 0 : nextTag.hashCode());
		result = prime * result + ((previousTag == null) ? 0 : previousTag.hashCode());
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
		Context other = (Context) obj;
		if (nextTag == null) {
			if (other.nextTag != null)
				return false;
		} else if (!nextTag.equals(other.nextTag))
			return false;
		if (previousTag == null) {
			if (other.previousTag != null)
				return false;
		} else if (!previousTag.equals(other.previousTag))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Context [previousTag=" + previousTag + ", nextTag=" + nextTag + ", contextCounter=" + contextCounter
				+ "]";
	}

}
