package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import json_converter.type.TypeContainer;
import json_converter.type.TypeToken;

public class TypeContainerTest {
	static public class MyClass<T>{
		List<String> list;
		int i;
		List<T> tList;
		T t;
	}
	
	@Test
	public void isGenericContainer() throws NoSuchFieldException, SecurityException {
		List<Type> types = List.of(
				new TypeToken<String>() {}.getType(), 
				new ArrayList<String>().getClass(),
				MyClass.class.getDeclaredField("list").getGenericType(),
				MyClass.class.getDeclaredField("i").getGenericType(),
				MyClass.class.getDeclaredField("tList").getGenericType(),
				List.class,
				new TypeToken<List<String>>() {}.getType(),
				(ParameterizedType)new ArrayList<String>().getClass().getGenericSuperclass());
		List<Boolean> boolExpecteds = List.of(
				false, 
				false,
				true,
				false,
				true,
				false,
				true,
				true);
		
		for(int i = 0; i < types.size(); ++i) {
			TypeContainer tc = new TypeContainer(types.get(i));
			assertEquals(boolExpecteds.get(i), tc.isGenericContainer(), "not equals: idx " + i);
		}
	}
	
	@Test 
	public void getBaseType() throws NoSuchFieldException, SecurityException {
		List<Type> types = List.of(
				new TypeToken<String>() {}.getType(), 
				new ArrayList<String>().getClass(),
				MyClass.class.getDeclaredField("list").getGenericType(),
				MyClass.class.getDeclaredField("i").getGenericType(),
				MyClass.class.getDeclaredField("tList").getGenericType(),
				List.class,
				new TypeToken<List<String>>() {}.getType(),
				(ParameterizedType)new ArrayList<String>().getClass().getGenericSuperclass());
		List<Type> typeExpecteds = List.of(
				String.class,
				ArrayList.class,
				List.class,
				int.class,
				List.class,
				List.class,
				List.class,
				AbstractList.class);
		for(int i = 0; i < types.size(); ++i) {
			TypeContainer tc = new TypeContainer(types.get(i));
			assertEquals(typeExpecteds.get(i), tc.getBaseType(), "not equals: idx " + i);
		}
	}
	
	@Test
	public void getFields() throws NoSuchFieldException, SecurityException {
		List<Type> types = List.of(
				MyClass.class,
				new TypeToken<MyClass<String>>() {}.getType()
				);
		List<Field[]> fieldsExpecteds = List.of(
				MyClass.class.getDeclaredFields(),
				MyClass.class.getDeclaredFields()
				);
		for(int i = 0; i < types.size(); ++i) {
			TypeContainer tc = new TypeContainer(types.get(i));
			assertArrayEquals(fieldsExpecteds.get(i), tc.getFields(), "not equals: idx " + i);
		}
	}
	
	@Test
	public void getGenericTypes() {
		List<Type> types = List.of(
				MyClass.class,
				new TypeToken<MyClass<String>>() {}.getType(),
				(ParameterizedType)new ArrayList<String>().getClass().getGenericSuperclass()
				);
		List<Type[]> typesExpecteds = List.of(
				MyClass.class.getTypeParameters(),
				new Type[] {String.class},
				((ParameterizedType)new ArrayList<String>().getClass().getGenericSuperclass()).getActualTypeArguments()
				);
		for(int i = 0; i < types.size(); ++i) {
			TypeContainer tc = new TypeContainer(types.get(i));
			assertArrayEquals(typesExpecteds.get(i), tc.getGenericTypes(), "not equals: idx " + i);
		}
		
	}
}	
