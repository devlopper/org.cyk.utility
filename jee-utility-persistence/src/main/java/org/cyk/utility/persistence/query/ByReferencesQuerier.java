package org.cyk.utility.persistence.query;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

@Deprecated
public interface ByReferencesQuerier<ENTITY,ENTITY_BUSINESS_IDENTIFIER,REFERENCE_ENTITY,REFERENCE_ENTITY_BUSINESS_IDENTIFIER> {

	/* read */
	
	Collection<ENTITY> readByBusinessIdentifiers(Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> businessIdentifiers,QueryExecutorArguments arguments);
	
	default Collection<ENTITY> readByBusinessIdentifiers(Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> businessIdentifiers) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(businessIdentifiers,null);
	}
	
	default Collection<ENTITY> readByBusinessIdentifiers(QueryExecutorArguments arguments,REFERENCE_ENTITY_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),arguments);
	}
	
	default Collection<ENTITY> readByBusinessIdentifiers(REFERENCE_ENTITY_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),null);
	}
	
	default Collection<ENTITY> read(Collection<REFERENCE_ENTITY> references,QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(references))
			return null;
		Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> identifiers = (Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER>) FieldHelper.readSystemIdentifiers(references); 
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return readByBusinessIdentifiers(identifiers, arguments);
	}
	
	default Collection<ENTITY> read(Collection<REFERENCE_ENTITY> references) {
		if(CollectionHelper.isEmpty(references))
			return null;
		return read(references,null);
	}
	
	default Collection<ENTITY> read(QueryExecutorArguments arguments,REFERENCE_ENTITY...references) {
		if(ArrayHelper.isEmpty(references))
			return null;
		return read(CollectionHelper.listOf(references),arguments);
	}
	
	default Collection<ENTITY> read(REFERENCE_ENTITY...references) {
		if(ArrayHelper.isEmpty(references))
			return null;
		return read(CollectionHelper.listOf(references),null);
	}
	
	/* count */
	
	Long countByBusinessIdentifiers(Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> businessIdentifiers,QueryExecutorArguments arguments);
	
	default Long countByBusinessIdentifiers(Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> businessIdentifiers) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(businessIdentifiers,null);
	}
	
	default Long countByBusinessIdentifiers(QueryExecutorArguments arguments,REFERENCE_ENTITY_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),arguments);
	}
	
	default Long countByBusinessIdentifiers(REFERENCE_ENTITY_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),null);
	}
	
	default Long count(Collection<REFERENCE_ENTITY> references,QueryExecutorArguments arguments) {
		if(CollectionHelper.isEmpty(references))
			return null;
		Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER> identifiers = (Collection<REFERENCE_ENTITY_BUSINESS_IDENTIFIER>) FieldHelper.readSystemIdentifiers(references); 
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return countByBusinessIdentifiers(identifiers, arguments);
	}
	
	default Long count(Collection<REFERENCE_ENTITY> references) {
		if(CollectionHelper.isEmpty(references))
			return null;
		return count(references,null);
	}
	
	default Long count(QueryExecutorArguments arguments,REFERENCE_ENTITY...references) {
		if(ArrayHelper.isEmpty(references))
			return null;
		return count(CollectionHelper.listOf(references),arguments);
	}
	
	default Long count(REFERENCE_ENTITY...references) {
		if(ArrayHelper.isEmpty(references))
			return null;
		return count(CollectionHelper.listOf(references),null);
	}
	
}