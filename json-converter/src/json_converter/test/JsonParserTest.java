package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import org.junit.Test;

import json_converter.parser.JsonParser;

public class JsonParserTest {
	String strJson = "\"\"Hello World\"\\n\"Hello JSON\"\"";
	String strExpected = "\"Hello World\"\n\"Hello JSON\"";
	
	List<String> charJsons = List.of("\"a\"","\"\\n\"","\"가\"","\"あ\"","\"\\\"\"");
	List<Character> charExpecteds = List.of('a','\n','가','あ','\"');
	
	List<String> numJsons = List.of("-123","123.123","0.1","127","123","12");
	List<Class<?>> numClass = List.of(Integer.class, Double.class, Float.class, Byte.class,Long.class,Short.class);
	List<Number> numExpecteds = List.of(-123, 123.123, 0.1f, (byte)127, 123L, (short)12);
	
	@Test 
	public void mapToString() {		
		JsonParser jp = new JsonParser();
		assertEquals(strExpected, jp.parse(strJson, String.class));
		
		
	}

	@Test
	public void mapToChar() {
		JsonParser jp = new JsonParser();
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
		JsonParser jp = new JsonParser();
		for(int i = 0; i < numJsons.size(); ++i) {
			assertEquals(numExpecteds.get(i),jp.parse(numJsons.get(i), numClass.get(i)));
		}
	}
}
