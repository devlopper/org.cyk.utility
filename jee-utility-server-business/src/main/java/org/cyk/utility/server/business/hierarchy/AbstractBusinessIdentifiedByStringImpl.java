package org.cyk.utility.server.business.hierarchy;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.server.business.AbstractBusinessEntityImpl;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractHierarchy;
import org.cyk.utility.server.persistence.jpa.hierarchy.AbstractIdentifiedByString;
import org.cyk.utility.server.persistence.jpa.hierarchy.Hierarchies;
import org.cyk.utility.server.persistence.jpa.hierarchy.HierarchyPersistence;
import org.cyk.utility.server.persistence.jpa.hierarchy.PersistenceIdentifiedByString;

public abstract class AbstractBusinessIdentifiedByStringImpl<ENTITY extends AbstractIdentifiedByString<ENTITY,?>,PERSISTENCE extends PersistenceIdentifiedByString<ENTITY>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractBusinessEntityImpl<ENTITY,PERSISTENCE> implements BusinessIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> findByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByParentsIdentifiers(String... parentsIdentifiers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByParents(Collection<ENTITY> parents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByParents(ENTITY... parents) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByChildrenIdentifiers(String... childrenIdentifiers) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByChildren(Collection<ENTITY> children) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<ENTITY> findByChildren(ENTITY... children) {
		// TODO Auto-generated method stub
		return null;
	}

}
