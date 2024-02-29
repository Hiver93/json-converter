package json_converter.tokenizer;

public enum Parentheses {
	BRACES('{','}'), BRAKETS('[',']'), QUOTES('\"','\"');
	private final char opening;
	private final char closing;
	private static final Parentheses[] PATH_ARR = Parentheses.values();
	
	Parentheses(char opening, char closing){
		this.opening = opening;
		this.closing = closing;
	}
	
	public char getClosing() {
		return this.closing;
	}
	
	public char getOpening() {
		return this.opening;
	}
	
	static public Parentheses getParenthesesByOpening(char opening) {
		for(Parentheses path : PATH_ARR) {
			if(path.getOpening() == opening) {
				return path;
			}
		}
		return null;
	}
}
