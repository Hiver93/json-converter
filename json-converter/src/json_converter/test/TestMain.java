package json_converter.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import json_converter.JsonConverter;

class Person{
	int age = 10;
	Long regNum = 1L; 
	String Name = "KIM";
	char sex = 'M';
	List<Pet> pets = Arrays.asList(new Pet(), new Pet());
	String[] belongings = {"bag", "phone"};
	Set<Integer> lottoNumbers = Set.of(1,14,22,41,23,19);
	Map<String, Integer> tstMap = Map.ofEntries(Map.entry("apple",1),Map.entry("banana",2));
}

class Pet{
	String name = "dog";
	int age = 10;
}

public class TestMain {
	public static void main(String[] args) {
		int i = 1;
		Person p = new Person();
		Object o = 'c';
		o.getClass();
		System.out.println(JsonConverter.toJson(p));
		int[] arr = {12,3};
		
		Object oArr = arr;
		System.out.println(Array.get(oArr, 0));
//		List<Person> list = new ArrayList();
//		list.add(p);
//		List<? extends Object> oList = list;
//		System.out.println(list.getClass());
	}
}
