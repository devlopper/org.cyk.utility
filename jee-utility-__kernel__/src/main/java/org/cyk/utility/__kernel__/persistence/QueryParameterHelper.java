package org.cyk.utility.__kernel__.persistence;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface QueryParameterHelper {

	static List<String> getStringsLike(String fieldName,Integer numberOfToken) {
		return null;
		/*
		numberOfToken = numberOfToken + 1;
		List<String> list = new ArrayList<>();
		org.cyk.utility.server.persistence.query.filter.Field field = getFilterFieldByKeys(fieldName);
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
		*/
	}
	
}
