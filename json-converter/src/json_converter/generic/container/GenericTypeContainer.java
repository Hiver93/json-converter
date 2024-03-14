package json_converter.generic.container;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

import json_converter.generic.type.TypeToken;

public class GenericTypeContainer {
	private Type type;
	private Type containerType;
	private List<Type> typeList;
	private int typeCnt;
	public GenericTypeContainer(Type type) {
		if(!(type instanceof ParameterizedType)) {
			throw new RuntimeException("Not a generic type: " + type);
		}
		this.type = type;
		init();
	}
	public GenericTypeContainer(TypeToken<?> typeToken) {
		this(typeToken.getType());
	}
	private void init() {
		setContainerType();
		setGenericTypeList();
	}
	private void setContainerType() {
		containerType = ((ParameterizedType)type).getRawType();
	}
	private void setGenericTypeList() {
		typeList = List.of(((ParameterizedType)type).getActualTypeArguments());
		typeCnt = typeList.size();
	}
	public Type getContainerType() {
		return containerType;
	}
	public List<Type> getGenericTypeList() {
		return typeList;
	}
	public int getGenericTypeCnt() {
		return typeCnt;
	}
	public Type getType() {
		return type;
	}
	static public boolean isGenericContainer(Type type) {
		return type instanceof ParameterizedType;
	}
}
