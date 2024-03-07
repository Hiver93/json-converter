package json_converter.parser;

import java.awt.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.Set;

import json_converter.enums.EscapeSequence;

public class JsonParser {	
	public <T> T parse(String jsonStr, Class<T> cl) {
		T object = null;
		try {
			if(Map.class.isAssignableFrom(cl)) {
				
			}
			else if(Set.class.isAssignableFrom(cl)) {
				
			}
			else if(List.class.isAssignableFrom(cl)) {
				
			}
			else if(cl.isArray()) {
				
			}
			else if(String.class.equals(cl)) {
				object = (T)mapToString(jsonStr);
			}
			else if(Number.class.isAssignableFrom(cl)) {
				object = (T)mapToNumber(jsonStr, cl);
			}
			else if(Character.class.equals(cl)) {
				object = (T)mapToChar(jsonStr);
			}
			else if(Boolean.class.equals(cl)) {
				object = (T)mapToBool(jsonStr);
			}
			else if(!cl.getName().startsWith("java")){
				
			}
			else if(cl.isPrimitive()) {
				
			}
			else {
				throw new RuntimeException("Unsupported type format");
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return object;
	}
	
	private Number mapToNumber(String jsonStr, Class<?> cl) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Number num = (Number)cl.getDeclaredMethod("valueOf", String.class).invoke(null, jsonStr);
		return num;
	}

	private String mapToString(String jsonStr) {
		StringBuilder sb = new StringBuilder();
		for(int i = 1; i < jsonStr.length()-1; ++i) {
			char c = jsonStr.charAt(i);
			if(c == EscapeSequence.BACKSLASH.getCharacter()) {
				sb.append(
						EscapeSequence
						.getEscapeSequenceByString(
								new StringBuilder()
								.append(c).append(jsonStr.charAt(++i))
								.toString())
						.getCharacter());
			}else {
				sb.append(c);
			}
		}
		return sb.toString();
	}
	
	private Character mapToChar(String jsonStr) {
		jsonStr = jsonStr.substring(1, jsonStr.length()-1);
		if(EscapeSequence.isEscapeSequence(jsonStr)) {
			return EscapeSequence.getEscapeSequenceByString(jsonStr).getCharacter();
		}else if(jsonStr.length() == 1) {
			return jsonStr.charAt(0);
		}
		throw new RuntimeException("Invalid character string: "+ jsonStr);
		
	}
	
	private Boolean mapToBool(String jsonStr) {
		return Boolean.valueOf(jsonStr);
	}
}
