package sea.nlp.pos;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Scanner;
import java.util.TreeSet;

/**
 * POS Tagging scans for the tags. Finds the count of the tags given a word.
 * Generates corpus by correcting the tags. Finds the errors of the tags. And
 * generate the rules for the errors.
 * 
 * @author Sagar
 *
 */
public class POSTagging {
	public static final String INPUT = "HW2_F15_NLP6320_POSTaggedTrainingSet.txt";
	public static final String OUTPUT = "ReTaggedTrainingSet.txt";
	public static int tokenCounter = 0;
	public static int errorCounter = 0;

	private HashMap<String, TreeSet<TagCounter>> wordTagMap;
	private TreeSet<TagError> tagErrors;

	public POSTagging() {
		this.wordTagMap = new HashMap<String, TreeSet<TagCounter>>();
		this.tagErrors = new TreeSet<TagError>(new Comparator<TagError>() {

			@Override
			public int compare(TagError o1, TagError o2) {
				if (o1.getErrorCount() > o2.getErrorCount())
					return 1;
				else if (o1.getErrorCount() < o2.getErrorCount())
					return -1;
				return 0;
			}
		});
	}

	public static void main(String[] args) {
		POSTagging tagger = new POSTagging();

		try {
			tagger.readInput();

			tagger.reTag();

		} catch (FileNotFoundException e) {
			System.out.println("Please check if the input file is present in the path.");
		}
	}

	/**
	 * Scans through the Training set and counts the tags for a word
	 * 
	 * @throws FileNotFoundException
	 */
	private void readInput() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(INPUT));

		while (scanner.hasNext()) {
			tokenCounter++;
			String token = scanner.next();
			String word = token.split("_")[0];
			TagCounter tag = new TagCounter(token.split("_")[1]);
			TreeSet<TagCounter> tags = new TreeSet<TagCounter>(new Comparator<TagCounter>() {
				@Override
				public int compare(TagCounter o1, TagCounter o2) {
					if (o1.getCounter() < o2.getCounter())
						return -1;
					else if (o1.getCounter() > o2.getCounter())
						return 1;
					return 0;
				}
			});
			tags.add(tag);
			if (!wordTagMap.containsKey(word)) {
				wordTagMap.put(word, tags);
			} else {
				tags = wordTagMap.get(word);
				for (TagCounter tagCounter : tags) {
					if (tagCounter.equals(tag)) {
						tagCounter.setCounter(tagCounter.getCounter() + 1);
						break;
					}
				}
			}

		}

		scanner.close();

	}

	/**
	 * Re Tag the input file tokens based on the analysis of tags
	 * 
	 * @throws FileNotFoundException
	 */
	private void reTag() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(INPUT));

		PrintWriter writer = new PrintWriter(OUTPUT);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (String token : line.split(" ")) {
				String correctTag = wordTagMap.get(token.split("_")[0]).first().getTag();
				if (!token.split("_")[1].equals(correctTag))
					errorCounter++;
				writer.write(token.split("_")[0] + "_" + correctTag + " ");
			}
			writer.println();
		}

		writer.close();
		scanner.close();
	}

	private float getErrorRate() {
		if (tokenCounter != 0)
			return errorCounter / tokenCounter;
		return 0.0f;
	}
}
