/*
 * Implementation of a Follow Set.
 * 
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


public class FollowSet {
	private ArrayList<String> labels;
	private ArrayList<String> terminals;
	
	/*
	 * Initializes the First Set for each nonterminals (labels).
	 */
	public FollowSet(ArrayList<Grammar> grammars) {
		labels = new ArrayList<String>();
		terminals = new ArrayList<String>();
		
		for(int i = 0; i < grammars.size(); i++) {
			if(findFollow(grammars.get(i).getLeftSide()) == null) {
				labels.add(grammars.get(i).getLeftSide());
				terminals.add("");
			}
		}
		terminals.set(0, "$");
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
		if(terminal.equals("e"))
			return 0;
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
	 * Adds each terminal into the labels Follow set. Returns
	 * 0 for no change or 1 for change.
	 */
	public int addTerminals(String label, String terminals) {
		StringTokenizer token = new StringTokenizer(terminals);
		int ret = 0;
		while(token.hasMoreTokens())
			ret += addTerminal(label, token.nextToken());
		return ret;
	}
	
	public String getFollow(int index) {
		return labels.get(index) + " " + terminals.get(index);
	}
	
	public String findFollow(String label) {
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
			ret += "Follow(" + labels.get(i) + ") = {" + terminals.get(i) + "}\n";
		return ret;
	}
}
