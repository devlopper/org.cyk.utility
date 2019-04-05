package org.cyk.utility.stream.distributed;

import java.io.Serializable;

import org.cyk.utility.function.AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl;

public abstract class AbstractProducerCallbackImpl extends AbstractFunctionWithPropertiesAsInputAndVoidAsOutputImpl implements ProducerCallback,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Message message;
	private String topic;
	private Throwable throwable;
	
	@Override
	protected void ____execute____() throws Exception {
		Throwable throwable = getThrowable();
		if (throwable == null) {
			String topic = getTopic();
			Message message = getMessage();
			System.out.println("Message with key "+message.getKey()+" sent to topic <<"+topic+">>");
		} else {
			System.out.println(throwable);
		}
	}
	
	@Override
	public Message getMessage() {
		return message;
	}

	@Override
	public ProducerCallback setMessage(Message message) {
		this.message = message;
		return this;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public ProducerCallback setTopic(String topic) {
		this.topic = topic;
		return this;
	}
	
	@Override
	public Throwable getThrowable() {
		return throwable;
	}
	
	@Override
	public ProducerCallback setThrowable(Throwable throwable) {
		this.throwable = throwable;
		return this;
	}

}
