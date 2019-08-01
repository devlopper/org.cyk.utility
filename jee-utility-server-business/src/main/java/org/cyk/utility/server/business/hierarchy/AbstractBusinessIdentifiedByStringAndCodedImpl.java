package org.cyk.utility.server.business.hierarchy;

import java.io.Serializable;

import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;
import org.cyk.utility.server.persistence.jpa.hierarchy.PersistenceIdentifiedByString;

public abstract class AbstractBusinessIdentifiedByStringAndCodedImpl<ENTITY extends AbstractIdentifiedByString<ENTITY,?>,PERSISTENCE extends PersistenceIdentifiedByString<ENTITY>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractBusinessIdentifiedByStringImpl<ENTITY,PERSISTENCE,HIERARCHY,HIERARCHIES,HIERARCHY_PERSISTENCE> implements BusinessIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

}
