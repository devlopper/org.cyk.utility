package org.cyk.utility.__kernel__.protocol.smtp;

import static org.assertj.core.api.Assertions.assertThat;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

import com.icegreen.greenmail.util.GreenMail;
import com.icegreen.greenmail.util.GreenMailUtil;

public class MailSenderUnitTestMailJet extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	private GreenMail greenMail;
	
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
		MailSender.INSTANCE.set(__inject__(MailSenderImplTest.class));
		greenMail = new GreenMail(); //uses test ports by default
		greenMail.start();
	}
	
	@Override
	protected void __listenAfter__() {
		super.__listenAfter__();
		greenMail.stop();
	}
	
	@Test
	public void ping() throws MessagingException{
		MailSender.getInstance().ping("kycdev@gmail.com");
		MimeMessage[] emails = greenMail.getReceivedMessages();
		assertThat(emails).isNotNull();
		assertThat(emails.length).isEqualTo(1);
		assertThat(emails[0].getSubject()).isEqualTo("Ping");
		assertThat(GreenMailUtil.getBody(emails[0])).isEqualTo("This is a ping.");
	}
	
	@Test
	public void pingTwice() throws MessagingException{
		MailSender.getInstance().ping("kycdev@gmail.com");
		MailSender.getInstance().ping("kycdev@gmail.com");
		MimeMessage[] emails = greenMail.getReceivedMessages();
		assertThat(emails).isNotNull();
		assertThat(emails.length).isEqualTo(2);
		assertThat(emails[0].getSubject()).isEqualTo("Ping");
		assertThat(GreenMailUtil.getBody(emails[0])).isEqualTo("This is a ping.");
	}
	
	@Test
	public void pingFourTimes() throws MessagingException{
		MailSender.getInstance().ping("kycdev@gmail.com");
		MailSender.getInstance().ping("kycdev@gmail.com");
		MailSender.getInstance().ping("kycdev@gmail.com");
		MailSender.getInstance().ping("kycdev@gmail.com");
		MimeMessage[] emails = greenMail.getReceivedMessages();
		assertThat(emails).isNotNull();
		assertThat(emails.length).isEqualTo(4);
		assertThat(emails[0].getSubject()).isEqualTo("Ping");
		assertThat(GreenMailUtil.getBody(emails[0])).isEqualTo("This is a ping.");
	}
	
	@Test
	public void greenMailSetup() throws MessagingException {
		GreenMailUtil.sendTextEmailTest("to@localhost.com", "from@localhost.com", "subject", "body");
		MimeMessage[] emails = greenMail.getReceivedMessages();
		assertThat(emails).isNotNull();
		assertThat(emails.length).isEqualTo(1);
		assertThat(emails[0].getSubject()).isEqualTo("subject");
		assertThat(GreenMailUtil.getBody(emails[0])).isEqualTo("body");
	}

	
}
