package org.cyk.utility.server.persistence.jpa.hierarchy;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import static org.cyk.utility.__kernel__.klass.ClassHelper.getTupleName;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.array.ArrayHelper;
import org.cyk.utility.server.persistence.AbstractPersistenceEntityImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.PersistenceQueryIdentifierStringBuilder;
import org.cyk.utility.persistence.query.QueryContext;
import org.cyk.utility.persistence.query.Field;
import org.cyk.utility.persistence.query.Filter;
import org.cyk.utility.__kernel__.string.Strings;


public abstract class AbstractPersistenceIdentifiedByStringImpl<ENTITY extends AbstractIdentifiedByString<ENTITY>,HIERARCHY extends AbstractHierarchy<ENTITY>,HIERARCHIES extends Hierarchies<HIERARCHY,ENTITY>,HIERARCHY_PERSISTENCE extends HierarchyPersistence<HIERARCHY,ENTITY, HIERARCHIES>> extends AbstractPersistenceEntityImpl<ENTITY> implements PersistenceIdentifiedByString<ENTITY>,Serializable {
	private static final long serialVersionUID = 1L;

	protected String readWhereNotHavingParent,readWhereNotHavingChild,readByParentsIdentifiers,readByChildrenIdentifiers;
	protected Class<HIERARCHY> __hierarchyClass__;
	protected Class<HIERARCHY_PERSISTENCE> __hierarchyPersistenceClass__;
	
	@Override
	protected void __listenBeforePostConstruct__() {
		super.__listenBeforePostConstruct__();
		__hierarchyClass__ = (Class<HIERARCHY>) ClassHelper.getParameterAt(getClass(), 1);
		__hierarchyPersistenceClass__ = (Class<HIERARCHY_PERSISTENCE>) ClassHelper.getParameterAt(getClass(), 3);
	}
	
	@Override
	protected void __listenPostConstructPersistenceQueries__() {
		super.__listenPostConstructPersistenceQueries__();
		addQueryCollectInstances(readWhereNotHavingParent, String.format("SELECT node FROM %1$s node "
				+ "WHERE EXISTS(SELECT tuple FROM %2$s tuple WHERE tuple.parent = node AND NOT EXISTS(SELECT subTuple FROM %2$s subTuple WHERE subTuple.child = tuple.parent))"
				,__tupleName__,getTupleName(__hierarchyClass__)));
		addQueryCollectInstances(readByParentsIdentifiers, String.format("SELECT node FROM %1$s node WHERE EXISTS(SELECT tuple FROM %2$s tuple WHERE tuple.child = node "
				+ "AND tuple.parent.identifier IN :parentsIdentifiers)",__tupleName__,getTupleName(__hierarchyClass__)));
		
		//TODO it has been copy paste , think and do the necessary
		addQueryCollectInstances(readWhereNotHavingChild, String.format("SELECT node FROM %1$s node "
				+ "WHERE EXISTS(SELECT tuple FROM %2$s tuple WHERE tuple.parent = node AND NOT EXISTS(SELECT subTuple FROM %2$s subTuple WHERE subTuple.child = tuple.parent))"
				,__tupleName__,getTupleName(__hierarchyClass__)));
		
		addQueryCollectInstances(readByChildrenIdentifiers, String.format(
				"SELECT node FROM %1$s node WHERE EXISTS("
				+ "SELECT tuple FROM %2$s tuple WHERE tuple.parent = node AND tuple.child.identifier IN :childrenIdentifiers"
				+ ")"
				//Arguments
				,__tupleName__,getTupleName(__hierarchyClass__)
				));
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers,Properties properties) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByParentsIdentifiers(parentsIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyChildren();	
	}
	
	@Override
	public Long countByParentsIdentifiers(Collection<String> parentsIdentifiers, Properties properties) {
		return __inject__(__hierarchyPersistenceClass__).countByParentsIdentifiers(parentsIdentifiers,properties);
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		return readByParentsIdentifiers(parentsIdentifiers,null);	
	}
	
