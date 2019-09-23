package org.cyk.utility.server.persistence.jpa;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.enterprise.context.Dependent;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.cyk.utility.__kernel__.computation.ComparisonOperator;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldName;
import org.cyk.utility.field.FieldNameGetter;
import org.cyk.utility.server.persistence.AbstractPersistenceFunctionReaderImpl;
import org.cyk.utility.server.persistence.PersistenceFunctionReader;
import org.cyk.utility.server.persistence.query.PersistenceQuery;
import org.cyk.utility.sql.builder.QueryWherePredicateStringBuilder;
import org.cyk.utility.sql.builder.Tuple;
import org.cyk.utility.sql.jpql.JpqlQualifier;
import org.cyk.utility.sql.jpql.builder.QueryStringBuilderSelectJpql;
import org.cyk.utility.sql.jpql.builder.QueryWherePredicateStringBuilderEqualJpql;
import org.cyk.utility.system.action.SystemAction;
import org.cyk.utility.system.action.SystemActionRead;
import org.cyk.utility.__kernel__.value.ValueUsageType;

@Dependent
public class PersistenceFunctionReaderImpl extends AbstractPersistenceFunctionReaderImpl implements PersistenceFunctionReader,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenPostConstruct__() {
		setAction(__inject__(SystemActionRead.class));
		super.__listenPostConstruct__();
	}
	
	@Override
	protected void __executeQuery__(SystemAction action) {
		EntityManager entityManager = __inject__(JavaPersistenceApiHelper.class).getEntityManager(getProperties());
		Class<?> aClass = getEntityClass();
		Object entityIdentifier = getEntityIdentifier();
		ValueUsageType valueUsageType = getEntityIdentifierValueUsageType();
		if(valueUsageType == null)
			valueUsageType = ValueUsageType.SYSTEM;
		String identifierFieldName = __inject__(FieldNameGetter.class).execute(aClass, FieldName.IDENTIFIER, valueUsageType).getOutput();
		Object entity;
		if(ValueUsageType.SYSTEM.equals(valueUsageType))
			entity = entityManager.find(aClass,entityIdentifier);
		else{
			Tuple tuple = new Tuple().setName(aClass.getSimpleName());
			QueryWherePredicateStringBuilder predicateBuilder = (QueryWherePredicateStringBuilder) JpqlQualifier.inject(QueryWherePredicateStringBuilderEqualJpql.class)
					.addOperandBuilderByAttribute(identifierFieldName,ComparisonOperator.EQ,tuple);
			
			QueryStringBuilderSelectJpql queryBuilder = JpqlQualifier.inject(QueryStringBuilderSelectJpql.class).from(tuple).where(predicateBuilder);
			
			Collection<?> objects = entityManager.createQuery(queryBuilder.execute().getOutput(), aClass).setParameter(identifierFieldName, entityIdentifier)
					//TODO should be take from parameters
					//.setHint("org.hibernate.readOnly", true)
					.getResultList();
			entity = CollectionHelper.getFirst(objects);
		}
		getProperties().setEntity(entity);
		if(entity == null)
			addLogMessageBuilderParameter(MESSAGE_NOT_FOUND);
		
		//__logWarning__("############### better to write a named query for select operation ###############");
	}
	
	@Override
	protected void __executeQuery__(SystemAction action, PersistenceQuery persistenceQuery) {
		EntityManager entityManager = __inject__(JavaPersistenceApiHelper.class).getEntityManager(getProperties());
		String identifier = persistenceQuery.getIdentifier() == null ? null : persistenceQuery.getIdentifier().toString();
		Class<?> resultClass = persistenceQuery.getResultClass();
		TypedQuery<?> typedQuery = __injectStringHelper__().isBlank(identifier) ? entityManager.createQuery(persistenceQuery.getValue(), resultClass) 
				: entityManager.createNamedQuery(identifier, resultClass);
		
		//Parameters
		Properties parameters = getQueryParameters();
		if(parameters != null && parameters.__getMap__()!=null)
			for(Map.Entry<Object, Object> entry : parameters.__getMap__().entrySet())
				typedQuery.setParameter(entry.getKey().toString(), entry.getValue());
		
		//Paging
		Boolean isQueryResultPaginated = getIsQueryResultPaginated();
		
		Long queryFirstTupleIndex = getQueryFirstTupleIndex();
		if(queryFirstTupleIndex == null && Boolean.TRUE.equals(isQueryResultPaginated)) {
			queryFirstTupleIndex = 0l;
		}
		if(queryFirstTupleIndex!=null)
			typedQuery.setFirstResult(queryFirstTupleIndex.intValue());
		
		Long queryNumberOfTuple = getQueryNumberOfTuple();
		if(queryNumberOfTuple == null && Boolean.TRUE.equals(isQueryResultPaginated)) {
			queryNumberOfTuple = 5l;
		}
		if(queryNumberOfTuple!=null)
			typedQuery.setMaxResults(queryNumberOfTuple.intValue());
		
		Collection<?> objects = typedQuery.getResultList();
		
		getProperties().setEntities(objects);
		if(CollectionHelper.isEmpty(objects)){
			//TODO log not found
		}	
	}
	
}
