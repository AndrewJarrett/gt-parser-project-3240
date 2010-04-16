// This class is a custom checked exception class for syntax errors.

public class SyntaxErrorException extends Exception {
	
	private static final long serialVersionUID = 1L;

	public SyntaxErrorException() {
		super();
	}
	
	public SyntaxErrorException(String msg) {
		super(msg);
	}
	
	public SyntaxErrorException(String msg, Throwable t) {
		super(msg, t);
	}
}
