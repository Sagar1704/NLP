package nlp.sea.lesk;

import java.util.Arrays;
import java.util.stream.Stream;

public class Sense {
	private int priority;
	private String gloss;
	private String example;

	public Sense() {
	}

	public Sense(int priority, String gloss, String example) {
		super();
		this.priority = priority;
		this.gloss = gloss;
		this.example = example;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriotity(int priority) {
		this.priority = priority;
	}

	public String getGloss() {
		return gloss;
	}

	public void setGloss(String gloss) {
		this.gloss = gloss;
	}

	public String getExample() {
		return example;
	}

	public void setExample(String example) {
		this.example = example;
	}

	@Override
	public String toString() {
		return "Sense [sense=" + priority + ", gloss=" + gloss + ", example=" + example + "]";
	}

	public int computeOverlap(String[] context, String[] signature) {
		int overlap = 0;
		for (String string1 : context) {
			for (String string2 : signature) {
				if(string1.equalsIgnoreCase(string2)) {
					overlap++;
					break;
				}
			}
		}
		return overlap;
	}

	public String[] getSignature() {
		gloss = gloss.replace(";", " ");
		String[] signature1 = gloss.split(" ");

		example = example.replace(";", " ");
		String[] signature2 = example.split(" ");

		return Stream.concat(Arrays.stream(signature1), Arrays.stream(signature2)).toArray(String[]::new);
	}
}