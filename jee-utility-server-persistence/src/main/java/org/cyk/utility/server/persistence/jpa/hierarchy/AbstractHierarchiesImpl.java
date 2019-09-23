package org.cyk.utility.server.persistence.jpa.hierarchy;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

public abstract class AbstractHierarchiesImpl<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?,?>> extends AbstractCollectionInstanceImpl<HIERARCHY> implements Hierarchies<HIERARCHY,ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> getHierarchyParents() {
		return CollectionHelper.isEmpty(collection) ? null : collection.stream().map(AbstractHierarchy::getParent).collect(Collectors.toList());
	}

	@Override
	public Collection<ENTITY> getHierarchyChildren() {
		return CollectionHelper.isEmpty(collection) ? null : collection.stream().map(AbstractHierarchy::getChild).collect(Collectors.toList());
	}

}