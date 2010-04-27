import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Reviser {
	
	private File grammar;
	private Scanner scan;
	private ArrayList<String> output;
	
	public Reviser(File in) {
		this.grammar = in;
		this.output = new ArrayList<String>();
		try {
			scan = new Scanner(grammar);
			while(scan.hasNext()) {
				output.add(scan.nextLine());
			}
		} catch(FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public ArrayList<String> execute() {
		output = combine(output);
		output = factor(output);
		write(output, "GrammarRevised.txt");
		return output;
	}
	/**
	 * Combines common rules into one rule with | separation
	 * @param grammar
	 * @return
	 */
	private ArrayList<String> combine(ArrayList<String> input) {
		ArrayList<String> g = new ArrayList<String>();
		for(int i = 0; i < input.size(); i++) {
			g.add(input.get(i));
		}
		System.out.println("**************START**************");
		for(int i = 0; i < g.size(); i++) {
			System.out.println(g.get(i));
		}
		ArrayList<String> temp = new ArrayList<String>();
		int newsize = 0;
		int currentsize = g.size();
		while(newsize < currentsize) {
			for(int i = 0; i < g.size(); i++) {
				String line1 = g.get(i);
				String line2 = "junk";
				if(i < g.size() - 1) {
					line2 = g.get(i+1);
				}
				String[] line1s = line1.split(" ");
				String[] line2s = line2.split(" ");
				if(line1s[0].equalsIgnoreCase(line2s[0]) && !(line1s[0].equalsIgnoreCase("%"))) {
					String line2e = "";
					for(int j = 2; j < line2s.length; j++) {
						line2e += line2s[j] + " ";
					}
					line2e.trim();
					line1 += " | " + line2e;
					i++;
				}
				temp.add(line1);
			}
			newsize = temp.size();
			currentsize = g.size();

			g.clear();
			for(int i = 0; i < temp.size(); i++) {
				g.add(temp.get(i).trim());
			}
			temp.clear();
		}
		System.out.println("**************FIXED**************");
		for(int i = 0; i < g.size(); i++) {
			System.out.println(g.get(i));
		}
		return g;
	}
	
	private ArrayList<String> factor(ArrayList<String> input) {
		ArrayList<String> g = new ArrayList<String>();
		for(int i = 0; i < input.size(); i++) {
			g.add(input.get(i));
		}
		
		String current = "";
		for(int i = 0; i < g.size(); i++) {
			current = g.get(i);
			String[] current_split = current.split(" ");
			String assign = current_split[0];
			for(int j = 1; j < current_split.length; j++) {
				String token = current_split[j];
				if(token.equals(":") || token.equals("|")) { //check for first tokens of assignments
					String token_next = current_split[j+1];
					if(assign.equalsIgnoreCase(token_next)) { //if the first token is the same as the assignment
						
					}
				}
			}
		}
		
		
		return g;
	}
	
	private void write(ArrayList<String> towrite, String name) {
		FileWriter writer;
		try {
			writer = new FileWriter(name);
			String write = "";
			for(int i = 0; i < towrite.size(); i++) {
				write += towrite.get(i) + "\n";
			}
			write.trim();
			writer.write(write);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		File grammar = new File("Grammar.txt");
		Reviser test = new Reviser(grammar);
		test.execute();
	}
}
