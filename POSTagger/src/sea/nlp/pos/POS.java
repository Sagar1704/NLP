package sea.nlp.pos;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeSet;

import sea.nlp.comparator.ErrorComparator;

public class POS {
	private TreeSet<WordTagsCount> words;

	// that
	private ArrayList<String> thatPreviousIN1 = new ArrayList<String>();
	private ArrayList<String> thatNextIN1 = new ArrayList<String>();

	private ArrayList<String> thatPreviousIN2 = new ArrayList<String>();
	private ArrayList<String> thatNextIN2 = new ArrayList<String>();

	private ArrayList<String> thatPreviousWDT1 = new ArrayList<String>();
	private ArrayList<String> thatNextWDT1 = new ArrayList<String>();

	private ArrayList<String> thatPreviousWDT2 = new ArrayList<String>();
	private ArrayList<String> thatNextWDT2 = new ArrayList<String>();

	private ArrayList<String> thatPreviousDT1 = new ArrayList<String>();
	private ArrayList<String> thatNextDT1 = new ArrayList<String>();
	//

	// have
	private ArrayList<String> havePreviousVBP1 = new ArrayList<String>();
	private ArrayList<String> haveNextVBP1 = new ArrayList<String>();

	private ArrayList<String> havePreviousVBP2 = new ArrayList<String>();
	private ArrayList<String> haveNextVBP2 = new ArrayList<String>();

	private ArrayList<String> havePreviousVB1 = new ArrayList<String>();
	private ArrayList<String> haveNextVB1 = new ArrayList<String>();

	private ArrayList<String> havePreviousVB2 = new ArrayList<String>();
	private ArrayList<String> haveNextVB2 = new ArrayList<String>();
	//

	// more
	private ArrayList<String> morePreviousJJR1 = new ArrayList<String>();
	private ArrayList<String> moreNextJJR1 = new ArrayList<String>();

	private ArrayList<String> morePreviousJJR2 = new ArrayList<String>();
	private ArrayList<String> moreNextJJR2 = new ArrayList<String>();

	private ArrayList<String> morePreviousJJR3 = new ArrayList<String>();
	private ArrayList<String> moreNextJJR3 = new ArrayList<String>();

	private ArrayList<String> morePreviousRBR1 = new ArrayList<String>();
	private ArrayList<String> moreNextRBR1 = new ArrayList<String>();

	private ArrayList<String> morePreviousRBR2 = new ArrayList<String>();
	private ArrayList<String> moreNextRBR2 = new ArrayList<String>();
	//

	// 's
	private ArrayList<String> sPreviousPOS1 = new ArrayList<String>();
	private ArrayList<String> sNextPOS1 = new ArrayList<String>();

	private ArrayList<String> sPreviousPOS2 = new ArrayList<String>();
	private ArrayList<String> sNextPOS2 = new ArrayList<String>();

	private ArrayList<String> sPreviousVBZ1 = new ArrayList<String>();
	private ArrayList<String> sNextVBZ1 = new ArrayList<String>();

	private ArrayList<String> sPreviousVBZ2 = new ArrayList<String>();
	private ArrayList<String> sNextVBZ2 = new ArrayList<String>();
	//

	// plans
	private ArrayList<String> plansPreviousVBZ1 = new ArrayList<String>();
	private ArrayList<String> plansNextVBZ1 = new ArrayList<String>();

	private ArrayList<String> plansPreviousNNS1 = new ArrayList<String>();
	private ArrayList<String> plansNextNNS1 = new ArrayList<String>();

	private ArrayList<String> plansPreviousNNS2 = new ArrayList<String>();
	private ArrayList<String> plansNextNNS2 = new ArrayList<String>();
	//

