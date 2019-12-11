package org.cyk.utility.__kernel__.protocol.smtp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public class MailSenderImpl extends AbstractMailSenderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __send__(Message message,Properties protocolProperties, Listener listener) throws Exception{
		java.util.Properties properties = System.getProperties();
		if(protocolProperties.getHost()!=null)
			properties.put(Properties.HOST, protocolProperties.getHost());
		if(protocolProperties.getPort()!=null)
			properties.put(Properties.PORT, protocolProperties.getPort());
		if(protocolProperties.getIsAuthenticationRequired()!=null)
			properties.put(Properties.AUTHENTICATION, protocolProperties.getIsAuthenticationRequired());
		if(protocolProperties.getIsSecuredConnectionRequired()!=null)
			properties.put(Properties.STARTTLS_ENABLE, protocolProperties.getIsSecuredConnectionRequired());
		Session session = Session.getDefaultInstance(properties, new Authenticator(protocolProperties));
		MimeMessage mimeMessage = new MimeMessage(session);		
		mimeMessage.setSubject(ValueHelper.returnOrThrowIfBlank("mail subject", message.getSubject()));
		mimeMessage.setContent(ValueHelper.returnOrThrowIfBlank("mail body", message.getBody()), "text/html");
		Collection<InternetAddress> internetAddresses = null;
		for (Object index : ValueHelper.returnOrThrowIfBlank("mail receivers", message.getReceivers())) {
			InternetAddress internetAddress = null;
			if(index instanceof String) {
				if(StringHelper.isBlank((String) index))
					continue;
				internetAddress = new InternetAddress((String) index);
			}
			if(internetAddress == null)
				continue;
			if(internetAddresses == null)
				internetAddresses = new ArrayList<>();
			internetAddresses.add(internetAddress);
		}
		ValueHelper.throwIfBlank("mail receivers", internetAddresses);
		mimeMessage.addRecipients(javax.mail.Message.RecipientType.TO, internetAddresses.toArray(new InternetAddress[] {}));
		Transport.send(mimeMessage);	
	}

}
