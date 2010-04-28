import java.util.ArrayList;
import java.util.StringTokenizer;

public class Table {
	
	public ArrayList<String> terminals;
	public ArrayList<String> nonterminals;
	public Grammar[][] entrees;
	
	public Table(ArrayList<Grammar> grammar, ArrayList<FirstSet> firstSets, ArrayList<FollowSet> followSets) {
		
	}
	
	public Table(ArrayList<Grammar> grammar, FirstSet first, FollowSet follow) {
		terminals = new ArrayList<String>();
		nonterminals = new ArrayList<String>();
		// Create the set of terminals and nonterminal symbols
		for(int i = 0; i < grammar.size(); i++) {
			StringTokenizer token = new StringTokenizer(grammar.get(i).getRightSide());
			while(token.hasMoreTokens()) {
				String next = token.nextToken();
				if(Grammar.findOccurance(grammar, next) != null) {
					for(int k = 0; k < nonterminals.size(); k++)
						if(nonterminals.get(k).equals(next))
							nonterminals.remove(k);
					nonterminals.add(next);
				}
				else {
					if(!next.equals("e"))
						terminals.add(next);
				}
			}
		}
		// Add the $ symbol to the terminals
		terminals.add("$");
		
		entrees = new Grammar[terminals.size()][nonterminals.size()];
		
		// Create the parser table
		for(int i = 0; i < first.getTerminals().size(); i++) {
			StringTokenizer token = new StringTokenizer(first.getTerminals().get(i));
			while(token.hasMoreTokens()) {
				String next = token.nextToken();
				Grammar a = Grammar.findOccurance(grammar, first.getLabels().get(i), first, next);
				if(a != null)
					setEntree(next, first.getLabels().get(i), a);
			}
			if(first.containsE(i)) {							// There is a bug here somewhere that does not let the e symbol be placed in the parser table
				StringTokenizer tok = new StringTokenizer(follow.findFollow(first.getLabels().get(i)));
				while(tok.hasMoreTokens()) {
					String str = tok.nextToken();
					Grammar a = Grammar.findOccurance(grammar, first.getLabels().get(i), first, str);
					if(a != null)
						setEntree(str, first.getLabels().get(i), a);
				}
			}
		}
	}
	
	public void setEntree(String terminal, String nonterminal, Grammar value) {
		int i, j;
		for(i = 0; i < terminals.size(); i++) {
			if(terminal.equals(terminals.get(i)))
				break;
			for(j = 0; j < nonterminals.size(); j++) {
				if(nonterminal.equals(nonterminals.get(j)))
					break;
				if(i < terminals.size() && j < nonterminals.size())
					entrees[i][j] = value;
			}
		}
	}
	
	public String toString() {
		String ret = "\t\t";
		for(int i = 0; i < terminals.size(); i++)
			ret += terminals.get(i) + "\t\t";
		ret += "\n";
		for(int i = 0; i < nonterminals.size(); i++) {
			ret += nonterminals.get(i) + "\t\t";
			for(int j = 0; j < terminals.size(); j++)
				ret += entrees[j][i] + "\t\t";
			ret += "\n";
		}
		return ret;
	}
}