	@Override
	public Long countByParentsIdentifiers(Collection<String> parentsIdentifiers) {
		return countByParentsIdentifiers(parentsIdentifiers, null);
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(Properties properties,String... parentsIdentifiers) {
		return readByParentsIdentifiers(List.of(parentsIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByParentsIdentifiers(String... parentsIdentifiers) {
		return readByParentsIdentifiers(null,parentsIdentifiers);
	}
	
	@Override
	public Collection<ENTITY> readByParents(Collection<ENTITY> parents,Properties properties) {
		if(CollectionHelper.isNotEmpty(parents))
			return readByParentsIdentifiers(parents.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByParents(Collection<ENTITY> parents) {
		return readByParents(parents,null);
	}
	
	@Override
	public Collection<ENTITY> readByParents(@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(List.of(parents));
	}
	
	@Override
	public Collection<ENTITY> readByParents(Properties properties,@SuppressWarnings("unchecked") ENTITY... parents) {
		return readByParents(null,parents);
	}
	
	@Override
	public Collection<ENTITY> readWhereNotHavingParent(Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIfNull(Properties.QUERY_IDENTIFIER,readWhereNotHavingParent);
		return __readMany__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public Long countWhereNotHavingParent(Properties properties) {
		if(properties == null)
			properties = new Properties();
		properties.setIsQueryResultPaginated(null);
		properties.setQueryFirstTupleIndex(null);
		properties.setQueryNumberOfTuple(null);
		if(properties.getQueryIdentifier() == null) {
			String queryIdentifier = __inject__(PersistenceQueryIdentifierStringBuilder.class).setIsDerivedFromQueryIdentifier(Boolean.TRUE)
					.setDerivedFromQueryIdentifier(readWhereNotHavingParent).setIsCountInstances(Boolean.TRUE)
					.execute().getOutput();
			properties.setQueryIdentifier(queryIdentifier);
		}
		return __count__(properties,____getQueryParameters____(properties));
	}
	
	@Override
	public Collection<ENTITY> readWhereNotHavingParent() {
		return readWhereNotHavingParent(null);
	}
	
	@Override
	public Long countWhereNotHavingParent() {
		return countWhereNotHavingParent(null);
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers,Properties properties) {
		HIERARCHIES hierarchies = __inject__(__hierarchyPersistenceClass__).readByChildrenIdentifiers(childrenIdentifiers);
		return hierarchies == null ? null : hierarchies.getHierarchyParents();	
	}

	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(Collection<String> childrenIdentifiers) {
		return readByChildrenIdentifiers(childrenIdentifiers,null);	
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(Properties properties,String... childrenIdentifiers) {
		return readByChildrenIdentifiers(List.of(childrenIdentifiers));
	}
	
	@Override
	public Collection<ENTITY> readByChildrenIdentifiers(String... childrenIdentifiers) {
		return readByChildrenIdentifiers(null,childrenIdentifiers);
	}
	
	@Override
	public Collection<ENTITY> readByChildren(Collection<ENTITY> children,Properties properties) {
		if(CollectionHelper.isNotEmpty(children))
			return readByChildrenIdentifiers(children.stream().map(AbstractIdentifiedByString::getIdentifier).collect(Collectors.toList()));
		return null;
	}
	
	@Override
	public Collection<ENTITY> readByChildren(Collection<ENTITY> children) {
		return readByChildren(children,null);
	}
	
	@Override
	public Collection<ENTITY> readByChildren(Properties properties,@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(List.of(children));
	}
	
	@Override
	public Collection<ENTITY> readByChildren(@SuppressWarnings("unchecked") ENTITY... children) {
		return readByChildren(null,children);
	}
	/*
	@Override
	public Collection<ENTITY> read(Properties properties) {
		Filter filter = (Filter) Properties.getFromPath(properties,Properties.QUERY_FILTERS);
		if(filter != null) {
			Field field = filter.getFieldByPath(AbstractIdentifiedByString.FIELD_PARENTS);
			if(field != null) {
				if(field.getValue() == null)
					return __readByFilterNoParent__(properties);
				else
					return __readByFilterParents__(properties,filter,field);
			}
		}
		return super.read(properties);
	}
	*/
	@SuppressWarnings("unchecked")
	public Collection<ENTITY> __readByFilterParents__(Properties properties,Filter filter,Field field) {
		return readByParentsIdentifiers((Collection<String>) field.getValue(),properties);
	}
	
	public Collection<ENTITY> __readByFilterNoParent__(Properties properties) {
		return readWhereNotHavingParent(properties);
	}
	
	@Override
	public Long count(Properties properties) {
		Filter filter = (Filter) Properties.getFromPath(properties,Properties.QUERY_FILTERS);
		if(filter != null) {
			Field field = filter.getFieldByPath(AbstractIdentifiedByString.FIELD_PARENTS);
			if(field != null) {
				if(field.getValue() == null)
					return __countByFilterNoParent__(properties);
				else
					return __countByFilterParents__(properties,filter,field);
			}
		}
		return super.count(properties);
	}
	
	@SuppressWarnings("unchecked")
	public Long __countByFilterParents__(Properties properties,Filter filter,Field field) {
		return countByParentsIdentifiers((Collection<String>) field.getValue());
	}
	
	public Long __countByFilterNoParent__(Properties properties) {
		return countWhereNotHavingParent(properties);
	}
	
	@Override
	protected void __listenExecuteReadAfter__(ENTITY entity,Properties properties) {
		super.__listenExecuteReadAfter__(entity,properties);
		Strings fields = __getFieldsFromProperties__(properties);
		if(CollectionHelper.isNotEmpty(fields))
			fields.get().forEach(new Consumer<String>() {
				@SuppressWarnings("unchecked")
				@Override
				public void accept(String field) {
					if(AbstractIdentifiedByString.FIELD_PARENTS.equals(field)) {
						Collection<ENTITY> parents = readByChildren(entity);
						if(CollectionHelper.isNotEmpty(parents))
							entity.addParents(parents);
					}else if(AbstractIdentifiedByString.FIELD_CHILDREN.equals(field)) {
						Collection<ENTITY> children = readByParents(entity);
						if(CollectionHelper.isNotEmpty(children))
							entity.addChildren(children);
					}else if(AbstractIdentifiedByString.FIELD_NUMBER_OF_CHILDREN.equals(field)) {
						entity.setNumberOfChildren(countByParentsIdentifiers(Arrays.asList(entity.getIdentifier())));
					}
				}
			});
	}
	
	@Override
	protected String __getQueryIdentifier__(Class<?> functionClass, Properties properties, Object... parameters) {
		Filter filter = (Filter) Properties.getFromPath(properties,Properties.QUERY_FILTERS);
		if(PersistenceFunctionReader.class.equals(functionClass)) {
			org.cyk.utility.persistence.query.Field field = filter == null? null : filter.getFieldByPath(AbstractIdentifiedByString.FIELD_PARENTS);
			if(field != null) {
				if(field.getValue() == null)
					return readWhereNotHavingParent;
				else
					return readByParentsIdentifiers;
			}else {
				field = filter == null? null : filter.getFieldByPath(AbstractIdentifiedByString.FIELD_CHILDREN);
				if(field != null) {
					if(field.getValue() == null)
						return readWhereNotHavingChild;
					else
						return readByChildrenIdentifiers;
				}
			}
		}
		return super.__getQueryIdentifier__(functionClass, properties, parameters);
	}
	
	@Override
	protected Object[] __getQueryParameters__(QueryContext queryContext, Properties properties,Object... objects) {
		if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByParentsIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_PARENTS)};
			}
			return new Object[]{"parentsIdentifiers",objects[0]};
		}else if(queryContext.getQuery().isIdentifierEqualsToOrQueryDerivedFromQueryIdentifierEqualsTo(readByChildrenIdentifiers)) {
			if(__inject__(ArrayHelper.class).isEmpty(objects)) {
				objects = new Object[] {queryContext.getFilterByKeysValue(AbstractIdentifiedByString.FIELD_CHILDREN)};
			}
			return new Object[]{"childrenIdentifiers",objects[0]};
		}
		return super.__getQueryParameters__(queryContext, properties, objects);
	}

}
