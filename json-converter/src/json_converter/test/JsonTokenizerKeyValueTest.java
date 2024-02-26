package json_converter.test;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.Test;

import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.TokenizerFactory;

public class JsonTokenizerKeyValueTest {
	@Test
	public void next() {
		JsonTokenizer tokenizer = TokenizerFactory.getJsonTokenizer("{\"first\":{\"ab\":123, \"cd\":\"ef\\\"g\"}, \"second\":{}, \"third\":123 }");
		assertTrue(tokenizer.hasMoreTokens());
		
		String token1 = tokenizer.next();
		String expected1 = "\"first\"";
		assertEquals(token1, expected1);
		
		String token2 = tokenizer.next();
		String expected2 = "{\"ab\":123, \"cd\":\"ef\\\"g\"}";
		
		
		String token3 = tokenizer.next();
		String expected3 = "\"second\"";
		assertEquals(token3, expected3);
		
		String token4 = tokenizer.next();
		String expected4 = "{}";
		assertEquals(token4, expected4);
		
		String token5 = tokenizer.next();
		String expected5 = "\"third\"";
		assertEquals(token5, expected5);
		
		
		String token6 = tokenizer.next();
		String expected6 = "123";
		assertEquals(token6, expected6);
		
		assertFalse(tokenizer.hasMoreTokens());
		String token7 = tokenizer.next();
		assertEquals(token7, null);
	}
	
	@Test
	public void hasMoreTokens() {
		JsonTokenizer tokenizer = TokenizerFactory.getJsonTokenizer("{\"first\":{\"ab\":123, \"cd\":\"ef\\\"g\"}, \"second\":{}, \"third\":123 }");
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
}
