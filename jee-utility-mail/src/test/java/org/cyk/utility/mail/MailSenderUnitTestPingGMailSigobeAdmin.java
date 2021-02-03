package org.cyk.utility.mail;

import javax.mail.MessagingException;

import org.cyk.utility.__kernel__.test.weld.AbstractWeldUnitTest;
import org.cyk.utility.__kernel__.variable.VariableHelper;
import org.cyk.utility.__kernel__.variable.VariableName;
import org.junit.jupiter.api.Test;

public class MailSenderUnitTestPingGMailSigobeAdmin extends AbstractWeldUnitTest {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void __listenBefore__() {
		super.__listenBefore__();
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_ENABLE, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_HOST, "smtp.gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PORT, 587);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_SECURED_CONNECTION_REQUIRED, Boolean.TRUE);
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_IDENTIFIER, "a.sigobe.dgbf@gmail.com");
		VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_AUTHENTICATION_CREDENTIALS_USER_SECRET, "budget@2020");
		
		//VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_HOST, "10.3.4.5");
		//VariableHelper.write(VariableName.PROTOCOL_SIMPLE_MAIL_TRANSFER_PROXY_PORT, 3128);
		MailSender.INSTANCE.set(new MailSenderImpl());
	}
	
	@Test
	public void ping() throws MessagingException{
		MailSender.getInstance().ping("a.sigobe.dgbf@gmail.com");
	}
	
}
