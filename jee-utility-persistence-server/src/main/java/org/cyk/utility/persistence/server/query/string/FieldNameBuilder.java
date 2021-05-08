package org.cyk.utility.persistence.server.query.string;

import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.field.FieldHelper;

public interface FieldNameBuilder {

	static String identifier(Collection<String> paths) {
		if(paths == null)
			paths = new ArrayList<>();
		paths.add("identifier");
		return FieldHelper.join(paths);
	}
	
	static String identifier(String...paths) {
		return identifier(CollectionHelper.listOf(paths));
	}
	
	static String id(Collection<String> paths) {
		return identifier(paths);
	}
	
	static String id(String...paths) {
		return identifier(paths);
	}
	
	static String code(Collection<String> paths) {
		if(paths == null)
			paths = new ArrayList<>();
		paths.add("code");
		return FieldHelper.join(paths);
	}
	
	static String code(String...paths) {
		return code(CollectionHelper.listOf(paths));
	}
	
	static String name(Collection<String> paths) {
		if(paths == null)
			paths = new ArrayList<>();
		paths.add("name");
		return FieldHelper.join(paths);
	}
	
	static String name(String...paths) {
		return name(CollectionHelper.listOf(paths));
	}
}