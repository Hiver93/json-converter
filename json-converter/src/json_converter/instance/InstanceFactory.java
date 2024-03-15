package json_converter.instance;

import java.util.List;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public class InstanceFactory {	
	static public <T>T newInstance(Type type) {		
		try {
			Class<T> cl = (Class<T>) Class.forName(type.getTypeName());
			return newInstance(cl);
		} catch (IllegalArgumentException | SecurityException | ClassNotFoundException e) {
			throw new RuntimeException("Cannot find a class matching the type: "+type);
		}
	}
	
	static public <T>T newInstance(Class<T> cl){
		T t = null;
		if(cl.isInterface()) {
			t = newInterfaceInstance(cl);
		}
		else {
			t = instance(cl);
		}
		return t;
	}
	
	static public <T>T newInterfaceInstance(Class<T> cl){
		T t = null;
		if(Map.class.equals(cl)) {
			t = (T)new HashMap();
		}
		else if(Set.class.equals(cl)) {
			t = (T)new HashSet();
		}
		else if(List.class.equals(cl)) {
			t = (T)new ArrayList();
		}
		else if(Collection.class.equals(cl)) {
			t = (T)new ArrayList();
		}
		else if(Queue.class.equals(cl)) {
			t = (T)new LinkedList();
		}
		else if(Deque.class.equals(cl)) {
			t = (T)new ArrayDeque();
		}
		else {
			throw new RuntimeException("Class has no default constructor: "+cl);
		}
		return t;
	}
	
	static public boolean isInstantiable(Class<?> cl) {
		Constructor[] constructors = cl.getDeclaredConstructors(); 
		if(constructors.length < 1) {
			return false;
		}
		for(Constructor c : constructors) {
			if(c.getParameterCount() == 0) {
				return true;
			}
		}
		return false;
	}
	
	static private <T>T instance(Class<T> cl) {
		if(!isInstantiable(cl)) {
			throw new RuntimeException("Class has no default constructor: "+cl);
		}
		try {
			return cl.getConstructor(null).newInstance(null);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			throw new RuntimeException("Class is not instantiable: "+cl);
		}		
	}
	
}
