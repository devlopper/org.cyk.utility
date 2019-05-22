package org.cyk.utility.network;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface MailHelper extends Helper {
	
	/*
	Boolean getIsEnable();
	MailHelper setIsEnable(Boolean isEnable);
	*/
	MailHelper send(String title,String body,Collection<Object> receiversIdentifiers,Boolean isExecuteAsynchronously);
	
	MailHelper produce(String title,String body,Collection<String> receiversIdentifiers);
	MailHelper produce(String title,String body,String...receiversIdentifiers);
}
