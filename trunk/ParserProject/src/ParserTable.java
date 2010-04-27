/*
 * Used to create an LL(1) parser table from
 * a particular set of grammar.
 */

import java.util.ArrayList;
import java.util.StringTokenizer;


public class ParserTable {
	private ArrayList<Grammar> grammars;
	
	public ParserTable(ArrayList<Grammar> g) {
		grammars = g;
	}
	
	/*
	 * Takes the grammar and expands it so that grammars in the
	 * form: A-> a b | c become A -> a b and A -> c.
	 */
	private void expandGrammar() {
		for(int i = 0; i < grammars.size(); i++) {
			StringTokenizer token = new StringTokenizer(grammars.get(i).getRightSide(), "|");	// Split the grammar into tokens based on OR
			String leftSide = grammars.get(i).getLeftSide();									// Get the Left side of the grammar (the label)
			grammars.remove(i);																	// Remove the grammar from the list so that we may add the expanded version
			while(token.hasMoreTokens()) {														// Add each expanded grammar to the list
				grammars.add(i, new Grammar(leftSide,token.nextToken()));
			}
		}
	}
	
	/*
	 * Creates the First set for the grammar.
	 */
	public FirstSet createFirst() {
		FirstSet first = new FirstSet(grammars);				// Initilize the First set so that all Nonterminals are of the form: First(S) = {}
		expandGrammar();										// Expand the grammar
		int cont;												// Holds whether a change took place
		
		do {
			cont = 0;
			for(int i = 0; i < grammars.size(); i++) {
				Grammar current = grammars.get(i);				// Get the ith grammar
				StringTokenizer token = new StringTokenizer(current.getRightSide());
				if(token.hasMoreTokens()) {
					String next = token.nextToken();			// Get the first token
					if(Grammar.findOccurance(grammars, next) == null)	// If it is a terminal, add it to the First set for the nonterminal label
						cont += first.addTerminal(current.getLeftSide(), next);
					else												// Else it is a nonterminal. Add the First set of the nonterminal to the current nonterminal's First set.
						cont += first.addTerminals(current.getLeftSide(), first.findFirst(next));
				}
			}

		} while(cont != 0);										// If nothing was update, do now continue and return the First set.
		return first;
	}
	
	/*
	 * Creates the LL(1) parsing table.
	 */
	public ArrayList<String> createTable() {
		if(grammars.isEmpty())
			return null;
		
		FirstSet first = createFirst();
		
		return new ArrayList();
	}
	
	/*
	 * Some test cases to test the ParserTable class.
	 */
	public static void testExpand() {
		ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("A", "a b | c | d"));
		glist.add(new Grammar("B", "a"));
		ParserTable p = new ParserTable(glist);
		System.out.println("Originial Grammar:");
		for(int i = 0; i < glist.size(); i++)
			System.out.println(glist.get(i));
		p.expandGrammar();
		System.out.println("Expanded Grammar:");
		for(int i = 0; i < glist.size(); i++)
			System.out.println(glist.get(i));
	}
	
	public static void testFirst() {
		ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("exp", "exp addop term | term"));			// This is an example from the book (Louden page 170) 
		glist.add(new Grammar("addop", "+ | -"));
		glist.add(new Grammar("term", "term mulop factor | factor"));
		glist.add(new Grammar("mulop", "*"));
		glist.add(new Grammar("factor", "( exp ) | number"));
		ParserTable p = new ParserTable(glist);
		FirstSet f = p.createFirst();
		System.out.println(f);
		
	}
	
	public static void main(String[] args) {
		/*ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("exp", "exp addop term | term"));
		glist.add(new Grammar("addop", "+ | -"));
		glist.add(new Grammar("term", "term mulop factor | factor"));
		glist.add(new Grammar("mulop", "*"));
		glist.add(new Grammar("factor", "( exp ) | number"));
		ParserTable p = new ParserTable(glist);
		p.createTable();*/
		testFirst();
	}
}
