package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import javax.persistence.EntityManager;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.PersistenceHelper;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryManager;
import org.cyk.utility.persistence.server.query.string.QueryStringBuilder;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

public interface Reader<ENTITY,IDENTIFIER,RESULT> {

	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER,RESULT> extends AbstractObject implements Reader<ENTITY,IDENTIFIER,RESULT>,Serializable {
		
		//protected Boolean namedQueryRegistered;
		protected String queryIdentifier;
		@Getter @Setter @Accessors(chain=true) protected EntityManager entityManager;
		
		protected void registerNamedQuery() {			
			//if(Boolean.TRUE.equals(namedQueryRegistered))
			//	return;		
			Class<ENTITY> entityClass = getEntityClass();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
			String queryName = getQueryName();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query name", queryName);			
			queryIdentifier = QueryIdentifierBuilder.getInstance().build(entityClass, queryName);
			//if(QUERIES_IDENTIFIERS.contains(queryIdentifier))
			//	return;
			if(Boolean.TRUE.equals(QueryManager.getInstance().isQueryRegistered(queryIdentifier)))
				return;
			//EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();			
			String queryValue = getQueryValue();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query value", queryValue);
			//entityManagerFactory.addNamedQuery(queryIdentifier, Boolean.TRUE.equals(getIsNativeQuery()) 
			//		? entityManagerFactory.createEntityManager().createNativeQuery(queryValue) : entityManagerFactory.createEntityManager().createQuery(queryValue));
			QueryManager.getInstance().register(new Query().setIdentifier(queryIdentifier).setValue(queryValue).setIsNative(getIsNativeQuery()));
			//namedQueryRegistered = Boolean.TRUE;
			//QUERIES_IDENTIFIERS.add(queryIdentifier);
		}
		
		protected Class<ENTITY> getEntityClass() {
			return (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		}
		
		protected String getQueryName() {
			return "read"+StringUtils.substringBetween(getClass().getSimpleName(), getEntityClass().getSimpleName(), "Reader");
		}
		
		protected String getQueryValue() {
			QueryStringBuilder.Arguments arguments = instantiateQueryStringBuilderArguments();
			ThrowableHelper.throwIllegalArgumentExceptionIfNull("Query value string builder arguments required", arguments);
			return QueryStringBuilder.getInstance().build(arguments);
		}
		
		protected Boolean getIsNativeQuery() {
			return null;
		}
		
		protected Boolean isRegisterable() {
			return Boolean.TRUE;
		}
		
		protected javax.persistence.Query instantiateQuery() {
			javax.persistence.Query query;
			if(StringHelper.isBlank(queryIdentifier) && Boolean.TRUE.equals(isRegisterable()))
				registerNamedQuery();
			if(entityManager == null)
				entityManager = EntityManagerGetter.getInstance().get();
			if(StringHelper.isBlank(queryIdentifier))
				query = entityManager.createQuery(getQueryValue());			
			else
				query = entityManager.createNamedQuery(queryIdentifier);
			entityManager = null;
			return query;
		}
		
		protected String getEntityName(Class<?> klass) {
			if(klass == null)
				return null;
			return PersistenceHelper.getEntityName(klass);
		}
	
		protected QueryStringBuilder.Arguments instantiateQueryStringBuilderArguments() {
			QueryStringBuilder.Arguments arguments = new QueryStringBuilder.Arguments();
			arguments.getTuple(Boolean.TRUE).add(getEntityClass());
			return arguments;
		}
	}
	
	/**/
	/*
	Set<String> QUERIES_IDENTIFIERS = new HashSet<>();
	
	static void clear() {
		QUERIES_IDENTIFIERS.clear();
	}*/
}