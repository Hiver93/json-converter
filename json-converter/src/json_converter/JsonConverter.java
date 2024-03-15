package json_converter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import json_converter.parser.JsonParser;
import json_converter.stringbuilder.JsonStringBuilder;

public class JsonConverter {
	public String toJson(Object object) {
		return new JsonStringBuilder().toJson(object);		
	}
	
	public <T>T fromJson(String jsonStr, Class<T> c) {
		return new JsonParser().parse(jsonStr, c);
	}
}