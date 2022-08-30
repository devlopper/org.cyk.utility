package org.cyk.utility.mail.postmark;

import javax.mail.MessagingException;

import org.cyk.utility.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.mail.MailSender;
import org.junit.jupiter.api.Test;

public class MailSenderUnitTestPingPostmark extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	private String key = "6a3a9070-bd49-4d2d-a0fd-7e881a7b0e6a";
	//private String key = "fa2f17e3-b4ef-492e-87c5-37793f9105ca";
	
	private String receiver = "kycdev@gmail.com";
	//private String receiver = "c.komenan@budget.gouv.ci";
	//private String receiver = "b.soro@budget.gouv.ci";
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST, "smtp.postmarkapp.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT, 587);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED, Boolean.FALSE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER, key);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET, key);
		
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_FROM, "testsender@sigobe.dgbf.ci");
		MailSender.INSTANCE.set(new MailSenderImpl());
	}
	
	@Test
	public void ping() {
		MailSender.getInstance().ping(receiver);
	}
}