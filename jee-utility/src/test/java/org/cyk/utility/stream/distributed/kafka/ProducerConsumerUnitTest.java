package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Date;

import org.cyk.utility.random.RandomHelper;
import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;
import org.cyk.utility.stream.distributed.AbstractProducerCallbackImpl;
import org.cyk.utility.stream.distributed.Consumer;
import org.cyk.utility.stream.distributed.Message;
import org.cyk.utility.stream.distributed.Messages;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.TimeHelper;
import org.junit.Test;

public class ProducerConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void produceAndConsume() {
		startServersZookeeperAndKafka();

		String topic = "mail";
		String group = "mygroup";
		
		Object messageKey = __inject__(RandomHelper.class).getAlphabetic(5);
		Object messageValue = "Time is "+new Date();
		
		Consumer consumer = __inject__(Consumer.class);
		consumer.setIsKeepMessages(Boolean.TRUE);
		consumer.addTopics(topic).setMessageProcessorClass(ConsumerMessageProcessorImpl.class).setNumberOfMessages(1l);
		consumer.setProperty("bootstrap.servers", "localhost:9092");
		consumer.setProperty("group.id", group);
		consumer.setProperty("enable.auto.commit", "false");
		consumer.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		Thread thread = (Thread) consumer.setIsExecuteAsynchronously(Boolean.TRUE).execute().getProperties().getThread();
		
		System.out.println("Consumer has started. we are waiting some times before producing...");
		__inject__(TimeHelper.class).pause(1000l * 5);
		
		Producer producer = __inject__(Producer.class);
		producer.addTopics(topic).setMessage(messageKey,messageValue).setCallbackClass(ProducerCallbackImpl.class);
		producer.setProperty("bootstrap.servers", "localhost:9092");
		producer.setProperty("acks", "all");
		producer.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer.execute();
		
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		Messages messages = consumer.getMessages();
		assertionHelper.assertNotNull("messages is null",messages);
		assertionHelper.assertTrue("no message has been read",messages.getSize()>0);
		assertionHelper.assertEquals(messageKey, consumer.getMessages().getLast().getKey());
		assertionHelper.assertEquals(messageValue, consumer.getMessages().getLast().getValue());
		
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
			System.out.printf("Message read. key = %s, value = %s%n", message.getKey(), message.getValue());
		}
		
	}
	
}
