package sea.nlp.ngram;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.NoSuchElementException;
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
	private List<Unigram> unigrams;
	private List<Bigram> bigrams;

	private static File input = new File("input.txt");

	public static void main(String[] args) {
		NGramMain ngram = new NGramMain();
		System.out.println("***********NGram************");
		while (true) {
			Unigram.totalUnigramCount = 0;
			try {
				System.out.println("Press enter when you are ready with the input file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

				// Unsmoothed Unigram
				System.out.println("\nGenerating Unsmoothed Unigram probabilities.");
				Thread.sleep(2000);
				ngram.generateUnigramCounts();
				ngram.calculateUnsmoothedUnigramProbability();

				Collections.sort(ngram.unigrams);
				PrintWriter writer = new PrintWriter("unsmoothed_unigram.txt");
				for (Unigram unigram : ngram.unigrams) {
					writer.println(unigram.toString() + unigram.getUnsmoothedProbability());
				}
				writer.close();

				System.out.println("Please check \"unsmoothed_unigram.txt\"");
				Thread.sleep(1000);

				// Unsmoothed Bigram
				System.out.println("\nGenerating Unsmoothed Bigram probabilities.");
				Thread.sleep(2000);
				ngram.generateBigramCounts();
				ngram.calculateUnsmoothedBigramProbability();

				Collections.sort(ngram.bigrams);
				writer = new PrintWriter("unsmoothed_bigram.txt");
				for (Bigram bigram : ngram.bigrams) {
					writer.println(bigram.toString() + bigram.getUnsmoothedProbability());
				}
				writer.close();

				System.out.println("Please check \"unsmoothed_bigram.txt\"");
				Thread.sleep(1000);

				// Smoothed Unigram
				System.out.println("\nGenerating Smoothed Unigram probabilities.");
				Thread.sleep(2000);
				ngram.calculateSmoothedUnigramProbabilities();

				Collections.sort(ngram.unigrams);

				writer = new PrintWriter("smoothed_unigram.txt");
				for (Unigram unigram : ngram.unigrams) {
					writer.println(unigram.toString() + "\t\t\t\t\t" + unigram.getSmoothedProbability());
				}

				writer.close();

				System.out.println("Please check \"smoothed_unigram.txt\"");
				Thread.sleep(1000);

				// Smoothed Bigram
				System.out.println("\nGenerating Smoothed Bigram probabilities.");
				Thread.sleep(2000);

				ngram.calculateSmoothedBigramProbabilities();

				Collections.sort(ngram.bigrams);

				writer = new PrintWriter("smoothed_bigram.txt");
				for (Bigram bigram : ngram.bigrams) {
					writer.println(bigram.toString() + "\t\t\t\t\t" + bigram.getSmoothedProbability());
				}

				writer.close();

				System.out.println("Please check \"smoothed_bigram.txt\"");
				System.out.println("\n**************Done***************");

				System.out.println("Close the window to exit!");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

			} catch (FileNotFoundException e) {

				System.out.println("\nThe file named \"input.txt\" cannot be found in the path."
						+ "\n\nPlease make sure the file is present in the directory where you are running the \"NGram.jar\" file.\n");

			} catch (InterruptedException e) {
				e.printStackTrace();
			} catch (NoSuchElementException e) {
				// Do Nothing
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				
			}
		}
	}

	/**
	 * Generate counts for Unigram
	 * 
	 * @throws FileNotFoundException
	 */
	private void generateUnigramCounts() throws FileNotFoundException {

		unigrams = new ArrayList<Unigram>();
		Scanner scanner = new Scanner(input);
		try {
			while (scanner.hasNextLine()) {
				Unigram.totalUnigramCount++;

				String token = scanner.nextLine();
				if (!token.isEmpty()) {
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
			while (scanner.hasNextLine()) {
				token1 = scanner.nextLine();
				if (!token1.isEmpty())
					break;

			}

			while (scanner.hasNextLine()) {
				String token2 = scanner.nextLine();
				if (token2.isEmpty())
					continue;
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

			float probability = 0.0f;
			if (count == 0) {
				probability = (float) Nc1 / (Unigram.totalUnigramCount - 1);
			} else {
				float temp = (float) (count + 1) * Nc1 / Nc;
				probability = temp / (Unigram.totalUnigramCount - 1);
			}
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
		ArrayList<Bigram> tempBigrams = new ArrayList<Bigram>();
		for (Iterator<Unigram> outerIterator = unigrams.iterator(); outerIterator.hasNext();) {
			Unigram outerUnigram = (Unigram) outerIterator.next();

			for (Iterator<Unigram> innerIterator = unigrams.iterator(); innerIterator.hasNext();) {
				Unigram innerUnigram = (Unigram) innerIterator.next();
				ArrayList<String> words = new ArrayList<String>();
				words.add(outerUnigram.getWords().get(0));
				words.add(innerUnigram.getWords().get(0));
				Bigram bigram = new Bigram(words);
				bigram.setCount(0);

				tempBigrams.add(bigram);
			}
		}
		for (Bigram bigram : bigrams) {
			if (tempBigrams.contains(bigram)) {
				Bigram temp = tempBigrams.get(tempBigrams.indexOf(bigram));
				temp.setCount(bigram.getCount());
				temp.setUnsmoothedProbability(bigram.getUnsmoothedProbability());
			}
		}
		bigrams = tempBigrams;
	}

}
