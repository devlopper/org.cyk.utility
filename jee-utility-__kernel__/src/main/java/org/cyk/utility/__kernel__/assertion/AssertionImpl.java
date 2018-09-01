package org.cyk.utility.__kernel__.assertion;
import java.io.Serializable;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.properties.Properties;

public class AssertionImpl extends AbstractObject implements Assertion,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Boolean getIsAffirmation(){
		return (Boolean) getProperties().getFromPath(Properties.IS,Properties.AFFIRMATION);
	}
	
	@Override
	public Assertion setIsAffirmation(Boolean value){
		getProperties().setFromPath(new Object[]{Properties.IS,Properties.AFFIRMATION},value);
		return this;
	}
	
	@Override
	public Boolean getValue(){
		return (Boolean) getProperties().getValue();
	}
	
	@Override
	public Assertion setValue(Boolean value){
		getProperties().setValue(value);
		return this;
	}
	
	@Override
	public String getMessageWhenValueIsNotTrue(){
		return (String) getProperties().getMessage();
	}
	
	@Override
	public Assertion setMessageWhenValueIsNotTrue(String message){
		getProperties().setMessage(message);
		return this;
	}
	
}
