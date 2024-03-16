package json_converter.test;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.junit.Test;

import com.google.gson.Gson;

public class LearningTest {
	static class MyClass{
		String str = "str";
		int i = 1;
	}
	
	@Test
	public void instanceofTest() {
		HashMap<String,String> hm = new HashMap<String,String>();
		MyClass myClass = new MyClass();
		
		assertTrue(hm instanceof Map);
		assertFalse(hm instanceof Set);
		
		assertTrue(hm.getClass() instanceof Class);
		assertTrue(hm.getClass().getInterfaces()[0] == Map.class);
	}
	
	@Test
	public void isAssignableFromTest() {
		HashMap<String,String> hm = new HashMap<String,String>();
		MyClass myClass = new MyClass();
		
		assertTrue(Map.class.isAssignableFrom(hm.getClass()));
		assertFalse(Map.class.isAssignableFrom(myClass.getClass()));	
	}
	
	@Test
	public void mapTest() throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		HashMap<String,Integer> hm = new HashMap<String,Integer>();
		Class<?> c = hm.getClass();
		Object obj = hm.getClass().getConstructor(null).newInstance(null);
		Map<String,String> hm2 = hm.getClass().cast(obj);
		// Map entry 원소 알아내기
		Map<String, Integer> myMap = new HashMap<>();
        Gson gson = new Gson();
//        hm = gson.fromJson("{\"a\":123L}",hm.getClass());
//        System.out.println(hm.get("a"));
        System.out.println(gson.fromJson("\"b\n a\"", String.class));
        
	}
	
	@Test
	public void stringTest() {
		String str = "\n";
		System.out.println(str.length());
		System.out.println(str.charAt(0) == '\n');
	}
}
