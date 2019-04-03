package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Properties;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.cyk.utility.stream.distributed.AbstractProducerImpl;

public class ProducerImpl extends AbstractProducerImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __execute__(String topic,Object message) throws Exception {
		Properties props = new Properties();
		props.put("bootstrap.servers", "localhost:9092");
		props.put("acks", "all");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		Producer<String, String> producer = new KafkaProducer<>(props);

		String messageAsString = message.toString();

		producer.send(new ProducerRecord<String, String>(topic, messageAsString), new Callback() {
			@Override
			public void onCompletion(RecordMetadata recordMetadata, Exception exception) {
				if (exception == null) {
					System.out.println("Message sent to topic <<"+recordMetadata.topic()+">>");
				} else {
					System.out.println(exception);
				}
			}
		});
		producer.close();
	}

}
