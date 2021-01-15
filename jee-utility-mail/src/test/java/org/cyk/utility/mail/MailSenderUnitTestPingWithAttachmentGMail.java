package org.cyk.utility.mail;

import java.io.IOException;
import java.util.List;

import javax.mail.MessagingException;

import org.apache.commons.io.IOUtils;
import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class MailSenderUnitTestPingWithAttachmentGMail extends AbstractWeldUnitTest {
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
		
		//VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_HOST, "10.3.4.5");
		//VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_PORT, 3128);
		MailSender.INSTANCE.set(new MailSenderImpl());
	}
	
	@Test
	public void ping() throws MessagingException, IOException{
		MailSender.getInstance().send(new Message().setSubject("Vos fichiers en Ping").setBody("This is a ping with attachment").setReceivers(List.of("kycdev@gmail.com"))
			.addAttachments(
			new Message.Attachment().setBytes(IOUtils.toByteArray(MailSender.class.getResourceAsStream("attachment/pdf01.pdf"))).setName("votre_fichier_v01").setExtension("pdf")
			,new Message.Attachment().setBytes(IOUtils.toByteArray(MailSender.class.getResourceAsStream("attachment/xlsx.xlsx"))).setName("votre_fichier_v02").setExtension("xlsx")
			,new Message.Attachment().setBytes(IOUtils.toByteArray(MailSender.class.getResourceAsStream("attachment/pdf02.pdf"))).setName("votre_fichier_v03").setExtension("pdf")
		));
	}	
}