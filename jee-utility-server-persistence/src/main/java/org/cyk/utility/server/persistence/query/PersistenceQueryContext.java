package org.cyk.utility.server.persistence.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.server.persistence.query.filter.Field;
import org.cyk.utility.server.persistence.query.filter.Filter;

public interface PersistenceQueryContext extends Objectable {

	PersistenceQuery getQuery();
	PersistenceQueryContext setQuery(PersistenceQuery query);
	
	Filter getFilter();
	PersistenceQueryContext setFilter(Filter filter);

	Object[] getParameters();
	PersistenceQueryContext setParameters(Object[] parameters);
	
	
	Boolean isFilterByKeys(String...keys);
	Object getFilterByKeysValue(String...keys);
	Field getFilterFieldByKeys(String... keys);
	
	default Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass,String fieldName) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("field name", fieldName);
		Collection<String> codes = (Collection<String>) getFilterByKeysValue(fieldName);
		if(CollectionHelper.isEmpty(codes)) {
			if(codes == null)
				codes = new ArrayList<>();
			codes.addAll(DependencyInjection.inject(Persistence.class).read(entityClass).stream().map(entity -> ((IdentifiableBusiness<String>)entity)
					.getBusinessIdentifier()).collect(Collectors.toList()));
		}
		return codes;
	}
	
	default Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		return getCodes(entityClass,StringHelper.getVariableNameFrom(entityClass.getSimpleName()));
	}
	
	default String getStringLike(String fieldName) {
		org.cyk.utility.server.persistence.query.filter.Field field = getFilterFieldByKeys(fieldName);				
		return "%"+(field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue()))+"%";
	}
	
	default Collection<String> getStrings(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		org.cyk.utility.server.persistence.query.filter.Field field = getFilterFieldByKeys(fieldName);
		if(field == null || field.getValue() == null)
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		if(field.getValue() instanceof Collection)
			return (Collection<String>) field.getValue();
		return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
	}
}
