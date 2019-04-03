package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.cyk.utility.stream.distributed.AbstractConsumerImpl;
import org.cyk.utility.stream.distributed.ConsumerMessageProcessor;

public class ConsumerImpl extends AbstractConsumerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(String topic,Class<? extends ConsumerMessageProcessor> messageProcessorClass) {
		Properties props = new Properties();
		props.setProperty("bootstrap.servers", "localhost:9092");
		props.setProperty("group.id", "test");
		props.setProperty("enable.auto.commit", "true");
		props.setProperty("auto.commit.interval.ms", "1000");
		props.setProperty("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		@SuppressWarnings("resource")
		KafkaConsumer<String, String> consumer = new KafkaConsumer<>(props);
		consumer.subscribe(Arrays.asList(topic));
		
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
			for (ConsumerRecord<String, String> record : records)
				__inject__(messageProcessorClass).setMessage(record).execute();
				//System.out.printf("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
		}
	}
	
}
