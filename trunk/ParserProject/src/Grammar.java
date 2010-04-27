/*
 * Implementation of a grammar.
 * The grammar will be in the following format:
 * A -> a | b
 * where A is the leftside and a | b is the rightside
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


public class Grammar {
	private String leftside;
	private String rightside;
	
	public Grammar() {
		leftside = "";
		rightside = "";
	}
	
	public Grammar(String ls, String rs) {
		leftside = ls;
		rightside = rs;
	}
	
	public boolean leftEquals(String grammar) {
		return leftside.equals(grammar);
	}
	
	public String getLeftSide() {
		return leftside;
	}
	
	public String getRightSide() {
		return rightside;
	}
	
	/*
	 * Find an occurance of the nonterminal in the grammar list.
	 * For example, trying to find the label A in:
	 * [A -> a | b; B -> c; D -> d] will return A -> a
	 */
	public static Grammar findOccurance(ArrayList<Grammar> grammarList, String grammar) {
		if(grammarList == null || grammar == null)
			return null;
		for(int i = 0; i < grammarList.size(); i++) {
			if(grammarList.get(i).leftEquals(grammar))
				return grammarList.get(i);
		}
		return null;
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
	
	public static Grammar findOccurance(ArrayList<Grammar> grammarList, String grammar, FirstSet first, String token) {
		if(grammarList == null || grammar == null)
			return null;
		for(int i = 0; i < grammarList.size(); i++) {
			if(grammarList.get(i).leftEquals(grammar)) {
				if(contains(grammarList.get(i).getRightSide(), token))
					return grammarList.get(i);
				else {
					StringTokenizer st = new StringTokenizer(grammarList.get(i).getRightSide());
					if(st.hasMoreTokens() && contains(first.findFirst(st.nextToken()), token))
						return grammarList.get(i);
				}
			}
		}
		return null;
	}
	
	public String toString() {
		return leftside + " -> " + rightside;
	}
}
