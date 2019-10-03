package org.cyk.utility.__kernel__.mapping;

import java.util.Collection;

import org.cyk.utility.__kernel__.instance.InstanceHelper;

public interface IdentifiedFromIdentifiersChildren<REPRESENTATION_ENTITY,PERSISTENCE_ENTITY> extends IdentifiedFromIdentifiers<REPRESENTATION_ENTITY,PERSISTENCE_ENTITY> {

	Class<PERSISTENCE_ENTITY> getChildPersistenceEntityClass();
	
	default Collection<PERSISTENCE_ENTITY> getChildren(Collection<REPRESENTATION_ENTITY> representations) {
    	return InstanceHelper.getIdentifiedFromIdentifiers(getChildPersistenceEntityClass(), representations);
    }
	
}
