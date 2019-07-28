package org.cyk.utility.server.persistence.hierarchy;
import java.io.Serializable;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQueryContext;

public abstract class AbstractHierarchyPersistenceImpl<HIERARCHY extends AbstractHierarchy<ENTITY>,ENTITY extends AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical<?,?>,HIERARCHIES extends HierarchyCollectionInstance<ENTITY, HIERARCHY>> extends AbstractPersistenceEntityImpl<HIERARCHY> implements HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>,Serializable {
	private static final long serialVersionUID = 1L;

	private String readByParentsCodes,readByChildrenCodes;
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readByParentsCodes, String.format("SELECT tuple FROM %s tuple WHERE tuple.parent.code IN :parentsCodes", __getTupleName__()));
		addQueryCollectInstances(readByChildrenCodes, String.format("SELECT tuple FROM %s tuple WHERE tuple.child.code IN :childrenCodes", __getTupleName__()));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByParentsCodes(Collection<String> parentsCodes) {
		Properties properties = new Properties().setQueryIdentifier(readByParentsCodes);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,parentsCodes));
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByParentsCodes(String... parentsCodes) {
		return readByParentsCodes(__injectCollectionHelper__().instanciate(parentsCodes));
	}
	
	@Override
	public HIERARCHIES readByParents(Collection<ENTITY> parents) {
		if(__injectCollectionHelper__().isNotEmpty(parents))
			return readByParentsCodes(parents.stream().map(AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical::getCode).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(__injectCollectionHelper__().instanciate(parents));
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public HIERARCHIES readByChildrenCodes(Collection<String> childrenCodes) {
		Properties properties = new Properties().setQueryIdentifier(readByChildrenCodes);
		Collection<HIERARCHY> hierarchies = __readMany__(properties,____getQueryParameters____(properties,childrenCodes));
		return __injectCollectionHelper__().isEmpty(hierarchies) ? null : (HIERARCHIES) __inject__(__getCollectionInstanceClass__()).add(hierarchies);
	}
	
	@Override
	public HIERARCHIES readByChildrenCodes(String... childrenCodes) {
		return readByChildrenCodes(__injectCollectionHelper__().instanciate(childrenCodes));
	}
	
	@Override
	public HIERARCHIES readByChildren(Collection<ENTITY> children) {
		if(__injectCollectionHelper__().isNotEmpty(children))
			return readByChildrenCodes(children.stream().map(AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical::getCode).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public HIERARCHIES readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(__injectCollectionHelper__().instanciate(children));
	}
	
	@Override
	protected Object[] __getQueryParameters__(PersistenceQueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsCodes)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical.FIELD_CHILDREN)};
			}
			return new Object[]{"parentsCodes",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByChildrenCodes)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical.FIELD_PARENTS)};
			}
			return new Object[]{"childrenCodes",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass, Properties properties, Object... parameters) {
		if(PersistenceFunctionReader.class.equals(functionClass)) {
			if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical.FIELD_PARENTS)))
				return readByParentsCodes;
			else if(Boolean.TRUE.equals(__isFilterByKeys__(properties, AbstractIdentifiedByStringAndCodedAndNamedAndHierarchical.FIELD_CHILDREN)))
				return readByChildrenCodes;
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@SuppressWarnings("unchecked")
	protected Class<HIERARCHIES> __getCollectionInstanceClass__() {
		return (Class<HIERARCHIES>) __injectClassHelper__().getByName(__injectClassHelper__().getParameterAt(getClass(), 2, Object.class).getName());
	}
	
}
