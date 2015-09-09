package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

public class ClassRepository implements Serializable {

	private static final long serialVersionUID = 2331517379189246417L;

	private static final ClassRepository INSTANCE = new ClassRepository();
	
	public static Boolean ENABLED = Boolean.TRUE;
	
	private Map<Class<?>, Clazz> map = new HashMap<>();
	private Boolean addOnGet = Boolean.TRUE;
	
	/**/
	
	public Clazz add(Class<?> value){
		Clazz clazz = map.get(value);
		if(clazz==null){
			map.put(value, clazz = new Clazz(value));
			populateFields(value, clazz.getFields());
		}
		return clazz;
	}
	
	public Clazz get(Class<?> value){
		Clazz clazz = map.get(value);
		if(clazz==null && Boolean.TRUE.equals(addOnGet))
			return add(value);
		return clazz;
	}
	
	public Collection<Field> getFields(Class<?> value){
		Collection<Field> fields = new ArrayList<>();
		if(Boolean.TRUE.equals(ENABLED))
			fields.addAll(get(value).getFields());
		else
			populateFields(value, fields);
		return fields;
	}
	
	/**/
	
	private void populateFields(Class<?> type,Collection<Field> fields) {
		//super class fields first
		if (type.getSuperclass() != null)
			populateFields(type.getSuperclass(),fields);
		//declared class fields second
		for (Field field : type.getDeclaredFields())
			fields.add(field);
	}
	
	/**/
	
	public static ClassRepository getInstance() {
		return INSTANCE;
	}
	
	@Getter @Setter
	public class Clazz implements Serializable {

		private static final long serialVersionUID = -4384997700934391685L;

		private final Class<?> value;
		private final Collection<Field> fields = new ArrayList<>();
		
		public Clazz(Class<?> value) {
			super();
			this.value = value;
		}
	}
	
}
