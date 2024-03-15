package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import json_converter.generic.type.TypeToken;
import json_converter.parser.JsonParser;

public class JsonParserTest {
	public static class MyClass{
		int i;
		String str;
		@Override
		public String toString() {
			return "MyClass [i=" + i + ", str=" + str + "]";
		}
		public MyClass(){}
		public MyClass(int i, String str) {this.i=i; this.str=str;}
		@Override
		public int hashCode() {
			return Objects.hash(i, str);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MyClass other = (MyClass) obj;
			return i == other.i && Objects.equals(str, other.str);
		};
		
	}
	JsonParser jp;
	String strJson = "\"\"Hello World\"\\n\"Hello JSON\"\"";
	String strExpected = "\"Hello World\"\n\"Hello JSON\"";
	
	List<String> charJsons = List.of("\"a\"","\"\\n\"","\"가\"","\"あ\"","\"\\\"\"");
	List<Character> charExpecteds = List.of('a','\n','가','あ','\"');
	
	List<String> numJsons = List.of("-123","123.123","0.1","127","123","12");
	List<Class<?>> numClass = List.of(Integer.class, Double.class, Float.class, Byte.class,Long.class,Short.class);
	List<Number> numExpecteds = List.of(-123, 123.123, 0.1f, (byte)127, 123L, (short)12);
	
	List<String> boolJsons = List.of("false","true");
	List<Boolean> boolExpecteds = List.of(false,true);
	
	List<String> primitiveJsons = List.of("-123","123.123","0.1","127","123","12","\"c\"","true");
	List<Class<?>> primitiveClass = List.of(int.class, double.class, float.class, byte.class, long.class, short.class, char.class, boolean.class);
	List<Object> primitiveExpecteds = List.of(-123, 123.123, 0.1f, (byte)127, 123L, (short)12, 'c', true);
	
	List<String> objectJsons = List.of("{\"i\":123,\"str\":\"thisisString\"}","{\"i\":123,\"str\":null}");
	List<Type> objectClass = List.of(MyClass.class, new TypeToken<MyClass>() {}.getType());
	List<Object> objectExpecteds = List.of(new MyClass(123,"thisisString"), new MyClass(123,null)); 
	
	@Before
	public void init() {
		jp = new JsonParser();
	}
	
	@Test 
	public void mapToString() {		
		assertEquals(strExpected, jp.parse(strJson, String.class));
		
		
	}

	@Test
	public void mapToChar() {
		for(int i = 0; i < charJsons.size(); ++i) {
			assertEquals(charExpecteds.get(i),jp.parse(charJsons.get(i), Character.class));
		}
		
		Exception e1 = assertThrows(RuntimeException.class, ()->jp.parse("\"aaa\"", Character.class));
		assertEquals("Invalid character string: "+ "aaa", e1.getMessage());
		
		Exception e2 = assertThrows(RuntimeException.class, ()->jp.parse("\"\"", Character.class));
		assertEquals("Invalid character string: "+ "", e2.getMessage());
	}
	
	@Test
	public void mapToNumber() {
		for(int i = 0; i < numJsons.size(); ++i) {
			assertEquals(numExpecteds.get(i),jp.parse(numJsons.get(i), numClass.get(i)));
		}
	}
	
	@Test
	public void mapToBool() {
		for(int i = 0; i < boolJsons.size(); ++i) {
			assertEquals(boolExpecteds.get(i), jp.parse(boolJsons.get(i), Boolean.class));
		}
	}
	
	@Test
	public void mapToPrimitive() {
		for(int i = 0; i < primitiveJsons.size(); ++i) {
			assertEquals(primitiveExpecteds.get(i), jp.parse(primitiveJsons.get(i), primitiveClass.get(i)));
		}
	}
	
	@Test
	public void mapToObject() {
		for(int i = 0; i < objectJsons.size(); ++i) {
			assertEquals(objectExpecteds.get(i), jp.parse(objectJsons.get(i), objectClass.get(i)));
		}
	}
	
	@Test
	public void parseJson() {
		List<String> jsons = List.of("[1,2,3]","213","\"abc\"","{\"a\":123, 2 : \"b\"}");
		List<Class<?>> classExpecteds = List.of(ArrayList.class,Double.class, String.class, HashMap.class);
		List<Object> instanceExpecteds = List.of(List.of(1.0,2.0,3.0),213.0, "abc", Map.of("a",123.0,2.0,"b"));
		for(int i = 0; i < jsons.size(); ++i) {
			Object obj = jp.parse(jsons.get(i));
			assertEquals(classExpecteds.get(i),obj.getClass());
			assertEquals(instanceExpecteds.get(i), obj);
		}
	}
}
