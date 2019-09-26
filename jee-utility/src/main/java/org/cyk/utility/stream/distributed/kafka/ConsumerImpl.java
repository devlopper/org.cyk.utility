package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.time.Duration;

import javax.enterprise.context.Dependent;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.stream.distributed.AbstractConsumerImpl;

@Dependent
public class ConsumerImpl extends AbstractConsumerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private KafkaConsumer<Object, Object> kafkaConsumer;
		
	@Override
	protected void __subscribe__(Strings topics) {
		getProperties().setIfNull("group.id", ValueHelper.defaultToIfNull(getGroupIdentifier(),"consumer"));
		getProperties().setIfNull("enable.auto.commit", "false");
		getProperties().setIfNull("bootstrap.servers", "localhost:9092");
		
		Class<?> keySerialisationClass = getKeySerialisationClass();
		Class<?> valueSerialisationClass = getValueSerialisationClass();
		getProperties().setIfNull("key.deserializer", ValueHelper.defaultToIfNull(keySerialisationClass, StringDeserializer.class));
		getProperties().setIfNull("value.deserializer", ValueHelper.defaultToIfNull(valueSerialisationClass, StringDeserializer.class));
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
