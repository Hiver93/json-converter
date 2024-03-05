package json_converter.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.List;

import org.junit.Test;

import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.factory.JsonTokenizerFactory;
import json_converter.tokenizer.impl.JsonTokenizerObject;
import json_converter.tokenizer.impl.JsonTokenizerPrimitive;
import json_converter.tokenizer.impl.JsonTokenizerString;

public class JsonTokenizerTest {
	String objectJson = "{\"first\":{\"ab\":123, \"cd\":\"ef\\\"g\"}, \"second\":{}, \"third\":123 }";
	String listJson = "[ {\"fist\": 1, \"second\": 2},{\"fist\": 3, \"second\": 4} ]";
	String stringJson = "\" \\\"Hello world\\\" \\n \\\"Hello JSON\\\"\"";	
	
	List<String> primitiveJsons =  List.of("1234", "    c     ", "123.123", "true", "\nfalse");
	List<String> primitiveExpecteds = List.of("1234", "c", "123.123", "true", "false");
	
	@Test
	public void objectNext() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(objectJson);
		
		String token1 = tokenizer.next();
		String expected1 = "\"first\"";
		assertEquals(expected1,token1);
		
		String token2 = tokenizer.next();
		String expected2 = "{\"ab\":123, \"cd\":\"ef\\\"g\"}";
		assertEquals(expected2,token2);
		
		
		String token3 = tokenizer.next();
		String expected3 = "\"second\"";
		assertEquals(expected3,token3);
		
		String token4 = tokenizer.next();
		String expected4 = "{}";
		assertEquals(expected4,token4);
		
		String token5 = tokenizer.next();
		String expected5 = "\"third\"";
		assertEquals(expected5,token5);
		
		
		String token6 = tokenizer.next();
		String expected6 = "123";
		assertEquals(expected6,token6);
		
		String token7 = tokenizer.next();
		assertEquals(null,token7);
		
	}
	
	@Test
	public void listNext() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(listJson);
		String listToken1 = tokenizer.next();
		String listExpected1 = "{\"fist\": 1, \"second\": 2}";
		assertEquals(listExpected1, listToken1);
		
		String listToken2 = tokenizer.next();
		String listExpected2 = "{\"fist\": 3, \"second\": 4}";
		assertEquals(listExpected2, listToken2);
		
		String listToken3 = tokenizer.next();
		assertEquals(null, listToken3);
		
	}
	
	@Test
	public void stringNext() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(stringJson);
		String token1 = tokenizer.next();
		String expected1 = "\" \\\"Hello world\\\" \\n \\\"Hello JSON\\\"\"";
		assertEquals(expected1, token1);
	}
	
	@Test
	public void primitiveNext() {
		for(int i = 0; i < primitiveJsons.size(); ++i) {
			JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(primitiveJsons.get(i));
			String token = tokenizer.next();
			String expected = primitiveExpecteds.get(i);
			assertEquals(expected, token);
		}
			
		
	}
	
	@Test
	public void objectHasMoreTokens() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(objectJson);
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertFalse(tokenizer.hasMoreTokens());
	}
	
	@Test
	public void listHasMoreTokens() {		
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(listJson);
		assertTrue(tokenizer.hasMoreTokens());
		
		tokenizer.next();
		assertTrue(tokenizer.hasMoreTokens());
		

		tokenizer.next();
		assertFalse(tokenizer.hasMoreTokens());
		

		tokenizer.next();
		assertFalse(tokenizer.hasMoreTokens());
	}

	@Test
	public void stringHasMoreTokens() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(stringJson);
		assertTrue(tokenizer.hasMoreTokens());		
		tokenizer.next();
		
		assertFalse(tokenizer.hasMoreTokens());
	}

	@Test
	public void primitiveHasMoreTokens() {
		JsonTokenizer tokenizer = JsonTokenizerFactory.jsonTokenizer(primitiveJsons.get(1));
		assertTrue(tokenizer.hasMoreTokens());		
		tokenizer.next();
		
		assertFalse(tokenizer.hasMoreTokens());
	}
	
}
