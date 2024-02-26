package json_converter.tokenizer;

public class JsonTokenizerKeyValue implements JsonTokenizer {
	private int idx = 0;
	private String jsonStr;
	private boolean hasMore = true;
	
	public JsonTokenizerKeyValue() {}
	public JsonTokenizerKeyValue(String jsonStr) {
		this.jsonStr = jsonStr;
		idx = 1;
		setNextIdx();
	}
	
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
		idx = 1;
		setNextIdx();
	}
	
	private void setNextIdx() {
		while(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':') {
			idx++;
		}
		if(jsonStr.charAt(idx) == '}') {
			hasMore = false;
		}
	}
	
	private String getObjectToken() {
		StringBuilder sb = new StringBuilder();
		int cnt = 1;
		char c = jsonStr.charAt(idx);
		sb.append(c);
		idx++;
		while(0 < cnt) {
			c = jsonStr.charAt(idx); 
			if(c == '{') {
				cnt++;
			}
			if(c == '}') {
				cnt--;
			}
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
		}
		return sb.toString();
	}
	
	private String getStringToken() {
		StringBuilder sb = new StringBuilder();
		char c = jsonStr.charAt(idx);
		sb.append(c);
		idx++;
		while(true) {
			c = jsonStr.charAt(idx); 
			if(c == '\"') {
				sb.append(c);
				idx++;
				break;
			}
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
		}
		return sb.toString();
	}

	private String getListToken() {
		StringBuilder sb = new StringBuilder();
		int cnt = 1;
		char c = jsonStr.charAt(idx);
		sb.append(c);
		idx++;
		while(0 < cnt) {
			c = jsonStr.charAt(idx); 
			if(c == '[') {
				cnt++;
			}
			if(c == ']') {
				cnt--;
			}
			if(c == '\\') {
				sb.append(c);
				c = jsonStr.charAt(++idx);
			}
			sb.append(c);
			idx++;
		}
		return sb.toString();
	}
	
	private String getPrimitiveToken() {
		StringBuilder sb = new StringBuilder();
		char c = jsonStr.charAt(idx);
		while(!(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':')) {
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
	
	private String getToken() {
		String token = null;	
		
		char opening = jsonStr.charAt(idx);
		if(opening == '{') {
			token = getObjectToken();
		}
		else if(opening == '[') {
			token = getListToken();
		}
		else if(opening == '\"') {
			token = getStringToken();
		}
		else {
			token = getPrimitiveToken();
		}
		setNextIdx();
		return token;
	}
	
	public boolean hasMoreTokens() {
		return hasMore;
	}
	
	@Override
	public String next() {
		if(!hasMoreTokens()) {
			return null;
		}
		String token = getToken(); 
		return token;
	}
}
