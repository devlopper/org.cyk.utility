package org.cyk.utility.condition;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;

@Deprecated
public class Condition extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Boolean getValue(){
		return (Boolean) getProperties().getValue();
	}
	
	public Condition setValue(Boolean value){
		getProperties().setValue(value);
		return this;
	}
	
	public String getMessage(){
		return (String) getProperties().getMessage();
	}
	
	public Condition setMessage(String message){
		getProperties().setMessage(message);
		return this;
	}
}
