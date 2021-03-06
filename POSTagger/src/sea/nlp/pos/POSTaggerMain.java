package sea.nlp.pos;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Sagar
 *
 */
public class POSTaggerMain {
	private static final String INPUT = "input.txt";
	private static final String OUTPUT1 = "output-1.txt";
	private static final int TOP = 5;
	private TreeSet<WordTagsCount> topErroneousWords;
	private POS pos;

	public POSTaggerMain() {
		pos = new POS();
	}

	public static void main(String[] args) {
		while (true) {
			try {

				System.out.println("************************\nPOS Tagger\n************************");
				System.out.println("Press enter when you are ready with the input file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

				POSTaggerMain tagger = new POSTaggerMain();

				// Read the input training file that is correctly tagged
				tagger.getWordTags();

				// Re-tag the input file and generate an output file with
				// supposedly
				// correct tags
				tagger.reTag();

				// Show the error rate
				System.out.println("Error Rate(By tagging with most probable tags):: " + tagger.getErrorRate());
				Thread.sleep(1000);

				// Show top erroneous words
				System.out.println("\nTop " + TOP + " Erroneous words::\n");
				tagger.setTopErroneousWords(tagger.getTopErroneousWords(TOP));
				Thread.sleep(1000);

				// Show contexts
				tagger.getContexts();

				// Display rules
				tagger.displayRules();
				Thread.sleep(1000);

				// Show error rate on retagging with rules
				System.out.println("\nError rate(By tagging with rules):: " + tagger.reTagWithRules());

				System.out.println("\n*****************Done**********************");

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

	public void getWordTags() throws FileNotFoundException {
		System.out.println("Reading Input");
		Scanner scanner = new Scanner(new File(INPUT));

		while (scanner.hasNext()) {
			String token = scanner.next();
			String word = token.split("_")[0];
			String tag = token.split("_")[1];

			pos.addWord(word, tag);
		}
		scanner.close();
	}

	public void reTag() throws FileNotFoundException, InterruptedException {
		System.out.println("\nRe-Tagging the " + INPUT + " file");
		Scanner scanner = new Scanner(new File(INPUT));

		PrintWriter writer = new PrintWriter(OUTPUT1);

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			for (String token : line.split(" ")) {
				String word = token.split("_")[0];
				String tag = token.split("_")[1];
				String correctTag = pos.getCorrectTag(word);

				if (!tag.equals(correctTag)) {
					pos.incrementErrorCounter(word);
				}
				writer.write(token.split("_")[0] + "_" + correctTag + " ");
			}
			writer.println();
		}

		writer.close();
		scanner.close();

		System.out.println("Please check the " + OUTPUT1 + " file.");
		Thread.sleep(1000);
	}

	public float getErrorRate() {
		return (float) pos.getErrorCount() / pos.getWordCount();
	}

	public TreeSet<WordTagsCount> getTopErroneousWords(int top) {
		TreeSet<WordTagsCount> wordTags = pos.getTopErroneousWords(top);
		for (WordTagsCount word : wordTags) {
			System.out.println(word.getWord() + " :: " + word.getErrorCounter() + "/" + word.getWordCounter());
		}

		return wordTags;
	}

	public void getContexts() throws FileNotFoundException {
		for (WordTagsCount wordError : topErroneousWords) {
			Pattern PATTERN = Pattern.compile("(\\w+_\\w+)*\\s*(" + wordError.getWord() + "_\\w*)\\s*(\\w+_\\w+)*");

			Scanner scanner = new Scanner(new File(INPUT));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Matcher matcher = PATTERN.matcher(line);
				while (matcher.find()) {
					String previousTag = "";
					String selfTag = "";
					String nextTag = "";
					if (matcher.group(1) != null && !matcher.group(1).trim().isEmpty()) {
						previousTag = matcher.group(1).split("_")[1];
					}
					if (matcher.group(2) != null && !matcher.group(2).trim().isEmpty()) {
						selfTag = matcher.group(2).split("_")[1];
					}
					if (matcher.group(3) != null && !matcher.group(3).trim().isEmpty()) {
						nextTag = matcher.group(3).split("_")[1];
					}

					pos.updateWordContext(wordError.getWord(), selfTag, previousTag, nextTag);
				}

			}
			scanner.close();

			/*
			 * System.out.println("\n" + wordError.getWord()); for (TagCount
			 * tagCount : wordError.getTags()) { for (Context context :
			 * tagCount.getContexts()) {
			 * System.out.print(context.getPreviousTag() + "\t");
			 * System.out.print(tagCount.getTag() + "\t");
			 * System.out.print(context.getNextTag() + "\t");
			 * System.out.println(context.getContextCounter()); } }
			 */
		}
	}

	public float reTagWithRules() throws FileNotFoundException {
		int errorCounter = 0;
		for (WordTagsCount wordError : topErroneousWords) {
			Pattern PATTERN = Pattern.compile("(\\w+_\\w+)*\\s*(" + wordError.getWord() + "_\\w*)\\s*(\\w+_\\w+)*");

			Scanner scanner = new Scanner(new File(INPUT));
			while (scanner.hasNextLine()) {
				String line = scanner.nextLine();
				Matcher matcher = PATTERN.matcher(line);
				while (matcher.find()) {
					String previousTag = "";
					String selfTag = "";
					String nextTag = "";
					if (matcher.group(1) != null && !matcher.group(1).trim().isEmpty()) {
						previousTag = matcher.group(1).split("_")[1];
					}
					if (matcher.group(2) != null && !matcher.group(2).trim().isEmpty()) {
						selfTag = matcher.group(2).split("_")[1];
					}
					if (matcher.group(3) != null && !matcher.group(3).trim().isEmpty()) {
						nextTag = matcher.group(3).split("_")[1];
					}

					String correctTag = selfTag;
					correctTag = pos.getCorrectTagUsingRules(wordError.getWord(), previousTag, nextTag);

					if (!correctTag.equals(selfTag))
						errorCounter++;
				}
			}
			scanner.close();
		}

		return (float) errorCounter / pos.getWordCount();
	}

	public void displayRules() throws InterruptedException {
		System.out.println("\nFollowing are the handwritten rules used for retagging the input file:");

		// that
		System.out.println("For word :: \"that\"");
		Thread.sleep(500);
		System.out.println("IF");
		System.out.println("\tPREVIOUS WORD - (NN/VBD)");
		System.out.println("\tNEXT WORD - (PRP)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (NN/VBD/VBZ/NNS/VB)");
		System.out.println("\tNEXT WORD - (DT)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - IN");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (NN)");
		System.out.println("\tNEXT WORD - (VBZ/VBD/MD)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (NNS)");
		System.out.println("\tNEXT WORD - (VBP/MD/VBD/VBZ)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - WDT");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (IN/CC/VBG/VBD/TO/VBP)");
		System.out.println("\tNEXT WORD - (NN)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - DT");
		Thread.sleep(500);
		//

		// have
		System.out.println("\n\nFor word :: \"have\"");
		Thread.sleep(500);
		System.out.println("IF");
		System.out.println("\tPREVIOUS WORD - (NNS/NN/PRP/WP/WDT/EX/NNP/CC)");
		System.out.println("\tNEXT WORD - (VBN)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (NNS)");
		System.out.println("\tNEXT WORD - (RB)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - VBP");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (MD)");
		System.out.println("\tNEXT WORD - (VBN/TO/JJ/DT/IN)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (TO)");
		System.out.println("\tNEXT WORD - (VBN/CD/TO/JJ)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - VB");
		Thread.sleep(500);
		//

		// more
		System.out.println("\n\nFor word :: \"more\"");
		Thread.sleep(500);
		System.out.println("IF");
		System.out.println("\tPREVIOUS WORD - (IN/VB/CD/VBD/RB)");
		System.out.println("\tNEXT WORD - (IN)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (DT/CD)");
		System.out.println("\tNEXT WORD - (NNS)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (VBZ/VB/VBD)");
		System.out.println("\tNEXT WORD - (NN)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - JJR");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (VBD/VBZ/NN/CC/VBG/VB/DT/VBN/RB/IN)");
		System.out.println("\tNEXT WORD - (JJ)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (DT/NNS/NN)");
		System.out.println("\tNEXT WORD - (RB)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - RBR");
		Thread.sleep(500);
		//

		// 's
		System.out.println("\n\nFor word :: \"'s\"");
		Thread.sleep(500);
		System.out.println("IF");
		System.out.println("\tPREVIOUS WORD - (NNP)");
		System.out.println("\tNEXT WORD - (NN/NNP/JJ/NNS/CD/JJS/IN/VBG)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (NN)");
		System.out.println("\tNEXT WORD - (NN/JJ/NNS/NNP/JJS/CD)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - POS");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (EX)");
		System.out.println("\tNEXT WORD - (DT/CD/VBN)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (PRP)");
		System.out.println("\tNEXT WORD - (VBG/RB/DT/IN/VBN/JJ)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - VBZ");
		Thread.sleep(500);
		//

		// plans
		System.out.println("\n\nFor word :: \"plans\"");
		Thread.sleep(500);
		System.out.println("IF");
		System.out.println("\tPREVIOUS WORD - (PRP/NNP/NN/WDT/RB)");
		System.out.println("\tNEXT WORD - (TO)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - VBZ");
		Thread.sleep(500);

		System.out.println("\nIF");
		System.out.println("\tPREVIOUS WORD - (NN/VBD/VBN/JJ)");
		System.out.println("\tNEXT WORD - (IN)");
		System.out.println("\t\tOR");
		System.out.println("\tPREVIOUS WORD - (IN/VB/VBN/CC/DT/VBD/VBZ/NNP)");
		System.out.println("\tNEXT WORD - (TO)");
		System.out.println("THEN");
		System.out.println("\tCORRECT TAG - NNS");
		Thread.sleep(500);
		//

	}

	public POS getPos() {
		return pos;
	}

	public void setPos(POS pos) {
		this.pos = pos;
	}

	public TreeSet<WordTagsCount> getTopErroneousWords() {
		return topErroneousWords;
	}

	public void setTopErroneousWords(TreeSet<WordTagsCount> topErroneousWords) {
		this.topErroneousWords = topErroneousWords;
	}
}