	public POS() {
		this.words = new TreeSet<WordTagsCount>(new ErrorComparator());
		// that(IN)
		thatPreviousIN1.add("NN");
		thatPreviousIN1.add("VBD");
		thatNextIN1.add("PRP");

		thatPreviousIN2.add("NN");
		thatPreviousIN2.add("VBD");
		thatPreviousIN2.add("VBZ");
		thatPreviousIN2.add("NNS");
		thatPreviousIN2.add("VB");
		thatNextIN2.add("DT");

		// that(WDT)
		thatPreviousWDT1.add("NN");
		thatNextWDT1.add("VBZ");
		thatNextWDT1.add("VBD");
		thatNextWDT1.add("MD");

		thatPreviousWDT2.add("NNS");
		thatNextWDT2.add("VBP");
		thatNextWDT2.add("MD");
		thatNextWDT2.add("VBD");
		thatNextWDT2.add("VBZ");

		// that(DT)
		thatPreviousDT1.add("IN");
		thatPreviousDT1.add("CC");
		thatPreviousDT1.add("VBG");
		thatPreviousDT1.add("VBD");
		thatPreviousDT1.add("TO");
		thatPreviousDT1.add("VBP");
		thatNextDT1.add("NN");

		// have(VBP)
		havePreviousVBP1.add("NNS");
		havePreviousVBP1.add("NN");
		havePreviousVBP1.add("PRP");
		havePreviousVBP1.add("WP");
		havePreviousVBP1.add("WPT");
		havePreviousVBP1.add("EX");
		havePreviousVBP1.add("NNP");
		havePreviousVBP1.add("CC");
		havePreviousVBP1.add("NNS");
		haveNextVBP1.add("VBN");

		havePreviousVBP2.add("NNS");
		haveNextVBP2.add("RB");

		// have(VB)
		havePreviousVB1.add("MD");
		haveNextVB1.add("VBN");
		haveNextVB1.add("TO");
		haveNextVB1.add("JJ");
		haveNextVB1.add("DT");
		haveNextVB1.add("IN");

		havePreviousVB2.add("TO");
		haveNextVB2.add("VBN");
		haveNextVB2.add("CD");
		haveNextVB2.add("TO");
		haveNextVB2.add("JJ");
		//

		// more(JJR)
		morePreviousJJR1.add("IN");
		morePreviousJJR1.add("VB");
		morePreviousJJR1.add("CD");
		morePreviousJJR1.add("VBD");
		morePreviousJJR1.add("RB");
		moreNextJJR1.add("IN");

		morePreviousJJR2.add("DT");
		morePreviousJJR2.add("CD");
		moreNextJJR2.add("NNS");

		morePreviousJJR3.add("VBZ");
		morePreviousJJR3.add("VB");
		morePreviousJJR3.add("VBD");
		moreNextJJR3.add("NN");
		//

		// more(RBR)
		morePreviousRBR1.add("VBD");
		morePreviousRBR1.add("VBZ");
		morePreviousRBR1.add("NN");
		morePreviousRBR1.add("CC");
		morePreviousRBR1.add("VBG");
		morePreviousRBR1.add("VB");
		morePreviousRBR1.add("DT");
		morePreviousRBR1.add("VBN");
		morePreviousRBR1.add("RB");
		morePreviousRBR1.add("IN");
		moreNextRBR1.add("JJ");

		morePreviousRBR2.add("DT");
		morePreviousRBR2.add("NNS");
		morePreviousRBR2.add("NN");
		moreNextRBR2.add("RB");
		//

		// 's(POS)
		sPreviousPOS1.add("NNP");
		sNextPOS1.add("NN");
		sNextPOS1.add("NNP");
		sNextPOS1.add("JJ");
		sNextPOS1.add("NNS");
		sNextPOS1.add("CD");
		sNextPOS1.add("JJS");
		sNextPOS1.add("IN");
		sNextPOS1.add("VBG");

		sPreviousPOS2.add("NN");
		sNextPOS2.add("NN");
		sNextPOS2.add("JJ");
		sNextPOS2.add("NNS");
		sNextPOS2.add("NNP");
		sNextPOS2.add("JJS");
		sNextPOS2.add("CD");
		//

		// 's(VBZ)
		sPreviousVBZ1.add("EX");
		sNextVBZ1.add("DT");
		sNextVBZ1.add("CD");
		sNextVBZ1.add("VBN");

		sPreviousVBZ2.add("PRP");
		sNextVBZ2.add("VBG");
		sNextVBZ2.add("RB");
		sNextVBZ2.add("DT");
		sNextVBZ2.add("IN");
		sNextVBZ2.add("VBN");
		sNextVBZ2.add("JJ");
		//

		// plans(VBZ)
		plansPreviousVBZ1.add("PRP");
		plansPreviousVBZ1.add("NNP");
		plansPreviousVBZ1.add("NN");
		plansPreviousVBZ1.add("WDT");
		plansPreviousVBZ1.add("RB");
		plansNextVBZ1.add("TO");
		//

		// plans(NNS)
		plansPreviousNNS1.add("NN");
		plansPreviousNNS1.add("VBD");
		plansPreviousNNS1.add("VBN");
		plansPreviousNNS1.add("JJ");
		plansNextNNS1.add("IN");

		plansPreviousNNS2.add("IN");
		plansPreviousNNS2.add("VB");
		plansPreviousNNS2.add("VBN");
		plansPreviousNNS2.add("CC");
		plansPreviousNNS2.add("DT");
		plansPreviousNNS2.add("VBD");
		plansPreviousNNS2.add("VBZ");
		plansPreviousNNS2.add("NNP");
		plansNextNNS2.add("TO");
		//
	}

	public TreeSet<WordTagsCount> getWords() {
		return words;
	}

	public void setWords(TreeSet<WordTagsCount> words) {
		this.words = words;
	}

	public void addWord(String word, String tag) {
		WordTagsCount wordTag = new WordTagsCount(word, tag);

		for (Iterator<WordTagsCount> treeIterator = words.iterator(); treeIterator.hasNext();) {
			WordTagsCount wordTagsCount = (WordTagsCount) treeIterator.next();
			if (wordTagsCount.equals(wordTag)) {
				wordTag.setWordCounter(wordTagsCount.getWordCounter() + 1);
				wordTagsCount.addTag(tag);
				wordTag.setTags(wordTagsCount.getTags());
				treeIterator.remove();
				break;
			}
		}
		words.add(wordTag);
	}

