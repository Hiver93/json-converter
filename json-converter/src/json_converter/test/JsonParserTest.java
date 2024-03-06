package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import json_converter.parser.JsonParser;

public class JsonParserTest {
	String strJson = "\"Hello World\"\\n\"Hello JSON\"";
	String strExpected = "\"Hello World\"\n\"Hello JSON\"";
	
	@Test 
	public void mapToString() {		
		JsonParser jp = new JsonParser();
		assertEquals(strExpected, jp.parse(strJson, String.class));
		
		
	}

	@Test
	public void mapToChar() {
		JsonParser jp = new JsonParser();
		assertEquals(strExpected, jp.parse(strJson, String.class));
	}
}
