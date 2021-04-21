package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.EntityManagerGetter;

public interface ArraysReaderByIdentifiers<ENTITY,IDENTIFIER> extends Reader<ENTITY, IDENTIFIER, Object[]> {

	Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters);
	Collection<Object[]> read(Map<String,Object> parameters,ENTITY...entities);
	Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters);
	Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER...identifiers);
	
	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER> extends Reader.AbstractImpl<ENTITY, IDENTIFIER, Object[]> implements ArraysReaderByIdentifiers<ENTITY,IDENTIFIER>,Serializable {
		
		@Override
		public Collection<Object[]> readByIdentifiers(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(identifiers))
				return null;
			return new ReaderByCollection.AbstractImpl<IDENTIFIER,Object[]>() {
				@Override
				public Collection<Object[]> __read__(Collection<IDENTIFIER> values) {
					return __readByIdentifiers__(values,parameters);
				}
			}.read(identifiers);
		}

		protected Collection<Object[]> __readByIdentifiers__(Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			registerNamedQuery();
			Query query = EntityManagerGetter.getInstance().get().createNamedQuery(queryIdentifier);
			setQueryParameters(query, identifiers,parameters);
			return query.getResultList();
		}
		
		protected void setQueryParameters(Query query,Collection<IDENTIFIER> identifiers,Map<String,Object> parameters) {
			query.setParameter("identifiers", identifiers);
		}
		
		@Override
		public Collection<Object[]> readByIdentifiers(Map<String,Object> parameters,IDENTIFIER... identifiers) {
			if(ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(CollectionHelper.listOf(identifiers),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Map<String,Object> parameters,ENTITY... entities) {
			if(ArrayHelper.isEmpty(entities))
				return null;
			return read(CollectionHelper.listOf(entities),parameters);
		}
		
		@Override
		public Collection<Object[]> read(Collection<ENTITY> entities,Map<String,Object> parameters) {
			if(CollectionHelper.isEmpty(entities))
				return null;
			return readByIdentifiers((Collection<IDENTIFIER>) FieldHelper.readSystemIdentifiers(entities),parameters);
		}
		
		/**/
		
		public static abstract class DefaultImpl<ENTITY> extends ArraysReaderByIdentifiers.AbstractImpl<ENTITY, String> implements Serializable {
			
		}
	}
}