package org.cyk.utility.network;

import java.io.Serializable;
import java.util.UUID;

import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;
import org.cyk.utility.stream.distributed.AbstractProducerCallbackImpl;
import org.cyk.utility.stream.distributed.Consumer;
import org.cyk.utility.stream.distributed.Message;
import org.cyk.utility.stream.distributed.Messages;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.stream.distributed.kafka.NetworkMessageDeserializer;
import org.cyk.utility.stream.distributed.kafka.NetworkMessageSerializer;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class MailProducerConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __listenBeforeCallCountIsZero__() throws Exception {
		super.__listenBeforeCallCountIsZero__();
		__inject__(ProtocolDefaults.class).getSimpleMailTransfer().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE)
			.setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
	}
	
	@Test
	public void produceAndConsume() {
		startServersZookeeperAndKafka();
		
		org.cyk.utility.network.message.Message mail = __inject__(org.cyk.utility.network.message.Message.class);
		mail.setTitle("Hi from distributed!").setBody("This is a hi from distributed stream.").addReceiversByIdentifiers("kycdev@gmail.com");
		
		Producer producer = __inject__(Producer.class);
		producer.addTopics("test").setMessage(UUID.randomUUID().toString(),mail).setCallbackClass(ProducerCallbackImpl.class);
		producer.setProperty("value.serializer", NetworkMessageSerializer.class);
		producer.execute();
		
		Consumer consumer = __inject__(Consumer.class);
		consumer.setIsKeepMessages(Boolean.TRUE);
		consumer.addTopics("test").setMessageProcessorClass(ConsumerMessageProcessorImpl.class).setNumberOfMessages(1l);
		consumer.setProperty("group.id", "test");
		consumer.setProperty("value.deserializer", NetworkMessageDeserializer.class);
		consumer.execute();
		
		Messages messages = consumer.getMessages();
		assertionHelper.assertNotNull("messages is null",messages);
		assertionHelper.assertTrue("no message has been read",messages.getSize()>0);
		assertionHelper.assertEquals(mail.getTitle(), ((org.cyk.utility.network.message.Message)consumer.getMessages().getLast().getValue()).getTitle());
		assertionHelper.assertEquals(mail.getBody(), ((org.cyk.utility.network.message.Message)consumer.getMessages().getLast().getValue()).getBody());
		
		stopServersKafkaAndZookeeper();
	}
	
	/**/
	
	public static class ProducerCallbackImpl extends AbstractProducerCallbackImpl implements Serializable {
		private static final long serialVersionUID = 1L;

	}
	
	public static class ConsumerMessageProcessorImpl extends AbstractConsumerMessageProcessorImpl implements Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected void __process__(Message message) {
			//if(Boolean.TRUE.equals(__isRunnable__(SenderMail.class))) {
				__inject__(SenderMail.class).setMessage((org.cyk.utility.network.message.Message) message.getValue()).execute();	
			//}
			
		}
		
	}
}
