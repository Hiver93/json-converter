package json_converter.test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Person{
	int age = 10;
	Long regNum = 1L; 
	String Name = "KIM";
	char sex = 'M';
	List<Pet> pets = Arrays.asList(new Pet(), new Pet());
	String[] belongings = {"bag", "phone"};
	Set<Integer> lottoNumbers = Set.of(1);
	Map<String, Integer> fruitMap = Map.ofEntries(Map.entry("apple",1));
	Pet nullPet = null;
	@Override
	public String toString() {
		return "Person [age=" + age + ", regNum=" + regNum + ", Name=" + Name + ", sex=" + sex + ", pets=" + pets
				+ ", belongings=" + Arrays.toString(belongings) + ", lottoNumbers=" + lottoNumbers + ", fruitMap="
				+ fruitMap + ", nullPet=" + nullPet + "]";
	}
}

class Pet{
	String name = "dog";
	int age = 10;
}