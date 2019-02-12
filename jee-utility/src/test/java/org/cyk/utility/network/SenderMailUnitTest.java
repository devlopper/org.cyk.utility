package org.cyk.utility.network;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.sender.Receiver;
import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SenderMailUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void pingWaitForTermination(){
		SenderMail sender = __inject__(SenderMail.class);
		sender.getProtocol().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
		.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
		
		sender
		.setMessage(__inject__(Message.class).setTitle("MyTitle Sync").setBody("MyBody"))
		.addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com"))
		.execute();
	}
	
	@Test
	public void pingDoNotWaitForTermination(){
		SenderMail sender = __inject__(SenderMail.class);
		sender.getProtocol().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
		.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
		
		sender
		.setMessage(__inject__(Message.class).setTitle("MyTitle Async").setBody("MyBody"))
		.addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com"))
		.executeAsynchronously();
	}
	
}
