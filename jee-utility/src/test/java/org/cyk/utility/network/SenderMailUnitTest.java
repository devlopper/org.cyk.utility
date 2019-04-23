package org.cyk.utility.network;

import java.util.Arrays;

import javax.enterprise.event.Event;
import javax.inject.Inject;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.Receiver;
import org.cyk.utility.network.message.SenderReader;
import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.network.protocol.ProtocolSimpleMailTransfer;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class SenderMailUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Inject private Event<SenderReader> senderReaderEvent;
	
	@Test
	public void pingWaitForTermination(){
		if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
			SenderMail sender = __inject__(SenderMail.class);
			sender.getProtocol(Boolean.TRUE).setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			sender
			.setMessage(__inject__(Message.class).setTitle("MyTitle Sync").setBody("MyBody").addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com")))
			.execute();	
			/*
			ReaderMail reader = __inject__(ReaderMail.class);
			reader.getProtocol(Boolean.TRUE).setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("kycdev").setSecret("P@sSw0rd@2O18"));
			reader.setSearchTerm(new SearchTerm() {
			    private static final long serialVersionUID = 1L;

				public boolean match(javax.mail.Message message) {
			        try {
			        	System.out.println(message.getSubject()+" ::: "+message.getSubject().contains("MyTitle Sync"));
			            if (message.getSubject().contains("MyTitle Sync")) {
			                return true;
			            }
			        } catch (MessagingException exception) {
			            exception.printStackTrace();
			        }
			        return false;
			    }
			});
			reader.execute();
			assertionHelper.assertEquals("MyTitle Sync", __inject__(CollectionHelper.class).getFirst(reader.getMessages()).getTitle());
			*/
		}
	}
	
	@Test
	public void pingDoNotWaitForTermination(){
		if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
			SenderMail sender = __inject__(SenderMail.class);
			sender.getProtocol(Boolean.TRUE).setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			sender
			.setMessage(__inject__(Message.class).setTitle("MyTitle Async").setBody("MyBody").addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com")))
			.setIsExecuteAsynchronously(Boolean.TRUE)
			.execute();	
		}
	}
	
	@Test
	public void pingUsingCdiObserver(){
		if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
			SenderMail sender = __inject__(SenderMail.class);
			sender.getProtocol(Boolean.TRUE).setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			sender
			.setMessage(__inject__(Message.class).setTitle("MyTitle CDI Observer").setBody("MyBody").addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com")))
			;
			senderReaderEvent.fire(sender);
		}
	}
	
	@Test
	public void pingUsingHelper(){
		if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
			if(Boolean.TRUE.equals(__isSkippable__(SenderMail.class))) return;
			__inject__(ProtocolDefaults.class).get(ProtocolSimpleMailTransfer.class).setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			__inject__(MailHelper.class).send("MyTitle CDI Helper", "MyBody", Arrays.asList("kycdev@gmail.com"), Boolean.FALSE);
		}
	}
	
	@Test
	public void pingUsingDefault(){
		if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
			__inject__(ProtocolDefaults.class).getSimpleMailTransfer().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			SenderMail sender = __inject__(SenderMail.class);
		
			sender
			.setMessage(__inject__(Message.class).setTitle("MyTitle Sync").setBody("MyBody").addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com")))
			.execute();	
		}
	}
}
