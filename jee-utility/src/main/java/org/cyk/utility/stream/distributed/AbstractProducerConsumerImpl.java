package org.cyk.utility.stream.distributed;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;
import org.cyk.utility.string.Strings;

public abstract class AbstractProducerConsumerImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements ProducerConsumer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Strings topics;
	
	@Override
	protected void ____execute____() throws Exception {
		Strings topics = __injectValueHelper__().returnOrThrowIfBlank("topics", getTopics());
		__execute__(topics);
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
	
	/**/
	
	public static final String FIELD_TOPICS = "topics";

}
