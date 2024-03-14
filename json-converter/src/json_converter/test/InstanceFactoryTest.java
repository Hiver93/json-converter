package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import json_converter.instance.InstanceFactory;

public class InstanceFactoryTest {
	public static class MyClass {}
	List<Type> types = List.of(Map.class, MyClass.class);
	List<Class> instanceClasses = List.of(HashMap.class,MyClass.class);
	
	@Test
	public void newInstance() {
		for(int i = 0; i < types.size(); ++i) {
			Type type = types.get(i);
			Object obj = InstanceFactory.newInstance(types.get(i));
			assertEquals(instanceClasses.get(i), obj.getClass());
		}
	}
}
