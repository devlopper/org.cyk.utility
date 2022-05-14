package org.cyk.utility.persistence.server.hibernate;

import java.util.Collection;
import java.util.stream.Collectors;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public interface Language {

	static String coalesce(Object...values) {
		Collection<Object> collection = CollectionHelper.listOf(Boolean.TRUE, values);
		return String.format("COALESCE(%s)", CollectionHelper.isEmpty(collection) ? "NULL" : collection.stream().map(i -> i.toString()).collect(Collectors.joining(",")));
	}
	
	static String coalesceZero(String projection,String zero) {
		return coalesce(projection,ValueHelper.defaultToIfBlank(zero, "0"));
	}
	
	static String coalesceZero(String projection) {
		return coalesceZero(projection, null);
	}
	
	static String coalesceZeroLong(String projection) {
		return coalesceZero(projection, "0l");
	}
}