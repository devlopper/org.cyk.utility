package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public class Attribute extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Attribute() {
		setIsPrefixedWithTuple(Boolean.TRUE);
	}
	
	public String getName(){
		return (String) getProperties().getName();
	}
	
	public Attribute setName(String name){
		getProperties().setName(name);
		return this;
	}
	
	public Tuple getTuple(){
		return (Tuple) getProperties().getTuple();
	}
	
	public Attribute setTuple(Tuple tuple){
		getProperties().setTuple(tuple);
		return this;
	}
	
	public Attribute setIsPrefixedWithTuple(Boolean isPrefixWithTuple){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.PREFIX,Properties.TUPLE}, isPrefixWithTuple);
		return this;
	}
	
	public Boolean getIsPrefixedWithTuple(){
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.PREFIX,Properties.TUPLE);
	}
	
	@Override
	public String toString() {
		return getName();
	}
}
