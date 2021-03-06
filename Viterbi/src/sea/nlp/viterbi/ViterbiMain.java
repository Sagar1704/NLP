package sea.nlp.viterbi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;
import java.util.Stack;

/**
 * This is the Viterbi implementation to provide correct hidden states for a
 * given observation sequence.
 * 
 * @author Sagar
 *
 */
public class ViterbiMain {
	private static final String START = "start";
	public static final String HMM_INPUT = "hmm.txt";
	public static final String OBSERVATION_INPUT1 = "inputSequence1.txt";
	public static final String OBSERVATION_INPUT2 = "inputSequence2.txt";

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
				System.out.println("Generated a HMM from the given inputs");
				Thread.sleep(1000);

				// Generate Output
				viterbi.generateObservationSequence(OBSERVATION_INPUT1);
				viterbi.generateObservationSequence(OBSERVATION_INPUT2);

				System.out.println("\n**************Done***************");
				System.out.println("Close the window to exit!");
				new BufferedReader(new InputStreamReader(System.in)).readLine();
			} catch (FileNotFoundException e) {
				System.out.println("\nThe file named \"hmm.txt\" cannot be found in the path."
						+ "\n\nPlease make sure the file is present in the directory where you are running the \"Viterbi.jar\" file.\n");
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

			Set<Transition> transitions = new HashSet<Transition>();
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

	/**
	 * @param file
	 * @throws IOException
	 * @throws InterruptedException
	 */
	public void generateObservationSequence(String file) throws IOException, InterruptedException {
		while (true) {
			try {
				System.out.println("\nPress enter when you are ready with the input sequence file.");
				new BufferedReader(new InputStreamReader(System.in)).readLine();

				displayInputSequence(file);
				Thread.sleep(500);

				// Generate Trellis
				generateTrellis(file);
				break;
			} catch (FileNotFoundException e) {
				System.out.println("\nThe file named \"" + file + "\" cannot be found in the path."
						+ "\n\nPlease make sure the file is present in the directory where you are running the \"Viterbi.jar\" file.\n");
				continue;
			}
		}
	}

	/**
	 * @param file
	 * @throws FileNotFoundException
	 * @throws InterruptedException
	 */
	private void generateTrellis(String file) throws FileNotFoundException, InterruptedException {
//		System.out.println("\nGenerating Trellis");
		boolean first = true;

		Scanner scanner = new Scanner(new File(file));

		while (scanner.hasNextLine()) {
			Observation observation = new Observation(scanner.nextLine().trim());
			int size = trellis.getTrellisStates().size();
			if (first) {
				// Generate a start state
				TrellisState startState = new TrellisState(new State(START));
				for (State state : hmm.getStates()) {
					float transitionProbability = state.getPriorProbability() * state.getObservations()
							.get(state.getObservations().indexOf(observation)).getObservationProbability();
					startState.addTransition(new Transition(state, transitionProbability));

					TrellisState trellisState = new TrellisState(state);
					trellisState.setMaxProbability(transitionProbability);
					trellisState.setBackpointer(startState, transitionProbability);

					trellis.addToTrellis(trellisState);
				}
				trellis.addToTrellis(startState, first);
				first = false;
			} else {
				// Generate the rest of the trellis states
				for (State state : hmm.getStates()) {
					TrellisState trellisState = new TrellisState(state);
					for (int i = (size - hmm.getStates().size()); i < size; i++) {
						TrellisState currentTrellisState = trellis.getTrellisStates().get(i);
						float stateTransitionProbability = currentTrellisState.getTransitinProbability(state);
						float observationProbability = state.getObservations()
								.get(state.getObservations().indexOf(observation)).getObservationProbability();
						float transitionProbability = stateTransitionProbability * observationProbability;

						currentTrellisState.addTransition(new Transition(state, transitionProbability));

						float maxProbability = transitionProbability * currentTrellisState.getMaxProbability();
						trellisState.setMaxProbability(maxProbability);
						trellisState.setBackpointer(currentTrellisState, maxProbability);
					}
					trellis.addToTrellis(trellisState);
				}
			}
		}

		// Generate the end state
		TrellisState endState = new TrellisState(new State("end"));
		int size = trellis.getTrellisStates().size();
		for (int i = (size - hmm.getStates().size()); i < size; i++) {

			TrellisState trellisState = trellis.getTrellisStates().get(i);
			float maxProbability = trellisState.getMaxProbability();
			endState.setMaxProbability(maxProbability);
			endState.setBackpointer(trellisState, maxProbability);
			trellisState.addTransition(new Transition(endState, maxProbability));

		}
		trellis.addToTrellis(endState);
		scanner.close();

//		System.out.println("Generated Trellis");
		Thread.sleep(1000);

		// Generate hidden states
		generateHiddenStates();
	}

	/**
	 * Traverse the trellis from end to start and display the hidden states. Use
	 * stack to store the back pointers.
	 */
	private void generateHiddenStates() {
		System.out.println("\nFollowing are the hidden states for the input sequence");
		Stack<State> stack = new Stack<State>();

		TrellisState trellisState = trellis.getTrellisStates().get(trellis.getTrellisStates().size() - 1);
		while (!trellisState.getStateName().equals(START)) {
			stack.push(trellisState.getBackpointer());
			trellisState = trellisState.getBackpointer();
		}

		if(!stack.isEmpty())
			stack.pop();
		while (!stack.isEmpty()) {
			System.out.println(stack.pop().getStateName());
		}

	}

	/**
	 * Display the input sequence
	 * 
	 * @param file
	 * @throws FileNotFoundException
	 */
	private void displayInputSequence(String file) throws FileNotFoundException {
		System.out.println("Input Sequence::");
		Scanner scanner = new Scanner(new File(file));

		while (scanner.hasNextLine()) {
			System.out.print(scanner.nextLine() + " ");
		}

		scanner.close();
	}
}
