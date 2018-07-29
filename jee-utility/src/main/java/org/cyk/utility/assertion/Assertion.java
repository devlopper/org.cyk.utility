package org.cyk.utility.assertion;

import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public class Assertion extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	public Boolean getIsAffirmation(){
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.AFFIRMATION);
	}
	
	public Assertion setIsAffirmation(Boolean value){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.AFFIRMATION},value);
		return this;
	}
	
	public Boolean getValue(){
		return (Boolean) getProperties().getValue();
	}
	
	public Assertion setValue(Boolean value){
		getProperties().setValue(value);
		return this;
	}
	
	public String getMessageWhenValueIsNotTrue(){
		return (String) getProperties().getMessage();
	}
	
	public Assertion setMessageWhenValueIsNotTrue(String message){
		getProperties().setMessage(message);
		return this;
	}
	
}
