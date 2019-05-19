package org.cyk.utility.stream.distributed.kafka;

import java.io.Serializable;
import java.util.Map;

import org.cyk.utility.__kernel__.annotation.JavaScriptObjectNotation;
import org.cyk.utility.__kernel__.object.dynamic.AbstractObject;
import org.cyk.utility.__kernel__.object.dynamic.Objectable;
import org.cyk.utility.clazz.ClassHelper;
import org.cyk.utility.object.ObjectFromStringBuilder;

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
		return (MESSAGE) __injectByQualifiersClasses__(ObjectFromStringBuilder.class,
				JavaScriptObjectNotation.Class.class).setString(new String(bytes))
						.setKlass(__inject__(__inject__(ClassHelper.class).getParameterAt(getClass(), 0, Object.class)).getClass())
						.addFieldNamesStrings(__getFieldNamesStrings__()).execute().getOutput();
	}

	protected abstract String[] __getFieldNamesStrings__();
}