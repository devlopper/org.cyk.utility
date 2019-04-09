package org.cyk.utility.stream.distributed.kafka.network;

import java.io.Serializable;

import org.cyk.utility.network.message.sender.SenderMail;
import org.cyk.utility.stream.distributed.AbstractConsumerMessageProcessorImpl;

public class ConsumerMessageProcessorImpl extends AbstractConsumerMessageProcessorImpl implements Serializable {
	private static final long serialVersionUID = 1L;

	@Override
	protected void __process__(org.cyk.utility.stream.distributed.Message message) {
		__inject__(SenderMail.class).setMessage((org.cyk.utility.network.message.Message) message.getValue()).execute();	
	}
	
}