package json_converter.stringbuilder;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import json_converter.enums.EscapeSequence;

public class JsonStringBuilder {
	public String toJson(Object object) {
		if(object == null) {
			return "null";
		}
		StringBuilder sb = new StringBuilder();
		Class<? extends Object> c = object.getClass();
		
		if(object instanceof Number) {
			sb.append(toJson((Number)object));
		}else if(object instanceof Boolean) {
			sb.append(toJson((Boolean)object));
		}else if(object instanceof Character) {
			sb.append(toJson((Character)object));
		}else if(object instanceof String) {
			sb.append(toJson((String)object));
		}else if(object instanceof List) {
			sb.append(toJson((List<? extends Object>)object));
		}else if(c.isArray()) {
			sb.append(toJsonAsArray(object));
		}else if(object instanceof Map) {
			sb.append(toJson((Map<? extends Object,? extends Object>) object));
		}else if(object instanceof Set) {
			sb.append(toJson((Set<? extends Object>)object));
		}else if(!c.getName().startsWith("java")) {
			sb.append(toJson(object,c));
		}else {
			throw new RuntimeException();
		}
		
		
		
		return sb.toString();		
	}
	private String toJson(Number n) {
		return n.toString();
	}
	private String toJson(Boolean b) {
		return b.toString();
	}	
	private String toJson(Character ch) {
		return new StringBuilder().append("\"").append(ch).append("\"").toString();
	}
	private String toJson(String str) {	
		String jsonStr = str.chars()
			.mapToObj(
					c->EscapeSequence.isEscapeSequence((char)c) ? 
							EscapeSequence.getEscapeSequenceByChar((char)c).getString() : String.valueOf((char)c))
			.collect(Collectors.joining("","\"","\""));
			
		
		return jsonStr;
	}
	
	private String toJson(List<? extends Object> list) {
		StringBuilder sb = new StringBuilder();
		Stream<? extends Object> stream = list.stream();		
		sb.append(
				stream.map(this::toJson)
				.collect(Collectors.joining(",","[","]"))
		);
		
		return sb.toString();
	}
	
	private String toJsonAsArray(Object arr) {
		StringBuilder sb = new StringBuilder();
		Stream<Object> stream = IntStream.range(0,Array.getLength(arr))
									.mapToObj(i -> Array.get(arr, i));
		sb.append(
				stream.map(this::toJson)
				.collect(Collectors.joining(",","[","]"))
		);
		return sb.toString();
	}
	
	private String toJson(Set<? extends Object> set) {
		StringBuilder sb = new StringBuilder();
		Stream<? extends Object> stream = set.stream();
		sb.append(
				stream.map(this::toJson)
				.collect(Collectors.joining(",","[","]"))
		);
		return sb.toString();
	};
	private String toJson(Map<? extends Object, ? extends Object> map) {
		StringBuilder sb = new StringBuilder();
		
		Stream<?> stream = map.entrySet().stream();
		sb.append(
				stream.map(entry -> toJson((Entry<?,?>)entry))
				.collect(Collectors.joining(",","{","}"))
		);
		return sb.toString();
	}
	private String toJson(Entry<?,?> entry) {
		StringBuilder sb = new StringBuilder();
		sb.append(toJson(entry.getKey()))
			.append(":")
			.append(toJson(entry.getValue()));
		return sb.toString();
	}

	
	private String toJson(Object object, Class c) {
		StringBuilder sb = new StringBuilder();
		Stream<Field> fieldStream = Stream.of(c.getDeclaredFields());
		
		sb.append(
			fieldStream.map(f->toJson(object,f))
			.collect(Collectors.joining(",","{","}"))
		);
		
		return sb.toString();
	}
	private String toJson(Object object, Field field){
		StringBuilder sb = new StringBuilder();
		field.setAccessible(true);
		try {
			sb.append(toJson(field.getName()))
			.append(":")
			.append(toJson(field.get(object)));
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}
}
