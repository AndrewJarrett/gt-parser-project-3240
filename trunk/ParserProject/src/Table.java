import java.util.ArrayList;
import java.util.StringTokenizer;

public class Table {
	
	private ArrayList<String> terminals;
	private ArrayList<String> nonterminals;
	private String[] entrees;
	
	public Table(ArrayList<Grammar> grammar, FirstSet first, FollowSet follow) {
		terminals = new ArrayList<String>();
		nonterminals = new ArrayList<String>();
		
		for(int i = 0; i < grammar.size(); i++)
			nonterminals.add(grammar.get(i).getLeftSide());
		
		for(int i = 0; i < grammar.size(); i++) {
			StringTokenizer token = new StringTokenizer(grammar.get(i).getRightSide());
			while(token.hasMoreTokens()) {
				String next = token.nextToken();
				if(Grammar.findOccurance(grammar, next) == null)
					nonterminals.add(next);
			}
		}
		
		entrees = new String[terminals.size() * nonterminals.size()];
		
	}
}
