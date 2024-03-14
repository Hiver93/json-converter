package json_converter.tokenizer.impl;

import json_converter.enums.Parentheses;
import json_converter.tokenizer.JsonTokenizer;

public class JsonTokenizerObject implements JsonTokenizer {
	private int idx = 0;
	private String jsonStr;
	private Parentheses parentheses = Parentheses.BRACES;
	
	public JsonTokenizerObject() {}
	public JsonTokenizerObject(String jsonStr) {
		this.jsonStr = jsonStr;
		init();
	}
	
	private void setIdxNext() {
		while(idx < jsonStr.length()&&(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r'|| jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':'||jsonStr.charAt(idx) == this.parentheses.getClosing())) {
			idx++;
		}
	}
	
	private void init() {
		idx++;
		setIdxNext();		
	}
		
	private String getPrimitiveToken() {
		StringBuilder sb = new StringBuilder();
		char c = jsonStr.charAt(idx);
		while(!(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r'|| jsonStr.charAt(idx) == ',' ||jsonStr.charAt(idx) == ':'||jsonStr.charAt(idx)==this.parentheses.getClosing())) {
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
	
	@Override
	public boolean hasMoreTokens() {
		return idx < jsonStr.length();
	}
	
	@Override
	public String next() {
		if(!hasMoreTokens()) {
			return null;
		}
		String token = getToken(); 
		setIdxNext();
		return token;
	}
	
}
