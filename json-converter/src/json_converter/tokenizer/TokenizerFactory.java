package json_converter.tokenizer;

public class TokenizerFactory {
	
	static private char findOpening(String jsonStr) {
		int idx = 0;
		while(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n') {
			idx++;
		}
		return jsonStr.charAt(idx);
	}
	
	static public JsonTokenizer getJsonTokenizer(String jsonStr) {
		JsonTokenizer jsonTokenizer = null;
		char opening = findOpening(jsonStr);
		if(opening == '{') {
			jsonTokenizer = new JsonTokenizerKeyValue(jsonStr);
		}
		else if(opening == '[') {
			
		}
		else {
			
		}
		return jsonTokenizer;
	}
}
