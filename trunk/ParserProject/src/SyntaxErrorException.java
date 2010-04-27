// This class is a custom checked exception class for syntax errors.

public class SyntaxErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;
	public String token;

	public SyntaxErrorException() {
		super();
	}
	
	public SyntaxErrorException(String msg) {
		super(msg);
		token = msg;
	}
	
	public SyntaxErrorException(String msg, Throwable t) {
		super(msg, t);
	}
}
