package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.server.persistence.query.filter.Field;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceIdentifiedByStringAndCodedImpl<ENTITY extends AbstractIdentifiedByStringAndCoded<ENTITY,?>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceIdentifiedByStringImpl<ENTITY,HIERARCHY,HIERARCHIES,HIERARCHY_PERSISTENCE> implements PersistenceIdentifiedByStringAndCoded<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes,Properties properties) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByParentsBusinessIdentifiers(__injectCollectionHelper__().cast(Object.class, parentsCodes));
		return hierarchies == null ? null : hierarchies.getHierarchyChildren();	
	}
	
	@Override
	public Collection<ENTITY> readByParentsCodes(Collection<String> parentsCodes) {
		return readByParentsCodes(parentsCodes, null);	
	}
	
	@Override
	public Collection<ENTITY> readByParentsCodes(Properties properties,String... parentsCodes) {
		return readByParentsCodes(__injectCollectionHelper__().instanciate(parentsCodes));
	}
	
	@Override
	public Collection<ENTITY> readByParentsCodes(String... parentsCodes) {
		return readByParentsCodes(null,parentsCodes);
	}
	
	@Override
	public Long countByParentsCodes(Collection<String> parentsCodes, Properties properties) {
		return __inject__(__hierarchyPersistenceClass__).countByParentsBusinessIdentifiers(__injectCollectionHelper__().cast(Object.class, parentsCodes));
	}
	
	@Override
	public Long countByParentsCodes(Collection<String> parentsCodes) {
		return countByParentsCodes(parentsCodes,null);
	}
	
	@Override
	public Long countByParentsCodes(Properties properties, String... parentsCodes) {
		return countByParentsCodes(__injectCollectionHelper__().instanciate(parentsCodes),properties);
	}
	
	@Override
	public Long countByParentsCodes(String... parentsCodes) {
		return countByParentsCodes(parentsCodes);
	}
	
	/* children */
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes,Properties properties) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByChildrenBusinessIdentifiers(__injectCollectionHelper__().cast(Object.class, childrenCodes));
		return hierarchies == null ? null : hierarchies.getHierarchyParents();	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(Collection<String> childrenCodes) {
		return readByChildrenCodes(childrenCodes,null);	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(Properties properties,String... childrenCodes) {
		return readByChildrenCodes(__injectCollectionHelper__().instanciate(childrenCodes));
	}
	
	@Override
	public Collection<ENTITY> readByChildrenCodes(String... childrenCodes) {
		return readByChildrenCodes(null,childrenCodes);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Collection<ENTITY> __readByFilterParents__(Properties properties, Filter filter, Field field) {
		if(ValueUsageType.SYSTEM.equals(field.getValueUsageType()))
			return super.__readByFilterParents__(properties, filter, field);
		return readByParentsCodes((Collection<String>) field.getValue(),properties);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Long __countByFilterParents__(Properties properties, Filter filter, Field field) {
		if(ValueUsageType.SYSTEM.equals(field.getValueUsageType()))
			return super.__countByFilterParents__(properties, filter, field);
		return countByParentsCodes((Collection<String>) field.getValue(),properties);
	}
}
