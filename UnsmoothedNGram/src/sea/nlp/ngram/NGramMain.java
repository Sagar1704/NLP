package sea.nlp.ngram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * This class takes tokenized corpus as input. It generates the output as 4
 * files: 1. Unsmoothed Unigram probabilities 2. Unsmoothed Bigram probabilities
 * 3. Smoothed Unigram probabilities 4. Smoothed Bigram probabilities
 * 
 * @author Sagar
 *
 */
public class NGramMain {
	private StringBuilder output;
	private List<Unigram> unigrams;
	private List<Bigram> bigrams;

	private static File input = new File("input.txt");

	public NGramMain() {
		setOutput(new StringBuilder());
	}

	public static void main(String[] args) {
		NGramMain ngram = new NGramMain();
		/*
		 * Scanner scan = null; char continueChoice = 0;
		 * 
		 * System.out.println(
		 * "This program will calculate the NGram proabilities."); do { try {
		 * scan = new Scanner(System.in);
		 * 
		 * System.out.println("Please do these pre-requisites:");
		 * Thread.sleep(2000); System.out.println(
		 * "(1) Make sure that you create a file name \"input.txt\" in the same folder as the jar that you executed."
		 * ); Thread.sleep(1000); System.out.println(
		 * "(2) The above file should contain the corpus."); Thread.sleep(1000);
		 * System.out.println("Are you sure you have the file ready? (y/n)");
		 * 
		 * continueChoice = scan.next().charAt(0); } catch (InterruptedException
		 * e) { e.printStackTrace(); } finally { scan.close(); }
		 * 
		 * } while (continueChoice == 'y' || continueChoice == 'Y');
		 */

		try {
			ngram.generateUnigramCounts();
			ngram.calculateUnsmoothedUnigramProbability();

			PrintWriter writer = new PrintWriter("unsmoothed_unigram.txt");
			for (Unigram unigram : ngram.unigrams) {
				writer.println(unigram.toString());
			}

			writer.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private void generateUnigramCounts() throws FileNotFoundException {

		unigrams = new ArrayList<Unigram>();
		Scanner scanner = new Scanner(input);
		try {
			while (scanner.hasNextLine()) {
				Unigram.totalUnigramCount++;

				String token = scanner.nextLine();
				ArrayList<String> tempList = new ArrayList<String>();
				tempList.add(token);
				Unigram unigram = new Unigram(tempList);
				if (!unigrams.contains(unigram)) {
					unigrams.add(unigram);
				} else {
					unigram = unigrams.get(unigrams.indexOf(unigram));
					unigram.setCount(unigram.getCount() + 1);
				}
			}
		} finally {
			scanner.close();
		}
	}

	private void generateBigramCounts() throws FileNotFoundException {
		unigrams = new ArrayList<Unigram>();
		Scanner scanner = new Scanner(input);
		boolean first = true;

		try {
			while (scanner.hasNextLine()) {
				String token = scanner.nextLine();
				ArrayList<String> tempList = new ArrayList<String>();
				if (first) {
					first = false;
					tempList.add(token);
				} else {
					first = true;
					Bigram.totalBigramCount++;
					tempList.add(token);
					Bigram bigram = new Bigram(tempList);
					if (!bigrams.contains(bigram)) {
						bigrams.add(bigram);
					} else {
						bigram = bigrams.get(bigrams.indexOf(bigram));
						bigram.setCount(bigram.getCount() + 1);
					}
				}
			}
		} finally {
			scanner.close();
		}
	}

	private void calculateUnsmoothedBigramProbability() {
		for (int index = 0; index < bigrams.size(); index++) {
			Bigram bigram = bigrams.get(index);
			int count = bigram.getCount();
			float probability = (float) count / Unigram.totalUnigramCount;
			bigram.setUnsmoothedProbability(probability);
			bigrams.set(index, bigram);
		}
	}

	private void calculateUnsmoothedUnigramProbability() {
		for (int index = 0; index < unigrams.size(); index++) {
			Unigram unigram = unigrams.get(index);
			int count = unigram.getCount();
			float probability = (float) count / Unigram.totalUnigramCount;
			unigram.setUnsmoothedProbability(probability);
			unigrams.set(index, unigram);
		}
	}

	public StringBuilder getOutput() {
		return output;
	}

	public void setOutput(StringBuilder output) {
		this.output = output;
	}

}
