package json_converter.tokenizer.impl;

import json_converter.tokenizer.JsonTokenizer;

public class JsonTokenizerPrimitive implements JsonTokenizer {
	private int idx = 0;
	private String jsonStr;
	
	public JsonTokenizerPrimitive() {}
	public JsonTokenizerPrimitive(String jsonStr) {
		this.jsonStr = jsonStr;
		init();
	}
	
	private void setIdxNext() {
		while(idx < jsonStr.length() && (jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r')) {
			idx++;
		}
	}
	
	private void init() {
		setIdxNext();		
	}
	
	private String getToken() {
		StringBuilder sb = new StringBuilder();
		while(idx < jsonStr.length() && !(jsonStr.charAt(idx) == ' ' || jsonStr.charAt(idx) == '\n' || jsonStr.charAt(idx) == '\t' || jsonStr.charAt(idx) == '\r')) {
			sb.append(jsonStr.charAt(idx));
			idx++;
		}
		return sb.toString();
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
