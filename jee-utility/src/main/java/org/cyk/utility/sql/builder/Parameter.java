package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class Parameter extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getName(){
		return (String) getProperties().getName();
	}
	
	public Parameter setName(String name){
		getProperties().setName(name);
		return this;
	}
	
}
