package org.cyk.utility.security.keycloak.server;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.__static__.identifiable.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.Value;

public interface PolicyManager {

	void createFromRolesNames(Collection<String> rolesNames);
	void createFromRolesNames(String...rolesNames);
	
	/**/
	
	public static abstract class AbstractImpl extends AbstractObject implements PolicyManager,Serializable{
		
		@Override
		public void createFromRolesNames(Collection<String> rolesNames) {
			
		}
		
		@Override
		public void createFromRolesNames(String...rolesNames) {
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("Roles names", rolesNames);
			createFromRolesNames(CollectionHelper.listOf(rolesNames));
		}
	}
	
	/**/
	
	static PolicyManager getInstance() {
		return Helper.getInstance(PolicyManager.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();	
}