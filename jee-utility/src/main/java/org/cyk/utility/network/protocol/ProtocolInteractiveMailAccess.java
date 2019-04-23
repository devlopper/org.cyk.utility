package org.cyk.utility.network.protocol;

public interface ProtocolInteractiveMailAccess extends Protocol {
	
	String PROPERTY_NAME_IDENTIFIER = "pop3";
	String PROPERTY_NAME_IDENTIFIER_OVER_SSL = PROPERTY_NAME_IDENTIFIER+"s";
	
	String PROPERTY_NAME_HOST = "mail.pop3.host";
	String PROPERTY_NAME_PORT = "mail.pop3.port";
	
	String PROPERTY_NAME_AUTHENTICATION = "mail.pop3.auth";
	String PROPERTY_NAME_STARTTLS_ENABLE = "mail.pop3.starttls.enable";
	String PROPERTY_NAME_SSL_ENABLE = "mail.pop3.ssl.enable";
	
	String PROPERTY_NAME_USER = "mail.pop3.user";
	String PROPERTY_NAME_PASSWORD = "mail.pop3.password";
	
	String PROPERTY_NAME_FOLDER_INBOX = "INBOX";
	
}
