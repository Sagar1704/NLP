package sea.nlp.viterbi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This is the Viterbi implementation to provide correct hidden states for a
 * given observation sequence.
 * 
 * @author Sagar
 *
 */
public class ViterbiMain {
	public static final String HMM_INPUT = "hmm.txt";
	private HMM hmm;
	private Trellis trellis;
	
	public ViterbiMain() {
		super();
		this.hmm = new HMM();
		this.trellis = new Trellis();
	}

	public HMM getHmm() {
		return hmm;
	}

	public void setHmm(HMM hmm) {
		this.hmm = hmm;
	}
	
	public Trellis getTrellis() {
		return trellis;
	}

	public void setTrellis(Trellis trellis) {
		this.trellis = trellis;
	}

	public static void main(String[] args) {
		System.out.println("**************************\nViterbi\n**************************");

		while (true) {
			try {
				System.out.println("Press enter when you are ready with the hmm file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

				ViterbiMain viterbi = new ViterbiMain();
				// Get the input
				viterbi.generateHMM();
				System.out.println("\nGenerated a HMM from the given inputs");
				Thread.sleep(1000);
				
				// Generate Trellis
				System.out.println("\nPress enter when you are ready with the input sequence file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();
				viterbi.generateTrellis();
				System.out.println("\nGenerated Trellis.");
				Thread.sleep(1000);

				// Compute output sequence
				
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
	 * Read the input file to get the HMM data
	 * 
	 * @throws FileNotFoundException
	 */
	public void generateHMM() throws FileNotFoundException {
		Scanner scanner = new Scanner(new File(HMM_INPUT));

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String[] stateInfo = line.split("#")[0].split(" ");
			String[] transitionInfos = line.split("#")[1].split("%");
			String[] observationInfos = line.split("#")[2].split("%");

			ArrayList<Transition> transitions = new ArrayList<Transition>();
			for (String transitionInfo : transitionInfos) {
				transitions.add(new Transition(new State(transitionInfo.split(" ")[0]),
						(float) Double.parseDouble(transitionInfo.split(" ")[1])));
			}

			ArrayList<Observation> observations = new ArrayList<Observation>();
			for (String observationInfo : observationInfos) {
				observations.add(new Observation(observationInfo.split(" ")[0],
						(float) Double.parseDouble(observationInfo.split(" ")[1])));
			}

			hmm.addState(new State(stateInfo[0], (float) Double.parseDouble(stateInfo[1]), transitions, observations));
		}

		scanner.close();
	}

	public void generateTrellis() {
		
	}
}