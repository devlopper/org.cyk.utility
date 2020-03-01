package org.cyk.utility.__kernel__.persistence.query;
import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;
import org.cyk.utility.__kernel__.properties.Properties;
import org.cyk.utility.__kernel__.string.StringHelper;

public interface QueryByReferences<REFERENCE,ENTITY,ENTITY_BUSINESS_IDENTIFIER> {

	/* read */
	
	Collection<ENTITY> readByBusinessIdentifiers(Collection<ENTITY_BUSINESS_IDENTIFIER> businessIdentifiers);
	/*
	default Collection<ENTITY> readByAdministrativeUnitsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(codes,null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnitsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnitsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return readByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Collection<REFERENCE> administrativeUnits,Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnitsCodes(administrativeUnits.stream().map(reference -> FieldHelper.readBusinessIdentifier(reference) ).collect(Collectors.toList()), properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Collection<REFERENCE> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(administrativeUnits,null);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(Properties properties,REFERENCE...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),properties);
	}
	
	default Collection<ENTITY> readByAdministrativeUnits(REFERENCE...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return readByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),null);
	}
	*/
	/* count */
	/*
	Long countByAdministrativeUnitsCodes(Collection<String> codes,Properties properties);
	
	default Long countByAdministrativeUnitsCodes(Collection<String> codes) {
		if(CollectionHelper.isEmpty(codes))
			return null;
		return countByAdministrativeUnitsCodes(codes,null);
	}
	
	default Long countByAdministrativeUnitsCodes(Properties properties,String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return countByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),properties);
	}
	
	default Long countByAdministrativeUnitsCodes(String...codes) {
		if(ArrayHelper.isEmpty(codes))
			return null;
		return countByAdministrativeUnitsCodes(CollectionHelper.listOf(codes),null);
	}
	
	default Long countByAdministrativeUnits(Collection<REFERENCE> administrativeUnits,Properties properties) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return countByAdministrativeUnitsCodes(administrativeUnits.stream().map(AdministrativeUnit::getCode).collect(Collectors.toList()), properties);
	}
	
	default Long countByAdministrativeUnits(Collection<REFERENCE> administrativeUnits) {
		if(CollectionHelper.isEmpty(administrativeUnits))
			return null;
		return countByAdministrativeUnits(administrativeUnits,null);
	}
	
	default Long countByAdministrativeUnits(Properties properties,REFERENCE...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return countByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),properties);
	}
	
	default Long countByAdministrativeUnits(REFERENCE...administrativeUnits) {
		if(ArrayHelper.isEmpty(administrativeUnits))
			return null;
		return countByAdministrativeUnits(CollectionHelper.listOf(administrativeUnits),null);
	}
	*/
}