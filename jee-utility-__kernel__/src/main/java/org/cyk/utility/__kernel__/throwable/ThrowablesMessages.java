package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;

public class ThrowablesMessages extends AbstractObject implements Serializable{

	private Collection<String> collection;
	
	public ThrowablesMessages add(Collection<String> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		if(collection == null)
			collection = new ArrayList<>();
		collection.addAll(messages);
		return this;
	}
	
	public ThrowablesMessages add(String...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return add(CollectionHelper.listOf(messages));
	}
	
	public ThrowablesMessages addIfTrue(String message,Boolean condition) {
		if(StringHelper.isBlank(message))
			return this;
		if(Boolean.TRUE.equals(condition))
			add(message);
		return this;
	}
	
	public void throwIfNotEmpty() {
		if(CollectionHelper.isNotEmpty(collection))
			throw new RuntimeException(StringHelper.concatenate(collection, "\r\n"));	
	}
	
	public ThrowablesMessages clear() {
		if(collection == null)
			return this;
		collection.clear();
		return this;
	}
}