	public String getCorrectTag(String word) {
		for (Iterator<WordTagsCount> treeIterator = words.iterator(); treeIterator.hasNext();) {
			WordTagsCount wordTagsCount = (WordTagsCount) treeIterator.next();
			if (wordTagsCount.equals(new WordTagsCount(word))) {
				return wordTagsCount.getCorrectTag();
			}
		}
		return "";
	}

	public String getCorrectTagUsingRules(String word, String previousTag, String nextTag) {
		if (word.equals("that")) {
			if ((thatPreviousIN1.contains(previousTag) && thatNextIN1.contains(nextTag))
					|| (thatPreviousIN2.contains(previousTag) && thatNextIN2.contains(nextTag))) {
				return "IN";
			} else if ((thatPreviousWDT1.contains(previousTag) && thatNextWDT1.contains(nextTag))
					|| (thatPreviousWDT2.contains(previousTag) && thatNextWDT2.contains(nextTag))) {
				return "WDT";
			} else if (thatPreviousDT1.contains(previousTag) && thatNextDT1.contains(nextTag)) {
				return "DT";
			}
		} else if (word.equals("have")) {
			if ((havePreviousVBP1.contains(previousTag) && haveNextVBP1.contains(nextTag))
					|| (havePreviousVBP2.contains(previousTag) && haveNextVBP2.contains(nextTag))) {
				return "VBP";
			} else if ((havePreviousVB1.contains(previousTag) && haveNextVB1.contains(nextTag))
					|| (havePreviousVB2.contains(previousTag) && haveNextVB2.contains(nextTag))) {
				return "VB";
			}
		} else if (word.equals("more")) {
			if ((morePreviousJJR1.contains(previousTag) && moreNextJJR1.contains(nextTag))
					|| (morePreviousJJR2.contains(previousTag) && moreNextJJR2.contains(nextTag)
							|| (morePreviousJJR3.contains(previousTag) && moreNextJJR3.contains(nextTag)))) {
				return "JJR";
			} else if ((morePreviousRBR1.contains(previousTag) && moreNextRBR1.contains(nextTag))
					|| (morePreviousRBR2.contains(previousTag) && moreNextRBR2.contains(nextTag))) {
				return "RBR";
			}
		} else if (word.equals("'s")) {
			if ((sPreviousPOS1.contains(previousTag) && sNextPOS1.contains(nextTag))
					|| (sPreviousPOS2.contains(previousTag) && sNextPOS2.contains(nextTag))) {
				return "POS";
			} else if ((sPreviousVBZ1.contains(previousTag) && sNextVBZ1.contains(nextTag))
					|| (sPreviousVBZ2.contains(previousTag) && sNextVBZ2.contains(nextTag))) {
				return "VBZ";
			}
		} else if (word.equals("plans")) {
			if (plansPreviousVBZ1.contains(previousTag) && plansNextVBZ1.contains(nextTag)) {
				return "VBZ";
			} else if ((plansPreviousNNS1.contains(previousTag) && plansNextNNS1.contains(nextTag))
					|| (plansPreviousNNS2.contains(previousTag) && plansNextNNS2.contains(nextTag))) {
				return "NNS";
			}
		}

		return "";
	}

	public void incrementErrorCounter(String word) {
		WordTagsCount wordTag = new WordTagsCount(word);
		for (Iterator<WordTagsCount> treeIterator = words.iterator(); treeIterator.hasNext();) {
			WordTagsCount wordTagsCount = (WordTagsCount) treeIterator.next();
			if (wordTagsCount.equals(new WordTagsCount(word))) {
				wordTag.setErrorCounter(wordTagsCount.getErrorCounter() + 1);
				wordTag.setWordCounter(wordTagsCount.getWordCounter());
				wordTag.setTags(wordTagsCount.getTags());
				treeIterator.remove();
				break;
			}
		}
		words.add(wordTag);
	}

	public int getErrorCount() {
		int count = 0;
		for (WordTagsCount wordTagsCount : words) {
			count += wordTagsCount.getErrorCounter();
		}
		return count;
	}

	public int getWordCount() {
		int count = 0;
		for (WordTagsCount wordTagsCount : words) {
			count += wordTagsCount.getWordCounter();
		}
		return count;
	}

	public TreeSet<WordTagsCount> getTopErroneousWords(int top) {
		TreeSet<WordTagsCount> wordErrors = new TreeSet<WordTagsCount>(new ErrorComparator());
		int size = (top < words.size() ? top : words.size());
		for (WordTagsCount wordError : words) {
			if (size == 0)
				break;
			wordErrors.add(wordError);
			size--;
		}

		return wordErrors;
	}

	public void updateWordContext(String word, String tag, String previousTag, String nextTag) {
		WordTagsCount wordTag = new WordTagsCount(word, tag);

		for (Iterator<WordTagsCount> treeIterator = words.iterator(); treeIterator.hasNext();) {
			WordTagsCount wordTagsCount = (WordTagsCount) treeIterator.next();
			if (wordTagsCount.equals(wordTag)) {
				wordTagsCount.updateTagContext(tag, previousTag, nextTag);
				break;
			}
		}
	}

}
