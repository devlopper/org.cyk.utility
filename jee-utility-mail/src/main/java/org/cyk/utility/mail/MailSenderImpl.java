package org.cyk.utility.mail;

import java.io.File;
import java.io.FileOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.cyk.utility.__kernel__.Helper;
import org.cyk.utility.__kernel__.log.LogHelper;
import org.cyk.utility.__kernel__.string.StringHelper;
import org.cyk.utility.__kernel__.value.ValueHelper;

public class MailSenderImpl extends AbstractMailSenderImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	public static Session SESSION;
	
	@Override
	protected void __send__(Message message,Properties protocolProperties, Listener listener) throws Exception{
		/*java.util.Properties properties = System.getProperties();
		if(protocolProperties.getHost()!=null)
			properties.put(Properties.HOST, protocolProperties.getHost());
		if(protocolProperties.getPort()!=null)
			properties.put(Properties.PORT, protocolProperties.getPort());
		if(protocolProperties.getIsAuthenticationRequired()!=null)
			properties.put(Properties.AUTHENTICATION, protocolProperties.getIsAuthenticationRequired());
		if(protocolProperties.getIsSecuredConnectionRequired()!=null)
			properties.put(Properties.STARTTLS_ENABLE, protocolProperties.getIsSecuredConnectionRequired());
		//Session session = Session.getDefaultInstance(properties, new Authenticator(protocolProperties));
		*/
		Session session = __getSession__(protocolProperties); //Session.getInstance(properties, new Authenticator(protocolProperties));
		if(session == null)
			LogHelper.logWarning("Session is NULL", getClass());
		MimeMessage mimeMessage = new MimeMessage(session);		
		mimeMessage.setSubject(ValueHelper.returnOrThrowIfBlank("mail subject", message.getSubject()));
		
		Multipart multipart = new MimeMultipart();
		mimeMessage.setContent(multipart);
		
		if(StringHelper.isNotBlank(message.getBody())) {
			BodyPart messageBodyPart = new MimeBodyPart(); 
			messageBodyPart.setContent(message.getBody(), "text/html");
			multipart.addBodyPart(messageBodyPart);
		}
		
		if(message.getAttachment() != null) {
			MimeBodyPart attachmentPart = new MimeBodyPart();
			File attachmentFile = File.createTempFile(message.getAttachment().getName(), StringHelper.isBlank(message.getAttachment().getExtension()) ? null 
					: "."+message.getAttachment().getExtension());
			attachmentFile.deleteOnExit();
			FileOutputStream attachmentFileOutputStream = new FileOutputStream(attachmentFile);
			attachmentFileOutputStream.write(message.getAttachment().getBytes());
			Helper.close(attachmentFileOutputStream);
			attachmentPart.attachFile(attachmentFile);
			multipart.addBodyPart(attachmentPart);
		}
		
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
	
	protected Session __getSession__(Properties protocolProperties) {
		if(SESSION != null)
			return SESSION;
		java.util.Properties properties = System.getProperties();	
		if(StringHelper.isNotBlank(protocolProperties.getHost()))
			properties.put(Properties.HOST, protocolProperties.getHost());
		if(protocolProperties.getPort()!=null)
			properties.put(Properties.PORT, protocolProperties.getPort());
		if(protocolProperties.getIsAuthenticationRequired()!=null)
			properties.put(Properties.AUTHENTICATION, protocolProperties.getIsAuthenticationRequired());
		if(protocolProperties.getIsSecuredConnectionRequired()!=null)
			properties.put(Properties.STARTTLS_ENABLE, protocolProperties.getIsSecuredConnectionRequired());
		//Proxy
		if(StringHelper.isNotBlank(protocolProperties.getProxyHost()))
			properties.put(Properties.PROXY_HOST, protocolProperties.getProxyHost());
		if(protocolProperties.getProxyPort()!=null)
			properties.put(Properties.PROXY_PORT, protocolProperties.getProxyPort());
		
		SESSION = Session.getInstance(properties, new Authenticator(protocolProperties));
		return SESSION;
	}

}