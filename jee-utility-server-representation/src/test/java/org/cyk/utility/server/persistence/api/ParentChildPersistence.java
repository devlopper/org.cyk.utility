package org.cyk.utility.server.persistence.api;

import java.util.Collection;

import org.cyk.utility.server.persistence.PersistenceEntity;
import org.cyk.utility.server.persistence.entities.Parent;
import org.cyk.utility.server.persistence.entities.ParentChild;

public interface ParentChildPersistence extends PersistenceEntity<ParentChild> {

	Collection<ParentChild> readByParentsCodes(Collection<String> parentsCodes);
	Collection<ParentChild> readByParentsCodes(String...parentsCodes);
	Collection<ParentChild> readByParents(Collection<Parent> parents);
	Collection<ParentChild> readByParents(Parent...parents);
	
}
