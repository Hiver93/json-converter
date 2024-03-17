package json_converter.test;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.lang.reflect.Array;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import json_converter.parser.JsonParser;
import json_converter.type.TypeContainer;
import json_converter.type.TypeToken;

public class JsonParserTest {
	public static class MyClass{
		int i;
		String str;
		List<String> strList;
		@Override
		public String toString() {
			return "MyClass [i=" + i + ", str=" + str + "]";
		}
		public MyClass(){}
		public MyClass(int i, String str) {this.i=i; this.str=str;}
		public MyClass(int i, String str, List<String> strList) {
			this.i = i;
			this.str = str;
			this.strList = strList;
		}
		@Override
		public int hashCode() {
			return Objects.hash(i, str, strList);
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
			return i == other.i && Objects.equals(str, other.str) && Objects.equals(strList, other.strList);
		}
	}
	public static class MyGenericClass<T>{
		int i;
		String str;
		T t;
		public MyGenericClass() {}
		public MyGenericClass(int i, String str, T t) {
			super();
			this.i = i;
			this.str = str;
			this.t = t;
		}
		@Override
		public int hashCode() {
			return Objects.hash(i, str, t);
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			MyGenericClass other = (MyGenericClass) obj;
			return i == other.i && Objects.equals(str, other.str) && Objects.equals(t, other.t);
		}
		
	}
	
	
	JsonParser jp;
	@Before
	public void init() {
		jp = new JsonParser();
	}
	
	@Test 
	public void mapToString() {		
		String strJson = "\"\"Hello World\"\\n\"Hello JSON\"\"";
		String strExpected = "\"Hello World\"\n\"Hello JSON\"";
		assertEquals(strExpected, jp.parse(strJson, new TypeContainer(String.class)));
		
		
	}

	@Test
	public void mapToChar() {
		List<String> charJsons = List.of("\"a\"","\"\\n\"","\"가\"","\"あ\"","\"\\\"\"");
		List<Character> charExpecteds = List.of('a','\n','가','あ','\"');
		for(int i = 0; i < charJsons.size(); ++i) {
			assertEquals(charExpecteds.get(i),jp.parse(charJsons.get(i), new TypeContainer(Character.class)));
		}
		
		Exception e1 = assertThrows(RuntimeException.class, ()->jp.parse("\"aaa\"", new TypeContainer(Character.class)));
		assertEquals("Invalid character string: "+ "aaa", e1.getMessage());
		
		Exception e2 = assertThrows(RuntimeException.class, ()->jp.parse("\"\"", new TypeContainer(Character.class)));
		assertEquals("Invalid character string: "+ "", e2.getMessage());
	}
	
	@Test
	public void mapToNumber() {
		List<String> numJsons = List.of("-123","123.123","0.1","127","123","12");
		List<Class<?>> numClass = List.of(Integer.class, Double.class, Float.class, Byte.class,Long.class,Short.class);
		List<Number> numExpecteds = List.of(-123, 123.123, 0.1f, (byte)127, 123L, (short)12);
		for(int i = 0; i < numJsons.size(); ++i) {
			assertEquals(numExpecteds.get(i),jp.parse(numJsons.get(i), new TypeContainer(numClass.get(i))));
		}
	}
	
	@Test
	public void mapToBool() {
		List<String> boolJsons = List.of("false","true");
		List<Boolean> boolExpecteds = List.of(false,true);
		for(int i = 0; i < boolJsons.size(); ++i) {
			assertEquals(boolExpecteds.get(i), jp.parse(boolJsons.get(i), new TypeContainer(Boolean.class)));
		}
	}
	
	@Test
	public void mapToPrimitive() {
		List<String> primitiveJsons = List.of("-123","123.123","0.1","127","123","12","\"c\"","true");
		List<Class<?>> primitiveClass = List.of(int.class, double.class, float.class, byte.class, long.class, short.class, char.class, boolean.class);
		List<Object> primitiveExpecteds = List.of(-123, 123.123, 0.1f, (byte)127, 123L, (short)12, 'c', true);
		for(int i = 0; i < primitiveJsons.size(); ++i) {
			assertEquals(primitiveExpecteds.get(i), jp.parse(primitiveJsons.get(i), new TypeContainer(primitiveClass.get(i))),"not Equals: idx +"+i);
		}
	}
	
