package org.cyk.utility.__kernel__.security;

import java.io.Serializable;
import java.security.Principal;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.value.Value;

public interface UserBuilder {

	User build(Principal principal);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements UserBuilder,Serializable {
		
		@Override
		public User build(Principal principal) {
			throw new RuntimeException("No implementation found to build Principal to User");
		}
		
	}
	
	static UserBuilder getInstance() {
		return Helper.getInstance(UserBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}