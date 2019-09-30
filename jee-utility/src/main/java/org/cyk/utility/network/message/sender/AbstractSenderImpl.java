package org.cyk.utility.network.message.sender;

import java.io.Serializable;

import org.cyk.utility.__kernel__.value.ValueHelper;
import org.cyk.utility.network.message.AbstractSenderReaderImpl;
import org.cyk.utility.network.message.Message;
import org.cyk.utility.network.protocol.Protocol;

public abstract class AbstractSenderImpl extends AbstractSenderReaderImpl implements Sender,Serializable {
	private static final long serialVersionUID = 6428760240698553361L;

	private Message message;
	//private Receivers receivers;
	
	@Override
	protected void ______execute______(Protocol protocol) throws Exception {
		Message message = getMessage();
		if(message == null)
			throw new RuntimeException("message sender : message is required");
		ValueHelper.throwIfBlank("message sender : receivers", message.getReceivers());
		________execute________(protocol,message);
		System.out.println("Message sent.");
	}
	
	protected abstract void ________execute________(Protocol protocol,Message message) throws Exception;
	
	@Override
	public Message getMessage() {
		return message;
	}
	
	@Override
	public Sender setMessage(Message message) {
		this.message = message;
		return this;
	}
	/*
	@Override
	public Receivers getReceivers() {
		return receivers;
	}
	
	@Override
	public Sender setReceivers(Receivers receivers) {
		this.receivers = receivers;
		return this;
	}
	
	@Override
	public Receivers getReceivers(Boolean injectIfNull) {
		return (Receivers) __getInjectIfNull__(FIELD_RECEIVERS, injectIfNull);
	}
	
	@Override
	public Sender addReceivers(Collection<Receiver> receivers) {
		getReceivers(Boolean.TRUE).add(receivers);
		return this;
	}
	
	@Override
	public Sender addReceivers(Receiver... receivers) {
		getReceivers(Boolean.TRUE).add(receivers);
		return this;
	}
	*/
	/**/
	
	//public static final String FIELD_RECEIVERS = "receivers";
}
