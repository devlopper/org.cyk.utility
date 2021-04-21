package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.klass.ClassHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.persistence.EntityManagerFactoryGetter;
import org.cyk.utility.persistence.EntityManagerGetter;
import org.cyk.utility.persistence.query.QueryIdentifierBuilder;

public interface ArraysReaderByIdentifiersOLD<ENTITY,IDENTIFIER> {

	Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers);
	Collection<Object[]> readByIdentifiers(IDENTIFIER...identifiers);
	Collection<Object[]> read(Collection<ENTITY> entities);
	Collection<Object[]> read(ENTITY...entities);
	
	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER> extends AbstractObject implements ArraysReaderByIdentifiersOLD<ENTITY,IDENTIFIER>,Serializable {
		
		protected Boolean namedQueryRegistered;
		protected String queryIdentifier;
		
		@Override
		public Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			return new ReaderByCollection<IDENTIFIER,Object[]>() {
				@Override
				public Collection<Object[]> read(Collection<IDENTIFIER> values) {
					return __readByIdentifiers__(values);
				}
			}.read(identifiers);
		}
		
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
		
		protected Collection<Object[]> __readByIdentifiers__(Collection<IDENTIFIER> identifiers) {
			registerNamedQuery();
			Query query = EntityManagerGetter.getInstance().get().createNamedQuery(queryIdentifier);
			setQueryParameters(query, identifiers);
			return query.getResultList();
		}
		
		protected void setQueryParameters(Query query,Collection<IDENTIFIER> identifiers) {
			query.setParameter("identifiers", identifiers);
		}
		
		@Override
		public Collection<Object[]> readByIdentifiers(IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(CollectionHelper.listOf(identifiers));
		}
		
		@Override
		public Collection<Object[]> read(Collection<ENTITY> entities) {
			if(CollectionHelper.isEmpty(entities))
				return null;
			return readByIdentifiers((Collection<IDENTIFIER>) FieldHelper.readSystemIdentifiers(entities));
		}
		
		@Override
		public Collection<Object[]> read(ENTITY... entities) {
			if(ArrayHelper.isEmpty(entities))
				return null;
			return read(CollectionHelper.listOf(entities));
		}
		
		/**/
		
		public static abstract class DefaultImpl<ENTITY> extends AbstractImpl<ENTITY, String> implements Serializable {
			
		}
	}
}