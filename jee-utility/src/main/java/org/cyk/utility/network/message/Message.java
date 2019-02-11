package org.cyk.utility.network.message;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Message extends Objectable {
	
	String getTitle();
	Message setTitle(String title);
	
	String getBody();
	Message setBody(String body);
	
}
