package org.cyk.utility.__kernel__.protocol.smtp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.__kernel__.configuration.ConfigurationHelper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.object.AbstractObject;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.__kernel__.variable.VariableName;

public class MailSenderImpl extends AbstractObject implements MailSender,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void send(Message message,Properties protocolProperties, Listener listener) {
		if(ConfigurationHelper.isNot(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE)) {
			LogHelper.logWarning("we cannot send mail because it has not been enabled.", getClass());
			return;
		}
		if(protocolProperties == null)
			protocolProperties = Properties.DEFAULT;
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
		try {
			mimeMessage.setSubject(ValueHelper.returnOrThrowIfBlank("mail title", message.getTitle()));
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
			LogHelper.logInfo("Mail has been sent.", getClass());
		} catch (Exception exception) {
			throw new RuntimeException(exception);
		}
	}

}
