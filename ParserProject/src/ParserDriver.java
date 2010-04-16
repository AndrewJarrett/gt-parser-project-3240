import java.io.*;

public class ParserDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0)
			System.out.println("Please provide an input program!");
		else {
			try {
				// Create a new tokenizer for the input program.
				Tokenizer tokenizer = new Tokenizer(args[0].toString());
				
				// Tokenize the input program and write out the tokenized file.
				tokenizer.tokenize();
			}
			catch (IOException e) {
				// Couldn't find the input program
				System.out.println("The input program could not be found. Please check the filename.");
				return;
			}
			catch (SyntaxErrorException e) {
				// There was an error in tokenizing the input
				System.out.println("There was a syntax error in the input program.");
				return;
			}
		}
		
	}
	
}
