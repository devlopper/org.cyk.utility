package org.cyk.utility.network.message;

import java.util.Collection;

import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public interface Message extends Objectable {
	
	String getTitle();
	Message setTitle(String title);
	
	String getBody();
	Message setBody(String body);
	
	Receivers getReceivers();
	Receivers getReceivers(Boolean injectIfNull);
	Message setReceivers(Receivers receivers);
	Message addReceivers(Collection<Receiver> receivers);
	Message addReceivers(Receiver...receivers);
	Message addReceiversByIdentifiers(Collection<Object> receiversIdentifiers);
	Message addReceiversByIdentifiers(Object...receiversIdentifiers);
	
	/**/
	
	String PROPERTY_TITLE = "title";
	String PROPERTY_BODY = "body";
	String PROPERTY_RECEIVERS = "receivers";
}
