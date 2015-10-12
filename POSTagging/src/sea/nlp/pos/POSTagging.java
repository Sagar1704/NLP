package sea.nlp.pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;

import sea.nlp.comparator.Counter;
import sea.nlp.comparator.ErrorCounter;

/**
 * POS Tagging scans for the tags. Finds the count of the tags given a word.
 * Generates corpus by correcting the tags. Finds the errors of the tags. And
 * generate the rules for the errors.
 * 
 * @author Sagar
 *
 */
public class POSTagging {
	public static final String INPUT = "input.txt";
	public static final String OUTPUT = "ReTaggedTrainingSet.txt";
	public static int tokenCounter = 0;
	public static int errorCounter = 0;

	private HashMap<String, TreeSet<TagCounter>> wordTagMap;
	private TreeSet<WordError> wordErrors;

	public POSTagging() {
		this.wordTagMap = new HashMap<String, TreeSet<TagCounter>>();
		this.wordErrors = new TreeSet<WordError>(new ErrorCounter());
	}

	public static void main(String[] args) {
		System.out.println("****************************************************************");
		System.out.println("POS Tagging");
		System.out.println("****************************************************************");

		while (true) {
			try {
				System.out.println("Press enter when you are ready with the input file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

				POSTagging tagger = new POSTagging();

				// Analyze the training set
				System.out.println("Analyzing the training set");
				Thread.sleep(1000);
				tagger.readInput();

				// Re-tag the training set
				System.out.println("Re-tagging the training set");
				Thread.sleep(1000);
				tagger.reTag();
				System.out.println("Please check " + OUTPUT + " to check the output of the re tagging.");
				Thread.sleep(1000);

				// Get the error rate
				System.out.println("The error rate of the training set is:: " + tagger.getErrorRate());
				Thread.sleep(1000);

				System.out.println("The Top 5 erroneously tagged words are::");
				tagger.getTopErroneuosTags(5);
				System.out.println("\n**************Done***************");
				System.out.println("Close the window to exit!");
				new BufferedReader(new InputStreamReader(System.in)).readLine();
			} catch (FileNotFoundException e) {
				System.out.println("\nThe file named \"input.txt\" cannot be found in the path."
						+ "\n\nPlease make sure the file is present in the directory where you are running the \"POSTagger.jar\" file.\n");
			} catch (IOException e) {
				e.printStackTrace();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
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
			if (!wordTagMap.containsKey(word)) {
				TreeSet<TagCounter> tags = new TreeSet<TagCounter>(new Counter());
				tags.add(tag);
				wordTagMap.put(word, tags);
			} else {
				TreeSet<TagCounter> tags = wordTagMap.get(word);
				for (Iterator<TagCounter> treeIterator = tags.iterator(); treeIterator.hasNext();) {
					TagCounter tagCounter = (TagCounter) treeIterator.next();
					if (tagCounter.equals(tag)) {
						tag.setCounter(tagCounter.getCounter() + 1);
						treeIterator.remove();
						break;
					}
				}
				tags.add(tag);
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
				if (!token.split("_")[1].equals(correctTag)) {
					errorCounter++;
					WordError word = new WordError(token.split("_")[0]);
					for (Iterator<WordError> treeIterator = wordErrors.iterator(); treeIterator.hasNext();) {
						WordError wordError = (WordError) treeIterator.next();
						if (wordError.equals(word)) {
							word.setErrorCount(wordError.getErrorCount() + 1);
							treeIterator.remove();
							break;
						}
					}
					wordErrors.add(word);

				}
				writer.write(token.split("_")[0] + "_" + correctTag + " ");
			}
			writer.println();
		}

		writer.close();
		scanner.close();
	}

	/**
	 * Get the tagged error rate
	 * 
	 * @return
	 */
	private float getErrorRate() {
		if (tokenCounter != 0)
			return (float) errorCounter / tokenCounter;
		return 0.0f;
	}

	/**
	 * Get the top erroneous words
	 * 
	 * @param top
	 */
	private void getTopErroneuosTags(int top) {
		int size = (top < wordErrors.size() ? top : wordErrors.size());

		for (WordError wordError : wordErrors) {
			if (size == 0)
				break;
			System.out.println(wordError.getWord() + " :: " + wordError.getErrorCount());
			size--;
		}
	}

	/**
	 * Generate Tagging rules based on the erroneously tagged words
	 * @throws FileNotFoundException 
	 */
	private void generateTaggingRules(int top) throws FileNotFoundException {
		int size = (top < wordErrors.size() ? top : wordErrors.size());

		for (WordError wordError : wordErrors) {
			if (size == 0)
				break;
			
			Scanner scanner = new Scanner(new File(INPUT));
			
			size--;
		}
	}

}
