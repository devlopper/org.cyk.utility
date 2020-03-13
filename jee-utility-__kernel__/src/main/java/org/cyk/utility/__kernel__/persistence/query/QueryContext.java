package org.cyk.utility.__kernel__.persistence.query;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.instance.InstanceGetter;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.object.marker.IdentifiableBusiness;
import org.cyk.utility.__kernel__.persistence.query.filter.Field;
import org.cyk.utility.__kernel__.persistence.query.filter.Filter;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class QueryContext extends AbstractObject implements Serializable {
	private static final long serialVersionUID = 1L;

	private Query query;
	private Filter filter;
	private Object[] arguments;
	
	public Boolean isFilterByKeys(String... keys) {
		Filter filter = getFilter();
		return filter == null ? Boolean.FALSE : filter.hasFieldWithPath(keys); //isFilterByKeys(getFilters(),keys);
	}
	
	public Object getFilterByKeysValue(String... keys) {
		Filter filter = getFilter();
		return filter == null ? null : filter.getFieldValueByPath(keys);
	}
	
	public Field getFilterFieldByKeys(String... keys) {
		Filter filter = getFilter();
		return filter == null ? null : filter.getFieldByPath(keys);
	}
	
	/**/
	
	public static Boolean isFilterByKeys(Filter filter,String... keys) {
		return filter == null ? Boolean.FALSE : filter.hasFieldWithPath(keys);
	}
	
	public String getString(String fieldName) {
		Field field = getFilterFieldByKeys(fieldName);				
		return field == null ? ConstantEmpty.STRING : (String) field.getValue();
	}
	
	public String getStringLike(String fieldName) {
		Field field = getFilterFieldByKeys(fieldName);				
		return "%"+(field == null ? ConstantEmpty.STRING : StringUtils.trimToEmpty((String) field.getValue()))+"%";
	}
	
	public List<String> getFieldValueLikes(String fieldName,Integer numberOfTokens) {
		String string = null;
		if(filter != null && StringHelper.isNotBlank(fieldName)) {
			Field field = filter.getField(fieldName);
			if(field != null && field.getValue() instanceof String)
				string = (String) field.getValue();
		}
		return QueryArgumentHelper.getLikes(string, numberOfTokens);
	}
	
	public List<String> getStringsLike(String fieldName,Integer numberOfToken) {
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
	
	public Collection<String> getStrings(String fieldName) {
		if(StringHelper.isBlank(fieldName))
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		Field field = getFilterFieldByKeys(fieldName);
		if(field == null || field.getValue() == null)
			return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
		if(field.getValue() instanceof Collection)
			return (Collection<String>) field.getValue();
		return ConstantEmpty.STRINGS_WITH_ONE_ELEMENT;
	}
	
	public Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass,String fieldName,Boolean readIfEmpty) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("field name", fieldName);
		Collection<String> codes = (Collection<String>) getFilterByKeysValue(fieldName);
		if(CollectionHelper.isEmpty(codes) && Boolean.TRUE.equals(readIfEmpty)) {
			if(codes == null)
				codes = new ArrayList<>();
			codes.addAll(InstanceGetter.getInstance().getAll(entityClass).stream().map(entity -> ((IdentifiableBusiness<String>)entity)
					.getBusinessIdentifier()).collect(Collectors.toList()));
		}
		return codes;
	}
	
	public Collection<String> getCodes(Class<? extends IdentifiableBusiness<String>> entityClass) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("entity class", entityClass);
		return getCodes(entityClass,StringHelper.getVariableNameFrom(entityClass.getSimpleName()),Boolean.TRUE);
	}
}
