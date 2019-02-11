package org.cyk.utility.network.protocol;

public interface ProtocolSimpleMailTransfer extends Protocol {
	
	String PROPERTY_NAME_HOST = "mail.smtp.host";
	String PROPERTY_NAME_PORT = "mail.smtp.port";
	
	String PROPERTY_NAME_AUTHENTICATION = "mail.smtp.auth";
	String PROPERTY_NAME_STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	String PROPERTY_NAME_SSL_ENABLE = "mail.smtp.ssl.enable";
	
	String PROPERTY_NAME_FROM = "mail.smtp.from";
	String PROPERTY_NAME_USER = "mail.smtp.user";
	String PROPERTY_NAME_PASSWORD = "mail.smtp.password";
	
	
	
}
