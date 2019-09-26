package org.cyk.utility.stream.distributed;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import org.cyk.utility.__kernel__.string.Strings;
import org.cyk.utility.__kernel__.value.ValueHelper;

public abstract class AbstractConsumerImpl extends AbstractProducerConsumerImpl implements Consumer,Serializable {
	private static final long serialVersionUID = 1L;
	
	private Class<? extends ConsumerMessageProcessor> messageProcessorClass;
	private Long numberOfPollRequest,numberOfMessages;
	private Messages messages;
	private Boolean isKeepMessages;
	private Object groupIdentifier;
	
	@Override
	protected void __execute__(Strings topics) throws Exception {
		Class<? extends ConsumerMessageProcessor> messageProcessorClass = ValueHelper.returnOrThrowIfBlank("consumer message processor",getMessageProcessorClass());
		__prepare__(topics);
		
		Boolean isKeepMessages = ValueHelper.defaultToIfNull(getIsKeepMessages(), Boolean.FALSE);	
		Long numberOfPollRequest = getNumberOfPollRequest();
		Long numberOfMessages = getNumberOfMessages();
		
		while( (numberOfMessages == null || numberOfMessages > 0) && (numberOfPollRequest == null || numberOfPollRequest > 0)) {
			Iterable<?> iterable = __poll__(100l);
			if(numberOfPollRequest != null)
				numberOfPollRequest--;
			if(iterable.iterator().hasNext()) {
				Collection<Message> messages = new ArrayList<>();
				for (Object index : iterable) {
					Message message = __inject__(Message.class).setKey(__getMessageKey__(index)).setValue(__getMessageValue__(index));
					__inject__(messageProcessorClass).setMessage(message).execute();
					if(numberOfMessages != null)
						numberOfMessages--;
					messages.add(message);
				}
				__commit__();
				if(Boolean.TRUE.equals(isKeepMessages))
					addMessages(messages);
			}
		}
		__close__();
	}
	
	@Override
	protected void __prepare__(Strings topics) {
		__subscribe__(topics);
	}
	
	protected abstract void __subscribe__(Strings topics);
	protected abstract Iterable<?> __poll__(Long millisecond);
	protected abstract Object __getMessageKey__(Object record);
	protected abstract Object __getMessageValue__(Object record);
	protected abstract void __commit__();
	
	//protected abstract void __execute__(Strings topics,Class<? extends ConsumerMessageProcessor> messageProcessorClass,Boolean isKeepMessages);
	
	@Override
	public Consumer addTopics(String...topics) {
		return (Consumer) super.addTopics(topics);
	}
	
	@Override
	public Object getGroupIdentifier() {
		return groupIdentifier;
	}
	
	@Override
	public Consumer setGroupIdentifier(Object groupIdentifier) {
		this.groupIdentifier = groupIdentifier;
		return this;
	}
	
	@Override
	public Class<? extends ConsumerMessageProcessor> getMessageProcessorClass() {
		return messageProcessorClass;
	}
	
	@Override
	public Consumer setMessageProcessorClass(Class<? extends ConsumerMessageProcessor> messageProcessorClass) {
		this.messageProcessorClass = messageProcessorClass;
		return this;
	}
	
	@Override
	public Long getNumberOfPollRequest() {
		return numberOfPollRequest;
	}
	
	@Override
	public Consumer setNumberOfPollRequest(Long numberOfPollRequest) {
		this.numberOfPollRequest = numberOfPollRequest;
		return this;
	}
	@Override
	public Long getNumberOfMessages() {
		return numberOfMessages;
	}
	
	@Override
	public Consumer setNumberOfMessages(Long numberOfMessages) {
		this.numberOfMessages = numberOfMessages;
		return this;
	}
	
	@Override
	public Messages getMessages() {
		return messages;
	}
	
	@Override
	public Messages getMessages(Boolean injectIfNull) {
		if(messages == null && Boolean.TRUE.equals(injectIfNull))
			messages = __inject__(Messages.class);
		return messages;
	}

	@Override
	public Consumer setMessages(Messages messages) {
		this.messages = messages;
		return this;
	}
	
	@Override
	public Consumer addMessages(Collection<Message> messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public Consumer addMessages(Message... messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public Consumer addMessage(Object key, Object value) {
		addMessages(__inject__(Message.class).setKey(key).setValue(value));
		return this;
	}
	
	@Override
	public Boolean getIsKeepMessages() {
		return isKeepMessages;
	}
	
	@Override
	public Consumer setIsKeepMessages(Boolean isKeepMessages) {
		this.isKeepMessages = isKeepMessages;
		return this;
	}
	
	/**/
	
}
