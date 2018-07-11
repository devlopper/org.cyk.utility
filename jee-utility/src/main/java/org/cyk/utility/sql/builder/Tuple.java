package org.cyk.utility.sql.builder;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.string.StringHelper;

public class Tuple extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public String getName(){
		return (String) getProperties().getName();
	}
	
	public Tuple setName(String name){
		getProperties().setName(name);
		if(__inject__(StringHelper.class).isBlank(getAlias()))
			setAlias(__inject__(StringHelper.class).getVariableNameFrom(getName()));
		return this;
	}
	
	public String getAlias(){
		return (String) getProperties().getAlias();
	}
	
	public Tuple setAlias(String alias){
		getProperties().setAlias(alias);
		return this;
	}
}
