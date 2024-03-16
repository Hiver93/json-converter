package json_converter.parser;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.TypeVariable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import json_converter.enums.EscapeSequence;
import json_converter.enums.PrimitiveWrapperMapping;
import json_converter.instance.InstanceFactory;
import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.factory.JsonTokenizerFactory;
import json_converter.type.TypeContainer;

public class JsonParser {	
	
	public <T> T parse(String jsonStr, TypeContainer tc) {
		if(JsonTokenizer.isNullValue(jsonStr)) {
			return null;
		}
		T object = null;
		try {
			if(Map.class.isAssignableFrom(tc.getBaseType())) {
				object = mapToMap(jsonStr,tc);
			}
			else if(Collection.class.isAssignableFrom(tc.getBaseType())) {
				object = mapToCollection(jsonStr, tc);
			}
			else if(tc.getBaseType().isArray()) {
				object = mapToArray(jsonStr, tc);
			}
			else if(String.class.equals(tc.getBaseType())) {
				object = (T)mapToString(jsonStr);
			}
			else if(Number.class.isAssignableFrom(tc.getBaseType())) {
				object = (T)mapToNumber(jsonStr, tc);
			}
			else if(Character.class.equals(tc.getBaseType())) {
				object = (T)mapToChar(jsonStr);
			}
			else if(Boolean.class.equals(tc.getBaseType())) {
				object = (T)mapToBool(jsonStr);
			}
			else if(tc.getBaseType().isPrimitive()) {
				object = mapToPrimitive(jsonStr,tc);
			}
			else if(!tc.getBaseType().getName().startsWith("java")){
				object = mapToObject(jsonStr,tc);
			}
			else {
				throw new RuntimeException("Unsupported type format");
			}
		}catch(Exception e) {
			throw new RuntimeException(e.getMessage(),e);
		}
		return object;
	}
	
	private <T>T mapToObject(String jsonStr, TypeContainer tc) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t = (T)InstanceFactory.newInstance(tc.getBaseType());
		while(tokenizer.hasMoreTokens()) {
			Field field = tc.getBaseType().getDeclaredField(parse(tokenizer.next(),new TypeContainer(String.class)));
			field.setAccessible(true);
			field.set(t, parse(tokenizer.next(), new TypeContainer(field.getType())));
		}
		return t;
	}

	private <T>T mapToArray(String jsonStr, TypeContainer tc) {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		List list = InstanceFactory.newInstance(List.class);
		TypeContainer componentTc = new TypeContainer(tc.getBaseType().getComponentType());
		while(tokenizer.hasMoreTokens()) {
			list.add(parse(tokenizer.next(),componentTc));
		}
		T arr = (T)Array.newInstance(componentTc.getBaseType(), list.size());
		for(int i = 0; i < list.size(); ++i) {
			Array.set(arr, i, list.get(i));
		}
		return arr;
	}

	private <T>T mapToPrimitive(String jsonStr, TypeContainer tc) {
		return (T) parse(jsonStr, new TypeContainer(PrimitiveWrapperMapping.getWrapperByPrimitive(tc.getBaseType())));
	}

	private <T>T mapToNumber(String jsonStr, TypeContainer tc) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		Number num = (Number)tc.getBaseType().getDeclaredMethod("valueOf", String.class).invoke(null, jsonStr);
		return (T)num;
	}

	private <T>T mapToCollection(String jsonStr, TypeContainer tc) {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t= (T)InstanceFactory.newInstance(tc.getBaseType());
		if(tc.getGenericTypes()[0] instanceof TypeVariable) {
			while(tokenizer.hasMoreTokens()) {
				((Collection)t).add(parse(tokenizer.next()));
			}
		}
		else {
			while(tokenizer.hasMoreTokens()) {
				((Collection)t).add(parse(tokenizer.next(), new TypeContainer(tc.getGenericTypes()[0])));
			}
		}
		return t;
	}

	private <T>T mapToMap(String jsonStr, TypeContainer tc) {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(jsonStr);
		T t = (T) InstanceFactory.newInstance(tc.getBaseType());
		while(tokenizer.hasMoreTokens()) {
			Object key  = null;
			Object value = null;
			if(tc.getGenericTypes()[0] instanceof TypeVariable) {
				key = parse(tokenizer.next());
				value = parse(tokenizer.next());
			}
			else {
				key = parse(tokenizer.next(), new TypeContainer(tc.getGenericTypes()[0]));
				value = parse(tokenizer.next(), new TypeContainer(tc.getGenericTypes()[1]));
			}
			((Map)t).put(key, value);
		}
		return t;
	}
	public <T>T parse(String jsonStr){
		T t = null;
		Class<?> cl = JsonTokenizer.inferClass(jsonStr);
		if(cl == null) {
			return null;
		}		
		t = (T)parse(jsonStr,new TypeContainer(cl));
		return t;
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
