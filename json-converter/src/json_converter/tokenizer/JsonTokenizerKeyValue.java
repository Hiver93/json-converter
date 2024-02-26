package json_converter.tokenizer;

public class JsonTokenizerKeyValue implements JsonTokenizer {
	private int idx = 0;
	private String jsonStr;
	private boolean hasMore = true;
	
	public JsonTokenizerKeyValue() {}
	public JsonTokenizerKeyValue(String jsonStr) {
		this.jsonStr = jsonStr;
		idx = 1;
	}
	
	public void setJsonStr(String jsonStr) {
		this.jsonStr = jsonStr;
		idx = 1;
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
	
	private String getSringToken() {
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
	
	private String getToken() {
		String token = null;
		char opening = jsonStr.charAt(idx);
		if(opening == '{') {
			token = getObjectToken();
		}
		else if(opening == '[') {
			
		}
		else if(opening == '\"') {
			token = getSringToken();
		}
		else {
			
		}
		return token;
	}
	
	public boolean hasMoreTokens() {
		return hasMore;
	}
	
	@Override
	public String next() {
		setNextIdx();
		String token = getToken(); 
		return token;
	}
}
