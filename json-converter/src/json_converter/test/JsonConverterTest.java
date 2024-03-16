package json_converter.test;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import org.junit.Before;
import org.junit.Test;

import json_converter.JsonConverter;
import json_converter.type.TypeToken;

public class JsonConverterTest {
	JsonConverter jc;
	
	static public class MyClass{
		List<String> strList;
		int[] intArr;
		String name;
		char c;
		A a;
		public MyClass(List<String> strList, int[] intArr, String name, char c, A a) {
			super();
			this.strList = strList;
			this.intArr = intArr;
			this.name = name;
			this.c = c;
			this.a = a;
		}
		public MyClass() {}
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + Arrays.hashCode(intArr);
			result = prime * result + Objects.hash(a, c, name, strList);
			return result;
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
			return Objects.equals(a, other.a) && c == other.c && Arrays.equals(intArr, other.intArr)
					&& Objects.equals(name, other.name) && Objects.equals(strList, other.strList);
		}
		
	}
	
	static public class A{
		int i;

		public A(int i) {
			super();
			this.i = i;
		}

		public A() {
			
		}

		@Override
		public int hashCode() {
			return Objects.hash(i);
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			A other = (A) obj;
			return i == other.i;
		}
		
	}
	
	@Before
	public void init() {
		jc = new JsonConverter();
	}
	
	
	@Test
	public void fromJson() {
		List<String> jsons = List.of(
			"{\"strList\":[\"str1\", \"str2\"], \"intArr\":[1,2,3], \"name\":\"john\", \"c\":\"C\", \"a\":{\"i\":123}}",
			"[1.2,3.4]"
				);
		List<Type> types = List.of(
				MyClass.class,
				new TypeToken<List<Double>>() {}.getType()
				);
		List<Object> instanceExpecteds = List.of(
				new MyClass(List.of("str1","str2"),new int[] {1,2,3},"john",'C',new A(123)),
				List.of(1.2,3.4)
				);
		
		for(int i = 0;i < jsons.size(); ++i) {
			assertEquals(instanceExpecteds.get(i), jc.fromJson(jsons.get(i), types.get(i)));
		}
	}
}
