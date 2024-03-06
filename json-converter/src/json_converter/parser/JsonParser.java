package json_converter.parser;

import java.awt.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import json_converter.enums.EscapeSequence;

public class JsonParser {	
	public <T> T parse(String jsonStr, Class<T> c) {
		T object = null;
		try {
			if(Map.class.isAssignableFrom(c)) {
				
			}
			else if(Set.class.isAssignableFrom(c)) {
				
			}
			else if(List.class.isAssignableFrom(c)) {
				
			}
			else if(c.isArray()) {
				
			}
			else if(String.class.equals(c)) {
				object = (T)mapToString(jsonStr);
			}
			else if(Number.class.isAssignableFrom(c)) {
				
			}
			else if(Character.class.equals(c)) {
				
			}
			else if(Boolean.class.equals(c)) {
				
			}
			else if(!c.getName().startsWith("java")){
				
			}
			else {
				throw new RuntimeException("Unsupported type format");
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return object;
	}
	
	private String mapToString(String jsonStr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 0; i < jsonStr.length(); ++i) {
			char c = jsonStr.charAt(i);
			if(c == '\\') {
				sb.append(
						EscapeSequence
						.getEscapeSequenceByString(
								new StringBuilder()
								.append('\\').append(jsonStr.charAt(++i))
								.toString())
						.getCharacter());
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
}
