package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.Case;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.PersistenceQueryIdentifierStringBuilder;
import org.cyk.utility.persistence.query.QueryContext;
import org.cyk.utility.persistence.query.Filter;

public abstract class AbstractHierarchyPersistenceImpl<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends AbstractPersistenceEntityImpl<HIERARCHY> implements HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>,Serializable {
	private static final long serialVersionUID = 1L;

	private String readWhereParentDoesNotHaveParent,readByParentsIdentifiers,readByParentsBusinessIdentifiers,readByChildrenIdentifiers,readByChildrenBusinessIdentifiers,readWhereIsParentOrChildIdentifiers
		//,readWhereIsParentOrChildBusinessIdentifiers
		;
	
	public static final String READ_WHERE_PARENT_DOES_NOT_HAVE_PARENT_FORMAT = 
			"SELECT tuple FROM %1$s tuple WHERE NOT EXISTS (SELECT subTuple FROM %1$s subTuple WHERE subTuple.child = tuple.parent)";
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		@SuppressWarnings("unchecked")
		Class<ENTITY> entityClass = (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 1);		
		addQueryCollectInstances(readWhereParentDoesNotHaveParent, String.format(READ_WHERE_PARENT_DOES_NOT_HAVE_PARENT_FORMAT,__tupleName__));
		addQueryCollectInstancesReadByParentsOrChildren(readByParentsIdentifiers,readByChildrenIdentifiers,FieldHelper.getSystemIdentifier(entityClass));			
		addQueryCollectInstancesReadByParentsOrChildren(readByParentsBusinessIdentifiers,readByChildrenBusinessIdentifiers,FieldHelper.getBusinessIdentifier(entityClass));
		addQueryCollectInstances(readWhereIsParentOrChildIdentifiers, String.format("SELECT tuple FROM %s tuple WHERE tuple.parent.identifier IN :identifiers OR tuple.child.identifier IN :identifiers"
				, __tupleName__));		
	}
	
	protected void addQueryCollectInstancesReadByParentsOrChildren(String queryIdentifier,Field identifierField,String parentOrChildFieldName) {
		if(identifierField != null) {
			addQueryCollectInstances(queryIdentifier, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s.%s IN :%s", __tupleName__
					,parentOrChildFieldName,identifierField.getName(),parentOrChildFieldName+StringHelper.applyCase(identifierField.getName(), Case.FIRST_CHARACTER_UPPER))+"s");
		}
	}
	
	protected void addQueryCollectInstancesReadByParentsOrChildren(String queryIdentifierParent,String queryIdentifierChildren,Field identifierField) {
		if(identifierField != null) {
			addQueryCollectInstancesReadByParentsOrChildren(queryIdentifierParent,identifierField,AbstractHierarchy.FIELD_PARENT);
			addQueryCollectInstancesReadByParentsOrChildren(queryIdentifierChildren,identifierField,AbstractHierarchy.FIELD_CHILD);	
		}
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(readByParentsIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,parentsIdentifiers));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public Long countByParentsIdentifiers(Collection<String> parentsIdentifiers,Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIsQueryResultPaginated(null);
		properties.setQueryFirstTupleIndex(null);
		properties.setQueryNumberOfTuple(null);
		if(properties.getQueryIdentifier() == null) {
			String queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(readByParentsIdentifiers).setIsCountInstances(Boolean.TRUE)
					.execute().getOutput();
			properties.setQueryIdentifier(queryIdentifier);
		}
		return __count__(properties,____getQueryParameters____(properties,parentsIdentifiers));
	}
	
	@Override
	public Long countByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		return countByParentsIdentifiers(parentsIdentifiers, null);
	}
	
	@Override
	public HIERARCHIES readByParentsIdentifiers(String... parentsIdentifiers) {
		return readByParentsIdentifiers(List.of(parentsIdentifiers));
	}
	
	@Override
	public Long countByParentsIdentifiers(String... parentsIdentifiers) {
		return countByParentsIdentifiers(List.of(parentsIdentifiers), null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(readByParentsBusinessIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,parentsBusinessIdentifiers));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByParentsBusinessIdentifiers(Object... parentsBusinessIdentifiers) {
		return readByParentsBusinessIdentifiers(List.of(parentsBusinessIdentifiers));
	}
	
	@Override
	public Long countByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers) {
		Properties properties = null;
		if(properties == null)
			properties = new Properties();
		properties.setIsQueryResultPaginated(null);
		properties.setQueryFirstTupleIndex(null);
		properties.setQueryNumberOfTuple(null);
		if(properties.getQueryIdentifier() == null) {
			String queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(readByParentsBusinessIdentifiers).setIsCountInstances(Boolean.TRUE)
					.execute().getOutput();
			properties.setQueryIdentifier(queryIdentifier);
		}
		return __count__(properties,____getQueryParameters____(properties,parentsBusinessIdentifiers));
	}
	
	@Override
	public Long countByParentsBusinessIdentifiers(Object... parentsBusinessIdentifiers) {
		return countByParentsBusinessIdentifiers(List.of(parentsBusinessIdentifiers));
	}
	
	@Override
	public HIERARCHIES readByParents(Collection<ENTITY> parents) {
		if(CollectionHelper.isNotEmpty(parents))
			return readByParentsIdentifiers(parents.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(List.of(parents));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readWhereParentDoesNotHaveParent(Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER,readWhereParentDoesNotHaveParent);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public Long countWhereParentDoesNotHaveParent(Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIsQueryResultPaginated(null);
		properties.setQueryFirstTupleIndex(null);
		properties.setQueryNumberOfTuple(null);
		if(properties.getQueryIdentifier() == null) {
			String queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(readWhereParentDoesNotHaveParent).setIsCountInstances(Boolean.TRUE)
					.execute().getOutput();
			properties.setQueryIdentifier(queryIdentifier);
		}
		//properties.setIfNull(Properties.QUERY_IDENTIFIER,readWhereParentDoesNotHaveParent);
		return __count__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public HIERARCHIES readWhereParentDoesNotHaveParent() {
		return readWhereParentDoesNotHaveParent(null);
	}
	
	@Override
	public Long countWhereParentDoesNotHaveParent() {
		return countWhereParentDoesNotHaveParent(null);
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(readByChildrenIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,childrenIdentifiers));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByChildrenIdentifiers(String... childrenIdentifiers) {
		return readByChildrenIdentifiers(List.of(childrenIdentifiers));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByChildrenBusinessIdentifiers(Collection<Object> childrenBusinessIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(childrenBusinessIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,childrenBusinessIdentifiers));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByChildrenBusinessIdentifiers(Object... childrenBusinessIdentifiers) {
		return readByChildrenBusinessIdentifiers(List.of(childrenBusinessIdentifiers));
	}
	
	@Override
	public HIERARCHIES readByChildren(Collection<ENTITY> children) {
		if(CollectionHelper.isNotEmpty(children))
			return readByChildrenIdentifiers(children.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(List.of(children));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readWhereIsParentOrChildIdentifiers(Collection<String> identifiers) {
		Properties properties = new Properties().setQueryIdentifier(readWhereIsParentOrChildIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,identifiers));
		return CollectionHelper.isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readWhereIsParentOrChildIdentifiers(String... identifiers) {
		return readWhereIsParentOrChildIdentifiers(List.of(identifiers));
	}
	
	@Override
	public HIERARCHIES readWhereIsParentOrChild(Collection<ENTITY> entities) {
		if(CollectionHelper.isNotEmpty(entities))
			return readWhereIsParentOrChildIdentifiers(entities.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readWhereIsParentOrChild(ENTITY... entities) {
		return readWhereIsParentOrChild(List.of(entities));
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass, Properties properties, Object... parameters) {
		Filter filter = (Filter) Properties.getFromPath(properties,Properties.QUERY_FILTERS);
		if(PersistenceFunctionReader.class.equals(functionClass)) {
			org.cyk.utility.persistence.query.Field field = filter == null? null : filter.getFieldByPath(AbstractIdentifiedByString.FIELD_PARENTS);
			if(field != null) {
				if(ValueUsageType.BUSINESS.equals(field.getValueUsageType()))
					return readByParentsBusinessIdentifiers;
				else
					return readByParentsIdentifiers;
			}
			field = filter == null? null : filter.getFieldByPath(AbstractIdentifiedByString.FIELD_CHILDREN);
			if(field != null) {
				if(ValueUsageType.BUSINESS.equals(field.getValueUsageType()))
					return readByChildrenBusinessIdentifiers;
				else
					return readByChildrenIdentifiers;
			}
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_CHILDREN)};
			}
			return new Object[]{"parentIdentifiers",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsBusinessIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_CHILDREN)};
			}
			return new Object[]{"parentCodes",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByChildrenIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_PARENTS)};
			}
			return new Object[]{"childIdentifiers",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readWhereIsParentOrChildIdentifiers)) {
			/*if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_PARENTS)};
			}*/
			return new Object[]{"identifiers",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	protected Class<HIERARCHIES> __getCollectionInstanceClass__() {
		return (Class<HIERARCHIES>) ClassHelper.getParameterAt(getClass(), 2);
	}
	
}
