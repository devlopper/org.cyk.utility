package org.cyk.utility.network.message.reader;

import java.util.Collection;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.Messages;
import org.cyk.utility.network.message.SenderReader;

public interface Reader extends SenderReader {

	Object getFirstMessageIndex();
	Reader setFirstMessageIndex(Object firstMessageIndex);
	
	Object getNumberOfMessageToRead();
	Reader setNumberOfMessageToRead(Object numberOfMessageToRead);
	
	Messages getMessages();
	Messages getMessages(Boolean injectIfNull);
	Reader setMessages(Messages messages);
	Reader addMessages(Collection<Message> messages);
	Reader addMessages(Message...messages);
	Reader addMessage(String title,String body);
	
	/*
	Receivers getReceivers();
	Receivers getReceivers(Boolean injectIfNull);
	Sender setReceivers(Receivers receivers);
	Sender addReceivers(Collection<Receiver> receivers);
	Sender addReceivers(Receiver...receivers);
	*/
}
