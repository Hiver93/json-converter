package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.Test;

import json_converter.tokenizer.JsonTokenizerList;
import json_converter.tokenizer.JsonTokenizerObject;
import json_converter.tokenizer.JsonTokenizerPrimitive;
import json_converter.tokenizer.JsonTokenizerString;
import json_converter.tokenizer.factory.JsonTokenizerFactory;

public class JsonTokenizerFactoryTest {
	private String objectJson = "{  }";
	private String stringJson = " \" \" ";
	private String listJson = "[]";
	private String primitiveJson = "\n123\n";
	private String emptyJson = "";
	
	@Test
	public void jsonTokenizer() {
		assertTrue(JsonTokenizerFactory.jsonTokenizer(objectJson) instanceof JsonTokenizerObject);
		assertTrue(JsonTokenizerFactory.jsonTokenizer(listJson) instanceof JsonTokenizerList);
		assertTrue(JsonTokenizerFactory.jsonTokenizer(stringJson) instanceof JsonTokenizerString);
		assertTrue(JsonTokenizerFactory.jsonTokenizer(primitiveJson) instanceof JsonTokenizerPrimitive);
		System.out.println(JsonTokenizerFactory.jsonTokenizer(emptyJson).getClass());
	}
}
