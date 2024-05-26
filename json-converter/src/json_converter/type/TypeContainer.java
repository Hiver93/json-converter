package json_converter.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.HashMap;
import java.util.Map;

public class TypeContainer {
	private Type type;
	private Map<String,Type> typeVariableMap;
	public TypeContainer(Type type) {
		this.type = type;
	}
	
	private TypeContainer(Type type, Map<String,Type> typeVariableMap) {
		this.type = type;
		this.typeVariableMap = typeVariableMap;
	}
	
	public TypeContainer(TypeToken<?> typeToken) {
		this.type = typeToken.getType();
		setTypeVariableMap();
	}
	
	private void setTypeVariableMap() {
		typeVariableMap = new HashMap<>();
		TypeVariable<?>[] typeVars = (((ParameterizedType)this.type).getRawType()).getClass().getTypeParameters();
		Type[] typeArgs = ((ParameterizedType)this.type).getActualTypeArguments();
		for(int i = 0; i < typeVars.length; ++i) {
			this.typeVariableMap.put(typeVars[i].getTypeName(),typeArgs[i]);
		}
	}
	
	public Class<?> getBaseClass() {
		if(this.type instanceof ParameterizedType) {
			return (Class<?>)((ParameterizedType)this.type).getRawType();
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
	
}
