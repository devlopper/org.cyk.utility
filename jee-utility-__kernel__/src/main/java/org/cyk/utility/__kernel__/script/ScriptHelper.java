package org.cyk.utility.__kernel__.script;

import java.util.Collection;

import org.apache.commons.lang3.StringUtils;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.throwable.ThrowableHelper;

public interface ScriptHelper {

	static String join(Collection<String> scripts) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("scripts", scripts);
		return StringUtils.join(scripts,";");
	}
	
	static String join(String...scripts) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("scripts", scripts);
		return join(CollectionHelper.listOf(scripts));
	}
	
	static String formatReturnIfNotExist(String body) {
		if(StringUtils.containsIgnoreCase(body, "return "))
			return body;
		return String.format(RETURN_FORMAT, body);
	}
	
	static String formatFunction(String name,Collection<String> parameters,String body) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("name", name);
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("parameters", parameters);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("body", body);
		return String.format(FUNCTION_FORMAT, name,StringUtils.join(parameters,","),body);
	}
	
	static String formatFunction(String name,String body,String...parameters) {
		ThrowableHelper.throwIllegalArgumentExceptionIfEmpty("parameters", parameters);
		return formatFunction(name, CollectionHelper.listOf(parameters), body);
	}
	
	static String formatLoopForCollection(String collection,String cursor,String action) {
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("collection", collection);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("cursor", cursor);
		ThrowableHelper.throwIllegalArgumentExceptionIfBlank("action", action);
		return String.format(LOOP_FOR_COLLECTION_FORMAT, collection,cursor,action);
	}
	
	static String formatLoopForCollectionOverFunction(String collection,String cursor,String action,String functionName,String functionBody,String...functionParameters) {
		return join(
				formatFunction(functionName, functionBody, functionParameters)//function
				,formatLoopForCollection(collection, cursor, action)//execution
				);
	}
	
	/**/
	
	String RETURN_FORMAT = "return %s";
	String FUNCTION_FORMAT = "function %s(%s){%s}";
	String LOOP_FOR_COLLECTION_FORMAT = "var %2$s;for (%2$s = 0; %2$s < %1$s.length; %2$s++) {%3$s}";
}