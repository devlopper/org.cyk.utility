package org.cyk.utility.server.business.hierarchy;

import java.io.Serializable;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;

public abstract class AbstractHierarchyBusinessImpl<HIERARCHY extends AbstractHierarchy<ENTITY>,PERSISTENCE extends HierarchyPersistence<HIERARCHY, ENTITY, HIERARCHIES>,ENTITY extends AbstractIdentifiedByString<?,?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends AbstractBusinessEntityImpl<HIERARCHY, PERSISTENCE> implements HierarchyBusiness<HIERARCHY,ENTITY,HIERARCHIES>,Serializable {
	private static final long serialVersionUID = 1L;

}