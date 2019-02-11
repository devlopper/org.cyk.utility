package org.cyk.utility.network.message.sender;

import java.util.Collection;

import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.message.SenderReader;

public interface Sender extends SenderReader {

	Message getMessage();
	Sender setMessage(Message message);
	
	Receivers getReceivers();
	Receivers getReceivers(Boolean injectIfNull);
	Sender setReceivers(Receivers receivers);
	Sender addReceivers(Collection<Receiver> receivers);
	Sender addReceivers(Receiver...receivers);
	
}
