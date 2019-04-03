package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractProducerImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements Producer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private String topic;
	private Object message;
	
	@Override
	protected void ____execute____() throws Exception {
		String topic = __injectValueHelper__().returnOrThrowIfBlank("topic", getTopic());
		Object message = __injectValueHelper__().returnOrThrowIfBlank("message", getMessage());
		__execute__(topic,message);
	}
	
	@Override
	public String getTopic() {
		return topic;
	}
	@Override
	public Producer setTopic(String topic) {
		this.topic = topic;
		return this;
	}
	
	protected abstract void __execute__(String topic,Object message) throws Exception;
	
	@Override
	public Object getMessage() {
		return message;
	}

	@Override
	public Producer setMessage(Object message) {
		this.message = message;
		return this;
	}

}
