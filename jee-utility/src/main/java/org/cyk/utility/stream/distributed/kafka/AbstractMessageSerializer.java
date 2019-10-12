package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public abstract class AbstractMessageSerializer<MESSAGE> extends AbstractObject implements org.apache.kafka.common.serialization.Serializer<MESSAGE>,Objectable,Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

	@Override
	public byte[] serialize(String string, MESSAGE message) {
		/*return __injectByQualifiersClasses__(ObjectToStringBuilder.class, JavaScriptObjectNotation.Class.class).setObject(message)
				.addFieldNamesStrings(__getFieldNamesStrings__()).execute().getOutput().getBytes();
		*/
		return null;
	}

	protected abstract String[] __getFieldNamesStrings__();
}