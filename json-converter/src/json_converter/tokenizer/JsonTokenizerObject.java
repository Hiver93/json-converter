package json_converter.tokenizer;

public class JsonTokenizerObject {
	private int idx = 0;
	private String jsonStr;
	private boolean hasMore = true;
	private Parentheses parentheses;
	
	public JsonTokenizerObject() {}
	public JsonTokenizerObject(String jsonStr) {
		this.jsonStr = jsonStr;
		init();
	}
	
	private void setIdxToBeginning() {
		while(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r') {
			idx++;
		}
	}
	
	private void setIdxNext() {
		while(idx < jsonStr.length()&&(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r'|| jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':'||jsonStr.charAt(idx) == this.parentheses.getClosing())) {
			idx++;
		}
		
		if(jsonStr.length() <= idx ) {
			hasMore = false;
		}
	}
	
	private void init() {
		setIdxToBeginning();
		this.parentheses = Parentheses.getParenthesesByOpening(jsonStr.charAt(idx));
		if(parentheses == Parentheses.BRACES || parentheses == Parentheses.BRAKETS) {
			idx++;
		}
		setIdxNext();		
	}
		
	private String getPrimitiveToken() {
		StringBuilder sb = new StringBuilder();
		char c = jsonStr.charAt(idx);
		while(!(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r'|| jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':')) {
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
			c = jsonStr.charAt(idx);
		}
		return sb.toString();
	}
	
	private String getStringToken() {
		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		do {
			char c = jsonStr.charAt(idx);
			if(c == '\"') {
				cnt++;
			}
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
		}while(cnt < 2);
		return sb.toString();
	}
	
	private String getObjectToken(Parentheses parentheses) {
		StringBuilder sb = new StringBuilder();
		int cnt = 0;
		do  {
			char c = jsonStr.charAt(idx);
			if(c == parentheses.getOpening()) {
				cnt++;
			}
			if(c == parentheses.getClosing()) {
				cnt--;
			}
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
		}while(0 < cnt);
		
		return sb.toString();
	}
	
	private String getToken() {
		String token = null;	
		
		char opening = jsonStr.charAt(idx);
		Parentheses parentheses = Parentheses.getParenthesesByOpening(opening);
		
		if(parentheses == Parentheses.QUOTES) {
			token = getStringToken();
		}else if(parentheses == Parentheses.BRACES || parentheses == Parentheses.BRAKETS){
			token = getObjectToken(parentheses);
		}
		else{
			token = getPrimitiveToken();
		}
		return token;
	}
	
	public boolean hasMoreTokens() {
		return hasMore;
	}
	
	public String next() {
		if(!hasMoreTokens()) {
			return null;
		}
		String token = getToken(); 
		setIdxNext();
		return token;
	}
	
}
