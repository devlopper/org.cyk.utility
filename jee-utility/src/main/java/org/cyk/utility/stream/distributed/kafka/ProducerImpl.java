package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;
import org.cyk.utility.stream.distributed.AbstractProducerImpl;
import org.cyk.utility.stream.distributed.Message;
import org.cyk.utility.stream.distributed.ProducerCallback;
import org.cyk.utility.string.Strings;

public class ProducerImpl extends AbstractProducerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Producer<Object, Object> kafkaProducer;
	
	@Override
	protected void __prepare__(Strings topics) {
		getProperties().setIfNull("bootstrap.servers", "localhost:9092");
		getProperties().setIfNull("acks", "all");
		getProperties().setIfNull("key.serializer", StringSerializer.class);
		getProperties().setIfNull("value.serializer", StringSerializer.class);
		kafkaProducer = new KafkaProducer<>(getProperties().buildJavaUtilProperties());
	}
	
	@Override
	protected void __send__(String topic, Message message,Class<? extends ProducerCallback> callbackClass) {
		kafkaProducer.send(new ProducerRecord<Object, Object>(topic,message.getKey() ,message.getValue()), new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
				if(callbackClass!=null)
					__inject__(callbackClass).setTopic(topic).setMessage(message).setThrowable(exception).execute();
			}
		});
	}
	
	@Override
	protected void __close__() {
		kafkaProducer.close();
	}
	
}
