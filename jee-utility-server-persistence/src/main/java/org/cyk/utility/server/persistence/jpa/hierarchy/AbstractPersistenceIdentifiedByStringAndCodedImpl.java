package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.server.persistence.query.filter.Field;
import org.cyk.utility.server.persistence.query.filter.Filter;
import org.cyk.utility.value.ValueUsageType;

public abstract class AbstractPersistenceIdentifiedByStringAndCodedImpl<ENTITY extends AbstractIdentifiedByStringAndCoded<ENTITY,?>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceIdentifiedByStringImpl<ENTITY,HIERARCHY,HIERARCHIES,HIERARCHY_PERSISTENCE> implements PersistenceIdentifiedByStringAndCoded<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String readByParentsCodes;
	
	public static final String READ_BY_PARENTS_CODES_FORMAT = 
			"SELECT node FROM %1$s node WHERE EXISTS(SELECT tuple FROM %2$s tuple WHERE tuple.child = node AND tuple.parent.code IN :parentsCodes))";
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByParentsCodes, String.format(READ_BY_PARENTS_CODES_FORMAT,__getTupleName__(),__getTupleName__(__hierarchyClass__)));
	}
	
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
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass, Properties properties, Object... parameters) {
		Filter filter = (Filter) Properties.getFromPath(properties,Properties.QUERY_FILTERS);
		if(PersistenceFunctionReader.class.equals(functionClass)) {
			org.cyk.utility.server.persistence.query.filter.Field field = filter == null? null : filter.getFieldByPath(AbstractIdentifiedByString.FIELD_PARENTS);
			if(field != null) {
				if(field.getValue() != null && ValueUsageType.BUSINESS.equals(field.getValueUsageType()))
					return readByParentsCodes;
			}
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsCodes)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_PARENTS)};
			}
			return new Object[]{"parentsCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
}
