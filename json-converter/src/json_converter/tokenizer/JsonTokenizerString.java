package json_converter.tokenizer;

public class JsonTokenizerString {
	private int idx = 0;
	private String jsonStr;
	
	public JsonTokenizerString() {}
	public JsonTokenizerString(String jsonStr) {
		this.jsonStr = jsonStr;
		init();
	}
	
	private void setIdxToBeginning() {
		while(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r' ) {
			idx++;
		}
	}
	
	private void setIdxNext() {
		while(idx < jsonStr.length() && (jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r')) {
			idx++;
		}
	}
	
	private void init() {
		setIdxToBeginning();
		setIdxNext();		
	}
	
	private String getToken() {
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
	
	public boolean hasMoreTokens() {
		return idx < jsonStr.length();
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
