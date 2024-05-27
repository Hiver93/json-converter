package json_converter.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

public class TypeContainer {
	private Type type;
	private Map<String,Type> typeVariableMap = new HashMap<String, Type>();
	public TypeContainer(Type type) {
		this.type = type;
		if(this.type instanceof TypeVariable) {
			this.type = restoreTypeVariable(this.type);			
		}
		if(this.type instanceof ParameterizedType) {
			setTypeVariableMap();
		}
	}
	
	private TypeContainer(Type type, Map<String,Type> typeVariableMap) {
		this.type = type;
		this.typeVariableMap.putAll(typeVariableMap);
		if(this.type instanceof TypeVariable) {
			this.type = restoreTypeVariable(this.type);			
		}
		if(this.type instanceof ParameterizedType) {
			setTypeVariableMap();
		}
	}
	
	public TypeContainer(TypeToken<?> typeToken) {
		this(typeToken.getType());
	}
	
	private void setTypeVariableMap() {
		TypeVariable<?>[] typeVars = (((Class<?>)((ParameterizedType)this.type).getRawType())).getTypeParameters();
		Type[] typeArgs = ((ParameterizedType)this.type).getActualTypeArguments();
		for(int i = 0; i < typeVars.length; ++i) {
			this.typeVariableMap.put(typeVars[i].getTypeName(),typeArgs[i]);
		}
	}
	
	public Class<?> getBaseClass() {
		if(this.type instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType)this.type).getRawType();
		}
		else if(this.type instanceof TypeVariable) {
			return ((TypeVariable<?>)this.type).getClass();
		}
		return (Class<?>)this.type;
	}
	
	public TypeContainer getFieldTypeContainer(Field field) {
		Type type = field.getGenericType();
		if(type instanceof TypeVariable) {
			type = typeVariableMap.get(type.getTypeName());
		}
		return new TypeContainer(type, typeVariableMap);	
	}
	
	public TypeContainer getComponentTypeContainer() {
		Type type = getBaseClass().getComponentType();
		if(type instanceof TypeVariable) {
			type = typeVariableMap.get(type);
		}
		return new TypeContainer(type, typeVariableMap);
	}
	
	public TypeContainer[] getTypeParameterContainers() {
		TypeVariable<?>[] typeVars = getBaseClass().getTypeParameters();
		return Stream.of(typeVars)
			.map(v -> typeVariableMap.containsKey(v.getTypeName()) ? 
			new TypeContainer(restoreTypeVariable(typeVariableMap.get(v.getTypeName())),typeVariableMap) : new TypeContainer(v))
			.toArray(TypeContainer[]::new);
	}
	
	private Type restoreTypeVariable(Type type) {
		if(type instanceof TypeVariable && typeVariableMap.containsKey(this.type.getTypeName())) {
			type = typeVariableMap.get(this.type.getTypeName());
			type = restoreTypeVariable(type);
		}
		return type;
	}
	
}
