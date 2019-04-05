package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.cyk.utility.stream.distributed.AbstractProducerImpl;
import org.cyk.utility.stream.distributed.Message;
import org.cyk.utility.string.Strings;

public class ProducerImpl extends AbstractProducerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	private Producer<Object, Object> kafkaProducer;
	
	@Override
	protected void __prepare__(Strings topics) {
		kafkaProducer = new KafkaProducer<>(getProperties().buildJavaUtilProperties());
	}
	
	@Override
	protected void __send__(String topic, Message message) {
		kafkaProducer.send(new ProducerRecord<Object, Object>(topic,message.getKey() ,message.getValue()), new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
				if (exception == null) {
					System.out.println("Message sent to topic <<"+recordMetadata.topic()+">>");
				} else {
					System.out.println(exception);
				}
			}
		});
	}
	
	@Override
	protected void __close__() {
		kafkaProducer.close();
	}
	
}
