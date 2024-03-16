package json_converter;

import java.lang.reflect.Type;

import json_converter.parser.JsonParser;
import json_converter.stringbuilder.JsonStringBuilder;
import json_converter.type.TypeContainer;
import json_converter.type.TypeToken;

public class JsonConverter {
	public String toJson(Object object) {
		return new JsonStringBuilder().toJson(object);		
	}
	
	public <T>T fromJson(String jsonStr, Class<T> c) {
		return new JsonParser().parse(jsonStr, new TypeContainer(c));
	}
	
	public <T>T fromJson(String jsonStr, Type type){
		return new JsonParser().parse(jsonStr, new TypeContainer(type));
	}
	
	public <T>T fromJson(String jsonStr, TypeToken typeToken){
		return new JsonParser().parse(jsonStr, new TypeContainer(typeToken.getType()));
	}
}