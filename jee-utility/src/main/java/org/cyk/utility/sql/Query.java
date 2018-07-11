package org.cyk.utility.sql;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Query extends AbstractObject implements Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Query setIdentifier(Object identifier) {
		return (Query) super.setIdentifier(identifier);
	}
	
	public String getValue(){
		return (String) getProperties().getValue();
	}
	
	public Query setValue(String value){
		getProperties().setValue(value);
		return this;
	}
	
}
