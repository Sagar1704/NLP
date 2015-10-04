package sea.nlp.ngram;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
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
			// Unsmoothed Unigram
			ngram.generateUnigramCounts();
			ngram.calculateUnsmoothedUnigramProbability();

			Collections.sort(ngram.unigrams, new Comparator<Unigram>() {

				@Override
				public int compare(Unigram o1, Unigram o2) {
					String unigram1 = o1.getWords().get(0);
					String unigram2 = o2.getWords().get(0);
					
					return unigram1.compareTo(unigram2);
				}
			});
			
			PrintWriter writer = new PrintWriter("unsmoothed_unigram.txt");
			for (Unigram unigram : ngram.unigrams) {
				writer.println(unigram.toString());
			}

			writer.close();

			// Unsmoothed Bigram
			ngram.generateBigramCounts();
			ngram.calculateUnsmoothedBigramProbability();

			Collections.sort(ngram.bigrams, new Comparator<Bigram>() {

				@Override
				public int compare(Bigram o1, Bigram o2) {
					String bigram1 = o1.getWords().get(0);
					String bigram2 = o2.getWords().get(0);
					
					return bigram1.compareTo(bigram2);
				}
			});
			
			writer = new PrintWriter("unsmoothed_bigram.txt");
			for (Bigram bigram : ngram.bigrams) {
				writer.println(bigram.toString());
			}

			writer.close();

			// Smoothed Unigram
			ngram.calculateSmoothedUnigramProbabilities();
			
			Collections.sort(ngram.unigrams, new Comparator<Unigram>() {

				@Override
				public int compare(Unigram o1, Unigram o2) {
					String unigram1 = o1.getWords().get(0);
					String unigram2 = o2.getWords().get(0);
					
					return unigram1.compareTo(unigram2);
				}
			});
			
			writer = new PrintWriter("smoothed_unigram.txt");
			for (Unigram unigram : ngram.unigrams) {
				writer.println(unigram.getWords() + "\t\t\t\t\t" + unigram.getSmoothedProbability());
			}

			writer.close();

			// Smoothed Bigram
			ngram.calculateSmoothedBigramProbabilities();
			
			Collections.sort(ngram.bigrams, new Comparator<Bigram>() {

				@Override
				public int compare(Bigram o1, Bigram o2) {
					String bigram1 = o1.getWords().get(0);
					String bigram2 = o2.getWords().get(0);
					
					return bigram1.compareTo(bigram2);
				}
			});

			writer = new PrintWriter("smoothed_bigram.txt");
			for (Bigram bigram : ngram.bigrams) {
				writer.println(bigram.getWords() + "\t\t\t\t\t" + bigram.getSmoothedProbability());
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

	private void calculateUnsmoothedUnigramProbability() {
		for (int index = 0; index < unigrams.size(); index++) {
			Unigram unigram = unigrams.get(index);
			int count = unigram.getCount();
			float probability = (float) count / Unigram.totalUnigramCount;
			unigram.setUnsmoothedProbability(probability);
			unigrams.set(index, unigram);
		}
	}

	private void generateBigramCounts() throws FileNotFoundException {
		bigrams = new ArrayList<Bigram>();
		Scanner scanner = new Scanner(input);
		ArrayList<String> tempList = new ArrayList<String>();

		try {
			String token1 = "";
			if (scanner.hasNextLine()) {
				token1 = scanner.nextLine();
			}

			while (scanner.hasNextLine()) {
				String token2 = scanner.nextLine();

				tempList = new ArrayList<String>();
				tempList.add(token1);
				token1 = token2;
				tempList.add(token2);

				Bigram bigram = new Bigram(tempList);
				if (!bigrams.contains(bigram)) {
					bigrams.add(bigram);
				} else {
					bigram = bigrams.get(bigrams.indexOf(bigram));
					bigram.setCount(bigram.getCount() + 1);
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

			String unigramWord = bigram.getWords().get(0);
			ArrayList<String> tempList = new ArrayList<String>();
			tempList.add(unigramWord);
			Unigram unigram = new Unigram(tempList);

			int unigramCount = unigrams.get(unigrams.indexOf(unigram)).getCount();

			float probability = (float) count / unigramCount;
			bigram.setUnsmoothedProbability(probability);
			bigrams.set(index, bigram);
		}
	}

	private void calculateSmoothedUnigramProbabilities() {
		Collections.sort(unigrams, new Comparator<Unigram>() {

			@Override
			public int compare(Unigram o1, Unigram o2) {
				Integer count1 = (Integer) o1.getCount();
				Integer count2 = (Integer) o2.getCount();

				return count1.compareTo(count2);
			}
		});

		Map<Integer, ArrayList<Unigram>> countMap = new HashMap<Integer, ArrayList<Unigram>>();
		for (Unigram unigram : unigrams) {

			if (countMap.containsKey(unigram.getCount())) {
				ArrayList<Unigram> unigramMapArray = countMap.get(unigram.getCount());
				unigramMapArray.add(unigram);
			} else {
				ArrayList<Unigram> unigramMapArray = new ArrayList<Unigram>();
				unigramMapArray.add(unigram);
				countMap.put(unigram.getCount(), unigramMapArray);
			}
		}

		ListIterator<Unigram> unigramIterator = unigrams.listIterator();
		for (Integer count : countMap.keySet()) {
			int Nc = countMap.get(count).size();
			int Nc1 = 0;
			if (countMap.containsKey(count + 1))
				Nc1 = countMap.get(count + 1).size();
			float probability = (((float) ((count + 1) * Nc1) / Nc) / Unigram.totalUnigramCount);

			while (unigramIterator.hasNext()) {
				Unigram unigram = (Unigram) unigramIterator.next();

				if (unigram.getCount() == count) {
					unigram.setSmoothedProbability(probability);
				} else {
					unigramIterator.previous();
					break;
				}
			}
		}
	}

	private void calculateSmoothedBigramProbabilities() {
		generateZeroBigrams();

		Collections.sort(bigrams, new Comparator<Bigram>() {

			@Override
			public int compare(Bigram o1, Bigram o2) {
				Integer count1 = (Integer) o1.getCount();
				Integer count2 = (Integer) o2.getCount();

				return count1.compareTo(count2);
			}
		});

		Map<Integer, ArrayList<Bigram>> countMap = new HashMap<Integer, ArrayList<Bigram>>();
		for (Bigram bigram : bigrams) {

			if (countMap.containsKey(bigram.getCount())) {
				ArrayList<Bigram> bigramMapArray = countMap.get(bigram.getCount());
				bigramMapArray.add(bigram);
			} else {
				ArrayList<Bigram> bigramMapArray = new ArrayList<Bigram>();
				bigramMapArray.add(bigram);
				countMap.put(bigram.getCount(), bigramMapArray);
			}
		}

		ListIterator<Bigram> bigramIterator = bigrams.listIterator();
		for (Integer count : countMap.keySet()) {
			int Nc = countMap.get(count).size();
			int Nc1 = 0;
			if (countMap.containsKey(count + 1))
				Nc1 = countMap.get(count + 1).size();
			float temp = (float) (count + 1) * Nc1 / Nc;
			float probability = temp / (Unigram.totalUnigramCount - 1);

			while (bigramIterator.hasNext()) {
				Bigram bigram = (Bigram) bigramIterator.next();

				if (bigram.getCount() == count) {
					bigram.setSmoothedProbability(probability);
				} else {
					bigramIterator.previous();
					break;
				}
			}
		}
	}

	private void generateZeroBigrams() {
		for (Unigram unigram : unigrams) {
			for (Unigram unigram1 : unigrams) {
				ArrayList<String> words = new ArrayList<String>();
				words.add(unigram.getWords().get(0));
				words.add(unigram1.getWords().get(0));
				Bigram bigram = new Bigram(words);
				bigram.setCount(0);
				
				if (!bigrams.contains(bigram)) {
					bigrams.add(bigram);
				}
			}
		}
	}

	public StringBuilder getOutput() {
		return output;
	}

	public void setOutput(StringBuilder output) {
		this.output = output;
	}

}
