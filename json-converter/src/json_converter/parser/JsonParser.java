package json_converter.parser;

import java.util.List;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import json_converter.enums.EscapeSequence;
import json_converter.enums.Parentheses;
import json_converter.enums.PrimitiveWrapperMapping;
import json_converter.generic.container.GenericTypeContainer;
import json_converter.generic.type.TypeToken;
import json_converter.instance.InstanceFactory;
import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.factory.JsonTokenizerFactory;

public class JsonParser {	
	public <T> T parse(String jsonStr, Class<T> cl) {
		if(JsonTokenizer.isNullValue(jsonStr)) {
			return null;
		}
		T object = null;
		try {
			if(GenericTypeContainer.isGenericContainer(cl)) {
				
			}
			if(Map.class.isAssignableFrom(cl)) {
				object = mapToMap(jsonStr,cl);
			}
			else if(Collection.class.isAssignableFrom(cl)) {
				object = mapToCollection(jsonStr, cl);
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
			else if(cl.isPrimitive()) {
				object = mapToPrimitive(jsonStr,cl);
			}
			else if(!cl.getName().startsWith("java")){
				object = mapToObject(jsonStr,cl);
			}
			else {
				throw new RuntimeException("Unsupported type format");
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return object;
	}

	public <T>T parse(String jsonStr, Type type){
//		T object = null;
		
		return parse(jsonStr,(Class<T>)type);
	}
	
	public <T>T parse(String jsonStr, TypeToken<T> typeToken){
		if(GenericTypeContainer.isGenericContainer(typeToken.getType())) {
			GenericTypeContainer gc = new GenericTypeContainer(typeToken);
		}
		return (T) parse(jsonStr,typeToken.getType());
	}
	
	public <T>T parse(String jsonStr){
		T t = null;
		Class<?> cl = JsonTokenizer.inferClass(jsonStr);
		if(cl == null) {
			return null;
		}		
		t = (T)parse(jsonStr,cl);
		return t;
	}
	
	private <T>T mapToPrimitive(String jsonStr, Class<T> cl) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		return (T) parse(jsonStr, PrimitiveWrapperMapping.getWrapperByPrimitive(cl));
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
	private <T>T mapToObject(String jsonStr, Class<T> cl) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t = InstanceFactory.newInstance(cl);
		while(tokenizer.hasMoreTokens()) {
			Field field = cl.getDeclaredField(parse(tokenizer.next(),String.class));
			field.setAccessible(true);
			field.set(t, parse(tokenizer.next(),field.getType()));
		}
		return t;
	}
	
	private <T>T mapToCollection(String jsonStr, Class<T> cl){
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t= InstanceFactory.newInstance(cl);
		while(tokenizer.hasMoreTokens()) {
			((Collection)t).add(parse(tokenizer.next()));
		}
		return t;
	}
	
	private <T>T mapToMap(String jsonStr, Class<T> cl) {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t = InstanceFactory.newInstance(cl);
		while(tokenizer.hasMoreTokens()) {
			Object key = parse(tokenizer.next());
			Object value = parse(tokenizer.next());
			((Map)t).put(key, value);
		}
		return t;
	}
}
