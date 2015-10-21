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
		POSTaggerMain tagger = new POSTaggerMain();

		try {
			System.out.println("************************\nPOS Tagger\n************************");
			System.out.println("Press enter when you are ready with the input file.");
			new BufferedReader(new InputStreamReader(System.in)).readLine();

			// Read the input training file that is correctly tagged
			tagger.getWordTags();

			// Re-tag the input file and generate an output file with supposedly
			// correct tags
			tagger.reTag();

			// Show the error rate
			System.out.println("Error Rate(By tagging with most probable tags):: " + tagger.getErrorRate());
			
			// Show top erroneous words
			System.out.println("\nTop " + TOP + " Erroneous words::\n");
			tagger.setTopErroneousWords(tagger.getTopErroneousWords(TOP));
			
			// Show contexts
			tagger.getContexts();
			
			// Show error rate on retagging with rules
			System.out.println("Error rate(By tagging with rules):: " + tagger.reTagWithRules());
			
		} catch (FileNotFoundException e) {
			System.out.println("\nThe file named \"input.txt\" cannot be found in the path."
					+ "\n\nPlease make sure the file is present in the directory where you are running the \"POSTagger.jar\" file.\n");
		} catch (IOException e) {
			e.printStackTrace();
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

	public void reTag() throws FileNotFoundException {
		System.out.println("Re-Tagging the " + INPUT + " file");
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
	}

	public float getErrorRate() {
		return (float) pos.getErrorCount() / pos.getWordCount();
	}

	public TreeSet<WordTagsCount> getTopErroneousWords(int top) {
		TreeSet<WordTagsCount> wordTags = pos.getTopErroneousWords(top);
		for (WordTagsCount word : wordTags) {
			System.out.println(word.getWord() + " :: " + word.getErrorCounter());
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
			
			/*System.out.println("\n" + wordError.getWord());
			for (TagCount tagCount : wordError.getTags()) {
				for (Context context : tagCount.getContexts()) {
					System.out.print(context.getPreviousTag() + "\t");
					System.out.print(tagCount.getTag() + "\t");
					System.out.print(context.getNextTag() + "\t");
					System.out.println(context.getContextCounter());
				}
			}*/
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
				
					String correctTag  = selfTag;
					correctTag = pos.getCorrectTagUsingRules(wordError.getWord(), previousTag, nextTag);
					
					if(!correctTag.equals(selfTag))
						errorCounter++;
				}
			}
			scanner.close();
		}
		
		return (float) errorCounter/pos.getWordCount();
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
