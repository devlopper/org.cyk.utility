package org.cyk.utility.common;

import java.io.Serializable;
import java.lang.reflect.Field;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @EqualsAndHashCode(of={"field"})
public class ClassField implements Serializable {
	
	private static final long serialVersionUID = 6076797357920215507L;
	
	public final String TO_STRING_FORMAT = "%s(%s)";
	
	private Field field;
	
	private ClassField parent;
	private String path;
	
	@Override
	public String toString() {
		return String.format(TO_STRING_FORMAT,field.getName(),field.getType().getSimpleName());
	}



	

}