package org.cyk.utility.network.message.sender;

import java.io.Serializable;

import javax.enterprise.context.Dependent;
import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.Receiver;
import org.cyk.utility.network.message.Receivers;
import org.cyk.utility.network.protocol.Protocol;
import org.cyk.utility.network.protocol.ProtocolSimpleMailTransfer;

@Dependent
public class SenderMailImpl extends AbstractSenderImpl implements SenderMail,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected Class<? extends Protocol> __getProtocolClass__() {
		return ProtocolSimpleMailTransfer.class;
	}
	
	@Override
	protected void ________execute________(Protocol protocol,Message message) throws Exception {
		java.util.Properties properties = System.getProperties();
		if(protocol.getHost()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_HOST, protocol.getHost());
		if(protocol.getPort()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_PORT, protocol.getPort());
		if(protocol.getIsAuthenticationRequired()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_AUTHENTICATION, protocol.getIsAuthenticationRequired());
		if(protocol.getIsSecuredConnectionRequired()!=null)
			properties.put(ProtocolSimpleMailTransfer.PROPERTY_NAME_STARTTLS_ENABLE, protocol.getIsSecuredConnectionRequired());
		
		Session session = Session.getDefaultInstance(properties, new Authenticator() {
    		@Override
    		protected PasswordAuthentication getPasswordAuthentication() {
    			return new PasswordAuthentication((String)protocol.getAuthenticationCredentials().getIdentifier(),(String)protocol.getAuthenticationCredentials().getSecret());
    		}
		});
		MimeMessage mimeMessage = new MimeMessage(session);

		String title = message.getTitle();
		throwRuntimeExceptionIfIsBlank(title, "title");
		mimeMessage.setSubject(title);
		
		String body = message.getBody();
		throwRuntimeExceptionIfIsBlank(body, "body");
		mimeMessage.setContent(body, "text/html");//for a html email
		//emailMessage.setText(emailBody);// for a text email
		
		Receivers receivers = message.getReceivers();
		throwRuntimeExceptionIfIsEmpty(receivers, "receivers");
		for (Receiver index : receivers.get())
			mimeMessage.addRecipient(javax.mail.Message.RecipientType.TO, new InternetAddress((String) index.getIdentifier()));

		Transport.send(mimeMessage);
		__logFine__("Mail has been sent.");
	}
	
}
