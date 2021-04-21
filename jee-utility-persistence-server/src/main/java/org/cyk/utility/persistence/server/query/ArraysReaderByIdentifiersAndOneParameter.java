package org.cyk.utility.persistence.server.query;

import java.io.Serializable;
import java.util.Collection;

import javax.persistence.Query;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.persistence.EntityManagerGetter;

public interface ArraysReaderByIdentifiersAndOneParameter<ENTITY,IDENTIFIER,PARAMETER> extends Reader<ENTITY, IDENTIFIER, Object[]> {

	Collection<Object[]> read(PARAMETER parameter,Collection<ENTITY> entities);
	Collection<Object[]> read(PARAMETER parameter,ENTITY...entities);
	Collection<Object[]> readByIdentifiers(PARAMETER parameter,Collection<IDENTIFIER> identifiers);
	Collection<Object[]> readByIdentifiers(PARAMETER parameter,IDENTIFIER...identifiers);
	
	/**/
	
	public static abstract class AbstractImpl<ENTITY,IDENTIFIER,PARAMETER> extends Reader.AbstractImpl<ENTITY, IDENTIFIER, Object[]> implements ArraysReaderByIdentifiersAndOneParameter<ENTITY,IDENTIFIER,PARAMETER>,Serializable {
		
		@Override
		public Collection<Object[]> readByIdentifiers(PARAMETER parameter,Collection<IDENTIFIER> identifiers) {
			if(parameter == null || CollectionHelper.isEmpty(identifiers))
				return null;
			return new ReaderByCollection<IDENTIFIER,Object[]>() {
				@Override
				public Collection<Object[]> read(Collection<IDENTIFIER> values) {
					return __readByIdentifiers__(parameter,values);
				}
			}.read(identifiers);
		}

		protected Collection<Object[]> __readByIdentifiers__(PARAMETER parameter,Collection<IDENTIFIER> identifiers) {
			registerNamedQuery();
			Query query = EntityManagerGetter.getInstance().get().createNamedQuery(queryIdentifier);
			setQueryParameters(query,parameter, identifiers);
			return query.getResultList();
		}
		
		protected void setQueryParameters(Query query,PARAMETER parameter,Collection<IDENTIFIER> identifiers) {
			query.setParameter("identifiers", identifiers);
		}
		
		@Override
		public Collection<Object[]> readByIdentifiers(PARAMETER parameter,IDENTIFIER... identifiers) {
			if(parameter == null || ArrayHelper.isEmpty(identifiers))
				return null;
			return readByIdentifiers(parameter,CollectionHelper.listOf(identifiers));
		}
		
		@Override
		public Collection<Object[]> read(PARAMETER parameter,Collection<ENTITY> entities) {
			if(parameter == null || CollectionHelper.isEmpty(entities))
				return null;
			return readByIdentifiers(parameter,(Collection<IDENTIFIER>) FieldHelper.readSystemIdentifiers(entities));
		}
		
		@Override
		public Collection<Object[]> read(PARAMETER parameter,ENTITY... entities) {
			if(parameter == null || ArrayHelper.isEmpty(entities))
				return null;
			return read(parameter,CollectionHelper.listOf(entities));
		}
		
		/**/
		
		public static abstract class DefaultImpl<ENTITY,PARAMETER> extends ArraysReaderByIdentifiersAndOneParameter.AbstractImpl<ENTITY, String,PARAMETER> implements Serializable {
			
		}
	}
}