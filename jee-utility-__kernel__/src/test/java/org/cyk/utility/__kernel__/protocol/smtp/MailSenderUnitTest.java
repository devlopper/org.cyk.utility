package org.cyk.utility.__kernel__.protocol.smtp;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class MailSenderUnitTest extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST, "smtp.gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT, 587);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER, "kycdev@gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET, "P@sSw0rd@2O18");
	}
	
	@Test
	public void ping(){
		MailSender.getInstance().ping("kycdev@gmail.com");
	}
	
	@Test
	public void java() throws Exception{
		Properties protocolProperties = Properties.DEFAULT;
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
		mimeMessage.setSubject("Java Mail");
		mimeMessage.setContent("It is working!", "text/html");
		mimeMessage.addRecipients(javax.mail.Message.RecipientType.TO, new InternetAddress[] {new InternetAddress("kycdev@gmail.com")});
		Transport.send(mimeMessage);
	}

}
