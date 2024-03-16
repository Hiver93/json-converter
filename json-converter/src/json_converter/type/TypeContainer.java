package json_converter.type;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class TypeContainer {
	private Type type;
	public TypeContainer(Type type){
		this.type = type;
	}
	public TypeContainer(Field field) {
		this.type = field.getGenericType();
	}
	
	public boolean isGenericContainer() {
		return this.type instanceof ParameterizedType;
	}
	public Class<?> getBaseType() {
		if(this.isGenericContainer()) {
			return (Class)((ParameterizedType)type).getRawType();
		}
		else {
			return (Class)type;
		}
	}
	public Field[] getFields() {
		if(this.getBaseType().getTypeName().startsWith("java")) {
			throw new RuntimeException();
		}		
		return ((Class)this.getBaseType()).getDeclaredFields();
	}
	
	public Type[] getGenericTypes() {
		if(isGenericContainer()) {
			return ((ParameterizedType)this.type).getActualTypeArguments();
		}
		return (((Class)this.getBaseType()).getTypeParameters());
	}
	
	
}
