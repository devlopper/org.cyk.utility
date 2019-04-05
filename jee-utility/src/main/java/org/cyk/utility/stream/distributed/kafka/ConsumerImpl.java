package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.time.Duration;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.cyk.utility.stream.distributed.AbstractConsumerImpl;
import org.cyk.utility.string.Strings;

public class ConsumerImpl extends AbstractConsumerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private KafkaConsumer<Object, Object> kafkaConsumer;
		
	@Override
	protected void __subscribe__(Strings topics) {
		kafkaConsumer = new KafkaConsumer<>(getProperties().buildJavaUtilProperties());
		kafkaConsumer.subscribe(topics.get());
	}
	
	@Override
	protected Iterable<?> __poll__(Long millisecond) {
		return kafkaConsumer.poll(Duration.ofMillis(millisecond));
	}
	
	@Override
	protected Object __getMessageKey__(Object record) {
		return ((ConsumerRecord<Object, Object>)record).key();
	}
	
	@Override
	protected Object __getMessageValue__(Object record) {
		return ((ConsumerRecord<Object, Object>)record).value();
	}
	
	@Override
	protected void __commit__() {
		kafkaConsumer.commitSync();
	}
	
	@Override
	protected void __close__() {
		kafkaConsumer.close();
	}
	
}
