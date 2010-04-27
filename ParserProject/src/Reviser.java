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
		int i = 0;
		while(i < output.size()) {
			String current_line = output.get(i);
			String[] tokens = current_line.split(" ");
			System.out.println(tokens[0]);
		}
		return output;
	}
	
	public void main(String[] args) {
		File grammar = new File("Grammar.txt");
		Reviser test = new Reviser(grammar);
		test.execute();
	}
}
