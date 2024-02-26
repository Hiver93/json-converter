package json_converter.tokenizer;

public class TokenizerFactory {
	static public JsonTokenizer getJsonTokenizer(String jsonStr) {
		JsonTokenizer jsonTokenizer = null;
		char opening = jsonStr.charAt(0);
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
