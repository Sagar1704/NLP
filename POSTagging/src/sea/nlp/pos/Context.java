package sea.nlp.pos;

public class Context {
	private TagCount left;
	private TagCount itself;
	private TagCount right;
	private int contextCounter;
	
	
	public Context(TagCount left, TagCount itself, TagCount right) {
		super();
		this.left = left;
		this.itself = itself;
		this.right = right;
		this.setContextCounter(1);
	}

	public TagCount getLeft() {
		return left;
	}

	public void setLeft(TagCount left) {
		this.left = left;
	}

	public TagCount getItself() {
		return itself;
	}

	public void setItself(TagCount itself) {
		this.itself = itself;
	}

	public TagCount getRight() {
		return right;
	}

	public void setRight(TagCount right) {
		this.right = right;
	}

	@Override
	public String toString() {
		return "[" +  left + "--" + itself + "--" + right + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((itself == null) ? 0 : itself.hashCode());
		result = prime * result + ((left == null) ? 0 : left.hashCode());
		result = prime * result + ((right == null) ? 0 : right.hashCode());
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
		if (itself == null) {
			if (other.itself != null)
				return false;
		} else if (!itself.equals(other.itself))
			return false;
		if (left == null) {
			if (other.left != null)
				return false;
		} else if (!left.equals(other.left))
			return false;
		if (right == null) {
			if (other.right != null)
				return false;
		} else if (!right.equals(other.right))
			return false;
		return true;
	}

	public int getContextCounter() {
		return contextCounter;
	}

	public void setContextCounter(int contextCounter) {
		this.contextCounter = contextCounter;
	}

}