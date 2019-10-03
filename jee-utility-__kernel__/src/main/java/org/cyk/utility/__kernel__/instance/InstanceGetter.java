package org.cyk.utility.__kernel__.instance;

import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface InstanceGetter {

	<INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass,Object identifier,ValueUsageType valueUsageType);
	
	default <INSTANCE> INSTANCE getBySystemIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.SYSTEM);
	}
	
	default <INSTANCE> INSTANCE getByBusinessIdentifier(Class<INSTANCE> klass,Object identifier) {
		return getByIdentifier(klass, identifier, ValueUsageType.BUSINESS);
	}
	
	/**/
	
	InstanceGetter INSTANCE = new InstanceGetter() {
		
		@Override
		public <INSTANCE> INSTANCE getByIdentifier(Class<INSTANCE> klass, Object identifier,ValueUsageType valueUsageType) {
			throw new RuntimeException("get "+klass.getName()+" with "+valueUsageType+" identifier "+identifier+" not yet implemented");
		}
	};
	
}
