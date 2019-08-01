package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Collection;

public abstract class AbstractPersistenceIdentifiedByStringAndCodedImpl<ENTITY extends AbstractIdentifiedByStringAndCoded<ENTITY,?>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceIdentifiedByStringImpl<ENTITY,HIERARCHY,HIERARCHIES,HIERARCHY_PERSISTENCE> implements PersistenceIdentifiedByStringAndCoded<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByParentsBusinessIdentifiers(parentsCodes);
		return hierarchies == null ? null : hierarchies.getHierarchyChildren();	
	}
	
	@Override
	public Collection<ENTITY> readByParentsCodes(String... parentsCodes) {
		return readByParentsCodes(__injectCollectionHelper__().instanciate(parentsCodes));
	}
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByChildrenBusinessIdentifiers(childrenCodes);
		return hierarchies == null ? null : hierarchies.getHierarchyParents();	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(String... childrenCodes) {
		return readByChildrenCodes(__injectCollectionHelper__().instanciate(childrenCodes));
	}
	
}
