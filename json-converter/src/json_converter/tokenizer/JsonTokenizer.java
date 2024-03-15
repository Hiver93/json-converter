package json_converter.tokenizer;

import java.util.List;
import java.util.Map;

import json_converter.enums.Parentheses;

public interface JsonTokenizer {
	public boolean hasMoreTokens();
	public String next();
	static public Class<?> inferClass(String jsonStr){
		Class cl = null;
		jsonStr = jsonStr.trim();
		if(jsonStr.equals("null")) {
			return null;
		}		
		Parentheses path = Parentheses.getParenthesesByOpening(jsonStr.trim().charAt(0));
		if(path == Parentheses.BRACES) {
			cl = Map.class;
		}
		else if(path == Parentheses.BRAKETS) {
			cl = List.class;
		}
		else if(path == Parentheses.QUOTES) {
			cl = String.class;
		}
		else{
			if(jsonStr.equals("true")||jsonStr.equals("false")) {
				cl = Boolean.class;
			}
			else {
				cl = Double.class;
			}
		}
		return cl;
	}
	
	static public Boolean isNullValue(String jsonStr) {
		return jsonStr.trim().equals("null");
	}
}	
