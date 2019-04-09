package org.cyk.utility.stream.distributed;

import java.util.Collection;

public interface Consumer extends ProducerConsumer {

	Object getGroupIdentifier();
	Consumer setGroupIdentifier(Object groupIdentifier);
	
	Class<? extends ConsumerMessageProcessor> getMessageProcessorClass();
	Consumer setMessageProcessorClass(Class<? extends ConsumerMessageProcessor> messageProcessorClass);
	
	Long getNumberOfPollRequest();
	Consumer setNumberOfPollRequest(Long numberOfPollRequest);
	
	Long getNumberOfMessages();
	Consumer setNumberOfMessages(Long numberOfMessages);
	
	Boolean getIsKeepMessages();
	Consumer setIsKeepMessages(Boolean isKeepMessages);
	
	Messages getMessages();
	Messages getMessages(Boolean injectIfNull);
	Consumer setMessages(Messages messages);
	Consumer addMessages(Collection<Message> messages);
	Consumer addMessages(Message...messages);
	Consumer addMessage(Object key,Object value);
	
	@Override Consumer addTopics(String... topics);
}
