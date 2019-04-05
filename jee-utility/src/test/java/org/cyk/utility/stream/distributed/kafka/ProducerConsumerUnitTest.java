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
import org.cyk.utility.system.OperatingSystemCommandExecutor;
import org.cyk.utility.system.SystemHelper;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.cyk.utility.time.TimeHelper;
import org.cyk.utility.value.ValueHelper;
import org.junit.Test;

public class ProducerConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void produceAndConsume() {
		startServers();

		Object messageKey = __inject__(RandomHelper.class).getAlphabetic(5);
		Object messageValue = "Time is "+new Date();
		
		Producer producer = __inject__(Producer.class);
		producer.addTopics("test").setMessage(messageKey,messageValue).setCallbackClass(ProducerCallbackImpl.class);
		producer.setProperty("bootstrap.servers", "localhost:9092");
		producer.setProperty("acks", "all");
		producer.setProperty("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer.setProperty("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		producer.execute();
		
		Consumer consumer = __inject__(Consumer.class);
		consumer.setIsKeepMessages(Boolean.TRUE);
		consumer.addTopics("test").setMessageProcessorClass(ConsumerMessageProcessorImpl.class).setNumberOfMessages(1l);
		consumer.setProperty("bootstrap.servers", "localhost:9092");
		consumer.setProperty("group.id", "test");
		consumer.setProperty("enable.auto.commit", "false");
		consumer.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer.execute();
		
		Messages messages = consumer.getMessages();
		assertionHelper.assertNotNull("messages is null",messages);
		assertionHelper.assertTrue("no message has been read",messages.getSize()>0);
		assertionHelper.assertEquals(messageKey, consumer.getMessages().getLast().getKey());
		assertionHelper.assertEquals(messageValue, consumer.getMessages().getLast().getValue());
		
		stopServers();
	}
	
	protected static void stopServers() {
		System.out.println("Stopping servers");
		stopKafka();
		stopZookeeper();
		System.out.println("Servers stopped");
	}
	
	protected static void startServers() {
		stopServers();
		
		System.out.println("Starting servers");
		startZookeeper();
		startKafka();
		System.out.println("Servers started");
	}

	protected static void stopZookeeper() {
		System.out.print("Stopping zookeeper...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("zookeeper-server-stop.bat")
			//.setIsTerminalStartable(Boolean.TRUE)
			//.setIsTerminalShowable(Boolean.FALSE)
			//.setWorkingDirectory("target")
			;
		operatingSystemCommandExecutor.execute();
		System.out.println("OK");
	}
	
	protected static void startZookeeper() {
		String workingDirectory = __inject__(ValueHelper.class).returnOrThrowIfBlank("Zookeeper home", __inject__(SystemHelper.class).getProperty("zookeeper.home",Boolean.TRUE));
		System.out.print("Starting zookeeper("+workingDirectory+")...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("zkserver").setIsTerminalStartable(Boolean.TRUE)
			.setIsTerminalShowable(Boolean.FALSE)
			.setTerminalTitle("Zookeeper")
			.setWorkingDirectory(workingDirectory)
			;
		operatingSystemCommandExecutor.setIsExecuteAsynchronously(Boolean.TRUE).execute();
		__inject__(TimeHelper.class).pause(1000l * 10);
		System.out.println("OK");
	}
	
	protected static void stopKafka() {
		System.out.print("Stopping kafka...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("kafka-server-stop.bat")
			//.setIsTerminalStartable(Boolean.TRUE)
			//.setIsTerminalShowable(Boolean.FALSE)
			//.setWorkingDirectory("target")
			;
		operatingSystemCommandExecutor.execute();
		System.out.println("OK");
	}
	
	protected static void startKafka() {
		String homeDirectory = __inject__(ValueHelper.class).returnOrThrowIfBlank("Kafka home", __inject__(SystemHelper.class).getProperty("kafka.home",Boolean.TRUE));
		System.out.print("Starting kafka("+homeDirectory+")...");
		OperatingSystemCommandExecutor operatingSystemCommandExecutor = __inject__(OperatingSystemCommandExecutor.class);
		operatingSystemCommandExecutor.getCommand(Boolean.TRUE).setCommand("kafka-server-start.bat "+homeDirectory+"\\config\\server.properties")
			.setIsTerminalStartable(Boolean.TRUE)
			.setIsTerminalShowable(Boolean.FALSE)
			.setTerminalTitle("Kafka")
			.setWorkingDirectory(homeDirectory)
			;
		operatingSystemCommandExecutor.setIsExecuteAsynchronously(Boolean.TRUE).execute();
		__inject__(TimeHelper.class).pause(1000l * 12);
		System.out.println("OK");
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
