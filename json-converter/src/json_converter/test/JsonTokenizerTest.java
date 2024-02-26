package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import json_converter.tokenizer.JsonTokenizer;
import json_converter.tokenizer.TokenizerFactory;

public class JsonTokenizerTest {
	@Test
	public void keyValueTest() {
		JsonTokenizer tokenizer = TokenizerFactory.getJsonTokenizer("{\"first\":{\"ab\":123, \"cd\":\"ef\\\"g\"}, \"second\":{}}");
		String token1 = tokenizer.next();
		String expected1 = "\"first\"";
		
		assertEquals(token1, expected1);
		
		String token2 = tokenizer.next();
		String expected2 = "{\"ab\":123, \"cd\":\"ef\\\"g\"}";
		
		assertEquals(token2, expected2);
		
		String token3 = tokenizer.next();
		String expected3 = "\"second\"";
		assertEquals(token3, expected3);
		
		String token4 = tokenizer.next();
		String expected4 = "{}";
		assertEquals(token4, expected4);
	}
}
