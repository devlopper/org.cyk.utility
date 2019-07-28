package org.cyk.utility.server.persistence.hierarchy;

import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.collection.AbstractCollectionInstanceImpl;
import org.cyk.utility.collection.CollectionHelper;

public abstract class AbstractHierarchyCollectionInstanceImpl<ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical<?,?>,HIERARCHY extends AbstractHierarchy<ENTITY>> extends AbstractCollectionInstanceImpl<HIERARCHY> implements HierarchyCollectionInstance<ENTITY,HIERARCHY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> getHierarchyParents() {
		return __inject__(CollectionHelper.class).isEmpty(collection) ? null : collection.stream().map(AbstractHierarchy::getParent).collect(Collectors.toList());
	}

	@Override
	public Collection<ENTITY> getHierarchyChildren() {
		return __inject__(CollectionHelper.class).isEmpty(collection) ? null : collection.stream().map(AbstractHierarchy::getChild).collect(Collectors.toList());
	}

}