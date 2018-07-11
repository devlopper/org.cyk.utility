package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

public class Column extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getName(){
		return (String) getProperties().getName();
	}
	
	public Column setName(String name){
		getProperties().setName(name);
		return this;
	}
	
	public Tuple getTuple(){
		return (Tuple) getProperties().getTuple();
	}
	
	public Column setTuple(Tuple tuple){
		getProperties().setTuple(tuple);
		return this;
	}
}
