package org.cyk.utility.__kernel__.identifier.resource;

import java.io.Serializable;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface RequestGetter {

	Object get();
	
	public static abstract class AbstractImpl extends AbstractObject implements RequestGetter,Serializable {
		
	}
	
	/**/
	
	static RequestGetter getInstance() {
		return Helper.getInstance(RequestGetter.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}
