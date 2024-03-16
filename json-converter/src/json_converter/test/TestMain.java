package json_converter.test;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import json_converter.JsonConverter;

public class TestMain {
	public static void main(String[] args) {
		int i = 1;
		Person p = new Person();
		Object o = 'c';
		o.getClass();
		int[] arr = {12,3};
		
		Object oArr = arr;
		System.out.println(Array.get(oArr, 0));
//		List<Person> list = new ArrayList();
//		list.add(p);
//		List<? extends Object> oList = list;
//		System.out.println(list.getClass());
	}
}
