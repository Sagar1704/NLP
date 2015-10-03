package sea.nlp.ngram;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class NGramMain {
	// HashMap<String, ArrayList<HashMap<String, Integer>>> map = new
	// HashMap<String, ArrayList<HashMap<String, Integer>>>();
	private StringBuilder output;

	public NGramMain() {
		setOutput(new StringBuilder());
	}

	public static void main(String[] args) {
		NGramMain ngram = new NGramMain();
		Scanner scan = new Scanner(System.in);
		char continueChoice = 0;

		do {
			System.out.println("What would you like to do?");
			System.out.println("(1) Calculate unigram probabilities.");
			System.out.println("(2) Calculate bigram probabilities.");
			System.out.println("(3) Exit.");
			System.out.println("Enter your choice::");

			int choice = scan.nextInt();
			switch (choice) {
			case 1:
				break;
			default:
				break;
			}

		} while (continueChoice != 'y' || continueChoice != 'Y');
	}

	private void readInput() {
		try {
			Scanner scanner = new Scanner(new File("input"));

			while (scanner.hasNext()) {
				scanner.nextLine().trim();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}

	public StringBuilder getOutput() {
		return output;
	}

	public void setOutput(StringBuilder output) {
		this.output = output;
	}

}
