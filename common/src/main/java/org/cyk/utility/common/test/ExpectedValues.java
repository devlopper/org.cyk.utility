package org.cyk.utility.common.test;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.cyk.utility.common.Constant;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

public class ExpectedValues implements Serializable {

	private static final long serialVersionUID = 1284100881789191895L;

	@Getter private final Map<Field, String> map = new HashMap<>();
	
	private Class<?> clazz;
	
	public ExpectedValues(Object...objects){
		set(objects);
	}
	
	public ExpectedValues setClass(Class<?> clazz){
		this.clazz = clazz;
		return this;
	}
	
	public ExpectedValues set(Class<?> clazz,String name,String value){
		map.put(new Field(clazz, name), value);
		return this;
	}
	public ExpectedValues set(String name,String value){
		set(clazz, name, value);
		return this;
	}
	
	public ExpectedValues set(Object...objects){
		for(int i=0;i<objects.length;i = i+3)
			set((Class<?>)objects[i], (String)objects[i+1], (String)objects[i+2]);
		return this;
	}
	
	public ExpectedValues setValues(String...strings){
		for(int i=0;i<strings.length;i = i+2)
			set(clazz, strings[i], strings[i+1]);
		return this;
	}
	
	public String get(Class<?> clazz,String name){
		return map.get(new Field(clazz, name));
	}
	
	/**/
	
	@Getter @Setter @AllArgsConstructor @EqualsAndHashCode(of={"clazz","name"})
	public static class Field implements Serializable {
		private static final long serialVersionUID = 6076797357920215507L;
		private Class<?> clazz;
		private String name;
		
		@Override
		public String toString() {
			return clazz.getSimpleName()+Constant.CHARACTER_SPACE+name;
		}
	}
}
