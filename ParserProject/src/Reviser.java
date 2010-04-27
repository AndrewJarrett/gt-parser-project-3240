import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;

public class Reviser {
	
	private File grammar;
	private Scanner scan;
	private ArrayList<String> output;
	
	public Reviser(File in) {
		grammar = in;
		try {
			scan = new Scanner(grammar);
		} catch(Exception FileNotFoundException) {
			System.out.println("File doesn't exist");
			return;
		}
	}
	
	public ArrayList<String> execute() {
		output = new ArrayList<String>();
		while(scan.hasNext()) {
			output.add(scan.nextLine());
		}
		return output;
	}
}
