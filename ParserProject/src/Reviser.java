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
		System.out.println("**************START**************");
		for(int i = 0; i < output.size(); i++) {
			System.out.println(output.get(i));
		}
		ArrayList<String> temp = new ArrayList<String>();
		int newsize = 0;
		int currentsize = output.size();
		while(newsize < currentsize) {
			for(int i = 0; i < output.size(); i++) {
				String line1 = output.get(i);
				String line2 = "junk";
				if(i < output.size() - 1) {
					line2 = output.get(i+1);
				}
				String[] line1s = line1.split(" ");
				String[] line2s = line2.split(" ");
				if(line1s[0].equalsIgnoreCase(line2s[0])) {
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
			currentsize = output.size();

			output.clear();
			for(int i = 0; i < temp.size(); i++) {
				output.add(temp.get(i).trim());
			}
			temp.clear();
		}
		/*while(i < output.size()) {
			String current_line = output.get(i);
			System.out.println(current_line);
			String[] tokens = current_line.split(" ");
			for(int j = 0; j < tokens.length; j++) {
				if(j == 0) {
					System.out.println(tokens[j]);
				} else {
					System.out.println("\t" + tokens[j]);
				}
			}
			i++;
		}*/
		System.out.println("**************FIXED**************");
		for(int i = 0; i < output.size(); i++) {
			System.out.println(output.get(i));
		}
		return output;
	}
	
	public static void main(String[] args) {
		File grammar = new File("Grammar.txt");
		Reviser test = new Reviser(grammar);
		test.execute();
	}
}
