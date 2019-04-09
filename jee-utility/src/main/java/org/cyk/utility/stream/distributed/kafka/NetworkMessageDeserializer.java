package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.annotation.Json;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.object.ObjectFromStringBuilder;

public class NetworkMessageDeserializer extends AbstractObject implements org.apache.kafka.common.serialization.Deserializer<org.cyk.utility.network.message.Message>,Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

	@Override
	public org.cyk.utility.network.message.Message deserialize(String string, byte[] bytes) {
		return (org.cyk.utility.network.message.Message) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,
				Json.Class.class).setString(new String(bytes))
						.setKlass(__inject__(org.cyk.utility.network.message.Message.class).getClass())
						.addFieldNamesStrings(org.cyk.utility.network.message.Message.PROPERTY_TITLE,
								org.cyk.utility.network.message.Message.PROPERTY_BODY,org.cyk.utility.network.message.Message.PROPERTY_RECEIVERS)
						.execute().getOutput();
	}

}