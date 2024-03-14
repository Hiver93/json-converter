package json_converter.test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import json_converter.generic.container.GenericTypeContainer;
import json_converter.generic.type.TypeToken;
import json_converter.instance.InstanceFactory;

public class GenericTypeContainerTest {
	GenericTypeContainer typeContainer;
	List<Type> typeList;
	List<Type> containerTypeExpecteds;
	List<List<Type>> genericTypeExpecteds;
	
	@Before
	public void init() {
		 typeList = List.of(
					new TypeToken<List<String>>() {}.getType(),
					new TypeToken<Data<Integer>>() {}.getType(),
					new TypeToken<Map<String, List<Data<Double>>>>(){}.getType()
					);
		 containerTypeExpecteds = List.of(
					List.class,
					Data.class,
					Map.class
					);
		 genericTypeExpecteds = List.of(
				 	List.of(String.class),
				 	List.of(Integer.class),
				 	List.of(String.class, new TypeToken<List<Data<Double>>>() {}.getType())
				 );
		
	}
	@Test
	public void constructor() {
		typeContainer = new GenericTypeContainer(new TypeToken<List<String>>() {}.getType());
		Exception e = assertThrows(RuntimeException.class, ()->new GenericTypeContainer(String.class));
		assertEquals("Not a generic type: " + String.class, e.getMessage());
	}
	
	@Test
	public void getContainerType() {
		for(int i = 0;i  < typeList.size(); ++i) {
			typeContainer = new GenericTypeContainer(typeList.get(i));
			assertEquals(containerTypeExpecteds.get(i), typeContainer.getContainerType());
		}	
	}
	
	@Test
	public void getGenericType() {
		for(int i = 0;i  < typeList.size(); ++i) {
			typeContainer = new GenericTypeContainer(typeList.get(i));
			assertEquals(genericTypeExpecteds.get(i), typeContainer.getGenericTypeList());
		}
	}
	
	@Test
	public void isGenericContainer() {
		for(int i = 0; i < typeList.size(); ++i) {
			assertEquals(true, GenericTypeContainer.isGenericContainer(typeList.get(i)));
		}
		assertEquals(false, GenericTypeContainer.isGenericContainer(List.class));
	}
	
	static class Data<T>{
		T t;
	}
}
