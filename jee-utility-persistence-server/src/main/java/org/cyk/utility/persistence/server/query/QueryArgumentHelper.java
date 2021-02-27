package org.cyk.utility.persistence.server.query;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.constant.ConstantCharacter;
import org.cyk.utility.__kernel__.constant.ConstantEmpty;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface QueryArgumentHelper {

	static String getLike(Object value) {
		if(value != null && !(value instanceof String))
			throw new RuntimeException("value must be an instance of string");
		String string = (String) value;
		if(StringHelper.isBlank(string))
			return LIKE_ALL;
		return "%"+string+"%";
	}
	
	static List<String> getLikes(Object value,Integer numberOfTokens) {
		if(value!= null && !(value instanceof String))
			throw new RuntimeException("value must be an instance of string");
		String string = (String) value;
		string = ValueHelper.defaultToIfBlank(string, ConstantEmpty.STRING);
		if(numberOfTokens == null || numberOfTokens < 0)
			numberOfTokens = 0;
		else
			numberOfTokens--;
		//numberOfToken = numberOfToken + 1;
		List<String> list = new ArrayList<>();
		list.add("%"+string+"%");
		String[] tokens = StringUtils.split(string, ConstantCharacter.SPACE);
		if(ArrayHelper.isNotEmpty(tokens)) {
			for(Integer index = 0; index < tokens.length && index < numberOfTokens; index = index + 1)
				list.add("%"+tokens[index]+"%");
		}
		if(list.size() < numberOfTokens+1)
			for(Integer index = list.size(); index <= numberOfTokens; index = index + 1)
				list.add(LIKE_ALL);
		return list;
	}
	
	/**/
	
	String LIKE_ALL = "%%";
}
