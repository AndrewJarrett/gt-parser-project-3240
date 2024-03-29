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
		ArrayList<Grammar> g = new ArrayList<Grammar>();										// Create a new list to hold the expanded grammar rules
		for(int i = 0; i < grammars.size(); i++) {
			StringTokenizer token = new StringTokenizer(grammars.get(i).getRightSide(), "|");	// Split the grammar into tokens based on | (OR)
			String leftSide = grammars.get(i).getLeftSide();									// Get the Left side of the grammar (the label)
			while(token.hasMoreTokens()) {														// Add each expanded grammar to the list
				g.add(i, new Grammar(leftSide,token.nextToken().trim()));
			}
		}
		grammars = g;
	}
	
	/*
	 * Creates the First set for the grammar.
	 */
	public ArrayList<FirstSet> createFirst() {
		ArrayList<FirstSet> firstSets = new ArrayList<FirstSet>(); // We are keeping a list of the First sets of *all* of the Nonterminals
		FirstSet first = new FirstSet(grammars);				// Initialize the First set so that all Nonterminals are of the form: First(S) = {}
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
		return firstSets;
	}
	
	/*
	 * Creates the Follow set for the grammar.
	 * You must run createFirst on the grammar in order to get the
	 * first set to use for the Follow set.
	 */
	public ArrayList<FollowSet> createFollow(ArrayList<FirstSet> firstSets) {
		FirstSet first = firstSets.get(0);
		ArrayList<FollowSet> followSets = new ArrayList<FollowSet>();
		FollowSet follow = new FollowSet(grammars);				// Initialize the Follow set so that all Nonterminals are of the form: First(X) = {} and the start symbol to First(S) = {$}
		int cont;												// Holds whether a change took place
		
		do {
			cont = 0;
			for(int i = 0; i < grammars.size(); i++) {
				String next = "";
				String last = "";
				Grammar current = grammars.get(i);				// Get the ith grammar
				StringTokenizer token = new StringTokenizer(current.getRightSide());
				while(token.hasMoreTokens()) {
					last = new String(next);					// Keep the last token
					next = token.nextToken();					// Get the next token
					if(!last.isEmpty()) {
						if(Grammar.findOccurance(grammars, next) == null)	// If the next symbol is a terminal, add the symbol to the Follow set of the previous symbol
							cont += follow.addTerminal(last, next);
						else
							cont += follow.addTerminals(last, first.findFirst(next));	// Add the First set of the last symbol to the Follow set of the 2nd to last symbol (Rule 2)
					}
				}
				if(first.containsE(i))
					cont += follow.addTerminals(last, follow.findFollow(current.getLeftSide()));
				cont += follow.addTerminals(next, follow.findFollow(current.getLeftSide()));
			}

		} while(cont != 0);										// If nothing was update, do now continue and return the First set.
		return followSets;
	}
	
	/*
	 * Creates the LL(1) parsing table.
	 */
	public Table createTable() {
		if(grammars.isEmpty())
			return null;
		
		ArrayList<FirstSet> firstSets = createFirst();
		ArrayList<FollowSet> followSets = createFollow(firstSets);
		//System.out.println(first);
		//System.out.println(follow);
		Table t = new Table(grammars, firstSets, followSets);
		return t;
	}
	
	/*
	 * The following are some test cases to test the ParserTable class.
	 */
	
	/*
	 * This test tests the expansion method.
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
	
	/*
	 * This tests the first and forward creation without e-productions
	 */
	public static void testFirstForward() {
		ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("exp", "exp addop term | term"));			// This is an example from the book (Louden page 170) 
		glist.add(new Grammar("addop", "+ | -"));
		glist.add(new Grammar("term", "term mulop factor | factor"));
		glist.add(new Grammar("mulop", "*"));
		glist.add(new Grammar("factor", "( exp ) | number"));
		ParserTable p = new ParserTable(glist);
		ArrayList<FirstSet> f = p.createFirst();
		System.out.println(f);
		ArrayList<FollowSet> fol = p.createFollow(f);
		System.out.println(fol);
		
	}
	
	/*
	 * This tests the first and forward creation with e-productions
	 */
	public static void testEmpty() {
		ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("statement", "if-stmt | other"));			// This is an example from the book (Louden page 177) 
		glist.add(new Grammar("if-stmt", "if ( exp ) statement else-part"));
		glist.add(new Grammar("else-part", "else statement | e"));
		glist.add(new Grammar("exp", "0 | 1"));
		ParserTable p = new ParserTable(glist);
		ArrayList<FirstSet> f = p.createFirst();
		System.out.println(f);
		ArrayList<FollowSet> fol = p.createFollow(f);
		System.out.println(fol);
		
	}
	
	public static void testTable() {
		ArrayList<Grammar> glist = new ArrayList<Grammar>();
		glist.add(new Grammar("statement", "if-stmt | other"));			// This is an example from the book (Louden page 177) 
		glist.add(new Grammar("if-stmt", "if ( exp ) statement else-part"));
		glist.add(new Grammar("else-part", "else statement | e"));
		glist.add(new Grammar("exp", "0 | 1"));
		ParserTable p = new ParserTable(glist);
		Table t = p.createTable();
		System.out.println(t);
	}
	
	public static void main(String[] args) {
		//testExpand();						// Uncomment to test cases above
		//testFirstForward();
		//testEmpty();
		testTable();
	}
}
