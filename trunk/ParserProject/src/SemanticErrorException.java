
public class SemanticErrorException extends Exception {

	private static final long serialVersionUID = 1L;
	public String stack;

	public SemanticErrorException() {
		super();
	}

	public SemanticErrorException(String msg) {
		super(msg);
		stack = msg;
	}

	public SemanticErrorException(Throwable t) {
		super(t);
	}

	public SemanticErrorException(String msg, Throwable t) {
		super(msg, t);
	}

}
