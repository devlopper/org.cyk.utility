package org.cyk.utility.mail.postmark;

import javax.mail.MessagingException;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.cyk.utility.mail.MailSender;
import org.junit.jupiter.api.Test;

public class MailSenderUnitTestPingPostmarkSigobe extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST, "smtp.postmarkapp.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT, 587);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER, "33e65919-b008-4072-8712-074cb8dbaa37");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET, "33e65919-b008-4072-8712-074cb8dbaa37");
		
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_FROM, "no-reply@sigobe.dgbf.ci");
		MailSender.INSTANCE.set(new MailSenderImpl());
	}
	
	@Test
	public void ping() throws MessagingException{
		MailSender.getInstance().ping("kycdev@gmail.com");
	}
	
}
