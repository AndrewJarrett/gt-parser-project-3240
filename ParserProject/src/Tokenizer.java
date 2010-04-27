import java.util.*;
import java.io.*;

public class Tokenizer {
	
	private Scanner scan;
	private String fileName;
	private FileReader fileHandle;
	private BufferedReader file;
	private String sourceCode;
	private String token;
	private ArrayList<String> tokenList;
	
	public Tokenizer(String fileName) throws IOException {
		this.fileName = fileName;
		
		// Get a string representation of the file and put a 
		// space in front of every semicolon to make it easier to parse.
		fileHandle = new FileReader(fileName);
		file = new BufferedReader(fileHandle);
		String s;
		sourceCode = "";
		while ((s = file.readLine()) != null) {
			sourceCode += " " + s;
		}
		sourceCode = sourceCode.replaceAll(";", " ;");
		file.close();
		fileHandle.close();
		
		// Make the new Scanner object that will read the input program
		scan = new Scanner(sourceCode);
		
		// Make the token list
		tokenList = new ArrayList<String>();
	}

	public ArrayList<String> tokenize() throws SyntaxErrorException {
		// Tokenize the input file into an array of tokens (in the order they were read)
		while (scan.hasNext()) {
			token = scan.next();
			
			// Check for keywords first
			if (token.equalsIgnoreCase("begin")) {
				tokenList.add("BEGIN");
				continue;
			}
			if (token.equalsIgnoreCase("end")) {
				tokenList.add("END");
				continue;
			}
			if (token.equalsIgnoreCase("print")) {
				tokenList.add("PRINT");
				continue;
			}
			if (token.equalsIgnoreCase("read")) {
				tokenList.add("READ");
				continue;
			}
			
			// Check for operators
			if (token.equals("+")) {
				tokenList.add("PLUS");
				continue;
			}
			if (token.equals("-")) {
				tokenList.add("MINUS");
				continue;
			}
			if (token.equals("%")) {
				tokenList.add("MODULO");
				continue;
			}
			if (token.equals("*")) {
				tokenList.add("MULTIPLY");
				continue;
			}
			if (token.equals(":=")) {
				tokenList.add("ASSIGN");
				continue;
			}
			
			// Check for punctuation
			if (token.equals("(")) {
				tokenList.add("LEFTPAR");
				continue;
			}
			if (token.equals(")")) {
				tokenList.add("RIGHTPAR");
				continue;
			}
			if (token.equals(";")) {
				tokenList.add("SEMICOLON");
				continue;
			}
			if (token.equals(",")) {
				tokenList.add("COMMA");
				continue;
			}
			
			// Check for int or id
			if (isInt(token)) {
				tokenList.add("INTNUM");
				continue;
			}
			if (isId(token)) {
				tokenList.add("ID");
				continue;
			}
			
			// If we get to this point, then we have a syntax error!
			throw new SyntaxErrorException(token);
		}
		
		// Write out the tokens into a file
		try {
			writeFile(tokenList);
		}
		catch (IOException e) {
			System.out.println("Error in writing the tokenized output.");
		}
		return tokenList;
	}

	private void writeFile(ArrayList<String> tokenList) throws IOException {
		// This method writes out the tokenized file
		// First, setup the new filename
		if (fileName.contains(".llc"))
			fileName = fileName.replace(".llc", ".tok");
		else
			fileName = fileName + ".tok";
		
		FileWriter fw = new FileWriter(new File(fileName));
		
		while (!tokenList.isEmpty()) {
			fw.append(tokenList.remove(0) + " ");
		}
		
		fw.close();
	}

	private boolean isId(String str) {
		// Check to see if this is classified as an identifier
		if (str.length() >= 1 && str.length() <= 10) {
			if (str.matches("(?:(?:[_]{1}[a-zA-Z]{1}|[_]{1}[0-9]{1})|(?:[a-zA-Z]{1}))(?:[a-zA-Z]|[0-9]|(?:[_]{1}[a-zA-Z]{1}|[_]{1}[0-9]{1}))*")) {
				return true;
			}
		}
		return false;
	}

	private boolean isInt(String str) {
		// Check if this string is an int by catching an exception
		try {
			Integer.parseInt(str);
			return true;
		}
		catch (NumberFormatException e) {
			return false;
		}
	}
	
}
