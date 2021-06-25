package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.query.Query;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;
import org.cyk.utility.persistence.query.QueryManager;

public interface Reader<ENTITY,IDENTIFIER,RESULT> {

	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER,RESULT> extends AbstractObject implements Reader<ENTITY,IDENTIFIER,RESULT>,Serializable {
		
		//protected Boolean namedQueryRegistered;
		protected String queryIdentifier;
		
		protected void registerNamedQuery() {			
			//if(Boolean.TRUE.equals(namedQueryRegistered))
			//	return;		
			Class<ENTITY> entityClass = getEntityClass();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
			String queryName = getQueryName();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query name", queryName);			
			queryIdentifier = QueryIdentifierBuilder.getInstance().build(entityClass, queryName);
			if(QUERIES_IDENTIFIERS.contains(queryIdentifier))
				return;
			//EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();			
			String queryValue = getQueryValue();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query value", queryValue);
			//entityManagerFactory.addNamedQuery(queryIdentifier, Boolean.TRUE.equals(getIsNativeQuery()) 
			//		? entityManagerFactory.createEntityManager().createNativeQuery(queryValue) : entityManagerFactory.createEntityManager().createQuery(queryValue));
			QueryManager.getInstance().register(new Query().setIdentifier(queryIdentifier).setValue(queryValue).setIsNative(getIsNativeQuery()));
			//namedQueryRegistered = Boolean.TRUE;
			QUERIES_IDENTIFIERS.add(queryIdentifier);
		}
		
		protected Class<ENTITY> getEntityClass() {
			return (Class<ENTITY>) ClassHelper.getParameterAt(getClass(), 0);
		}
		
		protected String getQueryName() {
			return "read"+StringUtils.substringBetween(getClass().getSimpleName(), getEntityClass().getSimpleName(), "Reader");
		}
		
		protected abstract String getQueryValue();
		
		protected Boolean getIsNativeQuery() {
			return null;
		}
	}
	
	/**/
	
	Set<String> QUERIES_IDENTIFIERS = new HashSet<>();
	
	static void clear() {
		QUERIES_IDENTIFIERS.clear();
	}
}