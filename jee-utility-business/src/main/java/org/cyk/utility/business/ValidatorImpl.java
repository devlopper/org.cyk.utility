package org.cyk.utility.business;

import java.io.Serializable;

import javax.enterprise.context.ApplicationScoped;

import org.cyk.utility.__kernel__.collection.CollectionHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.throwable.ThrowablesMessages;

@ApplicationScoped
public class ValidatorImpl extends Validator.AbstractImpl implements Serializable {

	public static void throwIfNotEmpty(ThrowablesMessages throwablesMessages) {
		if(throwablesMessages == null || CollectionHelper.isEmpty(throwablesMessages.getMessages()))
			return;
		throw new RequestException(StringHelper.concatenate(throwablesMessages.getMessages(), "\r\n"));	
	}
	
}