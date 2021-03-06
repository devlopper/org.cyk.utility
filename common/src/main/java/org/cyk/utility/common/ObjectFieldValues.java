package org.cyk.utility.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import lombok.Getter;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.common.ClassRepository.ClassField;
import org.cyk.utility.common.ClassRepository.Clazz;

public class ObjectFieldValues implements Serializable {

	private static final long serialVersionUID = 1284100881789191895L;

	@Getter private Clazz clazz;
	@Getter private final Map<ClassField, Object> valuesMap = new HashMap<>();
	
	private String baseName;
	
	public ObjectFieldValues(Class<?> aClass){
		super();
		this.clazz = ClassRepository.getInstance().get(aClass);
	}
	
	public ObjectFieldValues set(String name,Object value){
		valuesMap.put(clazz.getField((StringUtils.isBlank(baseName) ? Constant.EMPTY_STRING : baseName+Constant.CHARACTER_DOT)+name), value);
		return this;
	}
	
	public ObjectFieldValues set(Object...objects){
		for(int i=0;i<objects.length;i = i+2)
			set((String)objects[i], objects[i+1]);
		return this;
	}
	
	public Object get(String name){
		return valuesMap.get(clazz.getField(name));
	}
	
	public ObjectFieldValues setBaseName(String...names){
		this.baseName = StringUtils.join(names,Constant.CHARACTER_DOT);
		return this;
	}
	
	@Override
	public String toString() {
		return "Values of "+clazz.getValue().getSimpleName()+" - "+valuesMap;
	}
	
}
