package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.clazz.ClassInstance;
import org.cyk.utility.clazz.ClassInstancesRuntime;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;
import org.cyk.utility.string.Case;

public abstract class AbstractHierarchyPersistenceImpl<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByString<?,?>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>> extends AbstractPersistenceEntityImpl<HIERARCHY> implements HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByParentsIdentifiers,readByParentsBusinessIdentifiers,readByChildrenIdentifiers,readByChildrenBusinessIdentifiers;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		@SuppressWarnings("unchecked")
		Class<ENTITY> entityClass = (Class<ENTITY>) __injectClassHelper__().getByName(__injectClassHelper__().getParameterAt(getClass(), 1, Object.class).getName());
		ClassInstance classInstance = __inject__(ClassInstancesRuntime.class).get(entityClass);
		if(classInstance != null) {
			addQueryCollectInstancesReadByParentsOrChildren(readByParentsIdentifiers,readByChildrenIdentifiers,classInstance.getSystemIdentifierField());			
			addQueryCollectInstancesReadByParentsOrChildren(readByParentsBusinessIdentifiers,readByChildrenBusinessIdentifiers,classInstance.getBusinessIdentifierField());	
		}
	}
	
	protected void addQueryCollectInstancesReadByParentsOrChildren(String queryIdentifier,Field identifierField,String parentOrChildFieldName) {
		if(identifierField != null) {
			addQueryCollectInstances(queryIdentifier, String.format("SELECT tuple FROM %s tuple WHERE tuple.%s.%s IN :%s", __getTupleName__()
					,parentOrChildFieldName,identifierField.getName(),parentOrChildFieldName+__injectStringHelper__().applyCase(identifierField.getName(), Case.FIRST_CHARACTER_UPPER))+"s");
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
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByParentsIdentifiers(String... parentsIdentifiers) {
		return readByParentsIdentifiers(__injectCollectionHelper__().instanciate(parentsIdentifiers));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByParentsBusinessIdentifiers(Collection<Object> parentsBusinessIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(parentsBusinessIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,parentsBusinessIdentifiers));
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByParentsBusinessIdentifiers(Object... parentsBusinessIdentifiers) {
		return readByParentsBusinessIdentifiers(__injectCollectionHelper__().instanciate(parentsBusinessIdentifiers));
	}
	
	@Override
	public HIERARCHIES readByParents(Collection<ENTITY> parents) {
		if(__injectCollectionHelper__().isNotEmpty(parents))
			return readByParentsIdentifiers(parents.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(__injectCollectionHelper__().instanciate(parents));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(readByChildrenIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,childrenIdentifiers));
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByChildrenIdentifiers(String... childrenIdentifiers) {
		return readByChildrenIdentifiers(__injectCollectionHelper__().instanciate(childrenIdentifiers));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByChildrenBusinessIdentifiers(Collection<Object> childrenBusinessIdentifiers) {
		Properties properties = new Properties().setQueryIdentifier(childrenBusinessIdentifiers);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,childrenBusinessIdentifiers));
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByChildrenBusinessIdentifiers(Object... childrenBusinessIdentifiers) {
		return readByChildrenBusinessIdentifiers(__injectCollectionHelper__().instanciate(childrenBusinessIdentifiers));
	}
	
	@Override
	public HIERARCHIES readByChildren(Collection<ENTITY> children) {
		if(__injectCollectionHelper__().isNotEmpty(children))
			return readByChildrenIdentifiers(children.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(__injectCollectionHelper__().instanciate(children));
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_CHILDREN)};
			}
			return new Object[]{"parentIdentifiers",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByChildrenIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_PARENTS)};
			}
			return new Object[]{"childIdentifiers",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass, Properties properties, Object... parameters) {
		if(PersistenceFunctionReader.class.equals(functionClass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AbstractIdentifiedByString.FIELD_PARENTS)))
				return readByParentsIdentifiers;
			else if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AbstractIdentifiedByString.FIELD_CHILDREN)))
				return readByChildrenIdentifiers;
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<HIERARCHIES> __getCollectionInstanceClass__() {
		return (Class<HIERARCHIES>) __injectClassHelper__().getByName(__injectClassHelper__().getParameterAt(getClass(), 2, Object.class).getName());
	}
	
}
