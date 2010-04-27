import java.io.*;
import java.util.*;

public class ParserDriver {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("Please provide an input program!");
			return;
		}
		else {
			try {
				// Create a new tokenizer for the input program.
				Tokenizer tokenizer = new Tokenizer(args[0].toString());
				
				// Tokenize the input program and write out the tokenized file.
				ArrayList<String> tokenList = tokenizer.tokenize();
				System.out.println("Successfully tokenized the source code file.");
				System.out.println("Attempting to parse the grammar...");
				
				// Try to pull in the grammar file
				try {
					Reviser reviser = new Reviser(new File(args[1].toString()));
					reviser.execute();
					System.out.println("Revised the grammar!");
					
					ParserTable parserTable = new ParserTable(reviser.convertToGrammar());
					Table table = parserTable.createTable();
					System.out.println("Successfully created the parsing table.");
					System.out.println("Now parsing...");
					
					try {
						parse(table, tokenList);
						System.out.println("Successful parse!");
					}
					catch (SemanticErrorException e) {
						System.out.println("Unsuccessful parse!");
						System.out.println("Dumping parsing stack:");
						System.out.println(e.stack);
					}
				}
				catch (ArrayIndexOutOfBoundsException e) {
					System.out.println("Please provide an input grammar!");
					return;
				}
			}
			catch (IOException e) {
				// Couldn't find the input program
				System.out.println("The input program could not be found. Please check the filename.");
				return;
			}
			catch (SyntaxErrorException e) {
				// There was an error in tokenizing the input
				System.out.println("There was a syntax error in the input program.");
				System.out.println("Error occured in reading token '" + e.token + "'.");
				return;
			}
		}
		
	}
	
	private static void parse(Table table, ArrayList<String> tokenList) throws SemanticErrorException {
		
	}
}
