package org.cyk.utility.server.persistence.query;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.DependencyInjection;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.server.persistence.Persistence;
import org.cyk.utility.__kernel__.persistence.query.filter.Field;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;

@Deprecated
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
	
	default Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass,String fieldName,Boolean readIfEmpty) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("field name", fieldName);
		Collection<String> codes = (Collection<String>) getFilterByKeysValue(fieldName);
		if(CollectionHelper.isEmpty(codes) && Boolean.TRUE.equals(readIfEmpty)) {
			if(codes == null)
				codes = new ArrayList<>();
			codes.addAll(DependencyInjection.inject(Persistence.class).read(entityClass).stream().map(entity -> ((IdentifiableBusiness<String>)entity)
					.getBusinessIdentifier()).collect(Collectors.toList()));
		}
		return codes;
	}
	
	default Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		return getCodes(entityClass,StringHelper.getVariableNameFrom(entityClass.getSimpleName()),Boolean.TRUE);
	}
	
	default String getStringLike(String fieldName) {
		Field field = getFilterFieldByKeys(fieldName);				
		return "%"+(field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue()))+"%";
	}
	
	default List<String> getStringsLike(String fieldName,Integer numberOfToken) {
		numberOfToken = numberOfToken + 1;
		List<String> list = new ArrayList<>();
		Field field = getFilterFieldByKeys(fieldName);
		String string = field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue());
		list.add("%"+ValueHelper.defaultToIfBlank(string, ConstantEmpty.STRING)+"%");
		String[] tokens = StringUtils.split(string, ConstantCharacter.SPACE);
		if(ArrayHelper.isNotEmpty(tokens)) {
			for(Integer index = 0; index<tokens.length && index < numberOfToken; index = index + 1)
				list.add("%"+tokens[index]+"%");
		}
		if(list.size() < numberOfToken)
			for(Integer index = list.size(); index <= numberOfToken; index = index + 1)
				list.add("%%");
		return list;
	}
	
	default Collection<String> getStrings(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		Field field = getFilterFieldByKeys(fieldName);
		if(field == null || field.getValue() == null)
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		if(field.getValue() instanceof Collection)
			return (Collection<String>) field.getValue();
		return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
	}
}
