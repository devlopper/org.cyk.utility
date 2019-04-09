package org.cyk.utility.network;

import java.util.Collection;

import org.cyk.utility.helper.Helper;

public interface MailHelper extends Helper {

	MailHelper send(String title,String body,Collection<Object> receiversIdentifiers,Boolean isExecuteAsynchronously);
	
}
