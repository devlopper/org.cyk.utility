package org.cyk.utility.server.persistence.query;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class PersistenceQuery extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public PersistenceQuery setIdentifier(Object identifier) {
		return (PersistenceQuery) super.setIdentifier(identifier);
	}
	
	public String getValue(){
		return (String) getProperties().getValue();
	}
	
	public PersistenceQuery setValue(String value){
		getProperties().setValue(value);
		return this;
	}
	
	public Class<?> getResultClass(){
		return (Class<?>) getProperties().getClazz();
	}
	
	public PersistenceQuery setResultClass(Class<?> aClass){
		getProperties().setClass(aClass);
		return this;
	}
}
