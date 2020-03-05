package org.cyk.utility.__kernel__.persistence.query;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.persistence.query.QueryExecutor.Arguments;

public interface ByDimensionOneQuerier<ENTITY,ENTITY_BUSINESS_IDENTIFIER,DIMENSION,DIMENSION_BUSINESS_IDENTIFIER> {

	/* read */
	
	Collection<ENTITY> readByBusinessIdentifiers(Collection<DIMENSION_BUSINESS_IDENTIFIER> businessIdentifiers,Arguments arguments);
	
	default Collection<ENTITY> readByBusinessIdentifiers(Collection<DIMENSION_BUSINESS_IDENTIFIER> businessIdentifiers) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(businessIdentifiers,null);
	}
	
	default Collection<ENTITY> readByBusinessIdentifiers(Arguments arguments,DIMENSION_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),arguments);
	}
	
	default Collection<ENTITY> readByBusinessIdentifiers(DIMENSION_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return readByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),null);
	}
	
	default Collection<ENTITY> read(Collection<DIMENSION> dimensions,Arguments arguments) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		Collection<DIMENSION_BUSINESS_IDENTIFIER> identifiers = (Collection<DIMENSION_BUSINESS_IDENTIFIER>) FieldHelper.readSystemIdentifiers(dimensions); 
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return readByBusinessIdentifiers(identifiers, arguments);
	}
	
	default Collection<ENTITY> read(Collection<DIMENSION> dimensions) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		return read(dimensions,null);
	}
	
	default Collection<ENTITY> read(Arguments arguments,DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return read(CollectionHelper.listOf(dimensions),arguments);
	}
	
	default Collection<ENTITY> read(DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return read(CollectionHelper.listOf(dimensions),null);
	}
	
	/* count */
	
	Long countByBusinessIdentifiers(Collection<DIMENSION_BUSINESS_IDENTIFIER> businessIdentifiers,Arguments arguments);
	
	default Long countByBusinessIdentifiers(Collection<DIMENSION_BUSINESS_IDENTIFIER> businessIdentifiers) {
		if(CollectionHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(businessIdentifiers,null);
	}
	
	default Long countByBusinessIdentifiers(Arguments arguments,DIMENSION_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),arguments);
	}
	
	default Long countByBusinessIdentifiers(DIMENSION_BUSINESS_IDENTIFIER...businessIdentifiers) {
		if(ArrayHelper.isEmpty(businessIdentifiers))
			return null;
		return countByBusinessIdentifiers(CollectionHelper.listOf(businessIdentifiers),null);
	}
	
	default Long count(Collection<DIMENSION> dimensions,Arguments arguments) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		Collection<DIMENSION_BUSINESS_IDENTIFIER> identifiers = (Collection<DIMENSION_BUSINESS_IDENTIFIER>) FieldHelper.readSystemIdentifiers(dimensions); 
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return countByBusinessIdentifiers(identifiers, arguments);
	}
	
	default Long count(Collection<DIMENSION> dimensions) {
		if(CollectionHelper.isEmpty(dimensions))
			return null;
		return count(dimensions,null);
	}
	
	default Long count(Arguments arguments,DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return count(CollectionHelper.listOf(dimensions),arguments);
	}
	
	default Long count(DIMENSION...dimensions) {
		if(ArrayHelper.isEmpty(dimensions))
			return null;
		return count(CollectionHelper.listOf(dimensions),null);
	}
	
}