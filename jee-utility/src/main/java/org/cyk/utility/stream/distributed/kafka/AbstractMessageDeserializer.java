package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;

public abstract class AbstractMessageDeserializer<MESSAGE> extends AbstractObject implements org.apache.kafka.common.serialization.Deserializer<MESSAGE>,Objectable, Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	public void close() {

	}

	@Override
	public void configure(Map<String, ?> arg0, boolean arg1) {

	}

	@Override
	public MESSAGE deserialize(String string, byte[] bytes) {
		return null;
		/*
		return (MESSAGE) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,
				JavaScriptObjectNotation.Class.class).setString(new String(bytes))
						.setKlass(__inject__(org.cyk.utility.__kernel__.klass.ClassHelper.getParameterAt(getClass(), 0)).getClass())
						.addFieldNamesStrings(__getFieldNamesStrings__()).execute().getOutput();
		*/
	}

	protected abstract String[] __getFieldNamesStrings__();
}