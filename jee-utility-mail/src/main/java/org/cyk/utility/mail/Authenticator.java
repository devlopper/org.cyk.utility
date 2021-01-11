package org.cyk.utility.mail;

import java.io.Serializable;

import javax.mail.PasswordAuthentication;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter @Setter @Accessors(chain=true)
public class Authenticator extends javax.mail.Authenticator implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private Properties properties;
	
	public Authenticator(Properties properties) {
		this.properties = properties;
		if(this.properties == null)
			this.properties = Properties.DEFAULT;
	}
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication(properties.getAuthenticationCredentials().getIdentifier(),(String)properties.getAuthenticationCredentials().getSecret());
	}
}
