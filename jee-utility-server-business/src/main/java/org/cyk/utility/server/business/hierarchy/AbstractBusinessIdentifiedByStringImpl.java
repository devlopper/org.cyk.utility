package org.cyk.utility.server.business.hierarchy;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;
import org.cyk.utility.server.persistence.jpa.hierarchy.PersistenceIdentifiedByString;

public abstract class AbstractBusinessIdentifiedByStringImpl<ENTITY extends AbstractIdentifiedByString<ENTITY>,PERSISTENCE extends PersistenceIdentifiedByString<ENTITY>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>,HIERARCHY_BUSINESS extends HierarchyBusiness<HIERARCHY, ENTITY, HIERARCHIES>> extends AbstractBusinessEntityImpl<ENTITY,PERSISTENCE> implements BusinessIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected Class<HIERARCHY> __hierarchyClass__;
	protected Class<HIERARCHY_PERSISTENCE> __hierarchyPersistenceClass__;
	protected Class<HIERARCHY_BUSINESS> __hierarchyBusinessClass__;
	
	@SuppressWarnings("unchecked")
	@Override
	protected void __listenPostConstruct__() {
		super.__listenPostConstruct__();
		String name = __persistenceEntityClass__.getName();
		__hierarchyClass__ = (Class<HIERARCHY>) ClassHelper.getByName(name+"Hierarchy");
		name = StringUtils.replaceOnce(name, ".entities.", ".api.");
		__hierarchyPersistenceClass__ = (Class<HIERARCHY_PERSISTENCE>) ClassHelper.getByName(name+"HierarchyPersistence");
		name = StringUtils.replaceOnce(name, ".persistence.", ".business.");
		__hierarchyBusinessClass__ = (Class<HIERARCHY_BUSINESS>) ClassHelper.getByName(name+"HierarchyBusiness");
	}
}
