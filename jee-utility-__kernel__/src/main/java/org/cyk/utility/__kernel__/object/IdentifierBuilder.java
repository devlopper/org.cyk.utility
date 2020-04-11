package org.cyk.utility.__kernel__.object;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.value.Value;

public interface IdentifierBuilder {

	Object build(Object object,Type type);
	
	default Object build(Object object) {
		if(object == null)
			return null;
		return build(object, Type.RUNTIME);
	}
	
	/**/
	
	static IdentifierBuilder getInstance() {
		return Helper.getInstance(IdentifierBuilder.class, INSTANCE);
	}
	
	Value INSTANCE = new Value();
	
	/**/
	
	public static enum Type {
		RUNTIME
		,PERSISTENCE_SYSTEM
		,PERSISTENCE_BUSINESS
		,CACHE
	}
}
