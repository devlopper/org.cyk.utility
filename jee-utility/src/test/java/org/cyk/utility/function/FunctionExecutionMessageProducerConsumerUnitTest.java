package org.cyk.utility.function;

import java.util.UUID;

import org.cyk.utility.__kernel__.function.FunctionExecutionMessage;
import org.cyk.utility.map.MapHelper;
import org.cyk.utility.network.protocol.ProtocolDefaults;
import org.cyk.utility.security.Credentials;
import org.cyk.utility.stream.distributed.Consumer;
import org.cyk.utility.stream.distributed.ConsumerBuilder;
import org.cyk.utility.stream.distributed.Messages;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.stream.distributed.ProducerBuilder;
import org.cyk.utility.stream.distributed.Topic;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.TimeHelper;
import org.junit.Test;

public class FunctionExecutionMessageProducerConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;
	
	@Test
	public void produceAndConsume() {
		if(Boolean.TRUE.equals(__isRunnable__(Producer.class))) {
			startServersZookeeperAndKafka();
			
			Consumer consumer = __inject__(ConsumerBuilder.class).setTopic(Topic.FUNCTION).execute().getOutput();
			
			//Testing purpose
			consumer.setIsKeepMessages(Boolean.TRUE).setNumberOfMessages(1l).setIsExecuteAsynchronously(Boolean.TRUE);
			Thread thread = (Thread) consumer.execute().getProperties().getThread();
			
			System.out.println("Function consumer has started. we are waiting some times before producing...");
			__inject__(TimeHelper.class).pause(1000l * 5);
			
			FunctionExecutionMessage functionExecutionMessage = __inject__(FunctionExecutionMessage.class);
			functionExecutionMessage.setFunction("create").setInputs(__inject__(MapHelper.class).instanciateKeyAsStringValueAsString("i01","a","i02","b"))
				.setOutputs(__inject__(MapHelper.class).instanciateKeyAsStringValueAsString("o01","1","uid","myidentifiervalue"));
			
			Producer producer = __inject__(ProducerBuilder.class).setTopic(Topic.FUNCTION).execute().getOutput();
			producer.setMessage(UUID.randomUUID().toString(),functionExecutionMessage);		
			producer.execute();
			
			try {
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			Messages messages = consumer.getMessages();
			assertionHelper.assertNotNull("messages is null",messages);
			assertionHelper.assertTrue("no message has been read",messages.getSize()>0);
			assertionHelper.assertEquals(functionExecutionMessage.getFunction(), ((FunctionExecutionMessage)consumer.getMessages().getLast().getValue()).getFunction());
			//assertionHelper.assertEquals(mail.getBody(), ((org.cyk.utility.network.message.Message)consumer.getMessages().getLast().getValue()).getBody());
			
			stopServersKafkaAndZookeeper();	
		}
	}
	
	@Test
	public void produceAndMailShouldHaveBeenSent() {
		if(Boolean.TRUE.equals(__isRunnable__(Producer.class))) {
			__inject__(ProtocolDefaults.class).getSimpleMailTransfer().setHost("smtp.gmail.com").setPort(587).setIsAuthenticationRequired(Boolean.TRUE)
			.setIsSecuredConnectionRequired(Boolean.TRUE)
			.setAuthenticationCredentials(__inject__(Credentials.class).setIdentifier("dgbfdtideveloppers").setSecret("dgbf2016dti"));
			
			startServersZookeeperAndKafka();
			
			Topic.FUNCTION.startConsumer();
			
			System.out.println("Function consumer has started. we are waiting some times before producing...");
			__inject__(TimeHelper.class).pause(1000l * 5);
			
			__inject__(FunctionHelper.class).produce("delete", __inject__(MapHelper.class).instanciateKeyAsStringValueAsString("i01","a","i02","b")
					,__inject__(MapHelper.class).instanciateKeyAsStringValueAsString("o01","1","uid","myidentifiervalue"));
		
			//Get message from POP
			/*
			Messages messages = consumer.getMessages();
			assertionHelper.assertNotNull("messages is null",messages);
			assertionHelper.assertTrue("no message has been read",messages.getSize()>0);
			assertionHelper.assertEquals(mail.getTitle(), ((org.cyk.utility.network.message.Message)consumer.getMessages().getLast().getValue()).getTitle());
			assertionHelper.assertEquals(mail.getBody(), ((org.cyk.utility.network.message.Message)consumer.getMessages().getLast().getValue()).getBody());
			*/
			
			__inject__(TimeHelper.class).pause(1000l * 15);
			
			stopServersKafkaAndZookeeper();	
		}
	}
	
}