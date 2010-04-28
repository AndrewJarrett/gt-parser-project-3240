/**
 * Implementation of the FirstSet(A), where A is a non-terminal (and is called the label). 
 * The FirstSet class also has an ArrayList for the terminals that belong in this 
 * FirstSet(A). 
 * 
 */

import java.util.ArrayList;
import java.util.StringTokenizer;

public class FirstSet {
	private ArrayList<String> labels;
	private ArrayList<String> terminals;
	private String label;
	
	/**
	 * Construct for a FirstSet(A), where A is the label.
	 * 
	 * @param label	Non-terminal for which this FirstSet is created
	 */
	public FirstSet(String label) {
	  this.label = label;
	}
	
	/*
	 * Initializes the First Set for each nonterminals (labels).
	 */
	public FirstSet(ArrayList<Grammar> grammars) {
		labels = new ArrayList<String>();
		terminals = new ArrayList<String>();
		
		for(int i = 0; i < grammars.size(); i++) {
			labels.add(grammars.get(i).getLeftSide());
			terminals.add("");
		}
	}
	
	private static boolean contains(String grammar, String str) {
		StringTokenizer st = new StringTokenizer(grammar);
		while(st.hasMoreTokens()) {
			String next = st.nextToken();
			if(next.equals(str))
				return true;
		}
		return false;
	}
	
	/*
	 * Adds the terminal to the label (if it does not already exist)
	 * If the terminal already exists in the labels set, return 0 for
	 * no change. Else, add the terminal and return 1 for a change.
	 */
	public int addTerminal(String label, String terminal) {
		for(int i = 0; i < labels.size(); i++) {
			if(labels.get(i).equals(label)) {
				if(!contains(terminals.get(i),terminal)) {
					terminals.set(i, terminals.get(i).concat(" " + terminal).trim());
					return 1;
				}
				return 0;
			}
		}
		return 0;
	}
	
	/*
	 * Adds each terminal into the labels First set. Returns
	 * 0 for no change or 1 for change.
	 */
	public int addTerminals(String label, String terminals) {
		StringTokenizer token = new StringTokenizer(terminals);
		int ret = 0;
		while(token.hasMoreTokens())
			ret += addTerminal(label, token.nextToken());
		return ret;
	}
	
	public String getFirst(int index) {
		return labels.get(index) + " " + terminals.get(index);
	}
	
	public String findFirst(String label) {
		for(int i = 0; i < labels.size(); i++)
			if(labels.get(i).equals(label))
				return terminals.get(i);
		return "";
	}
	
	public ArrayList<String> getLabels() {
		return labels;
	}
		
	public boolean containsE(int i) {
		if(i >= terminals.size())
			return false;
		StringTokenizer st = new StringTokenizer(terminals.get(i));
		while(st.hasMoreTokens()) {
			String next = st.nextToken();
			if(next.equals("e"))
				return true;
		}
		return false;
	}
	
	/**
	 * Sets the label for this FirstSet (which is the non-terminal that it is built upon).
	 * 
	 * @param label	The non-terminal that this FirstSet is built upon.
	 */
	public void setLabel(String label) {
		this.label = label;
	}
	
	/**
	 * Sets the list of terminals associated with this FirstSet.
	 * 
	 * @param terminals	An ArrayList of the terminals associated with this FirstSet.
	 */
	public void setTerminals(ArrayList<String> terminals) {
		this.terminals = terminals;
	}
	
	/**
	 * Gets the label for this FirstSet.
	 * 
	 * @return	A String representation of the non-terminal (the label).
	 */
	public String getLabel() {
		return label;
	}
	
	/**
	 * Gets the terminals that are associated with this FirstSet.
	 * 
	 * @return	An ArrayList of the terminals associated with this FirstSet.
	 */
	public ArrayList<String> getTerminals() {
		return terminals;
	}
	
	/**
	 * Creates a String representation of the entire FirstSet in case it needs to be printed.
	 * 
	 * @return	A String representation of the FirstSet.
	 */
	public String toString() {
		String ret = "";
		for(int i = 0; i < labels.size(); i++)
			ret += "First(" + labels.get(i) + ") = {" + terminals.get(i) + "}\n";
		return ret;
	}
}
