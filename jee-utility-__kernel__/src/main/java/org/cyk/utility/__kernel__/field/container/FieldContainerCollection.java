package org.cyk.utility.__kernel__.field.container;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueUsageType;

public interface FieldContainerCollection extends FieldContainer {

	default <T> Collection<T> fetchByIdentifiers(ValueUsageType valueUsageType,Collection<String> identifiers,Class<T> klass) {
		ThrowableHelper.throwIllegalArgumentExceptionIfNull("identifier value usage type", valueUsageType);
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		if(klass == null)
			throw new RuntimeException("fetch by code : class is required");
		Collection<T> collection = null;
		for(String index : identifiers) {
			T element = ValueUsageType.SYSTEM.equals(valueUsageType) ? getBySystemIdentifier(klass, index) : getByBusinessIdentifier(klass, index);
			if(element != null) {
				if(collection == null)
					collection = new ArrayList<>();
				collection.add(element);
			}
		}
		return collection;
	}
	
	default <T> Collection<T> fetchBySystemIdentifiers(Collection<String> identifiers,Class<T> klass) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return fetchByIdentifiers(ValueUsageType.SYSTEM, identifiers, klass);
	}
	
	default <T> Collection<T> fetchByBusinessIdentifiers(Collection<String> identifiers,Class<T> klass) {
		if(CollectionHelper.isEmpty(identifiers))
			return null;
		return fetchByIdentifiers(ValueUsageType.BUSINESS, identifiers, klass);
	}
	
}
