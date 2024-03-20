package json_converter;

import java.lang.reflect.Type;

import json_converter.parser.JsonParser;
import json_converter.stringbuilder.JsonStringBuilder;
import json_converter.type.TypeContainer;
import json_converter.type.TypeToken;

public class JsonConverter {
	JsonStringBuilder jsonStringBuilder;
	JsonParser jsonParser;
	
	public JsonConverter() {
		jsonStringBuilder = new JsonStringBuilder();
		jsonParser = new JsonParser();
	}
	
	public String toJson(Object object) {
		return jsonStringBuilder.toJson(object);		
	}
	
	public <T>T fromJson(String jsonStr, Class<T> cl) {
		return jsonParser.parse(jsonStr, cl);
	}
	
	public <T>T fromJson(String jsonStr, Type type){
		return jsonParser.parse(jsonStr, type);
	}
	
	public <T>T fromJson(String jsonStr, TypeToken<?> typeToken){
		return jsonParser.parse(jsonStr, typeToken);
	}
}