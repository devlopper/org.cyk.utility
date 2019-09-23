package org.cyk.utility.stream.distributed;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractProducerConsumerImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements ProducerConsumer,Serializable {
	private static final long serialVersionUID = 1L;
	
	public static Boolean IS_ABLE = null;
	
	private Strings topics;
	private Class<?> keySerialisationClass;
	private Class<?> valueSerialisationClass;
	
	@Override
	protected void ____execute____() throws Exception {
		if(Boolean.TRUE.equals(__inject__(StreamDistributedHelper.class).getIsEnable())) {
			Strings topics = __injectValueHelper__().returnOrThrowIfBlank("topics", getTopics());
			__execute__(topics);	
		}else {
			__logWarning__(getClass().getSimpleName()+" : Cannot produce and consume because distributed stream functionnality is not enable.");
		}
	}
	
	protected abstract void __execute__(Strings topics) throws Exception;
	
	protected abstract void __prepare__(Strings topics);
	protected abstract void __close__();
	
	@Override
	public Strings getTopics() {
		return topics;
	}
	
	@Override
	public Strings getTopics(Boolean injectIfNull) {
		Strings topics = (Strings) __getInjectIfNull__(FIELD_TOPICS, injectIfNull);
		topics.setCollectionClass(Set.class);
		return topics;
	}
	
	@Override
	public ProducerConsumer setTopics(Strings topics) {
		this.topics = topics;
		return this;
	}
	
	@Override
	public ProducerConsumer addTopics(Collection<String> topics) {
		getTopics(Boolean.TRUE).add(topics);
		return this;
	}
	
	@Override
	public ProducerConsumer addTopics(String... topics) {
		getTopics(Boolean.TRUE).add(topics);
		return this;
	}
	
	@Override
	public Class<?> getValueSerialisationClass() {
		return valueSerialisationClass;
	}
	
	@Override
	public ProducerConsumer setValueSerialisationClass(Class<?> valueSerialisationClass) {
		this.valueSerialisationClass = valueSerialisationClass;
		return this;
	}
	
	@Override
	public Class<?> getKeySerialisationClass() {
		return keySerialisationClass;
	}
	
	@Override
	public ProducerConsumer setKeySerialisationClass(Class<?> keySerialisationClass) {
		this.keySerialisationClass = keySerialisationClass;
		return this;
	}
	
	/**/
	
	public static final String FIELD_TOPICS = "topics";

}
