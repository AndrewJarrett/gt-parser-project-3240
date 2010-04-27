/*
 * Implementation of a Follow Set.
 * 
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


public class FirstSet {
	private ArrayList<String> labels;
	private ArrayList<String> terminals;
	
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
	
	/*
	 * Adds the terminal to the label (if it does not already exist)
	 * If the terminal already exists in the labels set, return 0 for
	 * no change. Else, add the terminal and return 1 for a change.
	 */
	public int addTerminal(String label, String terminal) {
		for(int i = 0; i < labels.size(); i++) {
			if(labels.get(i).equals(label)) {
				if(!terminals.get(i).contains(terminal)) {
					terminals.set(i, terminals.get(i).concat(" " + terminal));
					return 1;
				}
				return 0;
			}
		}
		// If the label is not in the set already, add it to the set and add the new terminal such that First(label) = {terminal}
		labels.add(label);
		terminals.add(terminal);
		return 1;
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
		return null;
	}
	
	public ArrayList<String> getLabels() {
		return labels;
	}
	
	public ArrayList<String> getTerminals() {
		return terminals;
	}
	
	public String toString() {
		String ret = "";
		for(int i = 0; i < labels.size(); i++)
			ret += "First(" + labels.get(i) + ") = {" + terminals.get(i) + "}\n";
		return ret;
	}
}
