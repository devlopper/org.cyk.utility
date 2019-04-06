package org.cyk.utility.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Map;
import java.util.UUID;

import org.cyk.utility.network.message.sender.Receiver;
import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;
import org.cyk.utility.stream.distributed.AbstractProducerCallbackImpl;
import org.cyk.utility.stream.distributed.Consumer;
import org.cyk.utility.stream.distributed.Message;
import org.cyk.utility.stream.distributed.Messages;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class MailProducerConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void produceAndConsume() {
		startServersZookeeperAndKafka();
		
		org.cyk.utility.network.message.Message mail = __inject__(org.cyk.utility.network.message.Message.class);
		mail.setTitle("Hi from distributed!");
		mail.setBody("This is a hi from distributed stream.");
		
		Producer producer = __inject__(Producer.class);
		producer.addTopics("test").setMessage(UUID.randomUUID().toString(),mail).setCallbackClass(ProducerCallbackImpl.class);
		producer.setProperty("bootstrap.servers", "localhost:9092");
		producer.setProperty("acks", "all");
		producer.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer.setProperty("value.serializer", Serializer.class);
		producer.execute();
		
		Consumer consumer = __inject__(Consumer.class);
		consumer.setIsKeepMessages(Boolean.TRUE);
		consumer.addTopics("test").setMessageProcessorClass(ConsumerMessageProcessorImpl.class).setNumberOfMessages(1l);
		consumer.setProperty("bootstrap.servers", "localhost:9092");
		consumer.setProperty("group.id", "test");
		consumer.setProperty("enable.auto.commit", "false");
		consumer.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer.setProperty("value.deserializer", Deserializer.class);
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
				SenderMail sender = __inject__(SenderMail.class);
				sender.getProtocol().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE).setIsSecuredConnectionRequired(Boolean.TRUE)
				.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
				
				sender
				.setMessage((org.cyk.utility.network.message.Message) message.getValue())
				.addReceivers(__inject__(Receiver.class).setIdentifier("kycdev@gmail.com"))
				.execute();	
			//}
			
		}
		
	}
	
	public static class Serializer implements org.apache.kafka.common.serialization.Serializer<org.cyk.utility.network.message.Message> {

		@Override
		public void close() {

		}

		@Override
		public void configure(Map<String, ?> arg0, boolean arg1) {

		}

		@Override
		public byte[] serialize(String arg0, org.cyk.utility.network.message.Message message) {
			ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
			ObjectOutput objectOutput = null;
			try {
				objectOutput = new ObjectOutputStream(outputStream);
				objectOutput.writeObject(message);
				objectOutput.flush();
				return outputStream.toByteArray();
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					outputStream.close();
				} catch (IOException ex) {
					// ignore close exception
				}
			}
			return null;
		}

	}
	
	public static class Deserializer implements org.apache.kafka.common.serialization.Deserializer<org.cyk.utility.network.message.Message> {

		@Override
		public void close() {
			
		}

		@Override
		public void configure(Map<String, ?> arg0, boolean arg1) {
			
		}

		@Override
		public org.cyk.utility.network.message.Message deserialize(String arg0, byte[] bytes) {
			ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
			ObjectInput input = null;
			try {
				input = new ObjectInputStream(inputStream);
				return (org.cyk.utility.network.message.Message) input.readObject();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				try {
					if (input != null) {
						input.close();
					}
				} catch (IOException ex) {
					// ignore close exception
				}
			}
			return null;
		}
		
	}
	
}
