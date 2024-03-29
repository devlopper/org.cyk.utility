package org.cyk.utility.stream.distributed;

import java.util.Collection;

import org.cyk.utility.function.FunctionWithPropertiesAsInputAndVoidAsOutput;
import org.cyk.utility.__kernel__.string.Strings;

public interface ProducerConsumer extends FunctionWithPropertiesAsInputAndVoidAsOutput {

	Strings getTopics();
	Strings getTopics(Boolean injectIfNull);
	ProducerConsumer setTopics(Strings topics);
	ProducerConsumer addTopics(Collection<String> topics);
	ProducerConsumer addTopics(String...topics);

	Class<?> getKeySerialisationClass();
	ProducerConsumer setKeySerialisationClass(Class<?> keySerialisationClass);
	
	Class<?> getValueSerialisationClass();
	ProducerConsumer setValueSerialisationClass(Class<?> valueSerialisationClass);
}
