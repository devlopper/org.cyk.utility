package org.cyk.utility.__kernel__.throwable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.array.ArrayHelper;
import org.cyk.utility.__kernel__.collection.CollectionHelper;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class RuntimeException extends java.lang.RuntimeException implements Serializable {

	private Collection<Message> messages;
	
	public RuntimeException() {
		super();
	}

	public RuntimeException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RuntimeException(String message, Throwable cause) {
		super(message, cause);
	}

	public RuntimeException(String message) {
		super(message);
	}

	public RuntimeException(Throwable cause) {
		super(cause);
	}

	public Collection<Message> getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = new ArrayList<>();
		return messages;
	}
	
	public RuntimeException addMessages(Collection<Message> messages) {
		if(CollectionHelper.isEmpty(messages))
			return this;
		getMessages(Boolean.TRUE).addAll(messages);
		return this;
	}
	
	public RuntimeException addMessages(Message...messages) {
		if(ArrayHelper.isEmpty(messages))
			return this;
		return addMessages(CollectionHelper.listOf(messages));
	}
}
