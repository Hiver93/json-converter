package json_converter.tokenizer.factory;

import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.JsonTokenizerList;
import json_converter.tokenizer.JsonTokenizerObject;
import json_converter.tokenizer.JsonTokenizerPrimitive;
import json_converter.tokenizer.JsonTokenizerString;
import json_converter.tokenizer.Parentheses;

public class JsonTokenizerFactory {
	static public JsonTokenizer jsonTokenizer(String jsonStr) {
		JsonTokenizer jsonTokenizer;
		jsonStr = jsonStr.trim();
		Parentheses path = null;
		if(0 < jsonStr.length())
			path = Parentheses.getParenthesesByOpening(jsonStr.charAt(0));
		if(path == Parentheses.BRACES) {
			jsonTokenizer = new JsonTokenizerObject(jsonStr);
		}
		else if(path == Parentheses.BRAKETS) {
			jsonTokenizer = new JsonTokenizerList(jsonStr);
		}
		else if(path == Parentheses.QUOTES) {
			jsonTokenizer = new JsonTokenizerString(jsonStr);
		}
		else {
			jsonTokenizer = new JsonTokenizerPrimitive(jsonStr);
		}
		return jsonTokenizer;
	}
}
