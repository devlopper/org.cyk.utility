package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Date;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;
import org.cyk.utility.stream.distributed.Consumer;
import org.cyk.utility.stream.distributed.ConsumerMessageProcessor;
import org.cyk.utility.stream.distributed.Producer;
import org.cyk.utility.system.OperatingSystemCommandExecutor;
import org.cyk.utility.test.arquillian.AbstractArquillianUnitTestWithDefaultDeployment;
import org.junit.Test;

public class PublisherConsumerUnitTest extends AbstractArquillianUnitTestWithDefaultDeployment {
	private static final long serialVersionUID = 1L;

	@Test
	public void publish() {
		//stopKafka();
		//startKafka();
		System.out.println("PRODUCE");
		__inject__(Producer.class).setTopic("test").setMessage("Time is "+new Date()).execute();
		System.out.println("CONSUME");
		__inject__(Consumer.class).setTopic("test").setMessageProcessorClass(ConsumerMessageProcessorImpl.class).execute();
		//stopKafka();
	}

	protected static void stopKafka() {
		System.out.println("STOP KAFKA");
		__inject__(OperatingSystemCommandExecutor.class).setCommand("kafka-server-stop.bat").setNumberOfMillisecondToWait(1000l*10).execute();
	}
	
	protected static void startKafka() {
		System.out.println("START KAFKA");
		__inject__(OperatingSystemCommandExecutor.class).setCommand("kafka-server-start.bat E:\\Servers\\Distributed\\Kafka\\2.12-2.1.1\\__default__\\config/server.properties")
			.setNumberOfMillisecondToWait(1000l*30).execute();
	}
	
	public static class ConsumerMessageProcessorImpl extends AbstractConsumerMessageProcessorImpl implements ConsumerMessageProcessor,Serializable {
		private static final long serialVersionUID = 1L;

		@Override
		protected void __process__(Object message) {
			ConsumerRecord<String, String> record = (ConsumerRecord<String, String>) message;
			System.out.printf("Message read. offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		}
		
	}
	
}
