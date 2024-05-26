package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

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
				new TypeContainer(MyClass.class).getBaseClass(),
				new TypeContainer(new TypeToken<Map<String,Integer>>() {}).getBaseClass()
				);
		List<Type> typeExpecteds = List.of(
				MyClass.class
				, Map.class);
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
	
	@Test
	public void getComponentTypeContainer() {
//		TypeContainer typeContainer = new TypeContainer(new TypeToken<MyClass<Integer>[]>() {});
		List<Type> types = List.of(
//				typeContainer.getComponentTypeContainer().getBaseClass()
				new TypeContainer(int[].class).getComponentTypeContainer().getBaseClass()
				);
		List<Type> typeExpecteds = List.of(
				int.class
				);
		for(int i = 0; i < types.size(); ++i) {
			assertEquals(typeExpecteds.get(i), types.get(i), "not equals: idx " + i);
		}
	}
	
	@Test
	public void getTypeParameterContainers() {
		TypeContainer tc = new TypeContainer(new TypeToken<Map<String,Integer>>() {});
		TypeContainer[] parameterTc = tc.getTypeParameterContainers();
		List<Type> types = List.of(
				parameterTc[0].getBaseClass(),
				parameterTc[1].getBaseClass()
				);
		
		List<Type> typeExpecteds = List.of(
				String.class,
				Integer.class
				);
		for(int i = 0; i < types.size(); ++i) {
			assertEquals(typeExpecteds.get(i), types.get(i), "not equals: idx " + i);
		}
	}
	
}	
