package org.cyk.utility.business;

import java.io.Serializable;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.RuntimeException;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;

public class RequestException extends RuntimeException implements Serializable {

	public RequestException() {
		super();
	}

	public RequestException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public RequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public RequestException(String message) {
		super(message);
	}

	public RequestException(Throwable cause) {
		super(cause);
	}
	
	public static void throwIfNotEmpty(ThrowablesMessages throwablesMessages) {
		if(throwablesMessages == null || CollectionHelper.isEmpty(throwablesMessages.getMessages()))
			return;
		throw new RequestException(StringHelper.concatenate(throwablesMessages.getMessages(), "\r\n"));	
	}
}