package org.cyk.utility.persistence.server.query;

import java.io.Serializable;

import javax.persistence.EntityManagerFactory;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.EntityManagerFactoryGetter;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;

public interface Reader<ENTITY,IDENTIFIER,RESULT> {

	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER,RESULT> extends AbstractObject implements Reader<ENTITY,IDENTIFIER,RESULT>,Serializable {
		
		protected Boolean namedQueryRegistered;
		protected String queryIdentifier;
		
		protected void registerNamedQuery() {
			if(Boolean.TRUE.equals(namedQueryRegistered))
				return;
			EntityManagerFactory entityManagerFactory = EntityManagerFactoryGetter.getInstance().get();
			Class<ENTITY> entityClass = getEntityClass();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
			String queryName = getQueryName();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query name", queryName);			
			queryIdentifier = QueryIdentifierBuilder.getInstance().build(entityClass, queryName);
			String queryValue = getQueryValue();
			ThrowableHelper.throwIllegalArgumentExceptionIfBlank("query value", queryValue);
			entityManagerFactory.addNamedQuery(queryIdentifier, Boolean.TRUE.equals(getIsNativeQuery()) 
					? entityManagerFactory.createEntityManager().createNativeQuery(queryValue) : entityManagerFactory.createEntityManager().createQuery(queryValue));
			namedQueryRegistered = Boolean.TRUE;
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
}