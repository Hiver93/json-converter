package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import json_converter.JsonConverter;

public class JsonStringBuilderTest {
	Set<Integer> set = Set.of(1,14);
	JsonConverter jsonConverter;
	Person person = new Person();
	String personJson = "{\"age\":10,\"regNum\":1,\"Name\":\"KIM\",\"sex\":\"M\",\"pets\":[{\"name\":\"dog\",\"age\":10},{\"name\":\"dog\",\"age\":10}],\"belongings\":[\"bag\",\"phone\"],\"lottoNumbers\":[1],\"fruitMap\":{\"apple\":1},\"nullPet\":null}";

	
	@Before
	public void init() {
		jsonConverter = new JsonConverter();
	}
	
	@Test
	public void objectToJson() {
		assertEquals(personJson, jsonConverter.toJson(person));
	}
	
	@Test
	public void setToJson() {
		List<String> expectedSetList = List.of("[1,14]","[14,1]");
		String jsonStr = jsonConverter.toJson(set);
		assertTrue(jsonStr.equals(expectedSetList.get(0)) || jsonStr.equals(expectedSetList.get(1)));
	}
	
	@Test
	public void mapToJson() {
		Map<String, Integer> map1 = Map.ofEntries(Map.entry("apple",1),Map.entry("banana",2));
		List<String> expectedMapList1 = List.of("{\"apple\":1,\"banana\":2}","{\"banana\":2,\"apple\":1}");
		String jsonStr1 = jsonConverter.toJson(map1);
		
		Map<Integer,String> map2 = Map.ofEntries(Map.entry(1,"apple"),Map.entry(2,"banana"));
		List<String> expectedMapList2 = List.of("{1:\"apple\",2:\"banana\"}","{2:\"banana\",1:\"apple\"}");
		String jsonStr2 = jsonConverter.toJson(map2);
		
		assertTrue(jsonStr1.equals(expectedMapList1.get(0))||jsonStr1.equals(expectedMapList1.get(1)));
		assertTrue(jsonStr2.equals(expectedMapList2.get(0))||jsonStr2.equals(expectedMapList2.get(1)));
	}
	
	@Test
	public void stringToJson() {
		String str = "\"Hello World\"\n\"Hello JSON\"";
		String expected = "\"\\\"Hello World\\\"\\n\\\"Hello JSON\\\"\"";
		
		assertEquals(expected, jsonConverter.toJson(str));
	}
	
}
