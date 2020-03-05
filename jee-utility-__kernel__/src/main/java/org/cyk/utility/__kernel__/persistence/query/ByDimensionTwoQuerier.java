package org.cyk.utility.__kernel__.persistence.query;
import java.util.Collection;
import java.util.List;

import org.cyk.utility.__kernel__.field.FieldHelper;

public interface ByDimensionTwoQuerier<ENTITY,DIMENSION_1,DIMENSION_1_BUSINESS_IDENTIFIER,DIMENSION_2,DIMENSION_2_BUSINESS_IDENTIFIER> {

	/* read */
	
	Collection<ENTITY> readByDimension1BusinessIdentifierByDimension2BusinessIdentifier(Collection<DIMENSION_1_BUSINESS_IDENTIFIER> dimension1BusinessIdentifiers,Collection<DIMENSION_2_BUSINESS_IDENTIFIER> dimension2BusinessIdentifiers);
	
	default Collection<ENTITY> readByDimension1BusinessIdentifierByDimension2BusinessIdentifier(DIMENSION_1_BUSINESS_IDENTIFIER dimension1BusinessIdentifier,DIMENSION_2_BUSINESS_IDENTIFIER dimension2BusinessIdentifier) {
		return readByDimension1BusinessIdentifierByDimension2BusinessIdentifier(List.of(dimension1BusinessIdentifier),List.of(dimension2BusinessIdentifier));
	}
	
	default Collection<ENTITY> readByDimension1ByDimension2(DIMENSION_1 dimension1,DIMENSION_2 dimension2) {
		return readByDimension1BusinessIdentifierByDimension2BusinessIdentifier((DIMENSION_1_BUSINESS_IDENTIFIER)FieldHelper.readBusinessIdentifier(dimension1)
				,(DIMENSION_2_BUSINESS_IDENTIFIER)FieldHelper.readBusinessIdentifier(dimension2));
	}
	
	/* count */
	
	Long countByDimension1BusinessIdentifierByDimension2BusinessIdentifier(Collection<DIMENSION_1_BUSINESS_IDENTIFIER> dimension1BusinessIdentifiers,Collection<DIMENSION_2_BUSINESS_IDENTIFIER> dimension2BusinessIdentifiers);
	
	default Long countByDimension1BusinessIdentifierByDimension2BusinessIdentifier(DIMENSION_1_BUSINESS_IDENTIFIER dimension1BusinessIdentifier,DIMENSION_2_BUSINESS_IDENTIFIER dimension2BusinessIdentifier) {
		return countByDimension1BusinessIdentifierByDimension2BusinessIdentifier(List.of(dimension1BusinessIdentifier),List.of(dimension2BusinessIdentifier));
	}
	
	default Long countByDimension1ByDimension2(DIMENSION_1 dimension1,DIMENSION_2 dimension2) {
		return countByDimension1BusinessIdentifierByDimension2BusinessIdentifier((DIMENSION_1_BUSINESS_IDENTIFIER)FieldHelper.readBusinessIdentifier(dimension1)
				,(DIMENSION_2_BUSINESS_IDENTIFIER)FieldHelper.readBusinessIdentifier(dimension2));
	}
	
}