	@Test
	public void mapToObject() {
		List<String> objectJsons = List.of("{\"i\":123,\"str\":\"thisisString\"}"
				,"{\"i\":123,\"str\":null}", 
				"{\"i\":123,\"str\":null, \"strList\":[\"hello\"]}");
		List<Type> objectClass = List.of(MyClass.class, new TypeToken<MyClass>() {}.getType(), MyClass.class);
		List<Object> objectExpecteds = List.of(new MyClass(123,"thisisString"), new MyClass(123,null), new MyClass(123,null,List.of("hello"))); 
		for(int i = 0; i < objectJsons.size(); ++i) {
			assertEquals(objectExpecteds.get(i), jp.parse(objectJsons.get(i), new TypeContainer(objectClass.get(i))),"not Equals: idx +"+i);
		}
	}
	
	@Test
	public void mapToArray() {
		List<String> jsons = List.of("[1,2,3]", "[1.2,3.4]", "[\"str\",\"hello\"]", "[[1],[2]]");
		List<Class<?>> classes = List.of(int[].class,double[].class,String[].class,List[].class);
		List<Object> instanceExpecteds = List.of(new int[]{1,2,3},new double[]{1.2,3.4},new String[]{"str","hello"}, new List[] {List.of(1.0),List.of(2.0)});
		
		for(int i = 0; i < jsons.size(); ++i) {					
			Object obj = jp.parse(jsons.get(i), new TypeContainer(classes.get(i)));
			assertEquals(instanceExpecteds.get(i).getClass(), obj.getClass());
			assertEquals(Array.getLength(instanceExpecteds.get(i)), Array.getLength(obj));
			for(int j = 0; j < Array.getLength(obj); ++j) {
				assertEquals(Array.get(instanceExpecteds.get(i), j),Array.get(obj, j));
			}
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
		assertNull(jp.parse("null"));
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void mapToMap() {
		List<String> jsons = List.of("{\"str\": 123}");
		List<Type> types = List.of(new TypeToken<Map<String,Integer>>(){}.getType());
		List<Type> typeExpecteds = List.of(HashMap.class);
		List<Map> mapExpecteds = List.of(Map.of("str",123));
		
		for(int i =0; i < jsons.size(); ++i) {
			Map<?,?> map = jp.parse(jsons.get(0),new TypeContainer(types.get(0)));
			assertTrue(map.getClass().equals(typeExpecteds.get(i)));
			mapExpecteds.get(i).forEach(
					(key,value)->{
						assertTrue(map.containsKey(key));
						assertTrue(map.containsValue(value));
					}
					);
		}
	}
	
	@Test
	public void mapToCollection() {
		List<String> jsons = List.of("[1,2,3]","[[\"str1\",\"str2\"][\"str1\",\"str2\"]]");
		List<Type> types = List.of(new TypeToken<List<Integer>>() {}.getType(), new TypeToken<LinkedList<LinkedList<String>>>() {}.getType());
		List<Type> typeExpecteds = List.of(ArrayList.class,LinkedList.class);
		List<List> listExpecteds = List.of(
				List.of(1,2,3), 
				new LinkedList<LinkedList<String>>() {{ add(new LinkedList() {{add("str1"); add("str2");}});  add(new LinkedList() {{add("str1"); add("str2");}});   }});
		List<Type> elemTypeExpecteds = List.of(Integer.class, LinkedList.class);
		
		for(int i =0; i < jsons.size(); ++i) {
			List<?> list = jp.parse(jsons.get(i), new TypeContainer(types.get(i)));
			assertEquals(typeExpecteds.get(i), list.getClass());
			assertEquals(listExpecteds.get(i), list);
			assertEquals(elemTypeExpecteds.get(i), list.get(0).getClass());
		}
	}
	
	@Test
	public void mapTogenericObject() {
		List<String> jsons = List.of("{\"i\":123, \"str\":\"a\", \"t\":345}");
		List<Type> types = List.of(new TypeToken<MyGenericClass<Integer>>() {}.getType());
		List<Object> instanceExpecteds = List.of(new MyGenericClass<Integer>(123,"a",345));
	
		for(int i =0; i < jsons.size(); ++i) {
			Object instance = jp.parse(jsons.get(i), new TypeContainer(types.get(i)));
		}
	}
}
