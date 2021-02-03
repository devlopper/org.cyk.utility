package org.cyk.utility.mail;

import java.io.Serializable;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.protocol.AbstractProperties;
import org.cyk.utility.__kernel__.security.Credentials;
import org.cyk.utility.__kernel__.variable.VariableName;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Properties extends AbstractProperties implements Serializable {
	private static final long serialVersionUID = 1L;

	/**/
	
	public static final String HOST = "mail.smtp.host";
	public static final String PORT = "mail.smtp.port";
	
	public static final String AUTHENTICATION = "mail.smtp.auth";
	public static final String STARTTLS_ENABLE = "mail.smtp.starttls.enable";
	public static final String SSL_ENABLE = "mail.smtp.ssl.enable";
	public static final String SSL_TRUST = "mail.smtp.ssl.trust";
	
	public static final String FROM = "mail.smtp.from";
	public static final String USER = "mail.smtp.user";
	public static final String PASSWORD = "mail.smtp.password";
	
	public static final String PROXY_HOST = "mail.smtp.proxy.host";
	public static final String PROXY_PORT = "mail.smtp.proxy.port";
	public static final String PROXY_USER = "mail.smtp.proxy.user";
	public static final String PROXY_PASSWORD = "mail.smtp.proxy.password";
	
	/**/
	
	public static final Properties DEFAULT = new Properties();
	static {
		DEFAULT
		.setHost(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST))
		.setPort(ConfigurationHelper.getValueAsInteger(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT))
		.setIsAuthenticationRequired(ConfigurationHelper.is(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED))
		.setIsSecuredConnectionRequired(ConfigurationHelper.is(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED))
		.setAuthenticationCredentials(new Credentials()
				.setIdentifier(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER))
				.setSecret(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET)))
		.setFrom(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_FROM))
		//Proxy
		//.setProxyHost(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_HOST))
		//.setProxyPort(ConfigurationHelper.getValueAsInteger(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_PORT))
		//.setProxyAuthenticationCredentials(new Credentials()
		//		.setIdentifier(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER))
		//		.setSecret(ConfigurationHelper.getValueAsString(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_AUTHENTICATION_CREDENTIALS_USER_SECRET)))
		;
	}
}
