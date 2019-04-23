package org.cyk.utility.network.message.reader;

import java.io.Serializable;
import java.util.Collection;

import org.cyk.utility.network.message.AbstractSenderReaderImpl;
import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.Messages;
import org.cyk.utility.network.protocol.Protocol;

public abstract class AbstractReadderImpl extends AbstractSenderReaderImpl implements Reader,Serializable {
	private static final long serialVersionUID = 6428760240698553361L;

	private Messages messages;
	private Object firstMessageIndex,numberOfMessageToRead;
	
	
	@Override
	protected void ______execute______(Protocol protocol) throws Exception {
		Long firstMessageIndex = __injectNumberHelper__().getLong(getFirstMessageIndex(), 0l);
		Long numberOfMessageToRead = __injectNumberHelper__().getLong(getNumberOfMessageToRead(), 1l);
		________execute________(protocol,firstMessageIndex,numberOfMessageToRead);
		System.out.println("Message read.");
	}
	
	protected abstract void ________execute________(Protocol protocol,Long firstMessageIndex,Long numberOfMessageToRead) throws Exception;
	
	@Override
	public Object getFirstMessageIndex() {
		return firstMessageIndex;
	}
	
	@Override
	public Reader setFirstMessageIndex(Object firstMessageIndex) {
		this.firstMessageIndex = firstMessageIndex;
		return this;
	}
	
	@Override
	public Object getNumberOfMessageToRead() {
		return numberOfMessageToRead;
	}
	
	@Override
	public Reader setNumberOfMessageToRead(Object numberOfMessageToRead) {
		this.numberOfMessageToRead = numberOfMessageToRead;
		return this;
	}
	
	@Override
	public Messages getMessages() {
		return messages;
	}
	
	@Override
	public Messages getMessages(Boolean injectIfNull) {
		return (Messages) __getInjectIfNull__(FIELD_MESSAGES, injectIfNull);
	}
	
	@Override
	public Reader setMessages(Messages messages) {
		this.messages = messages;
		return this;
	}
	
	@Override
	public Reader addMessages(Collection<Message> messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public Reader addMessages(Message... messages) {
		getMessages(Boolean.TRUE).add(messages);
		return this;
	}
	
	@Override
	public Reader addMessage(String title, String body) {
		addMessages(__inject__(Message.class).setTitle(title).setBody(body));
		return this;
	}
	
	/**/
	
	public static final String FIELD_MESSAGES = "messages";
}
