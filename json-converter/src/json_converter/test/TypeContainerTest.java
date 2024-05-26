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
	public void getBaseClass() throws NoSuchFieldException, SecurityException {
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
			assertEquals(typeExpecteds.get(i), tc.getBaseClass(), "not equals: idx " + i);
		}
	}
	
	@Test
	public void getFieldTypeContainer() throws NoSuchFieldException, SecurityException {
		TypeContainer typeContainer = new TypeContainer(new TypeToken<MyClass<Integer>>() {});
		List<Type> types = List.of(
				typeContainer.getFieldTypeContainer(typeContainer.getBaseClass().getDeclaredField("list")).getBaseClass(),
				typeContainer.getFieldTypeContainer(typeContainer.getBaseClass().getDeclaredField("i")).getBaseClass(),
				typeContainer.getFieldTypeContainer(typeContainer.getBaseClass().getDeclaredField("tList")).getBaseClass(),
				typeContainer.getFieldTypeContainer(typeContainer.getBaseClass().getDeclaredField("t")).getBaseClass()
				);
		List<Type> typeExpecteds = List.of(
				List.class,
				int.class,
				List.class,
				Integer.class
				);
		for(int i = 0; i < types.size(); ++i) {
			assertEquals(typeExpecteds.get(i), types.get(i), "not equals: idx " + i);
		}
	}
	
}	
